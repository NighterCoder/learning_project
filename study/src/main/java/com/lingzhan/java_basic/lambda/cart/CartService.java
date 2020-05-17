package com.lingzhan.java_basic.lambda.cart;

import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务类
 * Created by 凌战 on 2019/11/25
 */
public class CartService {

    private static List<Sku> cartSkuList=new ArrayList<Sku>(){
        {
            add(new Sku(654032,"无人机",4999.00,1,4999.00,SkuCategoryEnum.ELECTRONICS));
            add(new Sku(642934,"VR一体机",2299.00,1,2299.00,SkuCategoryEnum.ELECTRONICS));
            add(new Sku(645321,"纯色衬衫",409.00,3,1227.00,SkuCategoryEnum.CLOTHING));
            add(new Sku(654327,"牛仔裤",528.00,1,528.00,SkuCategoryEnum.CLOTHING));

            add(new Sku(675489,"跑步机",2699.00,1,2699.00,SkuCategoryEnum.SPORTS));
            add(new Sku(644564,"Java编程思想",79.80,1,79.80,SkuCategoryEnum.BOOKS));
            add(new Sku(678678,"Java核心技术",149.00,1,149.00,SkuCategoryEnum.BOOKS));
            add(new Sku(696968,"TensorFlow进阶指南",85.10,1,85.10,SkuCategoryEnum.BOOKS));
        }
    };


    public static List<Sku> getCartSkuList(){
        return cartSkuList;
    }


    /**
     * Version 1.0.0
     * @param skuList
     */
    public static List<Sku> filterElectronicsSkus(List<Sku> skuList){
        List<Sku> result=new ArrayList<>();

        for (Sku sku:cartSkuList){
            if (SkuCategoryEnum.ELECTRONICS.equals(sku.getSkuCategory())){
                result.add(sku);
            }
        }

        return result;
    }

    /**
     * Version 2.0.0
     * @param skuList
     * @param category
     */
    public static List<Sku> filterSkusByCategory(List<Sku> skuList,SkuCategoryEnum category){
        List<Sku> result=new ArrayList<>();

        for (Sku sku:cartSkuList){
            if (category.equals(sku.getSkuCategory())){
                result.add(sku);
            }
        }

        return result;
    }


    //Version 3.0.0 还有多维度条件参数

    /**
     * Version 4.0.0
     * 根据不同的sku判断标准,对sku列表进行过滤
     * @param cartSkuList
     * @param predicate - 不同的Sku判断标准策略
     */
    public static List<Sku> filterSkus(List<Sku> cartSkuList,SkuPredicate predicate){
        List<Sku> result=new ArrayList<>();

        for (Sku sku:cartSkuList){
            //根据不同的Sku判断标准策略,对Sku进行判断
            if (predicate.test(sku)){
                result.add(sku);
            }
        }

        return result;
    }


}
