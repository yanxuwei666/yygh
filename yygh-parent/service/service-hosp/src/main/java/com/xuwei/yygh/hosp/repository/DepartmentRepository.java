package com.xuwei.yygh.hosp.repository;

import com.xuwei.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @Description 科室管理接口
 * @Date 2022/1/11 13:13
 * @Author yxw
 */
public interface DepartmentRepository extends MongoRepository<Department, String> {
    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
