package com.lingzhan.design_pattern.creational.abstractfactory;

/**
 * Created by 凌战 on 2020/8/27
 */
public class JavaVideo extends Video {
  @Override
  public void produce() {
    System.out.println("录制Java课程");
  }
}
