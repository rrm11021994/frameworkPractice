package org.example.utils;
import org.apache.commons.io.FileUtils;
import org.example.BaseClass;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Date;

public class Utilities extends BaseClass {
    static Date date;
    static String filePath;
    TakesScreenshot scDriver;
    static File srcFile;
        public static String getScreenShot(){
            date=new Date();
            srcFile=((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
            filePath=date.toString().replace(":","_").replace(" ","_");
            String imagepath=System.getProperty("user.dir")+"/src/main/java/org/example/images/"+filePath+".png";
            try {
                FileUtils.copyFile(srcFile,new File(imagepath));
            } catch (IOException e) {
                test.fail(e.getMessage());
            }
            return imagepath;
        }

    }
