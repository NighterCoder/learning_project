package com.tiangou.info_service.spring_basic;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * 定义一个增强的类
 *
 * Created by 凌战 on 2020/8/30
 */
@Aspect
@Component
public class Advice {

  @Before("execution(* com.tiangou.info_service.spring_basic.*Service.*(..))")
  public void advisor() {
    System.out.println("do before");
  }


  @Before("execution(* com.tiangou.info_service.spring_basic.MyComponent.*(..))")
  public void advisor2() {
    System.out.println("do before");
  }




}
