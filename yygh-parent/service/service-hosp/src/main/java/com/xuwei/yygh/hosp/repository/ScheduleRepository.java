package com.xuwei.yygh.hosp.repository;

import com.xuwei.yygh.model.hosp.Schedule;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Description 排班管理接口
 * @Date 2022/1/12 21:18
 * @Author yxw
 */
@Repository
public interface ScheduleRepository extends MongoRepository<Schedule, String> {
    //根据医院编号 和 排班编号查询
    Schedule getScheduleByHoscodeAndHosScheduleId(String hoscode, String hosScheduleId);

    //根据医院编号 、科室编号和工作日期，查询排班详细信息
    List<Schedule> findScheduleByHoscodeAndDepcodeAndWorkDate(String hoscode, String depcode, Date toDate);
}