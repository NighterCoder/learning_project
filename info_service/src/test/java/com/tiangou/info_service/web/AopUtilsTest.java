package com.tiangou.info_service.web;

import static org.junit.Assert.assertTrue;

import com.tiangou.info_service.spring_basic.AopConfig;
import com.tiangou.info_service.spring_basic.IEmployeeService;
import com.tiangou.info_service.spring_basic.MyComponent;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by 凌战 on 2020/8/30
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AopConfig.class)
public class AopUtilsTest {

  @Autowired
  private IEmployeeService service;

  @Autowired
  private MyComponent component;

  @Test
  public void testIsAop() {
    assertTrue(AopUtils.isAopProxy(service));
  }

  @Test
  public void  testIsJdk(){
    assertTrue(AopUtils.isJdkDynamicProxy(service));
  }

  @Test
  public void testJDKTargetClass(){
    System.out.println(service.getClass());
    // 打印出IEmployeeService的接口实现类
    System.out.println(AopUtils.getTargetClass(service));
  }

  @Test
  public void testCGLIBTargetClass(){
    System.out.println(component.getClass());
    System.out.println(AopUtils.getTargetClass(component));
  }


}
