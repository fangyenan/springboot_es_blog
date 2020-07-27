package cn.nan.bbs.springboot.es.responsitory.es;

import cn.nan.bbs.springboot.es.entity.es.EsBlog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsBlogRepository extends ElasticsearchRepository<EsBlog, Integer> {
}
