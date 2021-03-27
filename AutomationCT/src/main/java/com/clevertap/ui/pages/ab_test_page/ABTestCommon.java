package com.clevertap.ui.pages.ab_test_page;

import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TableUtility;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class ABTestCommon {

    protected WebDriver driver;

    @FindBy(xpath = "//button[text()=' AB Test']") private WebElement addABTestBtn;
    @FindBy(xpath = "//ol[@class='breadcrumb']/li") private List<WebElement> breadCrumb;
    @FindBy(xpath = "//div[contains(@class,'radio')]//div[contains(text(),'Android')]") private WebElement androidRadioBtn;
    @FindBy(xpath = "//div[contains(@class,'radio')]//div[contains(text(),'iOS')]") private WebElement iosRadioBtn;
    @FindBy(xpath = "//button[contains(text(),'Begin setup')]") private  WebElement beginSetupBtn;
    @FindBy(xpath = "//a[text()='Save as draft']") private WebElement saveAsDraftLink;
    @FindBy(xpath = "//input[@placeholder='Experiment Name']") private WebElement experimentNameInputBox;
    @FindBy(xpath = "//input[@name='search']") private  WebElement experimentSearchBox;
    @FindBy(className = "editable-title") private WebElement testNameBox;

    public void addTest(){
        SeleniumUtils.performClick(driver,addABTestBtn);
    }

    public String getHeader(){
        String header = "";
        for (WebElement ele:breadCrumb) {
            header += ele.getText()+"/";
        }
        return header;
    }

    public void createTest(Boolean osRequired,String os,String name){
        addTest();
        SeleniumUtils.waitForPageLoaded(driver);
        if(osRequired){
            if(os.equalsIgnoreCase("android")){
                SeleniumUtils.performClick(driver,androidRadioBtn);
            }
            else if (os.equalsIgnoreCase("ios")){
                SeleniumUtils.performClick(driver,iosRadioBtn);
            }
            SeleniumUtils.performClick(driver,beginSetupBtn);
        }
        SeleniumUtils.enterInputText(driver,testNameBox,name);
    }

    public void saveAsDraft(){
        SeleniumUtils.performClick(driver,saveAsDraftLink);
    }

    public void nameTheExperiment(String newName){
        SeleniumUtils.enterInputText(driver,experimentNameInputBox,newName);
    }

    public void searchForExperiment(String experimentName){
        SeleniumUtils.enterInputText(driver,experimentSearchBox,experimentName);
    }

    public boolean checkIfExperimentExist(String experiment){
        searchForExperiment(experiment);
        return checkIfDataExist(1,experiment);
    }

    public boolean checkIfCorrectOS(String experiment, String os){
        searchForExperiment(experiment);
        return checkIfDataExist(3,os);
    }

    public boolean checkIfStatusIsCorrect(String experiment, String status){
        searchForExperiment(experiment);
        return checkIfDataExist(4,status);
    }

    public boolean checkIfDataExist(int columnNo, String dataToCompare){
        boolean exists = false;
        TableUtility.InitializeTable(driver,"//tbody[@class='datatable-collections']");
        List<WebElement> listOfDataInColumn = TableUtility.getDataFromSpecificCell(columnNo);
        for (WebElement ele: listOfDataInColumn) {
            if(ele.getText().equalsIgnoreCase(dataToCompare)){
                exists = true;
                break;
            }
        }
        return exists;
    }

    public ABTestCommon() {
       //do Nothing
    }
}
