package e2e_exactly_once;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.apache.flink.api.java.tuple.Tuple3;

/**
 * Created by 凌战 on 2020/9/8
 */
public class TransactionTable implements Serializable {

  // 不会序列化
  private transient TransactionDB db;

  private final String transactionId;

  private final List<Tuple3<String, Long, String>> buffer = new ArrayList<>();

  public TransactionTable(String transactionId){
    this.transactionId=transactionId;
  }

  public String getTransactionId() {
    return transactionId;
  }


  public TransactionTable insert(Tuple3<String,Long,String> value){
    initDB();
    // 在生产环境中,应该逻辑写到远端DB或者文件系统中
    buffer.add(value);
    return this;
  }

  public TransactionTable flush(){
    initDB();

    return this;
  }

  public void close(){
    buffer.clear();
  }

  private void initDB(){
    if(null==db){
      db=TransactionDB.getInstance();
    }
  }



}
