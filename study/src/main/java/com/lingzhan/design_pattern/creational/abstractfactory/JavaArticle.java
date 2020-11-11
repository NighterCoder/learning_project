package com.lingzhan.design_pattern.creational.abstractfactory;

/**
 * Created by 凌战 on 2020/8/27
 */
public class JavaArticle extends Article {
  @Override
  public void produce() {
    System.out.println("编写Java课程手记");
  }
}
