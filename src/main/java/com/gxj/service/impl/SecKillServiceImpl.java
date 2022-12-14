package com.gxj.service.impl;

import com.gxj.exception.SellException;
import com.gxj.service.RedisLock;
import com.gxj.service.SecKillService;
import com.gxj.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 廖师兄
 * 2017-08-06 23:18
 */

@Service
@Slf4j
public class SecKillServiceImpl implements SecKillService {

    @Autowired
    private RedisLock redisLock;

    private static final int TIMEOUT = 10 * 1000; //超时时间 10s

    /**
     * 国庆活动，皮蛋粥特价，限量100000份
     */
    static Map<String,Integer> products;
    static Map<String,Integer> stock;
    static Map<String,String> orders;
    static
    {
        /**
         * 模拟多个表，商品信息表，库存表，秒杀成功订单表
         */
        products = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        products.put("111111", 100000);
        products.put("222222", 100000);
        products.put("333333", 100000);
        products.put("444444", 100000);
        products.put("555555", 100000);
        products.put("666666", 100000);

        stock.put("111111", 100000);
        stock.put("222222", 100000);
        stock.put("333333", 100000);
        stock.put("444444", 100000);
        stock.put("555555", 100000);
        stock.put("666666", 100000);
    }

    private String queryMap(String productId)
    {
        return "国庆活动，皮蛋粥特价，限量份"
                + products.get(productId)
                +" 还剩：" + stock.get(productId)+" 份"
                +" 该商品成功下单用户数目："
                +  orders.size() +" 人" ;
    }

    private String queryMap()
    {
        int sells = 0;
        for (String productId : Arrays.asList("111111", "222222", "333333", "444444", "555555", "666666")) {
            sells += products.get(productId) - stock.get(productId);
        }
        return "国庆活动，皮蛋粥特价，销量"
                + sells
                +" 份"
                +" 该商品成功下单用户数目："
                +  orders.size() +" 人" ;
    }

    @Override
    public String querySecKillProductInfo(String productId)
    {
        return this.queryMap(productId);
    }

    public String querySecKillProductInfo()
    {
        return this.queryMap();
    }

    @Override
    public void orderProductMockDiffUser(String productId)
    {
//        加锁
        long time = System.currentTimeMillis() + TIMEOUT;
        while (!redisLock.lock(productId, String.valueOf(time))) {
            time = System.currentTimeMillis() + TIMEOUT;
        }



//        if (!redisLock.lock(productId, String.valueOf(time))) {
//            log.info("【redis分布式锁】未抢到锁{}", System.currentTimeMillis());
//            throw new SellException(101, "哎呦喂，人也太多了");
//        }

        //1.查询该商品库存，为0则活动结束。
        int stockNum = stock.get(productId);
        if(stockNum == 0) {
            throw new SellException(100,"活动结束");
        }else {
            //2.下单(模拟不同用户openid不同)
            orders.put(KeyUtil.genUniqueKey(),productId);
            //3.减库存
            stockNum =stockNum-1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId,stockNum);
        }

        //解锁
        redisLock.unlock(productId, String.valueOf(time));
    }
}