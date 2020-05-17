package com.lingzhan.java_basic.lambda.cart;

/**
 * 下单商品信息
 * Created by 凌战 on 2019/11/25
 */
public class Sku {

    private Integer skuId;

    private String shuName;

    private Double skuPrice;

    private Integer totalNum;

    private Double totalPrice;

    private Enum skuCategory;


    public Sku(Integer skuId, String shuName, Double skuPrice, Integer totalNum, Double totalPrice, Enum skuCategory) {
        this.skuId = skuId;
        this.shuName = shuName;
        this.skuPrice = skuPrice;
        this.totalNum = totalNum;
        this.totalPrice = totalPrice;
        this.skuCategory = skuCategory;
    }

    public Integer getSkuId() {
        return skuId;
    }

    public String getShuName() {
        return shuName;
    }

    public Double getSkuPrice() {
        return skuPrice;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public Enum getSkuCategory() {
        return skuCategory;
    }
}
