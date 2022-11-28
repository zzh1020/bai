package com.bjpowernode.service;

import com.bjpowernode.pojo.ProductInfo;
import com.bjpowernode.pojo.vo.ProductInfoSelect;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface ProductInfoService {
    List<ProductInfo> getAll();
    //展示分页后的记录:当前页-1*每页的条数，每页取几条
    PageInfo splitPage(int pageNum,int pageSize);

    int save(ProductInfo info);
    ProductInfo getById(int pid);
    int update(ProductInfo info);
    int delete(int pid);
    int deleteBatch(String []ids);
    List<ProductInfo> selectCondition(ProductInfoSelect vo);
    PageInfo splitPageSelect(ProductInfoSelect vo,int pageSize);
}
