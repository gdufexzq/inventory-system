package com.cdc.inventorysystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;

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
 *
 * @author zhujiawei
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("user")
public class UserDetail implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "Id", type = IdType.AUTO)
    private Integer Id;
    
    private String username;

    private String password;

    @TableField("regTime")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date regTime;

    @TableField("schoolId")
    private Integer schoolId;

    private Integer credit;

    private Integer score;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date deadline;
    
    @TableField(exist = false)
    private int releaseNum;
    @TableField(exist = false)
    private int acceptNum;
}
