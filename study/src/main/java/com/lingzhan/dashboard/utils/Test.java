package com.lingzhan.dashboard.utils;

import java.util.Date;

/**
 * Created by 凌战 on 2019/11/12
 */
public class Test {
    public static void main(String[] args) {

        System.out.println(System.currentTimeMillis());
        long coalescedTime1 = ((System.currentTimeMillis() + 5000) / 5000) * 5000;
        long coalescedTime2 = ((System.currentTimeMillis()+1000 + 5000) / 5000) * 5000;
        long coalescedTime3 = ((System.currentTimeMillis()+2000 + 5000) / 5000) * 5000;
        long coalescedTime4 = ((System.currentTimeMillis()+3000 + 5000) / 5000) * 5000;
        long coalescedTime5 = ((System.currentTimeMillis()+4000 + 5000) / 5000) * 5000;
        long coalescedTime6 = ((System.currentTimeMillis()+5000 + 5000) / 5000) * 5000;

        System.out.println(new Date(coalescedTime1));
        System.out.println(new Date(coalescedTime2));
        System.out.println(new Date(coalescedTime3));
        System.out.println(new Date(coalescedTime4));
        System.out.println(new Date(coalescedTime5));
        System.out.println(new Date(coalescedTime6));

    }
}
