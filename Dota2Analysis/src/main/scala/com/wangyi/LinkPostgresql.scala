package com.wangyi

import java.util.Properties

import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.storage.StorageLevel

/**
  *
  * @author wangyi
  * @create 2018-11-26 11:03 PM
  **/
object LinkPostgresql {
	val connectionProperties = new Properties()
	connectionProperties.put("user", "postgres")
	connectionProperties.put("password", "123")
	connectionProperties.put("driver", "org.postgresql.Driver")
	
	/**
	  * 从数据库中读出dataframe
	  * @param spark
	  * @param tableName
	  * @param predicates
	  * @return
	  */
	def get_table_df(spark:SparkSession,
					 tableName: String,
					 predicates: Array[String] = Array()
					)={
		val table_df = spark.read.
				jdbc("jdbc:postgresql://10.10.65.176:5432/postgres", tableName,predicates, connectionProperties)
		table_df
	}
	
	/**
	  * 存dataframe到数据库
	  * @param df
	  * @param tableName
	  * @param mode
	  */
	def write_into_db(df:DataFrame,
					  tableName:String,
					  mode:String): Unit ={
		df.write.mode(mode)
				.jdbc("jdbc:postgresql://192.168.1.183:5432/postgres", tableName, connectionProperties)
	}
	
	/**
	  * 读csv文件成dataframe
	  * @param session
	  * @param path
	  * @return
	  */
	def read_csv(session: SparkSession,
				 path: String):DataFrame={
		val df = session.read.format("csv")
				.option("header","true")
        				.load(path)
		df
	}
	
	/**
	  * 写dataframe到csv
	  * @param ds
	  * @param path
	  * @param order
	  */
	def write_csv(ds: DataFrame,
				  path:String,
				  order:String): Unit ={
		ds.orderBy(order).repartition(1).write.csv(path)
	}
	
	/**
	  * 读取.dat文件
	  * @param session
	  * @param path
	  * @return
	  */
	def read_dat(session: SparkSession,
				 path:String) ={
		val rdd = session.sparkContext.textFile(path,session.sparkContext.defaultParallelism * 4)
        		.map(x=>{
					x.substring(1,x.length-1).split(",",2)
				})
		rdd
	}
	
	/**
	  * top5
	  * @param sc
	  * @param output
	  * @param top5
	  * @param freqItems
	  */
	def saveTOP5(
						sc: SparkContext,
						output: String,
						top5:collection.Map[Set[Int],Array[Int]],
						freqItems: Array[String]
				):Unit={
		sc.parallelize(top5.toSeq.map(x=>{
			(x._1.map(freqItems(_)).mkString(" "),x._2.map(freqItems(_)).mkString(" "))
		})).repartition(1).saveAsTextFile(output+"top5")
	}
	
	def readAsRDD(sc: SparkContext,
				  path: String): RDD[Array[String]] = {
		val dataRDD = sc.textFile(path + "data.dat", sc.defaultParallelism * 4).filter(!_.equals("0")).map(_.trim().split(","))
				.persist(StorageLevel.MEMORY_AND_DISK_SER)
		//    val userRDD = sc.textFile(path + "U.dat", sc.defaultParallelism * 4).map(_.trim().split("\\s+"))
		//      .persist(StorageLevel.MEMORY_AND_DISK_SER)
		
		dataRDD
	}
	
	def saveFreqItemset(
							   sc: SparkContext,
							   output: String,
							   freqItemsets: Array[(Set[Int], Int)],
							   freqItems: Array[String]
					   ): Unit = {
		val freqItemsBV = sc.broadcast(freqItems)
		sc.parallelize(freqItemsets).map { f =>
			val freqItemset = freqItemsBV.value
			f._1.toArray.sortBy(-_).map(freqItemset(_)).mkString(" ")
		}.repartition(1).sortBy(x => x).saveAsTextFile(output + "freqItemset")
		freqItemsBV.unpersist()
	}
	
	def main(args: Array[String]): Unit = {
//		val session = SparkSession.builder()
//				.master("local[*]")
//				.appName("Dota2Analysis")
//				.getOrCreate()
//
//		val df = read_csv(session,"./src/main/resources/temp.csv")
//		df.show(5)
		print(-5 toInt)
	}
	
}
