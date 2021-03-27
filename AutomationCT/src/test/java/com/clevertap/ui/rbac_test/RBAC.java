package com.clevertap.ui.rbac_test;

import com.clevertap.BaseTest;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RBAC extends BaseTest{
    private Logger logger;
    private WebDriver driver;
    private List<String> systemRoles=new ArrayList<>();


    public enum access {
        READ(1),
        WRITE(2),
        APPROVE(3);

        private int value;

        access(int n) {
            this.value = n;
        }

        public int getValue() {
            return value;
        }
    }

    @BeforeClass(alwaysRun = true)
    public void initialize() {
        BaseTest baseTest = BaseTest.getInstance();
        driver = baseTest.getDriver();
        Mocha.openLeftNavMenu(driver, true, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
        logger = baseTest.configureLogger(getClass());
        systemRoles.add("Admin");
        systemRoles.add("Member");
        systemRoles.add("Creator");
        systemRoles.add("Agent");
        systemRoles.add("Approver");

    }


    @Test
    public void test1() {
        Map<String, Integer> map = new HashMap<String, Integer>();
        int runCounter=0;

        driver.findElement(By.className("search-input")).sendKeys(Keys.chord("manmohan+5001@clevertap.com"+Keys.RETURN));
        SeleniumUtils.pause(3/**/);
        String roles=driver.findElement(By.xpath("//*[text()='manmohan+5001@clevertap.com']/../../following-sibling::td[2]//span")).getAttribute("innerHTML");
        System.out.println(roles);
        String[] roleArray=roles.split(",");
        for (int i=0;i<roleArray.length;i++){
            Mocha.forceNavigate=true;
            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.ROLES.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
            driver.findElement(By.xpath("//*[@class='datatable-collection']//span[text()='"+roleArray[i].trim()+"']")).click();
            SeleniumUtils.pause(2);
            List<WebElement> rows = driver.findElements(By.xpath("//*[@class='datatable-collection']//tr/td[2]/span"));

        /*getting name of all sub components*/
            List<String> subComponents = new ArrayList<>();
            for (WebElement row : rows) {
                subComponents.add(row.getText());
            }

            for (String subComponentName : subComponents) {
                int acessSum=0;
                try {
                    WebElement subCOmpReadDiv = driver.findElement(By.xpath("//*[@class='datatable-collection']//span[text()='" + subComponentName + "']/../following-sibling::td[1]/div"));
                    if (subCOmpReadDiv.getAttribute("class").contains("showTick")) {
                        if (map.get(subComponentName) == null) {
                            map.put(subComponentName, access.READ.getValue());
                        }else {
                            acessSum=acessSum+ access.READ.getValue();
                        }

                    }

                    try {

                        if (runCounter<1){
                            if (systemRoles.contains(roleArray[i].trim())){
                                WebElement subCOmpWriteSystemDiv = driver.findElement(By.xpath("//*[@class='datatable-collection']//span[text()='" + subComponentName + "']/../following-sibling::td[2]/div"));
                                if (subCOmpWriteSystemDiv.getAttribute("class").contains("showTick")) {
                                    map.put(subComponentName, map.get(subComponentName) + access.WRITE.getValue());
                                }
                                }else {
                                WebElement subCOmpWriteCustomDiv = driver.findElement(By.xpath("//*[@class='datatable-collection']//span[text()='" + subComponentName + "']/../following-sibling::td[2]//input"));
                                if (subCOmpWriteCustomDiv.isSelected()) {
                                    map.put(subComponentName, map.get(subComponentName) + access.WRITE.getValue());
                                }

                            }
                        }else {
                            if (systemRoles.contains(roleArray[i].trim())){
                                WebElement subCOmpWriteSystemDiv = driver.findElement(By.xpath("//*[@class='datatable-collection']//span[text()='" + subComponentName + "']/../following-sibling::td[2]/div"));
                                if (subCOmpWriteSystemDiv.getAttribute("class").contains("showTick")) {
                                    acessSum=acessSum+ access.WRITE.getValue();
                                }

                                }else {
                                WebElement subCOmpWriteCustomDiv = driver.findElement(By.xpath("//*[@class='datatable-collection']//span[text()='" + subComponentName + "']/../following-sibling::td[2]//input"));
                                if (subCOmpWriteCustomDiv.isSelected()) {
                                    acessSum=acessSum+ access.WRITE.getValue();
                                }
                            }
                        }




//                        WebElement subCOmpWriteDiv = driver.findElement(By.xpath("//*[@class='datatable-collection']//span[text()='" + subComponentName + "']/../following-sibling::td[2]//input"));
//                        if (runCounter<1){
//                            if (systemRoles.contains(roleArray[i].trim())){
//                                if (subCOmpWriteDiv.getAttribute("class").contains("showTick")) {
//                                    map.put(subComponentName, map.get(subComponentName) + access.WRITE.getValue());
//                                }
//                            }else {
//                                if (subCOmpWriteDiv.isSelected()) {
//                                    map.put(subComponentName, map.get(subComponentName) + access.WRITE.getValue());
//                                }
//                            }
//
//                        }else {
//                            if (subCOmpWriteDiv.isSelected()) {
//                                acessSum=acessSum+access.WRITE.getValue();
//                            }
//                        }

                    } catch (Exception e) {

                    }

                    if (runCounter<1){
                        if (systemRoles.contains(roleArray[i].trim())){
                            WebElement subCompApprovSystemDiv = driver.findElement(By.xpath("//*[@class='datatable-collection']//span[text()='" + subComponentName + "']/../following-sibling::td[3]/div"));
                            if (subCompApprovSystemDiv.getAttribute("class").contains("showTick")) {
                                map.put(subComponentName, map.get(subComponentName) + access.APPROVE.getValue());
                            }
                        }else {
                            WebElement subCOmpApprovCustomDiv = driver.findElement(By.xpath("//*[@class='datatable-collection']//span[text()='" + subComponentName + "']/../following-sibling::td[3]//input"));
                            if (subCOmpApprovCustomDiv.isSelected()) {
                                map.put(subComponentName, map.get(subComponentName) + access.APPROVE.getValue());
                            }

                        }
                    }else {
                        if (systemRoles.contains(roleArray[i].trim())){
                            WebElement subCompApprovSystemDiv = driver.findElement(By.xpath("//*[@class='datatable-collection']//span[text()='" + subComponentName + "']/../following-sibling::td[3]/div"));
                            if (subCompApprovSystemDiv.getAttribute("class").contains("showTick")) {
                                acessSum=acessSum+ access.APPROVE.getValue();
                            }

                        }else {
                            WebElement subCOmpApprovCustomDiv = driver.findElement(By.xpath("//*[@class='datatable-collection']//span[text()='" + subComponentName + "']/../following-sibling::td[3]//input"));
                            if (subCOmpApprovCustomDiv.isSelected()) {
                                acessSum=acessSum+ access.APPROVE.getValue();
                            }
                        }
                    }

//                    try {
//                        WebElement subCompApproDiv = driver.findElement(By.xpath("//*[@class='datatable-collection']//span[text()='" + subComponentName + "']/../following-sibling::td[3]//input"));
//                        if (runCounter<1){
//                            if (subCompApproDiv.isSelected()) {
//                                map.put(subComponentName, map.get(subComponentName) + access.APPROVE.getValue());
//                            }else {
//                                if (subCompApproDiv.isSelected()) {
//                                    acessSum=acessSum+access.APPROVE.getValue();
//                                }
//                            }
//                        }
//
//                    } catch (Exception e) {
//                        map.put(subComponentName, map.get(subComponentName));
//
//                    }

                } catch (Exception e) {
//                    map.put(subComponentName, map.get(subComponentName));

                }

                if (runCounter>=1){
                    if (map.get(subComponentName)<=acessSum){

                    }else {
                        map.put(subComponentName,acessSum);
                    }
                }
            }
            System.out.println("********** "+map);
            runCounter=runCounter+1;
        }






//        System.out.println(map);
    }
}
