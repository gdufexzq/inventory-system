package com.cdc.inventorysystem.dao;

import com.cdc.inventorysystem.entity.User;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-02
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {

}
