package custom_source

import org.apache.flink.streaming.api.functions.source.{RichParallelSourceFunction, SourceFunction}

/**
  *
  * Created by 凌战 on 2019/2/11.
  */
class SimpleSourceFunction extends RichParallelSourceFunction[Long]{

  var num=0L
  var isRunning:Boolean=true



  override def run(ctx: SourceFunction.SourceContext[Long]): Unit = {

    while (isRunning){
      ctx.collect(num)
      num=num+1
      Thread.sleep(10000)
    }


  }

  override def cancel(): Unit = {
    isRunning=false
  }
}
