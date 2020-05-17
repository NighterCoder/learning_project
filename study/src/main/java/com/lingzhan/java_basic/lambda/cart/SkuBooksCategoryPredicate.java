package com.lingzhan.java_basic.lambda.cart;

/**
 * 对Sku的商品类型为图书类的判断标准
 * Created by 凌战 on 2019/11/25
 */
public class SkuBooksCategoryPredicate implements SkuPredicate {
    @Override
    public boolean test(Sku sku) {
        return SkuCategoryEnum.BOOKS.equals(sku.getSkuCategory());
    }
}
