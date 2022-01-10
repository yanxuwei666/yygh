package com.xuwei.yygh.cmn.controller;

import com.xuwei.yygh.cmn.service.DictService;
import com.xuwei.yygh.common.result.Result;
import com.xuwei.yygh.model.cmn.Dict;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description 数据字典接口
 * @Date 2021/12/22 13:24
 * @Author yxw
 */
@Api(tags = "数据字典接口")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin/cmn/dict")
public class DictController {

    @Autowired
    private DictService dictService;

    //根据数据id查询子数据列表
    @ApiOperation(value = "根据数据id查询子数据列表")
    @GetMapping("findChildData/{id}")
    public Result findChildData(@PathVariable Long id) {
        List<Dict> list = dictService.findChlidData(id);
        return Result.ok(list);
    }

    @ApiOperation(value="导出")
    @GetMapping(value = "/exportDictData")
    public void exportDictData(HttpServletResponse response) {
        dictService.exportDictData(response);
    }

    @ApiOperation(value="导入")
    @PostMapping(value = "/importDictData")
    public Result importDictData(MultipartFile file) {
        dictService.importDictData(file);
        return Result.ok();
    }

}
