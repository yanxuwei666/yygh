package com.xuwei.yygh.hosp.controller.api;

import com.mysql.jdbc.StringUtils;
import com.xuwei.yygh.common.exception.YyghException;
import com.xuwei.yygh.common.helper.HttpRequestHelper;
import com.xuwei.yygh.common.result.Result;
import com.xuwei.yygh.common.result.ResultCodeEnum;
import com.xuwei.yygh.common.utils.MD5;
import com.xuwei.yygh.hosp.service.DepartmentService;
import com.xuwei.yygh.hosp.service.HospitalService;
import com.xuwei.yygh.hosp.service.HospitalSetService;
import com.xuwei.yygh.hosp.service.ScheduleService;
import com.xuwei.yygh.model.hosp.Department;
import com.xuwei.yygh.model.hosp.Schedule;
import com.xuwei.yygh.vo.hosp.DepartmentQueryVo;
import com.xuwei.yygh.vo.hosp.ScheduleQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * @Description 医院管理API接口
 * @Date 2022/1/11 13:16
 * @Author yxw
 */
@Api(tags = "医院管理API接口")
@RestController
@RequestMapping("/api/hosp")
public class ApiController {
    @Autowired
    private HospitalService hospitalService;

    @Autowired
    private HospitalSetService hospitalSetService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private ScheduleService scheduleService;

    @ApiOperation(value = "上传医院")
    @PostMapping("saveHospital")
    public Result saveHospital(HttpServletRequest request) {
        // 获取传递过来的医院信息
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        // 1、获取医院系统传递过来的签名，签名进行MD5加密
        String hospSign = (String) paramMap.get("sign");


        // 2、获取传递过来的医院编码，查询数据库，查询签名
        String hoscode = (String) paramMap.get("hoscode");
        String signKey = hospitalSetService.getSignKey(hoscode);

        // 3、把数据库查询签名进行MD5加密
        String signKeyMd5 = MD5.encrypt(signKey);

        // 4、判断签名是否一致
        if (!hospSign.equals(signKeyMd5)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //传输过程中图片“+”转换为了“ ”，因此我们要转换回来
        String logoData = (String)paramMap.get("logoData");
        logoData = logoData.replaceAll(" ", "+");
        paramMap.put("logoData", logoData);

        // 调用service的方法
        hospitalService.save(paramMap);
        return Result.ok();
    }

    @ApiOperation(value = "获取医院信息")
    @PostMapping("hospital/show")
    public Result hospital(HttpServletRequest request) {
        Map<String, String[]> requestMap = request.getParameterMap();
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(requestMap);

        //必须参数校验 略
        String hoscode = (String) paramMap.get("hoscode");
        if (StringUtils.isEmptyOrWhitespaceOnly(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }

        // 获取医院系统传过来的签名，已经进行了MD5加密
        String hospSign = (String) paramMap.get("sign");

        //把数据库查询签名进行MD5加密
        String signKey = MD5.encrypt(hospitalSetService.getSignKey(hoscode));
        //签名校验
        //if(!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hoscode))) {
        //    throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        //}
        if (!Objects.equals(hospSign, signKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        return Result.ok(hospitalService.getByHoscode((String)paramMap.get("hoscode")));
    }

    @ApiOperation(value = "上传科室")
    @PostMapping("saveDepartment")
    public Result saveDepartment(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        if(StringUtils.isEmptyOrWhitespaceOnly(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }

        // 获取医院系统传过来的签名，已经进行了MD5加密
        String hospSign = (String) paramMap.get("sign");

        //签名校验
        String signKey = MD5.encrypt(hospitalSetService.getSignKey(hoscode));
        if (!Objects.equals(hospSign, signKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        //if(!HttpRequestHelper.isSignEquals(paramMap, hospitalSetService.getSignKey(hoscode))) {
        //    throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        //}

        departmentService.save(paramMap);
        return Result.ok();
    }

    @ApiOperation(value = "获取分页列表")
    @PostMapping("department/list")
    public Result department(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        //非必填
        String depcode = (String)paramMap.get("depcode");
        int page = StringUtils.isEmptyOrWhitespaceOnly((String)paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmptyOrWhitespaceOnly((String)paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));

        if(StringUtils.isEmptyOrWhitespaceOnly(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        String hospSign = (String) paramMap.get("sign");
        String signKey = MD5.encrypt(hospitalSetService.getSignKey(hoscode));
        if (!Objects.equals(hospSign, signKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        DepartmentQueryVo departmentQueryVo = new DepartmentQueryVo();
        departmentQueryVo.setHoscode(hoscode);
        departmentQueryVo.setDepcode(depcode);
        Page<Department> pageModel = departmentService.selectPage(page, limit, departmentQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "删除科室")
    @PostMapping("department/remove")
    public Result removeDepartment(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        //必填
        String depcode = (String)paramMap.get("depcode");
        if(StringUtils.isEmptyOrWhitespaceOnly(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        String hospSign = (String) paramMap.get("sign");
        String signKey = MD5.encrypt(hospitalSetService.getSignKey(hoscode));
        if (!Objects.equals(hospSign, signKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }


        departmentService.remove(hoscode, depcode);
        return Result.ok();
    }

    @ApiOperation(value = "上传排班")
    @PostMapping("saveSchedule")
    public Result saveSchedule(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        if(StringUtils.isEmptyOrWhitespaceOnly(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        String hospSign = (String) paramMap.get("sign");
        String signKey = MD5.encrypt(hospitalSetService.getSignKey(hoscode));
        if (!Objects.equals(hospSign, signKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.save(paramMap);
        return Result.ok();
    }

    @ApiOperation(value = "获取排班分页列表")
    @PostMapping("schedule/list")
    public Result schedule(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        //非必填
        String depcode = (String)paramMap.get("depcode");
        int page = StringUtils.isEmptyOrWhitespaceOnly((String) paramMap.get("page")) ? 1 : Integer.parseInt((String)paramMap.get("page"));
        int limit = StringUtils.isEmptyOrWhitespaceOnly((String) paramMap.get("limit")) ? 10 : Integer.parseInt((String)paramMap.get("limit"));

        if(StringUtils.isEmptyOrWhitespaceOnly(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        String hospSign = (String) paramMap.get("sign");
        String signKey = MD5.encrypt(hospitalSetService.getSignKey(hoscode));
        if (!Objects.equals(hospSign, signKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        ScheduleQueryVo scheduleQueryVo = new ScheduleQueryVo();
        scheduleQueryVo.setHoscode(hoscode);
        scheduleQueryVo.setDepcode(depcode);
        Page<Schedule> pageModel = scheduleService.findPageSchedule(page , limit, scheduleQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "删除科室")
    @PostMapping("schedule/remove")
    public Result removeSchedule(HttpServletRequest request) {
        Map<String, Object> paramMap = HttpRequestHelper.switchMap(request.getParameterMap());
        //必须参数校验 略
        String hoscode = (String)paramMap.get("hoscode");
        //必填
        String hosScheduleId = (String)paramMap.get("hosScheduleId");
        if(StringUtils.isEmptyOrWhitespaceOnly(hoscode)) {
            throw new YyghException(ResultCodeEnum.PARAM_ERROR);
        }
        //签名校验
        String hospSign = (String) paramMap.get("sign");
        String signKey = MD5.encrypt(hospitalSetService.getSignKey(hoscode));
        if (!Objects.equals(hospSign, signKey)) {
            throw new YyghException(ResultCodeEnum.SIGN_ERROR);
        }

        scheduleService.remove(hoscode, hosScheduleId);
        return Result.ok();
    }
}
