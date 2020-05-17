package com.lingzhan.java_basic.lambda.cart;

/**
 * 对sku的总价是否超出2000作为判断标准
 * Created by 凌战 on 2019/11/25
 */
public class SkuTotalPricePredicate implements SkuPredicate {
    @Override
    public boolean test(Sku sku) {
        return sku.getTotalPrice()>2000;
    }
}
