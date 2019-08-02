package com.cdc.inventorysystem.entity;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author yangjinchao
 * @since 2019-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Mission implements Serializable {

	private static final long serialVersionUID = 1L;

	@TableId(value = "Id", type = IdType.AUTO)
	private Integer Id;

	private String title;

	private String content;

	private Integer score;

	@TableField("userId")
	private Integer userId;

	@TableField("pubTime")
	private String pubTime;

	@TableField("schoolId")
	private Integer schoolId;

	@TableField("recUserId")
	private Integer recUserId;

	@TableField("recTime")
	private String recTime;

	private Integer state;

	private Integer display;

	private String schoolName;

	private String recUser;

	public String getRecUser() {
		return recUser;
	}

	public void setRecUser(String recUser) {
		this.recUser = recUser;
	}

	public String getSchoolName() {
		return schoolName;
	}

	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	public Integer getId() {
		return Id;
	}

	public void setId(Integer id) {
		Id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getPubTime() {
		return pubTime;
	}

	public void setPubTime(String pubTime) {
		this.pubTime = pubTime;
	}

	public Integer getSchoolId() {
		return schoolId;
	}

	public void setSchoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	public Integer getRecUserId() {
		return recUserId;
	}

	public void setRecUserId(Integer recUserId) {
		this.recUserId = recUserId;
	}

	public String getRecTime() {
		return recTime;
	}

	public void setRecTime(String recTime) {
		this.recTime = recTime;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getDisplay() {
		return display;
	}

	public void setDisplay(Integer display) {
		this.display = display;
	}

	public Mission(Integer userId) {
		super();
		this.userId = userId;
	}

	public Mission() {
		super();
	}

	public Mission(Integer id, String title, String content, Integer score, Integer userId, String pubTime,
			Integer schoolId, Integer recUserId, String recTime, Integer state, Integer display, String schoolName) {
		super();
		Id = id;
		this.title = title;
		this.content = content;
		this.score = score;
		this.userId = userId;
		this.pubTime = pubTime;
		this.schoolId = schoolId;
		this.recUserId = recUserId;
		this.recTime = recTime;
		this.state = state;
		this.display = display;
		this.schoolName = schoolName;
	}

}
