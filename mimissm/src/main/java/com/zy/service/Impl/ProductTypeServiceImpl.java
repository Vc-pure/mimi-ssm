package com.zy.service.Impl;

import com.zy.mapper.ProductTypeMapper;
import com.zy.pojo.ProductType;
import com.zy.pojo.ProductTypeExample;
import com.zy.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService {

    //在业务逻辑层一定会有数据访问层的对象
    @Autowired
    ProductTypeMapper productTypeMapper;

    @Override
    public List<ProductType> getAll() {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
