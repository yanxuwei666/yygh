package com.xuwei.yygh.hosp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xuwei.yygh.hosp.domain.HospitalSet;
import com.xuwei.yygh.hosp.service.HospitalSetService;
import com.xuwei.yygh.hosp.mapper.HospitalSetMapper;
import org.springframework.stereotype.Service;

/**
* @author yxw
* @description 针对表【hospital_set(医院设置表)】的数据库操作Service实现
* @createDate 2021-12-22 13:21:37
*/
@Service
public class HospitalSetServiceImpl extends ServiceImpl<HospitalSetMapper, HospitalSet>
    implements HospitalSetService{

    @Override
    public String getSignKey(String hoscode) {
        QueryWrapper<HospitalSet> wrapper = new QueryWrapper<>();
        wrapper.eq("hoscode", hoscode);
        HospitalSet hospitalSet = baseMapper.selectOne(wrapper);
        return hospitalSet.getSignKey();
    }
}




