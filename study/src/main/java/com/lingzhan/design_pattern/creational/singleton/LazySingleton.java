package com.lingzhan.design_pattern.creational.singleton;

/**
 * 懒汉式 单线程安全
 *
 * Created by 凌战 on 2020/8/27
 */
public class LazySingleton {

  // 在初始化时并没有实例
  private static LazySingleton lazySingleton=null;

  private LazySingleton(){

  }

  public static LazySingleton getInstance(){
    if (lazySingleton==null){
      lazySingleton=new LazySingleton();
    }
    return lazySingleton;
  }

}
