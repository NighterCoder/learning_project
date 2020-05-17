package com.lingzhan.java_basic.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.List;

/**
 * Created by 凌战 on 2019/11/25
 */
public class Version2Test {


    @Test
    public void filterSkuByCategory(){
        List<Sku> cartSkuList=CartService.getCartSkuList();
        List<Sku> res=CartService.filterSkusByCategory(cartSkuList,SkuCategoryEnum.BOOKS);

        System.out.println(JSON.toJSONString(res,true));
    }




}
