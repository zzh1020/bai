package com.bjpowernode.service.impl;


import com.bjpowernode.mapper.ProductInfoMapper;
import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.ProductInfoExample;
import com.bjpowernode.pojo.vo.ProductInfoSelect;
import com.bjpowernode.service.ProductInfoService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductInfoServiceImpl implements ProductInfoService {
    @Autowired
    ProductInfoMapper productInfoMapper;
    @Override
    public List<ProductInfo> getAll() {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize) {
        //分页插件使用PageHelper完成分页
        PageHelper.startPage(pageNum,pageSize);
        //进行pageinfo的数据封装
        //进行有条件的查询操作，必须要创建ProductInfoExample对象
        ProductInfoExample example=new ProductInfoExample();
        //设置排序
        example.setOrderByClause("p_id desc");
        List<ProductInfo> list=productInfoMapper.selectByExample(example);
        PageInfo<ProductInfo> pageInfo=new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo info) {
        return productInfoMapper.insert(info);
    }

    @Override
    public ProductInfo getById(int pid) {
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

        return productInfoMapper.deleteBatch(ids);
    }

    @Override
    public List<ProductInfo> selectCondition(ProductInfoSelect vo) {
        return productInfoMapper.selectCondition(vo);
    }

    @Override
    public PageInfo<ProductInfo> splitPageSelect(ProductInfoSelect vo, int pageSize) {
        PageHelper.startPage(vo.getPage(),pageSize);
        List<ProductInfo> list=productInfoMapper.selectCondition(vo);
        return new PageInfo<ProductInfo>(list);
    }
}
