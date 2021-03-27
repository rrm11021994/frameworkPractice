package org.example.utils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.example.BaseClass;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

public class ExcelReading extends BaseClass {
    FileInputStream file;
    XSSFWorkbook wb;
    XSSFSheet sheet;
    XSSFCell cell;
    XSSFRow row;
    public ExcelReading(String filepath){
        try {
            file=new FileInputStream(filepath);
            wb=new XSSFWorkbook(file);
            file.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
    public int getRowCount(String sheetName){
        int index = wb.getSheetIndex(sheetName);
        if(index==-1)
            return 0;
        else{
            sheet = wb.getSheetAt(index);
            int number=sheet.getLastRowNum();
            return number;
        }

    }

    public String getData(String sheetName,int columnNum,int rowNum){
        if(rowNum<0)
            return "";
        int index=wb.getSheetIndex(sheetName);
        if(index<0)
            return "";
        sheet = wb.getSheetAt(index);
        row = sheet.getRow(rowNum);
        if(row==null)
            return "";
        cell = row.getCell(columnNum);
        if(cell==null)
            return "";
        if(cell.getCellType()==CellType.STRING)
            return cell.getStringCellValue();
        else if(cell.getCellType()==CellType.NUMERIC)
            return String.valueOf(cell.getNumericCellValue());
        else if(cell.getCellType()==CellType.BLANK)
            return "";
        else if(cell.getCellType()==CellType.BOOLEAN)
            return cell.getStringCellValue();
        else
            return "";
    }
    public String getData(String sheetName,String columnName,int rowNum){
        try{
            if(rowNum<0)
                return "";
            int index=wb.getSheetIndex(sheetName);
            if(index<0)
                return "";
            sheet = wb.getSheetAt(index);
            row = sheet.getRow(0);
            if(row==null)
                return "";
            int totalCols=row.getLastCellNum();
            int columnNum=-1;
            for(int i=0;i<=totalCols;i++){
                if(row.getCell(i).getStringCellValue().trim().equals(columnName.trim())){
                    columnNum=i;
                    break;
                }
            }
            if(columnNum==-1){
                return "";
            }
            row=sheet.getRow(rowNum);
            cell = row.getCell(columnNum);
            if(cell==null)
                return "";
            if(cell.getCellType()==CellType.STRING)
                return cell.getStringCellValue();
            else if(cell.getCellType()==CellType.NUMERIC)
                return String.valueOf(cell.getNumericCellValue());
            else if(cell.getCellType()==CellType.BLANK)
                return "";
            else if(cell.getCellType()==CellType.BOOLEAN)
                return cell.getStringCellValue();
            else
                return "";
        }
        catch (Exception e){
            System.out.println("no rows or columns found");
        }
        return "";
    }

    public Object[][] dataProvider(String m){
        String tcName=m;
        sheet= wb.getSheet("datasheet");
        int dataStartFromRow=0;
        int columnDataStartFrom=0;
        int dataEndAtRow=0;
        int columnEndAt=0;
        int lastRow=sheet.getLastRowNum();
        for(int i=0;i<=lastRow;i++){
            if(excel.getData("datasheet",0,i).equals(tcName)){
                dataStartFromRow=i+2;
                columnDataStartFrom=i+1;
                break;
            }
        }
        int i=0;
            while(excel.getData("datasheet",0,dataStartFromRow+i)!=""){
                i++;
            }
        dataEndAtRow=i;
            int c=0;
            while (excel.getData("datasheet",c,columnDataStartFrom)!=""){
                c++;
            }
            columnEndAt=c;
            Object[][] data=new Object[dataEndAtRow][1];
            for(int j=0;j<dataEndAtRow;j++){
                Hashtable<String,String> table=new Hashtable<>();
                for(int k=0;k<columnEndAt;k++){
                    String data1= excel.getData("datasheet",k,dataStartFromRow+j);
                    String columnName=excel.getData("datasheet",k,columnDataStartFrom);
                    table.put(columnName,data1);
                    data[j][0]=table;
                }
            }
            return data;
    }

    public boolean isRunnable(String methodName,String sheetName){
        boolean isRunnable=false;
        String testCaseName;
        String status="N";
        int testCaseRowNum=-1;
         for(int i=0;i<=excel.getRowCount(sheetName);i++){
            testCaseName= getData(sheetName,0,i);
            if(testCaseName.equals(methodName)){
               status=getData(sheetName,1,i);
               if(status.equalsIgnoreCase("Y")){
                   return true;
               }
               else
                   return false;
            }
         }

        return false;
    }

  /*  public static void main(String args[]){
        Rough rough=new Rough("/Users/rahul.mahajan/Downloads/dataset.xlsx");
        String testCase;
        String sheetName="datasheet";
        int index;
        boolean flag=false;
        int totalRows=rough.getRowCount("datasheet");
        for(index=0;index<totalRows;index++){
            testCase= rough.getData(sheetName,0,index);
            System.out.println(testCase);
            if(testCase.equalsIgnoreCase("TestCase2")){
                flag=true;
                break;
            }
        }
        if(flag==true)
            System.out.println("method data will start from row number = "+ index);
        else
            System.out.println("data not found");
        int datastartRow=index+2;
        System.out.println("data will start from row number = "+datastartRow);
        String datad;
        int sd=-1;
        for(int dataRow=datastartRow;dataRow<=totalRows+1;dataRow++)
        {
            datad=rough.getData(sheetName,0,dataRow);
            System.out.println(dataRow+" -- "+datad);
            if(datad.equals("")){
                sd=dataRow;
                break;
            }
        }
        System.out.println(sd);
    } */

}
