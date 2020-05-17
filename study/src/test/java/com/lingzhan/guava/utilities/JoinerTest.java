package com.lingzhan.guava.utilities;

import org.apache.flink.shaded.curator.org.apache.curator.shaded.com.google.common.base.Joiner;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.junit.Assert.assertThat;

/**
 * Created by 凌战 on 2019/11/13
 */
public class JoinerTest {

    private final List<String> stringList= Arrays.asList("Google","Guava","Java","Scala","Kafka");

    private final List<String> stringListWithNullValue=Arrays.asList("Google","Guava","Java","Scala",null);

    @Test
    public void testJoinOnJoin(){
        String result=Joiner.on("#").join(stringList);
        assertThat(result,equalTo("Google#Guava#Java#Scala#Kafka"));
    }


    @Test(expected = NullPointerException.class)
    public void testJoinOnJoinWithNullValue(){
        String result=Joiner.on("#").join(stringListWithNullValue);
        assertThat(result,equalTo("Google#Guava#Java#Scala"));
    }

    @Test
    public void testJoinOnJoinWithNullValueButSkip(){
        String result=Joiner.on("#").skipNulls().join(stringListWithNullValue);
        assertThat(result,equalTo("Google#Guava#Java#Scala"));
    }

    @Test
    public void testJoinOnJoinWithNullValueUseDefaultValue(){
        String result=Joiner.on("#").useForNull("DEFAULT").join(stringListWithNullValue);
        assertThat(result,equalTo("Google#Guava#Java#Scala#DEFAULT"));
    }

    @Test
    public void testJoin_On_Append_To_StringBuilder(){
        final StringBuilder builder=new StringBuilder("HELLO");
        StringBuilder resultBuilder=Joiner.on("#").useForNull("DEFAULT").appendTo(builder,stringListWithNullValue);
        assertThat(resultBuilder,sameInstance(builder));
        assertThat(resultBuilder.toString(),equalTo("HELLOGoogle#Guava#Java#Scala#DEFAULT"));

    }



}
