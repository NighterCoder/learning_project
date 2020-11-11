package com.lingzhan.jvm;

/**
 * 对于静态字段来说,只有直接定义了该字段的类才会被初始化
 * 当一个类在初始化时,要求其父类全部都已经初始化完毕了
 *
 * Created by 凌战 on 2020/5/17
 */
public class MyTest1 {

  public static void main(String[] args) {
    // 调用父类的静态变量,所以只会初始化父类,子类没有被初始化
    // System.out.println(MyChild1.str);

    // 调用子类的静态变量,会初始化父类,之后初始化子类
    System.out.println(MyChild1.str2);



  }
}

class MyParent1{

  public static String str="hello world";

  static {
    System.out.println("MyParent1 static block");
  }
}

class MyChild1 extends MyParent1{
  public static String str2="welcome";

  static {
    System.out.println("MyChild1 static block");
  }

}


