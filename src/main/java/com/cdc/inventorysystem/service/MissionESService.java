package com.cdc.inventorysystem.service;

import com.cdc.inventorysystem.entity.vo.MissionQueryVO;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-01
 */
public interface MissionESService {

	/**
	 * 根据id删除记录
	 */
	void deleteDocumentById(int id);

	/**
	 * 查询所有
	 * @return
	 */
	MissionQueryVO findAll(Integer pageNum, Integer pageSize);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	MissionQueryVO findById(int id);

	/**
	 * 根据标题查询
	 * @param title
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	MissionQueryVO findByTitle(String title, Integer pageNum, Integer pageSize);

	/**
	 * 根据内容查询
	 * @param content
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	MissionQueryVO findByContent(String content, Integer pageNum, Integer pageSize);

	/**
	 * 在标题和内容上查询
	 * @param content
	 * @param pageNum
	 * @param pageSzie
	 * @return
	 */
	MissionQueryVO getMissionListByStr(String content, Integer pageNum, Integer pageSzie);

}
