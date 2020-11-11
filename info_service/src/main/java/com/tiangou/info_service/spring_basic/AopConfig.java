package com.tiangou.info_service.spring_basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 创建主配置类
 * @Configuration 代表这是spring的java配置类
 * @ComponentScan 代表自动扫描组件,所以Advice类会自动加载
 * @EnableAspectJAutoProxy 如果要使用annotation的方式完成AOP，必须要在主配置类上添加这个标签，相当于XML中配置的
 * <aop:aspectj-autoproxy/>
 *
 * 提供被代理的目标对象,EmployeeServiceImpl
 *
 * Created by 凌战 on 2020/8/30
 */

@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class AopConfig {

  @Bean
  public IEmployeeService target() {
    return new EmployeeServiceImpl();
  }

}
