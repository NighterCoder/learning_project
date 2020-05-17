package com.lingzhan.guava.ordering;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.flink.shaded.guava18.com.google.common.collect.Ordering;

import java.util.Comparator;
import java.util.List;
import java.util.Set;

/**
 * Created by 凌战 on 2019/11/21
 */
public class Test {
    public static void main(String[] args) {

        List<Entity> list = Lists.newArrayList(
                new Entity(1, "h"),
                new Entity(3, "b"),
                new Entity(3, "a"),
                new Entity(0, "d"),
                new Entity(3, "b"),
                null
        );

        Set<Integer> res= Sets.newHashSet(Lists.newArrayList(1,1,2,3));
        System.out.println(res);
        // 整型按照大小排序
      /*  Ordering<Integer> integerOrdering = Ordering.natural();
        System.out.println(integerOrdering.greatestOf(Lists.newArrayList(1,2,3),2));

        Ordering<Object> ordering3 = Ordering.usingToString();
        System.out.println(ordering3.sortedCopy(list));*/

     /* Ordering<Entity> ordering=Ordering.from(Comparator.comparingInt(Entity::getStatus)).nullsFirst();
        System.out.println(ordering.greatestOf(list,2));*/



    }
}
