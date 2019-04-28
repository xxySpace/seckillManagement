package com.xxy.seckill.seckillmanagement;

import com.xxy.seckill.seckillmanagement.dao.UserDAOMapper;
import com.xxy.seckill.seckillmanagement.dataobject.UserDAO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.ws.Action;

@SpringBootApplication(scanBasePackages = {"com.xxy.seckill.seckillmanagement"})
@RestController
@MapperScan("com.xxy.seckill.seckillmanagement.dao")
public class SeckillManagementApplication {

    @Autowired
    private UserDAOMapper userDAOMapper;

    public static void main(String[] args) {
        System.out.println("项目启动！");
        SpringApplication.run(SeckillManagementApplication.class, args);
    }

    @RequestMapping("/")
    public String home() {
        UserDAO userDAO = userDAOMapper.selectByPrimaryKey(1);
        if (null == userDAO) {
            return "用户对象不存在";
        } else {
            return userDAO.getName();
        }
    }

}
