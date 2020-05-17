package com.lingzhan.java_basic.lambda.cart;

/**
 * Created by 凌战 on 2019/11/25
 */
public enum SkuCategoryEnum {

    CLOTHING(10,"服装类"),
    ELECTRONICS(20,"数码类"),
    SPORTS(30,"运动类"),
    BOOKS(40,"图书类")

    ;

    private Integer code;

    private String name;

    SkuCategoryEnum(Integer code, String name) {
        this.code = code;
        this.name = name;
    }



}
