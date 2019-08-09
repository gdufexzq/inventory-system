package com.cdc.inventorysystem.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdc.inventorysystem.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 * @author zzix
 * @since 2019-08-01
 */
@Mapper
public interface MessageMapper extends BaseMapper<Message> {
    Page<Message> getMessagesByPage(Page<Message> page, @Param("userId")Integer userId);

    @Select("select * from message where userId = 0 or userId = -1 or userId = -2 order by time desc")
    Page<Message> getAdminMsgByPage(Page<Message> page);
}
