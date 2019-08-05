package com.cdc.inventorysystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.cdc.inventorysystem.dao.MissionMapper;
import com.cdc.inventorysystem.entity.Mission;
import com.cdc.inventorysystem.entity.vo.ResponseVO;
import com.cdc.inventorysystem.repository.MissionRepository;
import com.cdc.inventorysystem.service.MissionESService;
import com.cdc.inventorysystem.service.MissionService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-01
 */
@Service
public class MissionESServiceImpl implements MissionESService {
	@Autowired
	private MissionRepository missionRepository;
	@Autowired
	private ElasticsearchTemplate elasticsearchTemplate;
	@Autowired
	private MissionService missionService;

	// 根据id删除记录
	@Override
	public void deleteDocumentById(int id) {
		missionRepository.deleteById(1);
	}


	@Override
	public List<Mission> findAll() {
		Iterable<Mission> missions = missionRepository.findAll();
		List<Mission> missionsList = new ArrayList<>();
		ArrayList<Integer> idList = new ArrayList<>();
		System.out.println(missions);
		missions.forEach(mission -> {
			System.out.println(mission);
			Integer id = mission.getId();
			idList.add(id);
		});

		QueryWrapper<Mission> queryWrapper = new QueryWrapper<>();
		queryWrapper.in("Id", idList);
		List<Mission> list = missionService.list(queryWrapper);
		return list;
	}


	@RequestMapping("/findById")
	public void findById() throws Exception {
		Optional<Mission> optional = missionRepository.findById(1);
		Mission mission = optional.get();
		System.out.println(mission);
	}


	@RequestMapping("/findByTitle")
	public void findByTitle() throws Exception {
		List<Mission> list = missionRepository.findByTitle("Maven是一个工程构建工具");
		list.forEach(mission -> System.out.println(mission));
	}

	public void testNativeSearchQuery() throws Exception {
		// 创建一个查询对象
//        maven是一个工程构建工具
		NativeSearchQuery query = new NativeSearchQueryBuilder()
				.withQuery(QueryBuilders.queryStringQuery("是").defaultField("title"))
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
							mission.setId(Integer.parseInt(hit.getId()));
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
		if (fieldName.charAt(0) == '_') {
			startIndex = 1;
		}
		return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
				+ fieldName.substring(startIndex + 1);
	}

}
