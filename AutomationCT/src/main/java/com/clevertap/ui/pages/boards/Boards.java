package com.clevertap.ui.pages.boards;

import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Boards {
    WebDriver driver;

    @FindBy(xpath = "//*[@class='ct-breadcrumb']")
    private WebElement pageTitle;
    @FindBy(xpath = "//*[@id='createBoard']")
    public WebElement newBoard;
    @FindBy(xpath = "//input[@id='new_board_name']")
    private WebElement createCustomBoard;
    @FindBy(xpath = "//*[@id='createnewboard']//button[1]")
    private WebElement cancelButton;
    @FindBy(xpath = "//*[text()='Add']")
    private WebElement addButton;
    @FindBy(xpath = "//*[@id='listCards']//div[text()='to pin a card on any board']")
    private WebElement text;
    @FindBy(xpath = "//*[@class='custom-card--emptycard']//a")
    private List<WebElement> customCardItems;
    @FindBy(xpath = "//input[@id='searchDiv']")
    private WebElement searchBoxBoards;
    @FindBy(xpath = "//*[@id=\"boards-table\"]")
    private WebElement boardsTable;
    @FindBy(xpath = "//input[@name='visibilityType']/../label")
    private List<WebElement> radioButtons;


    public String getHeaderText() {
        return pageTitle.getText();

    }

    public String BoardText() {
        return text.getText();

    }


    public void newBoardform(String newCreateBoardName) {
        createCustomBoard.sendKeys(newCreateBoardName);
    }

    public void createNewBoards() {
        SeleniumUtils.performClick(driver, newBoard);

    }


    public void clickOnBoardsfeatures(String itemName) {

        for (WebElement element : customCardItems) {
            if (element.getText().equalsIgnoreCase(itemName)) {
                SeleniumUtils.openElementInNewTab(driver, element);
            }
        }


    }

    public void selectPublicPrivateRadioButton() {
        for (WebElement ele : radioButtons) {
            if (ele.getText().equalsIgnoreCase("Public")) {
                ele.click();
            }
        }
        SeleniumUtils.performClick(driver, addButton);
    }

    public void sendTextToSearchBox(String newBoardName) {
        SeleniumUtils.enterInputText(driver, searchBoxBoards, newBoardName);
        searchBoxBoards.sendKeys(Keys.RETURN);
    }

    public String switchToTabs(String section) {
        clickOnBoardsfeatures(section);
        Set<String> tabs = driver.getWindowHandles();
        List<String> handles = new ArrayList<>(tabs);
        driver.switchTo().window(handles.get(1));
        String BoardsPageHeaderText = getHeaderText();
        driver.close();
        driver.switchTo().window(handles.get(0));
        return BoardsPageHeaderText;
    }

    public Boards(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }
}