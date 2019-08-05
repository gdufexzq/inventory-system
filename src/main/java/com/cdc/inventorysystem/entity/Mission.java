package com.cdc.inventorysystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

/**
 * <p>
 * 
 * </p>
 *
 *
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@Document(indexName = "inventory-system-index", type = "mission", shards = 1, replicas = 0, refreshInterval = "-1")
public class Mission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;

    /**
     * 任务标题
     */
	@Field
    private String title;

    /**
     * 任务概述
     */
	@Field
    private String content;

    /**
     * 任务积分
     */
	@Field
    private Integer score;

    /**
     * 发布人
     */
	@Field
    @TableField("userId")
    private Integer userId;

    /**
     * 发布时间
     */
	@Field
    @TableField("pubTime")
    private String pubTime;

    /**
     * 发布地点ID
     */
	@Field
    @TableField("schoolId")
    private Integer schoolId;

    /**
     * 接受者ID
     */
	@Field
    @TableField("recUserId")
    private Integer recUserId;

    /**
     * 接受时间
     */
	@Field
    @TableField("recTime")
    private String recTime;

    /**
     * 状态 0:待接受 1:已接受 2:已完成
     */
	@Field
    private Integer state;

    /**
     * 是否屏蔽 0:否 1:是  缺省值为0
     */
	@Field
    private Integer display;

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

	@Override
	public String toString() {
		return "Mission [Id=" + Id + ", title=" + title + ", content=" + content + ", score=" + score + ", userId="
				+ userId + ", pubTime=" + pubTime + ", schoolId=" + schoolId + ", recUserId=" + recUserId + ", recTime="
				+ recTime + ", state=" + state + ", display=" + display + "]";
	}

}
