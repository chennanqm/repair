package com.yjx.controller;

import com.yjx.module.PartsQueryModule;
import com.yjx.pojo.Parts;
import com.yjx.service.PartsService;
import com.yjx.unti.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/parts")
public class PartsController {
    @Autowired
    private PartsService partsService;
    @RequestMapping("list")
    public Result<Map<String, Object>> list(PartsQueryModule partsQueryModule) {
        int userId = partsQueryModule.getUserId();
        String searchKeyword = partsQueryModule.getSearchKeyword();
        Integer pageNum = partsQueryModule.getPageNum();
        Integer pageSize = partsQueryModule.getPageSize();
        String sortField = partsQueryModule.getSortField();
        String sortOrder = partsQueryModule.getSortOrder();

        Map<String, Object> data = partsService.list(userId, searchKeyword, pageNum, pageSize, sortField, sortOrder);
        return Result.success(data);
    }

    @RequestMapping("addPart")
    public Result<String> addPart(@RequestBody Parts parts) {
        return partsService.addPart(parts);
    }

    @RequestMapping("updatePart")
    public Result<String> updatePart(@RequestBody Parts parts) {
        return partsService.updatePart(parts);
    }

    @RequestMapping("/delete/{partId}")
    public Result<String> deletePart(@PathVariable Integer partId) {
        return partsService.deletePart(partId);
    }
}
