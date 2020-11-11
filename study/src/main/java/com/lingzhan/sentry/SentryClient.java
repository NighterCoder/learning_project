package com.lingzhan.sentry;


import com.sun.jdi.InternalException;
import java.util.Set;
import org.apache.sentry.core.model.db.AccessConstants;
import org.apache.sentry.provider.db.service.thrift.SentryPolicyServiceClient;
import org.apache.sentry.provider.db.service.thrift.TSentryRole;
import org.apache.sentry.service.thrift.SentryServiceClientFactory;
import org.junit.Test;
import org.apache.sentry.core.common.exception.SentryUserException;

/**
 * Created by 凌战 on 2020/11/11
 */
public class SentryClient {

  private static SentryConfig sentryConfig = new SentryConfig("C:\\resources\\workspace\\learning_project\\study\\src\\main\\resources\\sentry-site.xml");


  /**
   * 测试获取已有角色信息
   */
  @Test
  public void testListRoles()  {
    try (SentryServiceClient client = new SentryServiceClient()) {
      // 这里为了测试方便，使用hive管理员作为请求用户，来获取所有角色信息
      //SentryPolicyServiceClient client = SentryServiceClientFactory.create(conf);
      //Set<TSentryRole> roles = client.listAllRoles("hive");
      //Set<TSentryRole> roles = client.get().listAllRoles("hive");
      //Set<TSentryRole> roles = client.get().listRolesByGroupName("hive","user1");

      Set<TSentryRole> roles = client.get().listRoles("hive"); // 这里的参数是请求用户名,hive用户属于管理员组(hive),可以获取所有角色信息
      // Set<TSentryRole> roles = client.get().listRoles("hdfs"); // hdfs用户不可以
      for (TSentryRole role : roles) {
        System.out.println(role);
      }
    } catch (SentryUserException e) {
      e.printStackTrace();
    }
  }


  @Test
  public void testCreateAndGrantRole(){
    try(SentryServiceClient client=new SentryServiceClient()){
      client.get().createRole("hive","test_column");
      // 参数1:请求用户, 参数2:赋权角色名, 参数3:服务器名
      // 参数4:库名, 参数5:表名, 参数6:列名, 参数7:操作
      client.get().grantColumnPrivilege("hive","test_column","node1","sensitive","events","ip",
          AccessConstants.SELECT);

    } catch (SentryUserException e) {
      e.printStackTrace();
    }
  }





  /**
   * 删除已有角色信息
   */
  @Test
  public void testDropRoleIfExists() throws InternalException {
    try (SentryServiceClient client = new SentryServiceClient()) {
      // 参数1:请求用户 参数2:待删除角色
      client.get().dropRoleIfExists("hive", "test");
    } catch (InternalException | SentryUserException e) {
      e.printStackTrace();
    }
  }

  static class SentryServiceClient implements AutoCloseable {
    private final SentryPolicyServiceClient client_;

    /**
     * Creates and opens a new Sentry Service thrift client.
     */
    public SentryServiceClient() throws InternalException {
      client_ = createClient();
    }

    /**
     * Get the underlying SentryPolicyServiceClient.
     */
    public SentryPolicyServiceClient get() {
      return client_;
    }

    /**
     * Returns this client back to the connection pool. Can be called multiple times.
     */
    public void close() throws InternalException {
      try {
        client_.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    /**
     * Creates a new client to the SentryService.
     */
    private SentryPolicyServiceClient createClient() throws InternalException {
      SentryPolicyServiceClient client = null;
      try {
        sentryConfig.loadConfig();
        client = SentryServiceClientFactory.create(sentryConfig.getConfig());
      } catch (Exception e) {
        e.printStackTrace();
      }
      return client;
    }
  }

}
