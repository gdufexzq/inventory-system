package com.cdc.inventorysystem.repository;

import com.cdc.inventorysystem.entity.Mission;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author xzquan
 * @version V1.0
 * @Description: ${TODO}
 * @date 2019/8/1 23:31
 */
@Repository
public interface MissionRepository extends ElasticsearchRepository<Mission, Integer>{

    public List<Mission> findByTitle(String title);
}
