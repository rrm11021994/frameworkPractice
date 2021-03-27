package org.example;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExelOperations {
    public  void main(String args[]) throws IOException {
       ExelOperations exelOperations=new ExelOperations();
       exelOperations.getData();
    }

   @Test(dataProvider = "dp")
   public void login(String user,String password,String url){
        System.out.println(user+" "+ password+ " "+url);
   }
   @DataProvider(name="dp")
    public Object[][] getData() throws IOException {
        FileInputStream xlFile = new FileInputStream("/Users/rahul.mahajan/Downloads/ExcelOperations/src/main/java/org/example/dataset.xlsx");
        XSSFWorkbook wb = new XSSFWorkbook(xlFile);
        XSSFSheet sheet = wb.getSheetAt(0);
        int totalCols = sheet.getRow(0).getLastCellNum();
        System.out.println("total cols are "+totalCols);
        int totalRows = sheet.getLastRowNum();
       System.out.println("total rows are "+totalRows);
        Object[][] data = new Object[totalRows][totalCols];
        for (int r = 1; r <= totalRows; r++)
            for (int c = 0; c < totalCols; c++) {
                data[r - 1][c] = sheet.getRow(r).getCell(c).getStringCellValue().trim();
                System.out.println(sheet.getRow(r).getCell(c).getStringCellValue().trim());
                //System.out.println(data[r - 1][c]);
            }
        for (int r = 1; r <= totalRows; r++) {
            for (int c = 0; c < totalCols; c++) {
                System.out.print(data[r - 1][c]);
            }
            System.out.println();
        }
        return data;
    }

}
