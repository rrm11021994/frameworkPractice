package com.clevertap.ui.pages.settings_menu_page;

import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.ArrayList;
import java.util.List;


public class MyDownloads {
    WebDriver driver;

    @FindBy(xpath = "//*[@class='ct-breadcrumb']")
    private WebElement pageTitleText;
    @FindBy(xpath = "//*[contains(@class,'table')]")
    private WebElement tableData;


    public String getHeaderText() {
        return pageTitleText.getText();

    }

    public List getDataFromSpecificCell(int colNum) throws InterruptedException {
        List cellValue = new ArrayList();
        List<WebElement> rows = tableData.findElements(By.tagName("tr"));
        for (WebElement row : rows) {
            try {

                List<WebElement> cells = row.findElements(By.xpath("./*"));
                SeleniumUtils.elementHighlighter(driver,cells.get(colNum));
                cellValue.add(cells.get(colNum).getText());
                Thread.sleep(1000);
            }catch (IndexOutOfBoundsException ex){
                //do nothing
            }
        }
        return cellValue;
    }



    public MyDownloads(WebDriver previousBrowserDriver) {

        driver = previousBrowserDriver;

        PageFactory.initElements(previousBrowserDriver, this);

    }
}