package com.cdc.inventorysystem.dao;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cdc.inventorysystem.entity.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-05-15
 */
@Mapper
public interface UserMapperDemo extends BaseMapper<User> {

}
