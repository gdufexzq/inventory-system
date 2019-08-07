package com.cdc.inventorysystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cdc.inventorysystem.entity.Mission;
import com.cdc.inventorysystem.entity.vo.MissionQueryVO;
import com.cdc.inventorysystem.repository.MissionRepository;
import com.cdc.inventorysystem.service.MissionESService;
import com.cdc.inventorysystem.service.MissionService;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.matchQuery;

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
	private static Integer pageNum = 1;
	private static Integer pageSize = 10;
	private long totalSize = 0;

	public MissionQueryVO findResult(List<Mission> missionList, Long totalSize, Integer totalPages, Integer pageNum, Integer pageSize) {

			MissionQueryVO missionQueryVO = new MissionQueryVO();
			missionQueryVO.setTotalSize(totalSize);
			missionQueryVO.setTotalPages(totalPages);
			missionQueryVO.setPageNum(pageNum);
			missionQueryVO.setPageSize(pageSize);
			missionQueryVO.setMissionList(missionList);
			return missionQueryVO;
	}

	// 根据id删除记录
	@Override
	public void deleteDocumentById(int id) {
		missionRepository.deleteById(id);
	}

	@Override
	public MissionQueryVO findAll(Integer pageNum, Integer pageSize) {
		if(pageNum == null ) {
			pageNum = MissionESServiceImpl.pageNum;
		}
		if(pageSize == null) {
			pageSize = MissionESServiceImpl.pageSize;
		}
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		Page<Mission> missions = missionRepository.findAll(pageable);
		long totalSize = missions.getTotalElements();
		System.out.println("missions:" + missions);
		int totalPages = missions.getTotalPages();
		List<Mission> missionList = new ArrayList<>();
		missions.forEach(mission -> {
			String pubTime = timeZone(mission.getPubTime());
			String recTime = timeZone(mission.getRecTime());
			mission.setPubTime(pubTime);
			mission.setRecTime(recTime);
			missionList.add(mission);
		});

		return findResult(missionList, totalSize, totalPages, pageNum, missionList.size());
	}


	@Override
	public MissionQueryVO findById(int id) {
		Optional<Mission> optional = missionRepository.findById(id);
		if(optional != null) {
			Mission mission = optional.get();
			List<Mission> missionList = new ArrayList<>();
			if(mission != null) {
				String pubTime = timeZone(mission.getPubTime());
				String recTime = timeZone(mission.getRecTime());
				mission.setPubTime(pubTime);
				mission.setRecTime(recTime);
				missionList.add(mission);
				return findResult(missionList, 1L, 1, 1, missionList.size());
			}
		}
		return null;
	}


	@Override
	public MissionQueryVO findByTitle(String title, Integer pageNum, Integer pageSize) {
		if(pageNum == null ) {
			pageNum = MissionESServiceImpl.pageNum;
		}
		if(pageSize == null) {
			pageSize = MissionESServiceImpl.pageSize;
		}
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		SearchQuery searchQuery = (new NativeSearchQueryBuilder()).withQuery(QueryBuilders.queryStringQuery(title).defaultField("title")).withPageable(pageable).build();
		Page<Mission> missions = missionRepository.search(searchQuery);
		long totalSize = missions.getTotalElements();
		int totalPages = missions.getTotalPages();
		List<Mission> missionList = new ArrayList<>();
		missions.forEach(mission -> {
			String pubTime = timeZone(mission.getPubTime());
			String recTime = timeZone(mission.getRecTime());
			mission.setPubTime(pubTime);
			mission.setRecTime(recTime);
			missionList.add(mission);
		});
		return findResult(missionList, totalSize, totalPages, pageNum, missionList.size());
	}

	@Override
	public MissionQueryVO findByContent(String content, Integer pageNum, Integer pageSize) {
		if(pageNum == null ) {
			pageNum = MissionESServiceImpl.pageNum;
		}
		if(pageSize == null) {
			pageSize = MissionESServiceImpl.pageSize;
		}
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		SearchQuery searchQuery = (new NativeSearchQueryBuilder()).withQuery(QueryBuilders.queryStringQuery(content).defaultField("content")).withPageable(pageable).build();
		Page<Mission> missions = missionRepository.search(searchQuery);
		long totalSize = missions.getTotalElements();
		int totalPages = missions.getTotalPages();
		List<Mission> missionList = new ArrayList<>();
		missions.forEach(mission -> {
			String pubTime = timeZone(mission.getPubTime());
			String recTime = timeZone(mission.getRecTime());
			mission.setPubTime(pubTime);
			mission.setRecTime(recTime);
			missionList.add(mission);
		});

		return findResult(missionList, totalSize, totalPages, pageNum, missionList.size());
	}

	public MissionQueryVO findDBById(List<Integer> idList, Long totalSize, Integer totalPages, Integer pageNum, Integer pageSize) {
		if (idList != null && idList.size() > 0){
			QueryWrapper queryWrapper = new QueryWrapper<>();
			queryWrapper.in("id", idList);
			List<Mission> list = missionService.list(queryWrapper);
			MissionQueryVO missionQueryVO = new MissionQueryVO();
			missionQueryVO.setTotalSize(totalSize);
			missionQueryVO.setTotalPages(totalPages);
			missionQueryVO.setPageNum(pageNum);
			missionQueryVO.setPageSize(pageSize);
			list.forEach(mission -> {
				mission.setPubTime(mission.getPubTime().substring(0, mission.getPubTime().length()-2));
				mission.setRecTime(mission.getRecTime().substring(0, mission.getRecTime().length()-2));
			});
			missionQueryVO.setMissionList(list);
			return missionQueryVO;
		}
		return null;
	}

	@Override
	public MissionQueryVO getMissionListByStr(String content, Integer pageNum, Integer pageSize) {
		if(pageNum == null ) {
			pageNum = MissionESServiceImpl.pageNum;
		}
		if(pageSize == null) {
			pageSize = MissionESServiceImpl.pageSize;
		}
		Pageable pageable = PageRequest.of(pageNum-1, pageSize);
		// 色值
		String preTag = "<font color='#dd4b39'>";
		String postTag = "</font>";

		SearchQuery searchQuery = new NativeSearchQueryBuilder().
				withQuery(matchQuery("title", content)).
				withQuery(matchQuery("content", content)).
				withHighlightFields(new HighlightBuilder.Field("title").preTags(preTag).postTags(postTag),
						new HighlightBuilder.Field("content").preTags(preTag).postTags(postTag)).build();
		searchQuery.setPageable(pageable);

		// 不需要高亮直接return ideas
		// AggregatedPage<Idea> ideas = elasticsearchTemplate.queryForPage(searchQuery, Mission.class);

		// 高亮字段
		AggregatedPage<Mission> missions = elasticsearchTemplate.queryForPage(searchQuery, Mission.class, new SearchResultMapper() {

			@Override
			public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {
				List<Mission> chunk = new ArrayList<>();
				for (SearchHit searchHit : response.getHits()) {
					if (response.getHits().getHits().length <= 0) {
						return null;
					}
					totalSize = response.getHits().getTotalHits();
					System.out.println("总记录数:" + totalSize);
					Mission mission = new Mission();
					//name or memoe
					HighlightField title = searchHit.getHighlightFields().get("title");
					if (title != null) {
						mission.setTitle(title.fragments()[0].toString());
					}
					HighlightField content = searchHit.getHighlightFields().get("content");
					if (content != null) {
						mission.setContent(content.fragments()[0].toString());
					}
					mission.setId((Integer) searchHit.getSourceAsMap().get("id"));
					mission.setRecTime(timeZone((String) searchHit.getSourceAsMap().get("recTime")));
					mission.setPubTime(timeZone((String) searchHit.getSourceAsMap().get("pubTime")));
					mission.setSchoolId((Integer) searchHit.getSourceAsMap().get("schoolId"));
					mission.setDisplay((Integer) searchHit.getSourceAsMap().get("display"));
					mission.setRecUserId((Integer) searchHit.getSourceAsMap().get("userId"));
					mission.setScore((Integer) searchHit.getSourceAsMap().get("score"));
					mission.setState((Integer) searchHit.getSourceAsMap().get("state"));
					mission.setUserId((Integer) searchHit.getSourceAsMap().get("userId"));
					chunk.add(mission);
				}
				if (chunk.size() > 0) {
					return new AggregatedPageImpl<>((List<T>) chunk);
				}
				return null;
			}
		});
		int totalPages = pageSize % totalSize == 0 ?
				(int) totalSize / pageSize  :
				(int) totalSize /pageSize + 1;
		List<Mission> missionList = new ArrayList<>();
		if(missions != null) {
			missionList = missions.getContent();
		}
		return findResult(missionList, totalSize, totalPages, pageNum, missionList.size());
	}


	/**
	 * utc转为gmt
	 */
	public String timeZone(String utc) {
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
//			"2018-01-22T09:12:43.083Z"
			Date date = sdf1.parse(utc);//拿到Date对象
			String str = sdf2.format(date);//输出格式：2017-01-22 17:28:33
			System.out.println(str);
			return str;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
