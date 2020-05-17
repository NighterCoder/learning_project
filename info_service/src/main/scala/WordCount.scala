import com.tiangou.info_service.flink_example.WordCountData
import org.apache.flink.api.java.utils.ParameterTool
import org.apache.flink.api.scala._


/**
  *
  * Created by 凌战 on 2019/2/10.
  */
object WordCount {

  def main(args: Array[String]) {

    //1、获取命令行参数
    val params: ParameterTool = ParameterTool.fromArgs(args)


    // 2、设置运行环境
    // 如果本地运行，启动local;
    // 如果打包成jar，放到yarn上，则创建集群环境
    // 这是自适应的，固定不变
    val env = ExecutionEnvironment.getExecutionEnvironment

    // make parameters available in the web interface
    env.getConfig.setGlobalJobParameters(params)
    val text =
      if (params.has("input")) {
        env.readTextFile(params.get("input"))
      } else {
        println("Executing WordCount example with default input data set.")
        println("Use --input to specify file input.")
        //直接从一个集合当中去读取数据
        env.fromCollection(WordCountData.WORDS)
      }

    //flatMap是一进n出
    //map是n进n出
    val counts = text.flatMap { _.toLowerCase.split("\\W+") filter { _.nonEmpty } }
      .map { (_, 1) }
      //传入索引号
      .groupBy(0)
      .sum(1)

    val counts1=text.flatMap(_.toLowerCase.split("\\W+").filter(_.nonEmpty))
      .map(s=>(s,1))
      .groupBy(0)
      .sum(1)



    if (params.has("output")) {
      counts.writeAsCsv(params.get("output"), "\n", " ")
      env.execute("Scala WordCount Example")
    } else {
      println("Printing result to stdout. Use --output to specify output path.")
      counts.print()
      //counts1.print()
    }

  }
}

