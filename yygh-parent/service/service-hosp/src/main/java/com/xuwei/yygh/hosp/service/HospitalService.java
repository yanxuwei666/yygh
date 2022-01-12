package com.xuwei.yygh.hosp.service;

import com.xuwei.yygh.model.hosp.Hospital;

import java.util.Map;

/**
 * @Description TODO
 * @Date 2022/1/11 13:15
 * @Author yxw
 */
public interface HospitalService {
    /**
     * 上传医院信息
     * @param paramMap
     */
    void save(Map<String, Object> paramMap);

    /**
     * 查询医院
     * @param hoscode
     * @return
     */
    Hospital getByHoscode(String hoscode);
}
