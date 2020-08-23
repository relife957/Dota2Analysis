package com.wangyi

import org.apache.spark.broadcast.Broadcast
import org.apache.spark.rdd.RDD
import org.apache.spark.storage.StorageLevel
import org.apache.spark.{HashPartitioner, Partitioner, SparkContext}

import scala.collection.mutable

/**
  * @author wangyi
  * @create 2018-12-13 12:09 PM
  **/
class FastApriori(private var minSupport: Double, private var numPartitions: Int) extends Serializable {


  def setMinSupport(minSupport: Double): this.type = {
    this.minSupport = minSupport
    this
  }

  def setNumPartitions(numPartitions: Int): this.type = {
    this.numPartitions = numPartitions
    this
  }

  def run(
           sc: SparkContext,
           data: RDD[Array[String]]
         ): (Array[(Set[Int], Int)], mutable.HashMap[String, Int], Array[String]) = {
    val numParts = if (numPartitions > 0) numPartitions else data.partitions.length
    val partitioner = new HashPartitioner(numParts)

    val count = data.count()
    val minCount = math.ceil(minSupport * count).toInt    //将数字向上取整,代表最小支持度
    val (freqItemsWithCount, freqItems, itemToRank, newData, countMap, totalCount) = genFreqItems(sc, data, minCount, partitioner)
    val freqItemsets = genFreqItemsets(sc, newData, countMap, totalCount, minCount, freqItems) ++ freqItemsWithCount

    (freqItemsets, itemToRank, freqItems)
  }


  //
  private def genFreqItems(sc: SparkContext,
                            data: RDD[Array[String]],
                            minCount: Long,
                            partitioner: Partitioner
                          ):(Array[(Set[Int], Int)], Array[String], mutable.HashMap[String, Int],
		  				RDD[(Int, Array[Int])], collection.Map[Int, Int], Int) = {

    val freqItemsSet = mutable.HashSet.empty[String]                //可变的空集合
    val itemToRank = mutable.HashMap.empty[String, Int]
    val tmp = data.map(_.map(_.trim)).flatMap(_.map((_, 1)))         //计算所有英雄的出现次数,然后将出现次数小于mincount的筛掉
      .reduceByKey(partitioner, _ + _)
      .filter(_._2 >= minCount)
      .collect()

    val freqItems = tmp.sortBy(-_._2).map(_._1)      //将所有英雄根据选择次数降序排序,然后在(英雄编号,英雄被选择次数)只取英雄编号
    freqItems.foreach(freqItemsSet.add)              //将freqItems的内容加入到set中
    freqItems.zipWithIndex.foreach(x => itemToRank.put(x._1, x._2)) //将英雄编号和选择次数的排名存放在itemToRank中(英雄编号,选择次数排名)

    val freqItemsBV = sc.broadcast(freqItemsSet)     //将freqItemsSet设置为公共变量
    val itemToRankBV = sc.broadcast(itemToRank)

    val temp = data.map(_.map(_.trim)).map{x =>
      val freqItems = freqItemsBV.value
      val itemToRank = itemToRankBV.value
      (x.filter(freqItems.contains).map(itemToRank).toSet, 1)     //每一次选择记录中的英雄对应的英雄排名,组合一
    }.filter(x => x._1.size > 1)
      .reduceByKey(_ + _)
      .map(x => (x._1.toArray, x._2))                             //组合一的数组形式和这个组合的出现次数,构成组合二
      .zipWithIndex()
      .map(x => (x._2.toInt, x._1))                               //组合二和这个组合的序号,序号在前,
      .persist(StorageLevel.MEMORY_AND_DISK_SER)

    val countMap = temp.map(x => (x._1, x._2._2)).collectAsMap()   //组合二序号和这个组合一的出现次数
    val newData = temp.map(x => (x._1, x._2._1)).persist(StorageLevel.MEMORY_AND_DISK_SER)  //组合二的序号和组合一
    val totalCount = newData.count().toInt                        //相当于temp的count
    data.unpersist()
    temp.unpersist()

    val freqItemsWithCount = tmp.map(x => (Set[Int](itemToRank(x._1)), x._2))    //英雄的选择排名和英雄的选择次数

    (freqItemsWithCount, freqItems,         itemToRank,      newData,                        countMap, totalCount)
  //选择排名和次数          英雄编号按选择排名,  英雄编号和选择排名, 每一次选择记录中的英雄对应的选择排名,
  }

