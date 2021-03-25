package com.youchat.creative.factory.esayexcel;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.CellData;
import lombok.Data;

@Data
public class CellDataReadDemoData {
    @ExcelProperty(index = 0)
    private CellData<String>  string;
    @ExcelProperty(index = 1)
    private  CellData<String>  doubleData1;
    @ExcelProperty(index = 2)
    private  CellData<String>  doubleData2;
    @ExcelProperty(index = 3)
    private  CellData<String>  doubleData3;
    @ExcelProperty(index = 4)
    private  CellData<String>  formulaValue;
}