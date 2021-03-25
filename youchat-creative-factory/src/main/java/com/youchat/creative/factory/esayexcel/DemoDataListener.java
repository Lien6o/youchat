package com.youchat.creative.factory.esayexcel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// 有个很重要的点 DemoDataListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
public class DemoDataListener extends AnalysisEventListener<CellDataReadDemoData> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CellDataReadDemoData.class);



    @Override
    public void invoke(CellDataReadDemoData data, AnalysisContext analysisContext) {
        LOGGER.info("解析到一条数据:{}",  data );
        LOGGER.info("解析到一条数据:{}",  data.getFormulaValue().getFormulaValue() );

    }

    /**DemoDataListener
     * 所有数据解析完成了 都会来调用
     *
     * @param context
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        LOGGER.info("所有数据解析完成！");
    }

}