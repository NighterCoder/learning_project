import org.apache.flink.api.common.functions.FlatMapFunction
import org.apache.flink.api.scala._
import org.apache.flink.util.Collector


/**
  * 各种算子操作
  * Created by 凌战 on 2019/2/11.
  */
object TransformationDemo {
  def main(args: Array[String]): Unit = {

  /*  val env=ExecutionEnvironment.getExecutionEnvironment

    env.readTextFile()

    val dataSet:DataSet[Int]=env.fromElements(1,2,3,4)

    val flatMapDataSet=dataSet.flatMap(new FlatMapFunction[Int,Int] {
      override def flatMap(t: Int, collector: Collector[Int]): Unit = {
        if(t%2==0) {
          collector.collect(t * 2)
        }
      }
    })

    flatMapDataSet.print()*/

  }




}

