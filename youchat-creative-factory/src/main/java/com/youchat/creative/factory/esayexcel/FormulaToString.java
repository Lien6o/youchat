package com.youchat.creative.factory.esayexcel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
  import org.apache.poi.hssf.usermodel.HSSFSheet;
  import org.apache.poi.hssf.usermodel.HSSFRow;
  import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
 
 public class FormulaToString {
 
     /**
      */
     public void fileInput() throws IOException {

         XSSFWorkbook hw = new XSSFWorkbook(new FileInputStream(
                 "/Users/enboli/Downloads/test-function.xlsx"));
         XSSFSheet hsheet = hw.getSheet("Sheet1");
         XSSFRow hrow = hsheet.getRow(1);
         XSSFCell hcell = hrow.getCell(4);
         String cellValue = this.getCellValue(hcell);
         System.out.println(cellValue);
 
     }
 
     public String getCellValue(XSSFCell cell) {
         String value = null;
         if (cell != null) {
             switch (cell.getCellType()) {
             case XSSFCell.CELL_TYPE_FORMULA:
                 // cell.getCellFormula();
                 try {
                     value = String.valueOf(cell.getNumericCellValue());
                 } catch (IllegalStateException e) {
                     value = String.valueOf(cell.getRichStringCellValue());
                 }
                 break;
             case XSSFCell.CELL_TYPE_NUMERIC:
                 value = String.valueOf(cell.getNumericCellValue());
                 break;
             case XSSFCell.CELL_TYPE_STRING:
                 value = String.valueOf(cell.getRichStringCellValue());
                 break;
             }
         }
 
         return value;
     }
 
     public static void main(String[] args) {
         try {
             // TODO Auto-generated method stub
             FormulaToString fts = new FormulaToString();
             fts.fileInput();
         } catch (IOException e) {
             e.printStackTrace();
         }
     }
 
 }