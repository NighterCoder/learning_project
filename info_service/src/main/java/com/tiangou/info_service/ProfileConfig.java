package com.tiangou.info_service;



import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;


public class ProfileConfig {

    @Value("$user.name")
    private String userName;
    @Value("$user.password")
    private String userPassword;
    @Value("$user.driverclass")
    private String driverClass;
    @Profile("test")
    @Bean(name = "DataSource_Test")
    public DataSource dataSourceTest() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(userName);
        dataSource.setPassword(userPassword);
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test");
        dataSource.setDriverClassName(driverClass);
        return dataSource;
    }
    @Profile("dev")
    @Bean(name = "DataSource_Dev")
    public DataSource dataSourceDev() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(userName);
        dataSource.setPassword(userPassword);
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/blog");
        dataSource.setDriverClassName(driverClass);
        return dataSource;
    }
    @Profile("Prod")
    @Bean(name = "DataSource_Prod")
    public DataSource dataSourceProd() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUsername(userName);
        dataSource.setPassword(userPassword);
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/o2o");
        dataSource.setDriverClassName(driverClass);
        return dataSource;
    }



}
