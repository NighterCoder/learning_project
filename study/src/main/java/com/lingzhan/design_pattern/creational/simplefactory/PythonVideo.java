package com.lingzhan.design_pattern.creational.simplefactory;

/**
 * Created by 凌战 on 2020/8/26
 */
public class PythonVideo extends Video{
  @Override
  public void produce() {
    System.out.println("录制Python课程视频");
  }
}
