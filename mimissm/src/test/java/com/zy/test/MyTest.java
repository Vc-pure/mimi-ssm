package com.zy.test;

import com.zy.mapper.ProductInfoMapper;
import com.zy.pojo.ProductInfo;
import com.zy.pojo.vo.ProductInfoVo;
import com.zy.utils.MD5Util;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)  //用这个类让spring来接管单元测试
@ContextConfiguration(locations = {"classpath:applicationContext_dao.xml","classpath:applicationContext_service.xml"})
public class MyTest {
    @Test
    public void testMD5(){
        String mi = MD5Util.getMD5("000000");
        System.out.println(mi);
    }

    @Autowired
    ProductInfoMapper mapper;

    @Test
    public void testSelectCondition(){
        ProductInfoVo vo = new ProductInfoVo();
//        vo.setPname("4");
//        vo.setTypeid(3);
        vo.setLprice(2299.0);
        vo.setHprice(3000.0);
        List<ProductInfo> list = mapper.selectCondition(vo);
        list.forEach(productInfo -> System.out.println(productInfo));
    }
}
