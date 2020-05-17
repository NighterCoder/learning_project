package com.lingzhan.guava.collections;

import com.google.common.collect.Lists;
import org.apache.flink.shaded.guava18.com.google.common.collect.FluentIterable;
import org.junit.Test;

import java.util.ArrayList;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

/**
 * Created by 凌战 on 2019/11/13
 */
public class FluentIterableExampleTest {

    private FluentIterable<String> build() {
        ArrayList<String> list = Lists.newArrayList("Alex", "Wang", "Guava", "Scala");
        return FluentIterable.from(list);
    }


    @Test
    public void testFilter() {
        FluentIterable<String> fit = build();
        assertThat(fit.size(), equalTo(4));

        FluentIterable<String> result = fit.filter(e -> e != null && e.length() > 4);
        assertThat(result.size(), equalTo(2));
    }





}