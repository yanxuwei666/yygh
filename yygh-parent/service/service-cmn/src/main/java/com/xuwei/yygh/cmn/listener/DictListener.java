package com.xuwei.yygh.cmn.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.xuwei.yygh.cmn.mapper.DictMapper;
import com.xuwei.yygh.model.cmn.Dict;
import com.xuwei.yygh.vo.cmn.DictEeVo;
import org.springframework.beans.BeanUtils;

/**
 * @Description 回调监听器
 * @Date 2022/1/6 10:33
 * @Author yxw
 */
public class DictListener extends AnalysisEventListener<DictEeVo> {

    private DictMapper dictMapper;

    public DictListener(DictMapper dictMapper) {
        this.dictMapper = dictMapper;
    }

    //一行一行读取,这里默认从第2行开始读取
    @Override
    public void invoke(DictEeVo dictEeVo, AnalysisContext analysisContext) {
        //调用方法添加数据库
        Dict dict = new Dict();
        BeanUtils.copyProperties(dictEeVo, dict);
        dictMapper.insert(dict);
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
