package com.xuwei.yygh.cmn.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xuwei.yygh.model.cmn.Dict;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author yxw
* @description 针对表【hospital_set(医院设置表)】的数据库操作Service
* @createDate 2021-12-22 13:21:37
*/
public interface DictService extends IService<Dict> {
    // 根据数据id查询子数据列表
    List<Dict> findChlidData(Long id);

    // 数据字典导出
    void exportDictData(HttpServletResponse response);

    // 数据字典导入
    void importDictData(MultipartFile file);
}
