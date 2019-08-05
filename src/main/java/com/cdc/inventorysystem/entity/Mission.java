package com.cdc.inventorysystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

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
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
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

}
