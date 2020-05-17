package com.lingzhan.java_basic.lambda.cart;

import com.alibaba.fastjson.JSON;
import org.junit.Test;


import java.util.List;

/**
 * Created by 凌战 on 2019/11/25
 */
public class Version1Test {


    @Test
    public void filterElectronicsSkus(){
        List<Sku> cartSkuList=CartService.getCartSkuList();
        List<Sku> res=CartService.filterElectronicsSkus(cartSkuList);

        System.out.println(JSON.toJSONString(res,true));
    }




}
