package com.cdc.inventorysystem.entity.vo;

import com.cdc.inventorysystem.entity.Mission;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xzquan
 * @version V1.0
 * @Description: ${TODO}
 * @date 2019/8/8 19:47
 */
@Data
public class MissionVO extends Mission {
    public static Map staticMap = new HashMap();

    private String userName;
    private String schoolName;
    private String stateValue;

    static {
        staticMap.put("0", "未接收");
        staticMap.put("1", "已接收");
        staticMap.put("2", "完成");
    }
}
