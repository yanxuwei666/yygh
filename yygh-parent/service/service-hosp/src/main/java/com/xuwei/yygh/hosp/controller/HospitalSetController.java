package com.xuwei.yygh.hosp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xuwei.yygh.common.result.Result;
import com.xuwei.yygh.common.utils.MD5;
import com.xuwei.yygh.hosp.domain.HospitalSet;
import com.xuwei.yygh.hosp.service.HospitalSetService;
import com.xuwei.yygh.vo.hosp.HospitalSetQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

/**
 * @Description TODO
 * @Date 2021/12/22 13:24
 * @Author yxw
 */
@Api(tags = "医院设置管理")
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin/hosp/hospitalSet")
public class HospitalSetController {

    @Autowired
    private HospitalSetService hospitalSetService;

    @ApiOperation("查询医院设置表所有信息")
    @GetMapping("findAll")
    public Result findAllHospitalSet() {
        List<HospitalSet> list = hospitalSetService.list();
        return Result.ok(list);
    }

    @ApiOperation("条件查询带分页")
    @PostMapping("findPageHospSet/{current}/{limit}")
    public Result findPageHospSet(@PathVariable Long current,
                                  @PathVariable Long limit,
                                  @RequestBody(required = false) HospitalSetQueryVo hospitalSetQueryVo) {
        // 创建page对象
        Page<HospitalSet> page = new Page<>(current, limit);
        // 构建对象
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        String hosname = hospitalSetQueryVo.getHosname();
        String hoscode = hospitalSetQueryVo.getHoscode();
        if (!StringUtils.isBlank(hosname)) {
            wrapper.like("hosname", hosname);
            wrapper.eq("hoscode", hoscode);
        }
        // 调用方法实现分页查询
        Page<HospitalSet> hospitalSetPage = hospitalSetService.page(page, wrapper);
        return Result.ok(hospitalSetPage);
    }

    @ApiOperation("根据id查询医院设置")
    @GetMapping("/getHospSet/{id}")
    public Result getHospSet(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        return Result.ok(hospitalSet);
    }

    @ApiOperation("根据id删除医院设置")
    @DeleteMapping("{id}")
    public Result removeHospSet(@PathVariable Long id) {
        boolean flag = hospitalSetService.removeById(id);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation("批量删除医院设置")
    @DeleteMapping("batchRemove")
    public Result batchRemoveHsopSet(@RequestBody List<Long> idList) {
        hospitalSetService.removeByIds(idList);
        return Result.ok();
    }

    @ApiModelProperty("增加医院设置")
    @PutMapping("saveHospSet")
    public Result saveHospSet(@RequestBody HospitalSet hospitalSet) {
        hospitalSet.setStatus(1);
        Random random = new Random();
        hospitalSet.setSignKey(MD5.encrypt(System.currentTimeMillis()+""+random.nextInt(1000)));
        boolean save = hospitalSetService.save(hospitalSet);
        if(save) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiModelProperty("修改医院设置")
    @PostMapping("updateHospSet")
    public Result updateHospSet(@RequestBody HospitalSet hospitalSet) {
        boolean flag = hospitalSetService.updateById(hospitalSet);
        if (flag) {
            return Result.ok();
        } else {
            return Result.fail();
        }
    }

    @ApiOperation(value = "医院设置锁定和解锁")
    @PutMapping("lockHospitalSet/{id}/{status}")
    public Result lockHospitalSet(@PathVariable Long id,
                                  @PathVariable Integer status) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        hospitalSet.setStatus(status);
        hospitalSetService.updateById(hospitalSet);
        return Result.ok();
    }

    @ApiOperation(value = "发送签名秘钥")
    @PutMapping("sendKey/{id}")
    public Result sendKey(@PathVariable Long id) {
        HospitalSet hospitalSet = hospitalSetService.getById(id);
        String signKey = hospitalSet.getSignKey();
        String hoscode = hospitalSet.getHoscode();
        //发送短信
        return Result.ok();
    }
}
