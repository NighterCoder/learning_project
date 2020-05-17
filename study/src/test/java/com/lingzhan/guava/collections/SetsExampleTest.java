package com.lingzhan.guava.collections;

import com.google.common.collect.Lists;
import org.apache.flink.shaded.guava18.com.google.common.collect.Sets;
import org.junit.Test;

import java.util.HashSet;
import java.util.List;

/**
 * Created by 凌战 on 2019/11/21
 */
public class SetsExampleTest {

    @Test
    public void testCreate(){


        HashSet<Integer> set= Sets.newHashSet(1,2,3);
        System.out.println(set);

        List<Integer> list= Lists.newArrayList(1,1,2,3);
        System.out.println(list);

        HashSet<Integer> set2=Sets.newHashSet(list);
        System.out.println(set2);


    }
}
