package com.lingzhan.kafka;

import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
import kafka.zk.AdminZkClient;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.KafkaAdminClient;
import org.apache.kafka.common.security.scram.ScramCredential;
import org.apache.kafka.common.security.scram.internals.ScramCredentialUtils;
import org.apache.kafka.common.security.scram.internals.ScramFormatter;
import org.apache.kafka.common.security.scram.internals.ScramMechanism;

import java.security.NoSuchAlgorithmException;
import java.util.Properties;

/**
 * Created by 凌战 on 2020/3/10
 */
public class KafkaUser {

    public static void main(String[] args) throws NoSuchAlgorithmException {


        ZkClient zkClient = new ZkClient("localnode2:2181/kafka23", 5000, 5000, ZKStringSerializer$.MODULE$);


        ZkUtils zkUtils =ZkUtils.apply(zkClient,false);

        Properties prop=new Properties();

        String entityType="users";
        // 用户姓名
        String entityName="haha";
        // 用户密码
        String password="123456";
        // iterations就取默认值
        int iterations=4096;
        for (ScramMechanism mechanism: ScramMechanism.values()){
            String mechanismName=mechanism.mechanismName();
            ScramCredential credential= new ScramFormatter(mechanism).generateCredential(password,iterations);
            String credentialStr= ScramCredentialUtils.credentialToString(credential);
            prop.put(mechanismName,credentialStr);
        }

        AdminUtils.changeConfigs(zkUtils,entityType,entityName,prop);


    }

}
