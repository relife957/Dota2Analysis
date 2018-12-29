package com.wangyi

import org.apache.spark.SparkContext

import scala.collection.mutable
/**
  * @author wangyi
  * @create  2018-12-13 12:09 PM
  **/
class OutputRules(
				  private val freqItemset: Array[(Set[Int], Int)],  //从一项频繁集开始的所有频繁集(频繁集的内容是对应商品的排名)
				  private val freqItems: Array[String],
				  private val itemToRank: mutable.HashMap[String, Int] //商品编号和商品排名
		  ) extends Serializable {

	def run(sc: SparkContext)= {
		val grouped = freqItemset.groupBy(_._1.size)
		val rules1 = genRules(sc, grouped)
		val rules = rules1.map(x=>{
			val top5 = x._2.toArray.sortWith(associationRulesSort).take(5).map(x => x._2)
			(x._1,top5)
		})
		rules
	}

	/**
	  * 按置信度由高到低排序,如果置信度相同,就按商品编号排
	  * @param x
	  * @param y
	  * @return
	  */
	def associationRulesSort(x: (Set[Int], Int, Double), y: (Set[Int], Int, Double)): Boolean = {
		if (x._3 > y._3) true
		else if (x._3 < y._3) false
		else freqItems(x._2).trim().toInt < freqItems(y._2).trim().toInt
	}
	def genRules(
				sc: SparkContext,
				grouped: Map[Int, Array[(Set[Int], Int)]]   //int是一个下标,array是一个包含(频繁集,频繁数)元组的数组,其中array中所有频繁集的长度相等,长度为int
				)= {
		val supersets = freqItemset.filter(_._1.size != 1) //筛除掉长度为1的频繁集
		val freqItemsetBV = sc.broadcast(grouped)

		val rules = sc.parallelize(supersets).flatMap { case (superset, count) => //从2频繁集开始
			val subsets = freqItemsetBV.value(superset.size - 1) //是当前待推荐的购买组合的长度-1的长度的频繁集,例如:当前待推荐的购买组合长度为三,subsets就是所有长度为2的频繁集
		val complements = mutable.ListBuffer.empty[(Set[Int], Int, Double)]
			val targets = mutable.HashSet.empty[Set[Int]] //每一个频繁集去除掉其中一个元素后的所有集合
			superset.foreach(i => targets.add(superset - i))
			var i = 0
			val subsetsLen = subsets.length
			while (targets.nonEmpty && i < subsetsLen) {
				val subset = subsets(i) //遍历subsets
				if (targets contains subset._1) { //如果n项频繁集中包含n+1项的去一项子集
					complements.append((subset._1, (superset -- subset._1).head, count.toDouble / subset._2)) //(n项频繁集,n+1想比n项多出的那个元素,n+1项频繁集的计数/n项频繁集的计数)
					targets.remove(subset._1)
				}
				i += 1
			}
			complements.toList
		}.groupBy(_._1).collectAsMap()
		rules
	}
}