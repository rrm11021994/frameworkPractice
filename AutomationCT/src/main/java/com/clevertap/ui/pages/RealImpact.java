package com.clevertap.ui.pages;

import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class RealImpact {

    WebDriver driver;

    @FindBy(xpath = "//*[contains(@class,'riButton')]")
    private WebElement viewRealImpactBtn;
    @FindBy(xpath = "//*[contains(@class,'cardHeader')]")
    private WebElement realImpactCardHeader;
    @FindBy(xpath = "//*[contains(@class,'cancel')]")
    private WebElement cancel;
    @FindBy(xpath = "//*[@class='pull-left']/span[text()]")
    private WebElement realHeader;
    @FindBy(xpath = "//*[@class='pull-left']/span")
    private WebElement PageTitle;
    @FindBy(xpath = "//*[@class='ct-breadcrumb_meta tooltip-bottom']/span/span")
    private WebElement ChartTitle;
    @FindBy(xpath="//img[contains(@class,'js-pane-container_close')]")
    private WebElement Closebutton;



    public WebElement getStickyNavItem(String nameOfItem) {

        String xpathFirst = "//*[contains(@class,'navItemText') and text()='";
        String xpathSecond = "']";
        String finalXpath = xpathFirst + nameOfItem + xpathSecond;
        WebElement navItems = driver.findElement(By.xpath(finalXpath));
        return navItems;

    }

    public WebElement getChartComponent(String TitleOfComponent) {
        String xpathFirst = "//*[contains(@class,'rfm-grid__info')]//*[text()='";
        String xpathSecond = "']";
        String finalXpath = xpathFirst + TitleOfComponent + xpathSecond;
        WebElement componentItems = driver.findElement(By.xpath(finalXpath));
        componentItems.click();
        return componentItems;



    }


    public void clickViewRealImpact() {
        SeleniumUtils.performClick(driver, viewRealImpactBtn);
    }

//    public void clickCancel() {
//        cancel.click();
//    }



    public void closeTheCurrentPage(){
        SeleniumUtils.performClick(driver,Closebutton);
    }


    public void getSpeedometerForRealImp(String realImpactComponent) {
        WebElement stickyQuotient =this.getStickyNavItem(realImpactComponent);
        SeleniumUtils.performClick(driver,stickyQuotient);
        this.clickViewRealImpact();
    }

    public void getChartOfEachComponent(String realChartComponent) {
        WebElement compTitle =this.getChartComponent(realChartComponent);
        SeleniumUtils.performClick(driver,compTitle);

    }




    public String getHeaderText(){
        return PageTitle.getText();
    }

    public String getRFMItemsHeaderText(){
        return ChartTitle.getText();
    }

    public WebElement getRFMItemsHeaderElement(){
        return ChartTitle;
    }



    public String getRealImpactCardHeaderText() {
        return SeleniumUtils.getElementText(driver, realImpactCardHeader);
    }


    public RealImpact(WebDriver previousBrowserDriver) {
        this.driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);

    }



}