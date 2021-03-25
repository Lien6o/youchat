package com.youchat.creative.factory.esayexcel;

import com.alibaba.excel.EasyExcel;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;

public class FileFunctionGet {

    /**    Expression exp = AviatorEvaluator.getInstance().compileScript("examples/if_return.av");

     * https://www.e-iceblue.cn/spirexlsjava_formulas/insert-and-read-formulas-in-excel-in-java.html
     * @param args
     */
    @SneakyThrows
    public static void main(String[] args) {
        String fileName =  "/Users/enboli/Downloads/test-function.xlsx";
        File file = new File(fileName);
        InputStream inputStream = new FileInputStream(file);
        //    LaborBillExcelListener readListener = new LaborBillExcelListener(lmsLaborBillExcelDataLogRepo, lmsLaborBillExcelUploadTaskRepo, excelConfig, task);
        //
        //        try {
        //            EasyExcel.read(file.getInputStream(), readListener).sheet().doRead();
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        EasyExcel.read(file,CellDataReadDemoData.class, new DemoDataListener()).sheet().doRead();
    }
}
