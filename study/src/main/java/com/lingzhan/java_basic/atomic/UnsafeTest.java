package com.lingzhan.java_basic.atomic;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by 凌战 on 2019/12/23
 */
public class UnsafeTest {
    public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {
        //通过反射获取类属性
        Field f=Unsafe.class.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe= (Unsafe) f.get(null);


    }
}
