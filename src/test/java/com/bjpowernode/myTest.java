package com.bjpowernode;

import com.bjpowernode.mapper.ProductInfoMapper;
import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductInfoSelect;
import com.bjpowernode.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class myTest {
    @Autowired
    ProductInfoMapper productInfoMapper;
    @Test
    public void test(){
        ProductInfoSelect vo=new ProductInfoSelect();
//        vo.setPname("4");
//        vo.setTypeid(3);
        vo.setLprice(3000);
        vo.setHprice(4000);
        List<ProductInfo> list=productInfoMapper.selectCondition(vo);
        System.out.println(list);
    }


}
