package com.lingzhan.guava.collections;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.junit.Test;

import java.util.List;

/**
 * Created by 凌战 on 2019/11/21
 */
public class ListsExampleTest {


    @Test
    public void testCartesianProduct(){
        //不需要指定泛型类型
        List<String> strs=Lists.newArrayList();

        //计算笛卡尔积
       /* List<List<String>> result=Lists.cartesianProduct(
                Lists.newArrayList("1","2"),
                Lists.newArrayList("A","B")
        );
        System.out.println(result);*/
    }


    @Test
    public void testTransform(){
        List<String> sourceList=Lists.newArrayList("Scala","Guava","Lists");
        List<String> upperStrs=Lists.transform(sourceList,e->e.toUpperCase());
        upperStrs.forEach(System.out::println);
        sourceList.forEach(System.out::println);
    }

    @Test
    public void testNewArrayList(){
        //几个初始化List的方法

        //指定容器初始化大小,因为容器的扩容是有代价的,对于可以预估的容器,建议加上参数
        List<String> res=Lists.newArrayListWithCapacity(10);  //最后List的大小由添加的元素个数决定
        res.add("x");
        res.add("y");
        res.add("z");
        res.add("x");
        res.add("y");
        res.add("z");
        res.add("x");
        res.add("y");
        res.add("z");
        res.add("x");
        res.add("y");
        res.add("z");
        System.out.println(res);
        System.out.println("列表大小为："+res.size());


        List<String> expectRes=Lists.newArrayListWithExpectedSize(5);  //5L + (long)arraySize + (long)(arraySize / 10)
        expectRes.add("aa");
        System.out.println(expectRes);

    }


    //返回列表倒置
    @Test
    public void testReverse(){
        List<String> list=Lists.newArrayList("1","2","3");
        List<String> reverseList=Lists.reverse(list);
        System.out.println(list);  //原列表不变
        System.out.println(reverseList); //返回源列表的反转视图
        list.add("4");
        System.out.println(list);
        System.out.println(reverseList);
        reverseList.add("5");
        System.out.println(list);
        System.out.println(reverseList);
    }


    //返回列表分片
    @Test
    public void testPartition(){
        List<String> list=Lists.newArrayList("1","2","3","4","5");
        List<List<String>> res=Lists.partition(list,2);
        System.out.println(res.size());
        System.out.println(res.get(0));
        System.out.println(res.get(2));
        list.add("6");
        System.out.println(res.get(2));
        //Lists.newArrayList(Sets.newHashSet("1","2"));
    }





}
