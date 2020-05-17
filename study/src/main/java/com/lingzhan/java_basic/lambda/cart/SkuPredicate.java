package com.lingzhan.java_basic.lambda.cart;

/**
 * Sku选择谓词标准
 * Created by 凌战 on 2019/11/25
 */
public interface SkuPredicate {

    /**
     * 选择判断标签
     * @param sku
     */
    boolean test(Sku sku);
}
