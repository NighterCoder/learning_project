package com.lingzhan.java_basic.lambda.file;

import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by 凌战 on 2019/11/25
 */
public class FileServiceTest {


    @Test
    public void fileHandle() throws IOException {
        FileService fileService=new FileService();

        fileService.fileHandle("C:\\resources\\workspace\\tiangou\\flink_analysis\\flink_study\\src\\main\\java\\com\\lingzhan\\java_basic\\lambda\\file\\FileService.java",fileContent -> System.out.println(fileContent));
    }



    @Test
    public void test1(){
        Consumer<String> consumer1=(String number)-> System.out.println(Integer.parseInt(number));
        Consumer<String> consumer2= Integer::parseInt;
        consumer1.accept("123");

        List<String>  strs= Lists.newArrayList("1","33","44","66");
        System.out.println(strs.stream().map(Integer::parseInt).max(Ordering.natural()).get());


    }



}
