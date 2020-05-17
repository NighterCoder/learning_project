package com.lingzhan.kafka;

import kafka.admin.AdminUtils;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.CreateAclsResult;
import org.apache.kafka.common.acl.AccessControlEntry;
import org.apache.kafka.common.acl.AclBinding;
import org.apache.kafka.common.acl.AclOperation;
import org.apache.kafka.common.acl.AclPermissionType;
import org.apache.kafka.common.resource.Resource;
import org.apache.kafka.common.resource.ResourceType;
import org.apache.zookeeper.OpResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

/**
 * Created by 凌战 on 2020/3/10
 */
public class KafkaPrivilege {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.setProperty("java.security.auth.login.config", "C:\\resources\\workspace\\tiangou\\flink_analysis\\flink_study\\src\\main\\resources\\kafka_admin_jass.conf");
        AdminClient adminClient;
        Properties properties = new Properties();
        properties.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG,
                "localnode2:9094");
        properties.put("security.protocol", "SASL_PLAINTEXT");
        properties.put("sasl.mechanism", "SCRAM-SHA-256");

        adminClient = AdminClient.create(properties);


        AclBinding aclBinding1=new AclBinding(new Resource(ResourceType.TOPIC,"test"),
                new AccessControlEntry("User:haha","*", AclOperation.READ, AclPermissionType.ALLOW));
        AclBinding aclBinding2=new AclBinding(new Resource(ResourceType.TOPIC,"test"),
                new AccessControlEntry("User:haha","*", AclOperation.WRITE, AclPermissionType.ALLOW));

        List<AclBinding> bindings=new ArrayList<AclBinding>();
        bindings.add(aclBinding1);
        bindings.add(aclBinding2);

        //AclBinding
        CreateAclsResult res =adminClient.createAcls(bindings);
        res.all().get();




    }

}
