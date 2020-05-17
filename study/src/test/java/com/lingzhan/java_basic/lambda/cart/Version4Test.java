package com.lingzhan.java_basic.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;

/**
 * Created by 凌战 on 2019/11/25
 */
public class Version4Test {


    @Test
    public void filterSkus() {
        List<Sku> cartSkuList = CartService.getCartSkuList();

        //过滤商品总价大于2000的商品
        List<Sku> res = CartService.filterSkus(cartSkuList, new SkuTotalPricePredicate());

        System.out.println(JSON.toJSONString(res, true));
    }


    @Test
    public void filterSkus1() {
        List<Sku> cartSkuList = CartService.getCartSkuList();

        //过滤商品sku价大于2000的商品
        //todo 这里的接口实现类只会使用一次,所以可以在这里直接使用匿名内部类
        List<Sku> res = CartService.filterSkus(cartSkuList, new SkuPredicate() {
            @Override
            public boolean test(Sku sku) {
                return sku.getSkuPrice() > 3000;
            }
        });

        System.out.println(JSON.toJSONString(res, true));
    }

    @Test
    public void filterSkus2() {
        List<Sku> cartSkuList = CartService.getCartSkuList();

        //过滤商品sku价大于2000的商品
        //todo 这里的接口实现类只会使用一次,所以可以在这里直接使用匿名内部类
        List<Sku> res = CartService.filterSkus(cartSkuList, sku -> sku.getSkuPrice() > 1000);

        System.out.println(JSON.toJSONString(res, true));
    }


}
