package com.clevertap.ui.pages.engage_page;

import com.clevertap.BaseTest;
import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TableUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;

public class ControlGroupPage {
    private WebDriver driver;
    private SweetAlert sweetAlertObj;


    @FindBy(xpath = "//*[@class='ct-breadcrumb']//a[@data-t='tooltip']")
    private WebElement tooltip;
    @FindBy(xpath = "//*[@class='campaign-controls']//li")
    private List<WebElement> controlGroupTabs;
    @FindBy(xpath = "//*[@data-original-title='Delete']")
    private WebElement deleteIcon;
    @FindBy(xpath = "//*[@data-original-title='Please delete all Custom control groups to enable deletion of the System control group.']")
    private WebElement systemControlDeleteIcon;
    @FindBy(xpath = "//*[contains(@class,'js-create-custom-cg-btn')]")
    private WebElement customControlGroupPlusButton;
    @FindBy(className = "enable_system_cg_text")
    private WebElement warningMessageForAddingSystemControlGroup;
    @FindBy(xpath = "//a[text()='Add a system control group']")
    private WebElement addSystemControlGroupElement;
    @FindBy(xpath = "//a[text()='Continue']")
    private WebElement continueButton;
    @FindBy(xpath = "//*[@id='applyToAllActiveSegs']/../..//a[text()='Create']")
    private WebElement createButton;
    @FindBy(className = "js-scg-created-by")
    private WebElement systemControlGrpCreatedBy;
    @FindBy(className = "js-new-custom-cg-name")
    private WebElement customGroupName;
    @FindBy(className = "js-new-custom-cg-purpose")
    private WebElement customGroupPurpose;
    @FindBy(xpath = "//*[@id='createCustomControlGroup']//a[text()='Create']")
    private WebElement createCustomGroupButton;
    @FindBy(xpath = "//a[@data-original-title=\"You don't have permission to delete System control group.\"]")
    private WebElement disableDeleteSystemControlBtn;


    public void addCustomControlGroup(){
        customControlGroupPlusButton.click();
        SeleniumUtils.enterInputText(driver,customGroupName,"AutomationCustomControlGroup");
        SeleniumUtils.enterInputText(driver,customGroupPurpose,"AutomationCustomControlGroup Purpose");
        createCustomGroupButton.click();
        sweetAlertObj.sweetAlertConfirmOK();

    }

    public void addSystemControlGroup(){
        SeleniumUtils.performClick(driver,addSystemControlGroupElement);
        SeleniumUtils.performClick(driver,continueButton);
        SeleniumUtils.performClick(driver,createButton);
        sweetAlertObj.sweetAlertConfirmOK();
    }

    public boolean verifySystemGroupCreatedByField(){
        return systemControlGrpCreatedBy.getText().contains(BaseTest.getValue("UserName"));
    }

    public String getControlGroupsTooltipText() {
        Actions actions = new Actions(driver);
        actions.moveToElement(tooltip).perform();
        return tooltip.getAttribute("data-original-title");
    }


    public List<String> getTabsName() {
        List<String> tabsName = new ArrayList<>();
        for (WebElement contorlGroupTab : controlGroupTabs) {
            tabsName.add(contorlGroupTab.getText());
        }
        return tabsName;
    }

    public void switchToTab(String tabName) {
        for (WebElement contorlGroupTab : controlGroupTabs) {
            if (contorlGroupTab.getText().equalsIgnoreCase(tabName)) {
                SeleniumUtils.performClick(driver, contorlGroupTab);
                break;
            }
        }
    }

    public void deleteAllControlGroup() {

        List<WebElement> ellipsisIcons = driver.findElements(By.xpath("//*[@data-original-title='Show actions']"));
        int totalEllipsisIcons = ellipsisIcons.size();
        for (int i = 0; i < totalEllipsisIcons; i++) {
            WebElement icon = driver.findElement(By.xpath("(//*[@data-original-title='Show actions'])[" + driver.findElements(By.xpath("//*[@data-original-title='Show actions']")).size() + "]"));
            SeleniumUtils.performClick(driver, icon);
            SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@data-original-title='Delete']")));
            sweetAlertObj.sweetAlertConfirmOK();
            sweetAlertObj.sweetAlertConfirmOK();
        }
    }

    public ControlGroupPage(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
        sweetAlertObj = new SweetAlert(previousBrowserDriver);
    }

    public int getNumberOfReconrdsInControlGroupTable() {
        return TableUtility.getNumberOfRowsInTable(driver, "//*[@class='CT-table__left']");
    }

    public void deleteSystemControlGroup() {
        SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//*[@data-original-title='Delete']")));
        sweetAlertObj.sweetAlertConfirmOK();
        sweetAlertObj.sweetAlertConfirmOK();

    }


    public String getSystemControlGroupTooltipText() {
        WebElement systemControlGroupTooltipIcon = driver.findElement(By.xpath("//*[contains(text(),'System control group ')]/span"));
        Actions actions = new Actions(driver);
        actions.moveToElement(systemControlGroupTooltipIcon).perform();
        WebElement systemControlGroupTooltipWrap = driver.findElement(By.xpath("//*[contains(text(),'System control group ')]/span//p"));
        return systemControlGroupTooltipWrap.getText();
    }

    public boolean isSystemControlGroupTooltipIconExist() {
        WebElement systemControlGroupTooltipIcon = driver.findElement(By.xpath("//*[contains(text(),'System control group ')]/span"));
        return systemControlGroupTooltipIcon.isDisplayed();
    }

    public boolean getCustomControlButtonStatus(){
        return customControlGroupPlusButton.getAttribute("class").contains("is-disabled");
    }

    public String getMessageOnCustomControlGroupPage(){
        return warningMessageForAddingSystemControlGroup.getText();
    }

    //validating custom control group creation with special characters in name
    public boolean addCustomControlGroupWithSpecialCharInName() {
        customControlGroupPlusButton.click();
        SeleniumUtils.enterInputText(driver,customGroupName,"Automation'CustomControl\"Group");
        SeleniumUtils.enterInputText(driver,customGroupPurpose,"AutomationCustomControlGroup Purpose");
        createCustomGroupButton.click();
        return sweetAlertObj.getSweetAlertErrorSignal();
    }

    //validating custom control group creation with special characters in description
    public boolean addCustomControlGroupWithSpecialCharInDesc() {
        customControlGroupPlusButton.click();
        SeleniumUtils.enterInputText(driver,customGroupName,"AutomationCustomControlGroup");
        SeleniumUtils.enterInputText(driver,customGroupPurpose,"AutomationC'ustomControlGroup \"Purpose");
        createCustomGroupButton.click();
        return sweetAlertObj.getSweetAlertErrorSignal();
    }
}
