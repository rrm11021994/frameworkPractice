package org.example.pages;

import org.example.BaseClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BaseClass {
    //WebDriver driver;
    @FindBy(xpath = "//input[@name='email']")
    private WebElement email;
    @FindBy(xpath = "//input[@name='password']")
    private WebElement password;
    @FindBy(xpath = "//button[text()='Log In']")
    private WebElement LogInButton;
    @FindBy(xpath = "//span[text()='Need Help?']")
    private WebElement needHelp;
    @FindBy(xpath = "//button[text()='Okay, Got it']")
    private WebElement okayGotIt;
    String frameId= "wiz-iframe-intent";
    @FindBy(xpath = "//div[@class='announcement-popup_description']")
    private WebElement descriptionText;

    public LoginPage(WebDriver driver)
    {
        PageFactory.initElements(driver,this);
    }
    public void LoginToApplication(){
        driver.get(file.getProperty("Url"));
        email.sendKeys("rahul.mahajan@clevertap.com");
        password.sendKeys("Test1234");
        LogInButton.click();
        driver.getTitle();
    }

    public boolean needHelpPresent(){
        return needHelp.isDisplayed();
    }

    public void closeTheAlert(){
       switchFrame(frameId);
        System.out.println(descriptionText.getText());
       okayGotIt.click();
       needHelp.click();
    }

}
