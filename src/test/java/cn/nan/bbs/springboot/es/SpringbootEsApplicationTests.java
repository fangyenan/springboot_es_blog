package cn.nan.bbs.springboot.es;

import cn.nan.bbs.springboot.es.entity.es.EsBlog;
import cn.nan.bbs.springboot.es.responsitory.es.EsBlogRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.Iterator;
import java.util.List;

@SpringBootTest
@Slf4j
class SpringbootEsApplicationTests {

    @Autowired
    EsBlogRepository esBlogRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testEs(){
        Iterable<EsBlog> iterable = esBlogRepository.findAll();
        Iterator<EsBlog> iterator = iterable.iterator();
        EsBlog next = iterator.next();
        System.out.println(next.getTitle());
        BoolQueryBuilder builder = QueryBuilders.boolQuery();
        builder.should(QueryBuilders.matchPhraseQuery("title","发生"));
        builder.should(QueryBuilders.matchPhraseQuery("content","发生"));
        String s = builder.toString();
        log.info(">>> {} <<<",s);
        Page<EsBlog> esBlogs = (Page<EsBlog>) esBlogRepository.search(builder);
        List<EsBlog> content = esBlogs.getContent();
        content.size();

    }

}
