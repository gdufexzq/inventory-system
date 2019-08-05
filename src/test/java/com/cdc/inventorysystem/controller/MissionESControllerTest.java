package com.cdc.inventorysystem.controller;

import com.cdc.inventorysystem.InventorySystemApplicationTests;
import com.cdc.inventorysystem.repository.MissionRepository;
import com.cdc.inventorysystem.entity.Mission;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author xzquan
 * @version V1.0
 * @Description: ${TODO}
 * @date 2019/8/1 23:55
 */
public class MissionESControllerTest extends InventorySystemApplicationTests {

    @Autowired
    private MissionRepository missionRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Test
    public void deleteDocumentById() throws Exception {
        missionRepository.deleteById(1);
    }

    @Test
    public void findAll() throws Exception {
        Iterable<Mission> missions = missionRepository.findAll();
        missions.forEach(mission -> System.out.println(mission));
    }

    @Test
    public void testFindById() throws Exception {
        Optional<Mission> optional = missionRepository.findById(1);
        Mission mission = optional.get();
        System.out.println(mission);
    }

    @Test
    public void testFindByTitle() throws Exception {
        List<Mission> list = missionRepository.findByTitle("Maven是一个工程构建工具");
        list.forEach(mission -> System.out.println(mission));
    }

    @Test
    public void testNativeSearchQuery() throws Exception {
        // 创建一个查询对象
//        maven是一个工程构建工具
        NativeSearchQuery query = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.queryStringQuery("测试是一项工具").defaultField("title"))
                .withPageable(PageRequest.of(0, 15))
                .withHighlightFields(new HighlightBuilder.Field("title"))
                .build();
        // 执行查询
        AggregatedPage<Mission> missions = elasticsearchTemplate.queryForPage(query, Mission.class,
                new SearchResultMapper() {
                    @Override
                    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
                        ArrayList<Mission> missions = new ArrayList<>();
                        SearchHits hits = response.getHits();
                        for (SearchHit hit:hits){
                            if(hits.getHits().length <= 0) {
                                return null;
                            }
                            Mission mission = new Mission();
                            String highLightMessage = hit.getHighlightFields().get("title").fragments()[0].toString();
                            mission.setId(Integer.parseInt(hit.getSourceAsMap().get("Id").toString()));
                            mission.setTitle(hit.getSourceAsMap().get("title").toString());
                            mission.setContent(hit.getSourceAsMap().get("content").toString());
                            try {
                                String setMethodName = parSetName("title");
                                Class<? extends Mission> missionClass = Mission.class;
                                Method setMethod = missionClass.getMethod(setMethodName, String.class);
                                setMethod.invoke(mission, highLightMessage);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            missions.add(mission);
                        }
                        if(missions.size() > 0) {
                            return new AggregatedPageImpl<>((List<T>) missions);
                        }
                        return null;
                    }
                });
        if(missions != null && missions.getSize() > 0) {
            missions.getContent().forEach(mission -> System.out.println(mission));
        }
    }

    public String parSetName(String fieldName){
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }
}