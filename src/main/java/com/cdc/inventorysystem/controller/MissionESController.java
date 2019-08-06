package com.cdc.inventorysystem.controller;

import com.cdc.inventorysystem.common.enums.ResponseStatusEnum;
import com.cdc.inventorysystem.entity.vo.ResponseVO;
import com.cdc.inventorysystem.service.MissionESService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/missionEs")
public class MissionESController {

    @Autowired
    private MissionESService missionESService;



    public ResponseVO deleteDocumentById(@RequestParam int id) {
        missionESService.deleteDocumentById(1);
        return new ResponseVO(ResponseStatusEnum.SUCCESS, "");
    }


    @RequestMapping("/findAll")
    public ResponseVO findAll() {
        return new ResponseVO(ResponseStatusEnum.SUCCESS, missionESService.findAll());
    }


//    @RequestMapping("/findById")
//    public void findById() throws Exception {
//        Optional<Mission> optional = missionRepository.findById(1);
//        Mission mission = optional.get();
//        System.out.println(mission);
//    }
//
//
//    @RequestMapping("/findByTitle")
//    public void findByTitle() throws Exception {
//        List<Mission> list = missionRepository.findByTitle("Maven是一个工程构建工具");
//        list.forEach(mission -> System.out.println(mission));
//    }
//
//    public void testNativeSearchQuery() throws Exception {
//        // 创建一个查询对象
////        maven是一个工程构建工具
//        NativeSearchQuery query = new NativeSearchQueryBuilder()
//                .withQuery(QueryBuilders.queryStringQuery("是").defaultField("title"))
//                .withPageable(PageRequest.of(0, 15))
//                .withHighlightFields(new HighlightBuilder.Field("title"))
//                .build();
//        // 执行查询
//        AggregatedPage<Mission> missions = elasticsearchTemplate.queryForPage(query, Mission.class,
//                new SearchResultMapper() {
//                    @Override
//                    public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
//                        ArrayList<Mission> missions = new ArrayList<>();
//                        SearchHits hits = response.getHits();
//                        for (SearchHit hit:hits){
//                            if(hits.getHits().length <= 0) {
//                                return null;
//                            }
//                            Mission mission = new Mission();
//                            String highLightMessage = hit.getHighlightFields().get("title").fragments()[0].toString();
//                            mission.setId(Integer.parseInt(hit.getId()));
//                            mission.setTitle(hit.getSourceAsMap().get("title").toString());
//                            mission.setContent(hit.getSourceAsMap().get("content").toString());
//                            try {
//                                String setMethodName = parSetName("title");
//                                Class<? extends Mission> missionClass = Mission.class;
//                                Method setMethod = missionClass.getMethod(setMethodName, String.class);
//                                setMethod.invoke(mission, highLightMessage);
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            missions.add(mission);
//                        }
//                        if(missions.size() > 0) {
//                            return new AggregatedPageImpl<>((List<T>) missions);
//                        }
//                        return null;
//                    }
//                });
//        if(missions != null && missions.getSize() > 0) {
//            missions.getContent().forEach(mission -> System.out.println(mission));
//        }
//    }
//
//    public String parSetName(String fieldName){
//        if (null == fieldName || "".equals(fieldName)) {
//            return null;
//        }
//        int startIndex = 0;
//        if (fieldName.charAt(0) == '_') {
//            startIndex = 1;
//        }
//        return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
//                + fieldName.substring(startIndex + 1);
//    }

}

