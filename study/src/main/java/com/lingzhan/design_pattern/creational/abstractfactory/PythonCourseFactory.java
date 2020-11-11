package com.lingzhan.design_pattern.creational.abstractfactory;

/**
 * Created by 凌战 on 2020/8/27
 */
public class PythonCourseFactory implements CourseFactory {
  @Override
  public Video getVideo() {
    return new PythonVideo();
  }

  @Override
  public Article getArticle() {
    return new PythonArticle();
  }
}
