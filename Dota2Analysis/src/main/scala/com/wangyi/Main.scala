package com.wangyi

/**
  * @author wangyi
  * @create 2018-12-13 12:09 PM
  **/
import org.apache.spark.sql.SparkSession

object Main {
	def main(args: Array[String]) {
		val session = SparkSession.builder()
				//			  .master("local[*]")                    //本地测试
				.appName("Dota2Analysis")
				.getOrCreate()
		session.conf.set("spark.numShufflePartitions", 500)
		session.conf.set("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
		session.conf.set("spark.speculation", "true")
		
		
//		//购买信息
//		val purchase = new Purchase(session)
//		purchase.run(args(0), args(1))
		//	  purchase.run("./src/main/resources/","")        //本地测试
		
		
		
//		top5
		val sc = session.sparkContext
		val minSupport = 0.0625
		val input = args(0)
		val output = args(1)
		val dataRDD = LinkPostgresql.readAsRDD(sc, input)
	
		val time1 = System.currentTimeMillis()
		val (freqItemsets, itemToRank, freqItems) =
		  new FastApriori(minSupport, sc.defaultParallelism).run(sc, dataRDD)
		LinkPostgresql.saveFreqItemset(sc, output, freqItemsets, freqItems)
		println("==== Total time for get freqItemsets " + (System.currentTimeMillis() - time1))
	
		val time2 = System.currentTimeMillis()
		val top5 = new OutputRules(freqItemsets,freqItems,itemToRank).run(sc)
		LinkPostgresql.saveTOP5(sc,output,top5,freqItems)
		println("==== Total time for get recommends " + (System.currentTimeMillis() - time2))
	}

		
		
	
}

