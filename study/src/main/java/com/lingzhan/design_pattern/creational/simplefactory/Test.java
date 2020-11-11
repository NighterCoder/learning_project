package com.lingzhan.design_pattern.creational.simplefactory;

/**
 * Created by 凌战 on 2020/8/26
 */
public class Test {

  public static void main(String[] args) {

    VideoFactory videoFactory=new VideoFactory();
    Video video=videoFactory.getVideo(JavaVideo.class);
    if (video==null){
      return;
    }
    video.produce();
  }

}
