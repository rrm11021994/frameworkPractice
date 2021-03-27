package com.clevertap.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;


public class TableUtility {


    public static String xpathOfTable=null;

    public static WebDriver driver;

    public static void InitializeTable(WebDriver driverFromTest,String xpath)
    {
        driver = driverFromTest;
        xpathOfTable = xpath;
    }

    public static int GetTotalRows()
    {
        List<WebElement> RowList = driver.findElements(By.xpath(xpathOfTable + "/div/div[contains(@class,\"CT-table__left\")]/div"));
        int TotalRows = RowList.size() - 1;
        return TotalRows;

    }

    public static int GetTotalColumns()
    {
        List<WebElement> ColList = driver.findElements(By.xpath(xpathOfTable + "/div/div/div[contains(@class,\"rowindex_0\")]/div"));
        int TotalCol = ColList.size();
        return TotalCol;
    }

    public static WebElement GetCellData(int RowNo, int ColNo)
    {
        String xpath = xpathOfTable + "/div/div[1]/div["+ (RowNo + 1) +"]/div["+ ColNo +"]";
        WebElement CellData = driver.findElement(By.xpath(xpath));
        return CellData;
    }


    public static List getDataFromSpecificCell(int colNum){
        List cellValue=new ArrayList();
        List<WebElement> rows= driver.findElement(By.xpath(xpathOfTable)).findElements(By.tagName("tr"));
        for (WebElement row:rows){
            try {
                System.out.println("Row : "+row.getText());
                List<WebElement> cells=row.findElements(By.tagName("td")); /* ./ *checks for all the child object*/
                cellValue.add(cells.get(colNum));
            }catch (IndexOutOfBoundsException ex){
                //do nothing
            }

        }
        return cellValue;
    }

    /*This method is used to get data from tables which has only Divs*/
    public static List<String> getDataFromRightTable(String rightTableXpath,int colCOunt,WebDriver driver){

        List<String> columnDataList=new ArrayList<>();

        /*gives total rows*/
        /*Rows start with index 2 as header is at first row*/
        List<WebElement> rows=driver.findElements(By.xpath(rightTableXpath+"/div"));
        int numberOfRows=rows.size();
        System.out.println(numberOfRows);
        if (numberOfRows<=2){
            WebElement column=rows.get(1).findElement(By.xpath(rightTableXpath+"/div["+numberOfRows+"]/div["+colCOunt+"]"));
            SeleniumUtils.elementHighlighter(driver,column);
            columnDataList.add(column.getAttribute("innerText"));
        }else {
            for (int i = 2; i < numberOfRows; i++) {
                System.out.println(i);
                /*gives total columns in a row*/
                /*column start with count 1*/
                WebElement column = rows.get(i).findElement(By.xpath(rightTableXpath + "/div" + "[" + i + "]/div[" + colCOunt + "]"));
                SeleniumUtils.elementHighlighter(driver, column);
                columnDataList.add(column.getAttribute("innerText"));
            }
        }

        return columnDataList;

    }

    /*Below method will get number of rows from left/right table
    * need to provide the xpath for table only
    * append the remaining xpath for counting the number of rows in table like div*/

    public static int getNumberOfRowsInTable(WebDriver driver,String tableXPath){
        List<WebElement> leftTableObject=driver.findElements(By.xpath(tableXPath+"/div"));
        return leftTableObject.size();

    }
}





