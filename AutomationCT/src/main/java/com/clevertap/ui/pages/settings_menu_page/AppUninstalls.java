package com.clevertap.ui.pages.settings_menu_page;

import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.Set;


public class AppUninstalls {
    WebDriver driver;

    @FindBy(xpath = "//*[@class='ct-breadcrumb']")
    private WebElement PageTitle;
    @FindBy(xpath = "//*[contains(@class,'Switch')]")
    private WebElement androidSwitchBtn;
    @FindBy(xpath = "//*[@class='grid MT20 P20']//a[contains(text(),'Click here for more information')]")
    private WebElement linkToValidate;
    @FindBy(xpath = "//*[@class='Switch_slider']") private WebElement switchSlider;
    @FindBy(xpath = "//*[contains(@class,'Switch')]") private WebElement slider;
    @FindBy(xpath = "//table[contains(@class,'gtwTable')]")private WebElement appUninstallTable;

    public String getSwitchSliderInnefrText(){
        return SeleniumUtils.getElementText(driver,switchSlider);
    }

    public String getCSSValue(){
        return slider.getCssValue("background-color");
    }


    public void clickSliderAgain(){
        SeleniumUtils.performClick(driver,slider);
    }


    public String getHeaderText() {
        return PageTitle.getText();

    }

    public boolean getValidLink() {

        boolean status = false;
        SeleniumUtils.performClick(driver,linkToValidate);
        Set<String> windowHandles = driver.getWindowHandles();
        for (String handle : windowHandles) {
            if (driver.switchTo().window(handle).getTitle().contains("Android")) {
                status = true;

            }
        }
        return status;

    }


    public AppUninstalls(WebDriver previousBrowserDriver) {

        driver = previousBrowserDriver;

        PageFactory.initElements(previousBrowserDriver, this);

    }
}
