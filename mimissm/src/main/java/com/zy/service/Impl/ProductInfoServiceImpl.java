package com.zy.service.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zy.mapper.ProductInfoMapper;
import com.zy.pojo.ProductInfo;
import com.zy.pojo.ProductInfoExample;
import com.zy.pojo.vo.ProductInfoVo;
import com.zy.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    //切记：业务逻辑层中一定有数据访问层的对象
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll() {
        //因为是查询所有，所以没有任何条件可添加，所以直接new一个空的ProductInfoExample到这就够了
        //如果有条件的话，new出来ProductInfoExample之后要去打点createCriteria().去添加各种条件
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    //-- 当前是第2页，显示分页后第2页所得的记录
    //select * from product_info limit 起始记录数=((当前页-1)*每页的条数)，每页取几条
    //select * from product_info limit 5,5
    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //分页插件使用当前的PageHelper工具类完成分页设置
        PageHelper.startPage(pageNum,pageSize);

        //进行PageInfo数据封装
        //进行有条件的查询操作必须要创建条件封装对象ProductInfoExample
        ProductInfoExample example = new ProductInfoExample();
        //设置排序，按照主键降序排序
        //select * from product_info  order by p_id desc
        example.setOrderByClause("p_id desc");
        //设置完排序后，取集合，切记切记：一定在取集合之前设置PageHelper.startPage(pageNum,pageSize);
        List<ProductInfo> list = productInfoMapper.selectByExample(example);
        //将查询到的集合封装进PageInfo对象中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getByID(int pid) {
        return productInfoMapper.selectByPrimaryKey(pid);
    }

    @Override
    public int update(ProductInfo info) {
        return productInfoMapper.updateByPrimaryKey(info);
    }

    @Override
    public int delete(int pid) {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] ids) {
        return productInfoMapper.deleteBatch(ids);  //如果发生异常是在控制器那处理
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoVo vo) {
        return productInfoMapper.selectCondition(vo); //没什么其他变化，就是从控制器那把参数接过来，在这查询就行
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductInfoVo vo, int pageSize) {
        //取出集合之前，要先设置PageHelper.startPage()属性
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list = productInfoMapper.selectCondition(vo);
        return new PageInfo<>(list);

    }

}
