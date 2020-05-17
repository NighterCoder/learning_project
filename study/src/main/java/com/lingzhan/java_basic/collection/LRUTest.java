package com.lingzhan.java_basic.collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by 凌战 on 2020/1/12
 */
public class LRUTest {
    public static void main(String[] args) {

        // 创建一个只有5个元素的缓存
        LRU<Integer,Integer> lru=new LRU<>(5,0.75f);

        lru.put(1,1);
        lru.put(2,2);
        lru.put(3,3);
        lru.put(4,4);
        lru.put(5,5);
        lru.put(6,6);
        lru.put(7,7);

        System.out.println(lru);
        System.out.println(lru.get(4));
        System.out.println(lru);

        lru.put(6,666);
        System.out.println(lru);
    }
}


class LRU<K,V> extends LinkedHashMap<K,V>{

    // 保存缓存的容量
    private int capacity;

    public LRU(int capacity,float loadFactor){
        super(capacity,loadFactor,true);
        this.capacity=capacity;
    }


    /**
     * 重写该方法设置何时移除旧元素
     * @param eldest
     */
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        // 当元素个数大于缓存的容量,就移除元素
        return size() > this.capacity;
    }
}
