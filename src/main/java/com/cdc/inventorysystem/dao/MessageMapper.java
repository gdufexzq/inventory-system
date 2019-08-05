package com.cdc.inventorysystem.dao;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cdc.inventorysystem.entity.Message;
import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
}
