package com.xuwei.yygh.hosp.repository;

import com.xuwei.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description 医院管理接口
 * @Date 2022/1/11 13:13
 * @Author yxw
 */
@Repository
public interface HospitalRepository extends MongoRepository<Hospital, String> {
    Hospital getHospitalByHoscode(String hoscode);
}
