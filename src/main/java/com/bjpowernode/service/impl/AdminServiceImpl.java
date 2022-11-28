package com.bjpowernode.service.impl;

import com.bjpowernode.mapper.AdminMapper;
import com.bjpowernode.pojo.Admin;
import com.bjpowernode.pojo.AdminExample;
import com.bjpowernode.service.AdminService;
import com.bjpowernode.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {
    //数据访问层的对象
    @Autowired
    AdminMapper adminMapper;
    @Override
    public Admin login(String name, String pwd) {
        //根据传入的用户到数据库中查询相应用户对象
        //如果有条件，则一定要创建AdminExample对象，用来封装条件
        AdminExample example=new AdminExample();
        //将用户名作为条件封装进AdminExample对象
        example.createCriteria().andANameEqualTo(name);
        List<Admin> list=adminMapper.selectByExample(example);
        if(list.size()>0){
            Admin admin=list.get(0);
//            进行密码对比时，将传入的pwd进行MD5加密
            String mpwd= MD5Util.getMD5(pwd);
            if(mpwd.equals(admin.getaPass())){
                return admin;
            }
        }
        return null;
    }
}
