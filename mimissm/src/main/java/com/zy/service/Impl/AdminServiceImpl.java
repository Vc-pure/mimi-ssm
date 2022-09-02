package com.zy.service.Impl;

import com.zy.mapper.AdminMapper;
import com.zy.pojo.Admin;
import com.zy.pojo.AdminExample;
import com.zy.service.AdminService;
import com.zy.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    //在业务逻辑层一定会有数据访问层的对象
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd) {

        //根据传入的用户名到数据库中查询相应用户对象
        //如果有条件，则一定要创建AdminExample的对象，用来封装条件（条件就是where后面的东西）
        AdminExample example = new AdminExample(); //example其实就是拼接的sql语句封装成对象，然后直接调用方法执行查询操作
        /**
         * 如何添加条件
         * select * from admin where a_name = 'admin'
         */
        //添加用户名 a_name条件
        example.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(example);
        if(list.size() > 0){
            Admin admin = list.get(0);  //查询的第一个数据就是admin对象，0表示下标
            //如果查询到用户对象，再进行密码的比对， 注意密码是密文
            /**
             * 分析：
             *  admin.getApass==>c984aed014aec7623a54f0591da07a85fd4b762d
             *  pwd==>000000
             *  在进行密码对比时，要将传入的pwd进行MD5加密，再与数据库中查到的对象的密码进行对比
             */
            String miPwd = MD5Util.getMD5(pwd);
            if(miPwd.equals(admin.getaPass())){
                return admin;  //这俩如果一致，说明当前登录成功，就返回admin，除此之外任何情况返回空
            }
        }
        return null;
    }
}
