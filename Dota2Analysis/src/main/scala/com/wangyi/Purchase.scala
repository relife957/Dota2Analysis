package com.wangyi

import org.apache.spark.sql.{DataFrame, SparkSession}

/**
  * @author wangyi
  * @create 2018-12-17 10:11 PM
  **/
class Purchase(session:SparkSession) {
	
	import org.apache.spark.sql.functions._
	import session.implicits._
	
	/**
	  * 传入时间字符串,然后判断此时是游戏的什么时间段
	  * 1 : 出门
	  * 2 : 前期
	  * 3 : 中期
	  * 4 : 后期
	  */
	val classify = udf((time:String)=>{
		val temp = time.toInt
		if(temp < 0){
			1
		}else if(temp < 900){
			2
		}else if(temp < 2100){
			3
		}else{
			4
		}
	})
	
	val toInt = udf((hero_id:String)=>{
		hero_id.toInt
	})
	
	def run(input: String,output:String)={
		
		val df = LinkPostgresql.read_csv(session,input+"purchase.csv")
        		.withColumn("hero_id",toInt(col("hero_id")))
				.repartition(col("hero_id"))
		//统计每个英雄的出装
		val stats = stat(df)
		LinkPostgresql.write_into_db(stats,"api.purchase","append")
		
	}
	
	/**
	  * 统计每个英雄的不同时间段的被选次数
	  * @param df
	  * @return
	  */
	def stat(df:DataFrame)={
		df.groupBy("hero_id","time","item")
				.count()
				.withColumn("kind",classify(col("time")))
				.drop("time")
				.groupBy("hero_id","item","kind")
        		.sum("count")
        		.filter("sum(count)>10")
        		.withColumnRenamed("sum(count)","count")
	}
	
}