  private def genFreqItemsets(
                               sc: SparkContext,
                               newData: RDD[(Int, Array[Int])],
                               countMap: collection.Map[Int, Int],
                               totalCount: Int,
                               minCount: Int,
                               freqItems: Array[String]
                             ): Array[(Set[Int], Int)] = {
    val freqItemsSize = freqItems.length
    val freqItemsTrans = getFreqItemsTrans(newData, freqItems, totalCount)
    val freqItemsBV = sc.broadcast(freqItems)
    val countMapBV = sc.broadcast(countMap)
    val freqItemsTransBV = sc.broadcast(freqItemsTrans.toMap)
    val freqItemsets = mutable.ListBuffer.empty[(Set[Int], Int)]

    var time = System.currentTimeMillis()
	  										//组合二和对应组合一出现的次数    每个英雄和其在所有组合中是否出现		英雄数目			组合的数目(去重)  最小支持度
    val tuplesWithCount = genTwoFreqItems(sc, countMapBV, 				freqItemsTransBV, 				freqItemsSize, totalCount,     minCount)
    freqItemsets ++= tuplesWithCount
    var kItems = tuplesWithCount.map(_._1)
    println("==== 2 freq items " + kItems.length)
    println("==== Use Time 2 items " + (System.currentTimeMillis() - time))

    var k = 3
    while (k<=5) {                             //K等于5是因为最大的频繁集长度就是5
      time = System.currentTimeMillis()
      val candidates = genCandidates(sc, kItems, freqItemsSize)
      println("==== " + k + " candidate items " + candidates.length)
      val kItemsWithCount = genNextFreqItemsets(sc, candidates, countMapBV, freqItemsTransBV, freqItemsSize, totalCount, minCount)
      freqItemsets ++= kItemsWithCount
      kItems = kItemsWithCount.map(_._1)
      println("==== " + k + " freq items " + kItems.length)
      println("==== Use Time " + k + " items " + (System.currentTimeMillis() - time))
      k += 1
    }

    freqItemsBV.unpersist()
    countMapBV.unpersist()
    freqItemsTransBV.unpersist()

    println("==== Total freq items sets " + freqItemsets.toList.length)

    freqItemsets.toArray         //所有的频繁集
  }
	
	/**
	  * 这里的主要思路是,对于每一个k项频繁集xs,如果想求出k+1项频繁集,我们不是用k项和每一个1项频繁集做运算,然后统计支持度,而是先在k项频繁集中,
	  * 1:找到k项中的最大值,然后求出一个max+1--freqItemsSize的集合zs,2:使xs中的每一个k-1项子集和zs中的每一个元素y构成的一个k项集合,
	  * 然后判断这个集合是不是频繁集,如果是就将y加入到另一个集合ys中,最后返回xs和ys (以上是getcandidate方法的作用),这杨做的目的就是
	  * 减少两个0-1数组做运算的次数,虽然xs和ys中的元素组成的k+1项组合就是频繁的,但被过滤掉的就肯定不是频繁集,根据Apriori定理,频繁集的子集都是频繁集,
	  * 也就是说,如果某k项频繁集a不是频繁集,那么任何元素和a的组合得到的k+1项频繁集b就肯定不是频繁集.
	  * @return
	  */
  private def genNextFreqItemsets(sc: SparkContext,
                                  candidates: Array[(Set[Int], Array[Int])],
                                  countMapBV: Broadcast[collection.Map[Int, Int]],
                                  freqItemsTransBV: Broadcast[Map[Int, Array[Boolean]]],
                                  freqItemsSize: Int,
                                  totalCount: Int,
                                  minCount: Int): Array[(Set[Int], Int)] = {

    val res = sc.parallelize(candidates).flatMap { case (subSet, items) =>
      val countMap = countMapBV.value
      val freqItemsTrans = freqItemsTransBV.value                     //每个英雄在其每一个trans(事务)中是否出现
      val common = subSet.toArray.map(freqItemsTrans(_))
      val commonArray = new Array[Boolean](totalCount)
      Range(0, totalCount).foreach(i => commonArray(i) = logicalAnd(i, common))

      val right = items.map { i =>
        val iArray = freqItemsTrans(i)
        val indexes = Range(0, totalCount).filter(x => commonArray(x) && iArray(x))
        var count = 0
        indexes.foreach(count += countMap(_))
        if (count >= minCount) (subSet + i, count)
        else (Set.empty[Int], 0)
      }.filter(_._2 != 0)

      right
    }.collect()

    res
  }

