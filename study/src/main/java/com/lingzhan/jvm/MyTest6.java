package com.lingzhan.jvm;

/**
 * Created by 凌战 on 2020/5/17
 */
public class MyTest6 {

  public static void main(String[] args) {

    Singleton singleton=Singleton.getInstance();
    // counter1和counter2都是1
    System.out.println("counter1:"+Singleton.counter1);
    System.out.println("counter2:"+Singleton.counter2);


    Singleton1 singleton1=Singleton1.getInstance();
    // counter1和counter2都是1
    System.out.println("counter1:"+Singleton1.counter1);
    System.out.println("counter2:"+Singleton1.counter2);



  }

}

class Singleton{

  public static int counter1;

  public static int counter2=0;

  private static Singleton singleton=new Singleton();

  private Singleton(){
    counter1++;
    counter2++;
  }

  public static Singleton getInstance(){
    return singleton;
  }


}


/**
 * 准备阶段: 1.counter1初始化为0 2.singleton初始化为null 3.counter2初始化为0
 *
 * 调用静态方法 getInstance() 初始化阶段
 * 1.counter1为0
 * 2.new Singleton1(),counter1变为1,counter2也变为1
 * 3.之后又用0覆盖counter2
 *
 */



class Singleton1{

  public static int counter1;


  private static Singleton1 singleton=new Singleton1();

  private Singleton1(){
    counter1++;
    counter2++;
    System.out.println(counter1);
    System.out.println(counter2);
  }

  // 运行期阶段 将counter2从1又赋值为0
  public static int counter2=0;

  public static Singleton1 getInstance(){
    return singleton;
  }


}