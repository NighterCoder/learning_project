package e2e_exactly_once;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.flink.api.java.tuple.Tuple3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * 模拟远程存储或者本地业务为了实现端到端精准一次的载体
 *
 * Created by 凌战 on 2020/9/8
 */
public class TransactionDB {

  private static Logger LOG= LoggerFactory.getLogger(TransactionDB.class);
  private final Map<String, List<Tuple3<String,Long,String>>> transactionRecords= new HashMap<>();


  private static TransactionDB instance;

  // 懒汉单例模式
  public static synchronized TransactionDB getInstance(){
    if (instance==null){
      instance=new TransactionDB();
    }
    return instance;
  }

  private TransactionDB(){

  }

  /**
   * 创建当前事务的临时存储
   * 临时存储需要记录事务id
   *
   * @param transactionId 事务id
   */
  public TransactionTable createTable(String transactionId){
    LOG.error(String.format("Create Table for current transaction...[%s]", transactionId));
    transactionRecords.put(transactionId, new ArrayList<>());
    return new TransactionTable(transactionId);
  }

  /**
   * 两阶段提交,将临时存储的数据获取,存储到真正外部数据库
   * @param transactionId 事务id
   */
  public void secondPhase(String transactionId){
    LOG.error(String.format("Persist current transaction...[%s] records...", transactionId));
    List<Tuple3<String, Long, String>> content = transactionRecords.get(transactionId);
    if(null == content){
      return;
    }
    content.forEach(this::print);
    // 这个清除工作很重要,因为Notify和Recovery都会调用
    removeTable("Notify or Recovery",transactionId);
    LOG.error(String.format("Persist current transaction...[%s] records...[SUCCESS]", transactionId));
  }

  private void print(Tuple3<String, Long, String> record){
    LOG.error(record.toString());
  }

  // 一阶段提交
  public void firstPhase(String transactionId,List<Tuple3<String, Long, String>> values){
    List<Tuple3<String, Long, String>> content = transactionRecords.get(transactionId);
    content.addAll(values);
  }

  // 删除临时存储数据
  public void removeTable(String who, String transactionId){
    LOG.error(String.format("[%s], Remove table for transaction...[%s]", who, transactionId));
    transactionRecords.remove(transactionId);
  }


}