  private def logicalAnd(index: Int, items: Array[Array[Boolean]]): Boolean = {
    items.foreach(x => if (!x(index)) return false)
    true
  }

	/*
	以三频繁集为例,返回的是每一个二项组合,例如(2,3),会返回(2,x),(3,x)[3< x < freqItemsSize]这些二项组合中频繁的那部分中的x组成的数组
	 */
  private def genCandidates(
                             sc: SparkContext,
                             kItems: Array[Set[Int]],
                             freqItemsSize: Int
                           ): Array[(Set[Int], Array[Int])] = {
    val kItemsSetBV = sc.broadcast(kItems.toSet)
    val candidates = sc.parallelize(kItems)                //rdd
	  .map{ x =>
      val kItemsSet = kItemsSetBV.value
      val candidates = mutable.HashSet.empty[Int]
      Range(x.max + 1, freqItemsSize).foreach(candidates.add)
      candidates --= x   //?
      val temp = x.toArray
      val len = temp.length
      var i = 0
      while (candidates.nonEmpty && i < len) {
        val subSet = x - temp(i)
        candidates.toArray.foreach{y =>           //去除 因为频繁度小于最小置信而被筛掉 的集合,kItemset是所有频繁的二项集,
          if (!kItemsSet.contains(subSet + y))    //这里体现了Apriori算法定理,所有频繁集的子集都是频发集
            candidates -= y
        }
        i += 1
      }
      (x, candidates.toArray)
    }.filter(_._2.nonEmpty).collect()

    candidates
  }
//每一个频繁一项集和它对应的0-1数组(即是否在记录中出现过)
//返回一个元素是一个二元元组的数组,这个元组中的第一个元素是一个下标,这个下标代表着这个这是排名第几的英雄,
// 然后第二个元素是一个数组,这个数组的每一位代表这个英雄是否在本次选择记录中
  private def getFreqItemsTrans(newData: RDD[(Int, Array[Int])],
                                freqItems: Array[String],
                                totalCount: Int
                               ): Array[(Int, Array[Boolean])] = {
    val itemsWithTrans = freqItems.indices
      .map(x => (x, newData.filter(y => arrayContains(y._2, x)).map(_._1).collect()))
      .toArray
      .map{x =>
        val array = new Array[Boolean](totalCount)
        x._2.foreach(array(_) = true)
        (x._1, array)
      }
    newData.unpersist()

    itemsWithTrans
  }

  private def genTwoFreqItems(//组合二和对应组合一出现的次数    每个英雄的排名和其在所有组合中是否出现		英雄数目			组合的数目(去重)  最小支持度
                               sc: SparkContext,
                               countMapBV: Broadcast[collection.Map[Int, Int]],		//组合二序号和对应组合一出现的次数
                               freqItemsTransBV: Broadcast[Map[Int, Array[Boolean]]],//每个英雄和其在所有组合中是否出现
                               freqItemsSize: Int,									//英雄数目
                               totalCount: Int,										//组合的数目(去重)
                               minCount: Int										//最小支持度
                             ): Array[(Set[Int], Int)] = {
    val tuples = mutable.ListBuffer.empty[(Int, Int)]

    //获取所有二项集    此处的i和j是指对应英雄的排名
    for (i <- 0 until freqItemsSize - 1)
      for (j <- i + 1 until freqItemsSize)
        tuples.append((i, j))

    println("==== 2 candidates items " + tuples.length)

    val res = sc.parallelize(tuples.toList).map{t =>
      val countMap = countMapBV.value
      val freqItemsTrans = freqItemsTransBV.value
      val x = freqItemsTrans(t._1)
      val y = freqItemsTrans(t._2)
      val indexes = Range(0, totalCount).filter(i => x(i) && y(i)).toArray          //过滤掉了为0的值,indexes只包含
      var count = 0
      indexes.foreach(count += countMap(_))                     //count=这个(i,j)英雄组合出现的次数
      if (count >= minCount) (Set[Int](t._1, t._2), count)      //筛选
      else (Set.empty[Int], 0)
    }.filter(_._2 != 0).collect()

    res
  }

  private def arrayContains(transaction: Array[Int], item: Int): Boolean = {
    transaction.foreach(x => if (x == item) return true)
    false
  }

}
