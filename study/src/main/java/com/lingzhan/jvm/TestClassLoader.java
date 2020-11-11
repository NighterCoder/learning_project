package com.lingzhan.jvm;

/**
 * Created by 凌战 on 2020/6/14
 */
public class TestClassLoader {

  public static void main(String[] args) {


 //   System.out.println(TestClassLoader.class.getResource(""));

    //System.out.println(TestClassLoader.class.getResource("/"));

  //System.out.println(TestClassLoader.class.getClassLoader().getResource(""));
    System.out.println(TestClassLoader.class.getClassLoader().getResource("/"));


  }
}
