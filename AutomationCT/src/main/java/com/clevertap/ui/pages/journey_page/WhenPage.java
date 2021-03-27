package com.clevertap.ui.pages.journey_page;

import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import static com.clevertap.utils.SeleniumUtils.performClick;

public class WhenPage {

    private WebDriver driver;
    private SweetAlert sweetAlert;
    private static Logger logger = Logger.getLogger(com.clevertap.ui.pages.campaigns_page.WhenPage.class);

    @FindBy(xpath = "//label[@for='js-start-now']")
    private WebElement journeyStartNow;

    @FindBy(xpath = "//label[@for='js-start-later']")
    private WebElement journeyStartLater;

    public WhenPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        sweetAlert = new SweetAlert(driver);
    }

    public  void setWhenPage(Object node){


    switch (node.getClass().getSimpleName().toLowerCase()){

            case "action":
                Action action = (Action)node;
                if(action.getWhen().getJourneyStartTime().equalsIgnoreCase("now")){
                    System.out.println("Wating");
                    SeleniumUtils.pause(30);
                    System.out.println("Done waiting");
                    System.out.println(driver.getPageSource());
                    
                    performClick(driver,journeyStartLater);
                }
        }
    }

}


