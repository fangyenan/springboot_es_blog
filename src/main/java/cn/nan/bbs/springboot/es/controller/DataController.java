package cn.nan.bbs.springboot.es.controller;

import cn.nan.bbs.springboot.es.entity.es.EsBlog;
import cn.nan.bbs.springboot.es.entity.mysql.MysqlBlog;
import cn.nan.bbs.springboot.es.responsitory.es.EsBlogRepository;
import cn.nan.bbs.springboot.es.responsitory.mysql.MysqlBlogRepository;
import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
public class DataController {

    @Autowired
    MysqlBlogRepository mysqlBlogRepository;

    @Autowired
    EsBlogRepository esBlogRepository;

    @GetMapping("/blogs")
    public Object blog(){
        System.out.println("开始从容Mysql读取数据");
        List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.findAll();
        return mysqlBlogs;
    }

    @PostMapping("/search")
    public Object search(@RequestBody Param param){
        HashMap<String, Object> map = new HashMap<String, Object>();
        StopWatch watch = new StopWatch();
        watch.start();
        if(param.getType().equals("mysql")){
            //mysql
            System.out.println("开始从容Mysql查询数据");
            List<MysqlBlog> mysqlBlogs = mysqlBlogRepository.findBlogs(param.getKeyword());
            map.put("list", mysqlBlogs);
        }else if (param.getType().equals("es")) {
            //es
//            POST /blog/_search
//            {
//                "query": {
//                "bool": {
//                    "should": [
//                    {
//                        "match_phrase": {
//                        "title": "sdf"
//                    }
//                    },
//                    {
//                        "match_phrase": {
//                        "content": "福克斯"
//                    }
//                    }
//      ]
//                }
//            }
//        }
            BoolQueryBuilder builder = QueryBuilders.boolQuery();
            builder.should(QueryBuilders.matchPhraseQuery("title", param.getKeyword()));
            builder.should(QueryBuilders.matchPhraseQuery("content", param.getKeyword()));
            String s = builder.toString();
            System.out.println(s);
            System.out.println("开始从容es查询数据");
            Page<EsBlog> search = (Page<EsBlog>)esBlogRepository.search(builder);
            List<EsBlog> content = search.getContent();
            map.put("list", content);
        }else {
            return "I don't undertand!";
        }
        watch.stop();
        long totalTimeMillis =  watch.getTotalTimeMillis();
        map.put("duration", totalTimeMillis);
        return map;
    }

    @GetMapping("/blog/{id}")
    public Object blog(@PathVariable("id") Integer id){
        Optional<MysqlBlog> byId = mysqlBlogRepository.findById(id);
        MysqlBlog blog = byId.get();
        return blog;
    }

    @Data
    public static class Param{
        // mysql es
        private String type;
        private String keyword;
    }
}
