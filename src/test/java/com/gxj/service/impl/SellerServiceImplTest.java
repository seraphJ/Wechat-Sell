package com.gxj.service.impl;

import com.gxj.dataobject.SellerInfo;
import com.gxj.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
class SellerServiceImplTest {

    private static final String OPENID = "abc";

    @Autowired
    private SellerService sellerService;

    @Test
    void findSellerInfoByOpenid() throws Exception {
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(OPENID);
        Assert.assertEquals(OPENID, sellerInfo.getOpenid());
    }
}