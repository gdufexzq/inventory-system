package com.cdc.inventorysystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
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

    /**
     * 任务标题
     */
    private String title;

    /**
     * 任务概述
     */
    private String content;

    /**
     * 任务积分
     */
    private Integer score;

    /**
     * 发布人
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 发布时间
     */
    @TableField("pubTime")
    private String pubTime;

    /**
     * 发布地点ID
     */
    @TableField("schoolId")
    private Integer schoolId;

    /**
     * 接受者ID
     */
    @TableField("recUserId")
    private Integer recUserId;

    /**
     * 接受时间
     */
    @TableField("recTime")
    private String recTime;

    /**
     * 状态 0:待接受 1:已接受 2:已完成
     */
    private Integer state;

    /**
     * 是否屏蔽 0:否 1:是  缺省值为0
     */
    private Integer display;
	private String schoolName;
	private String recUser;

	public Mission(Integer userId) {
		super();
		this.userId = userId;
	}

	public Mission() {
		super();
	}
}
