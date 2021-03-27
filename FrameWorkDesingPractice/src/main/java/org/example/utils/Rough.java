package org.example.utils;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

public class Rough {
    @DataProvider(name = "dp")
    public Object[][] getData(Method m){
        ExcelReading excelReading=new ExcelReading("/Users/rahul.mahajan/Downloads/dataset.xlsx");
        String methodName= m.getName();
        return excelReading.dataProvider(methodName);
    }
}
