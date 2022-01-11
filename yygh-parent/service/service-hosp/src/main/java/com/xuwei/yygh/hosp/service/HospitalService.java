package com.xuwei.yygh.hosp.service;

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
}
