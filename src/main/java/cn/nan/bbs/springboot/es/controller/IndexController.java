package cn.nan.bbs.springboot.es.controller;

import cn.nan.bbs.springboot.es.entity.mysql.MysqlBlog;
import cn.nan.bbs.springboot.es.responsitory.mysql.MysqlBlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private MysqlBlogRepository mysqlBlogRepository;
    @RequestMapping("/")
    public String index(){
        List<MysqlBlog> all = mysqlBlogRepository.findAll();
        return "index.html";
    }
}
