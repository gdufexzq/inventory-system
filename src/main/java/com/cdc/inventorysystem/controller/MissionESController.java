package com.cdc.inventorysystem.controller;

import com.cdc.inventorysystem.common.enums.ResponseStatusEnum;
import com.cdc.inventorysystem.entity.vo.ResponseVO;
import com.cdc.inventorysystem.service.MissionESService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author xuzhiquan
 * @since 2019-08-01
 */
@RestController
@RequestMapping("/missionEs")
public class MissionESController {

    @Autowired
    private MissionESService missionESService;

    public ResponseVO deleteDocumentById(@RequestParam int id) {
        missionESService.deleteDocumentById(id);
        return new ResponseVO(ResponseStatusEnum.SUCCESS, "");
    }

    @PostMapping("/findAll")
    public ResponseVO findAll(Integer pageNum, Integer pageSize) {
        return new ResponseVO(ResponseStatusEnum.SUCCESS, missionESService.findAll(pageNum, pageSize));
    }

    @RequestMapping("/findById")
    public ResponseVO findById(@RequestParam int id) {
        return new ResponseVO(ResponseStatusEnum.SUCCESS, missionESService.findById(id));
    }

    @PostMapping("/findByTitle")
    public ResponseVO findByTitle(@RequestParam String title, Integer pageNum, Integer pageSize) {
        return new ResponseVO(ResponseStatusEnum.SUCCESS, missionESService.findByTitle(title, pageNum, pageSize));
    }

    @PostMapping("/findByContent")
    public ResponseVO findByContent(@RequestParam String content, Integer pageNum, Integer pageSize) {
        return new ResponseVO(ResponseStatusEnum.SUCCESS, missionESService.findByContent(content, pageNum, pageSize));
    }

    @PostMapping("/getMissionListByStr")
    public ResponseVO getMissionListByStr(String content, Integer pageNum, Integer pageSize) {
        return new ResponseVO(ResponseStatusEnum.SUCCESS, missionESService.getMissionListByStr(content, pageNum, pageSize));
    }

}

