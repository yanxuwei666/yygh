package com.xuwei.yygh.hosp.controller.api;

import com.xuwei.yygh.common.exception.YyghException;
import com.xuwei.yygh.common.helper.HttpRequestHelper;
import com.xuwei.yygh.common.result.Result;
import com.xuwei.yygh.common.result.ResultCodeEnum;
import com.xuwei.yygh.common.utils.MD5;
import com.xuwei.yygh.hosp.service.HospitalService;
import com.xuwei.yygh.hosp.service.HospitalSetService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

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
}
