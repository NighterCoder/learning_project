package com.lingzhan.design_pattern.creational.abstractfactory;

/**
 * Created by 凌战 on 2020/8/27
 */
public class JavaCourseFactory implements CourseFactory {
  @Override
  public Video getVideo() {
    return new JavaVideo();
  }

  @Override
  public Article getArticle() {
    return new JavaArticle();
  }
}
