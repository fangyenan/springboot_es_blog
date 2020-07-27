package cn.nan.bbs.springboot.es.controller;

import cn.nan.bbs.springboot.es.entity.mysql.MysqlBlog;
import cn.nan.bbs.springboot.es.responsitory.mysql.MysqlBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class AddDataController {
    @Autowired
    MysqlBlogRepository mysqlBlogRepository;

    @RequestMapping("/addData")
    public String addData(MysqlBlog mysqlBlog) throws Exception{
        Date date=new Date();
//        DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time=format.format(date);
        mysqlBlog.setCreateTime(date);
        mysqlBlog.setUpdateTime(date);
        mysqlBlogRepository.save(mysqlBlog);
        return "redirect:adddata.html";
    }
}
