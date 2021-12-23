package com.xuwei.yygh.hosp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Description 医院设置启动类
 * @Date 2021/12/21 13:22
 * @Author yxw
 */
@SpringBootApplication
@MapperScan(basePackages = "com.xuwei.yygh.hosp.mapper") // 添加扫描mapper注解
public class ServiceHospApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServiceHospApplication.class, args);
    }
}
