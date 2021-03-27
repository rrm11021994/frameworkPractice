package org.example.tests;
import org.example.BaseClass;
import org.example.pages.LoginPage;
import org.example.utils.Rough;
import org.testng.SkipException;
import org.testng.annotations.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;

public class loginTest extends BaseClass {
    LoginPage loginPage;
    @BeforeClass
    public void init(){
        BaseClass.initilization();
        loginPage=new LoginPage(driver);
        loginPage.LoginToApplication();
    }
    @Test(dataProviderClass = Rough.class,dataProvider = "dp")
    public void verifyPageTitleTest(Hashtable<String, String> table) throws IOException {
        if(!excel.isRunnable("verifyPageTitleTest","runSheet")){
            throw new SkipException("Not runnable test");
        };
        String expectedTitle="CleverTap Dashboard";
        String actualTitle=driver.getTitle();
        boolean result=expectedTitle.equals(actualTitle);
    }
    @Test
    public void verifyNeedHelpMenuTest() throws IOException {
        if(!excel.isRunnable("verifyNeedHelpMenuTest","runSheet")){
            throw new SkipException("Not runnable test");
        };
        loginPage.needHelpPresent();
    }
   @Test
    public void closeThePopUp(){
       if(!excel.isRunnable("closeThePopUp","runSheet")){
           throw new SkipException("Not runnable test");
       };
        loginPage.closeTheAlert();
    }
    @AfterClass
    public void tearDown(){
            extent.flush();
            driver.quit();
    }

    @DataProvider
    public Object[][] getData(){
        Object[][] data=new Object[][]
        {
            {
                "A", "B", "C"
            },
            {
                "D", "E", "F"
            }
        };
       /* data[0][0]="A";
        data[0][1]="B";
        data[0][2]="C";
        data[1][0]="D";
        data[1][1]="E";
        data[1][2]="F";*/
        return data;
    }
}
