package com.cdc.inventorysystem.entity.vo;

import com.cdc.inventorysystem.entity.Mission;
import lombok.Data;

import java.util.List;

/**
 * @author xzquan
 * @version V1.0
 * @Description: ${TODO}
 * @date 2019/8/6 17:07
 */
@Data
public class MissionQueryVO {
    /**
     * 总记录数
     */
    private long totalSize;
    /**
     * 总页数
     */
    private int totalPages;
    /**
     * 当前页
     */
    private int pageNum;
    /**
     * 每页记录数
     */
    private int pageSize;
    /**
     * 返回数据
     */
    private List<Mission> missionList;
}
