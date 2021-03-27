package com.clevertap.ui.pages.settings_menu_page;

import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

public class Roles {
    WebDriver driver;
    private SweetAlert sweetAlert;

    @FindBy(xpath = "//div[@class='breadcrumbs']//span")
    private WebElement breadcumb;
    @FindBy(xpath = "//*[contains(@class,'add-button')]")
    public WebElement addRole;
    @FindBy(xpath = "//button[contains(@class,'action-button')]")
    public WebElement saveRole;
    @FindBy(xpath = "//*[@id='nameInput']")
    public WebElement saveRoleAs;
    @FindBy(xpath = "//button[contains(@class,'form-button') and text()='Save']")
    public WebElement saveRoleButton;
    @FindBy(xpath = "//*[@class='confirm']")
    private WebElement confirm;
    @FindBy(xpath = "//table[contains(@class,'datatable-resultset')]")
    private WebElement tableObject;
    @FindBy(xpath = "//div[@class='sweet-overlay']/..//p")
    private WebElement RoleSavingErrorMessage;
    @FindBy(xpath = "//button[contains(@class,'form-button') and text()='Cancel']")
    public WebElement cancelRoleButton;
    @FindBy(xpath = "//div[@class='setting-menuSection']//div[text()='Roles']//parent::a")
    private WebElement breadcumbRoleElement;
    @FindBy(xpath = "//*[contains(@id,'__BV_popover') ]//div[@class='action-icons-container']/span[1]")
    private WebElement cloneRole;
//    @FindBy(xpath = "//*[contains(@id,'__BV_popover') ]//div[@class='action-icons-container']/span[3]")
    @FindBy(xpath = "//button[@class='confirm']")
    private WebElement deleteRole;
    @FindBy(xpath = "//*[contains(@class,'popover') ]//div[@class='action-icons-container']/span")
    private List<WebElement> actions;
    //    @FindBy(xpath = "//table[contains(@class,'datatable-resultset')]") private WebElement rolesTable;
    @FindBy(xpath = "(//*[contains(@class,'custom-checkbox')])[1]")
    private WebElement customRoleCheckbox;
    @FindBy(xpath = "//*[contains(@class,'showSweetAlert')]/p[text()='You do not have permission to edit/save roles.']")
    private WebElement permissionDeniedForRoleLoc;
    @FindBy(xpath = "//div[@role='document']//button[text()='Cancel']")
    public WebElement cancelSaveRoleModal;


    public String getPageHeaderText() {
        return breadcumb.getText();

    }

    public void checkRoleCheckbox() {
        Actions actions = new Actions(driver);
        actions.moveToElement(customRoleCheckbox);
        customRoleCheckbox.click();
    }

    public void clickSaveRolePage() throws InterruptedException {
        saveRole.click();
        Thread.sleep(1000);
    }

    public void EnterRoleNameAndSave(String featureName) {
        saveRoleAs.sendKeys("Automation" + featureName + "Removed");
        saveRoleButton.click();
        confirm.click();
    }


    public void clickPlusRole() throws InterruptedException {
        addRole.click();
        Thread.sleep(1000);
    }

    public Roles(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
        sweetAlert = new SweetAlert(this.driver);
    }

    public void CreateNewDefaultReadRole(String roleName){
        addRole.click();
        saveRole.click();
        SeleniumUtils.pause(1);
        saveRoleAs.sendKeys(roleName);
        saveRoleButton.click();
        SeleniumUtils.pause(1);
        confirm.click();


    }

    public void createDuplicateRole(String roleName)  {
        addRole.click();
        saveRole.click();
        SeleniumUtils.pause(1);
        saveRoleAs.sendKeys(roleName);
        saveRoleButton.click();
    }

    public String getErrorMessage(){
        SeleniumUtils.pause(2);
        String errorText = RoleSavingErrorMessage.getText();
        SeleniumUtils.pause(1);
        confirm.click();
        SeleniumUtils.pause(3);
        cancelRoleButton.click();
        SeleniumUtils.pause(1);
        return errorText;
    }

    public void clickDottedEditButton(String roleName) {
        //table[contains(@class,'datatable-resultset')]/tr//span[text()='AutomationDefaultRead']/../../following-sibling::td[5]
        String firstXpath = "//table[contains(@class,'datatable-resultset')]/tr//span[text()='";
        String secondXpath = "']/../../following-sibling::td[5]";

        String finalXpath = firstXpath + roleName + secondXpath;
        WebElement singleElement = driver.findElement(By.xpath(finalXpath));
        singleElement.click();
    }

    public void goBackToRlesPage() throws InterruptedException {
        breadcumbRoleElement.click();
//        driver.navigate().refresh();
        Thread.sleep(2000);
    }

    public void cloneRole(String clonedRoleName) throws InterruptedException {
//        cloneRole.click();
        saveRole.click();
        saveRoleAs.sendKeys(clonedRoleName);
        SeleniumUtils.pause(1);
        saveRoleButton.click();
        SeleniumUtils.pause(1);
        confirm.click();
//        driver.navigate().refresh();
        Thread.sleep(2000);


    }

    public void editRole(WebDriver driver) throws InterruptedException {
        Actions action = new Actions(driver);
        System.out.println("********** " + actions.size());
//        for (WebElement element:actions){

        SeleniumUtils.elementHighlighter(driver, actions.get(1));
        Thread.sleep(3000);
        JavascriptExecutor ex = (JavascriptExecutor) driver;
        ex.executeScript("arguments[0].click()", actions.get(1));
        Thread.sleep(1000);
        confirm.click();
        Thread.sleep(1000);


    }

    public boolean isRoleEditable() {
        return saveRole.isDisplayed();
    }

    public void deleteAllAutomationRoles(String roleName) throws InterruptedException {

        // "//table[contains(@class,'datatable-resultset')]/tr//span[text()='AutomationDefaultRead']/../../following-sibling::td[5]");
//        String firstXpath = "//table[contains(@class,'datatable-resultset')]/tr//span[text()='";
//        String secondXpath = "']/../../following-sibling::td[5]";
//        String finalXpath = firstXpath + roleName + secondXpath;

        SeleniumUtils.pause(1);
        driver.findElement(By.xpath("//span[@title='"+roleName+"']/../../..//*[name()='g']")).click();
        WebElement roleElement = driver.findElement(By.xpath("//span[@title='"+roleName+"']//ancestor::div[@id='wrap']/following::span[@title='Delete role']"));
        Actions actions = new Actions(driver);
        WebDriverWait wait = new WebDriverWait(driver, 10);
        roleElement.click();
        SeleniumUtils.pause(1);
        actions.moveToElement(deleteRole);
        SeleniumUtils.pause(4);
        deleteRole.click();
        SeleniumUtils.pause(1);
        confirm.click();
//        SeleniumUtils.pause(1);
//        confirm.click();
//        SeleniumUtils.pause(1);

    }


    /*fetch data for spoecific cell*/

    public List getDataFromSpecificCell(int colNum) {
        List cellValue = new ArrayList();
        List<WebElement> rows = tableObject.findElements(By.tagName("tr"));
        for (WebElement row : rows) {
            List<WebElement> cells = row.findElements(By.xpath("./*")); /* ./* checks for all the child object*/
            cellValue.add(cells.get(colNum).getText());
        }
        return cellValue;
    }

    public void getCreatedRoleName() {
        List<WebElement> rows = tableObject.findElements(By.tagName("tr"));
        for (WebElement row : rows) {
//            List<WebElement> cells=row.findElements(By.xpath("./*"));


        }
    }

    public void uncheckAllAccess(WebDriver driver, String featureToBeUnchecked) {

//        List<String> features=new ArrayList<>();
//        features.add("boards");
//        features.add("Segments");
//        features.add("Analyze");
//        features.add("Engage");
//        features.add("RealImpact");
//        features.add("Settings");
//        features.add("Others");

        //*[@data-id='Others']//label
        String firstXpath = "//*[@data-id='";
        String secondXpath = "']//label";

//        for (String feature:features){
        String finalXpath = firstXpath + featureToBeUnchecked + secondXpath;
        System.out.println(finalXpath);
        WebElement ele = driver.findElement(By.xpath(finalXpath));
        boolean clickable = SeleniumUtils.isClickable(ele, driver);
        System.out.println("********** " + clickable);
        ele.click();
//            SeleniumUtils.scrollDown(driver); /*This is just to debug*/

//        }


    }

//    public void deleteExistingItems() throws InterruptedException {
//        Thread.sleep(2000);
//        String xpath = "//table[contains(@class,'datatable-resultset')]/tr//span[contains(text(),'Automation')]/../../following-sibling::td[5]";
//        String deleteIconXpath = "//*[contains(@id,'__BV_popover') ]//div[@class='action-icons-container']/span[3]";
//        List<WebElement> elements = driver.findElements(By.xpath(xpath));
//        WebElement deleteElement;
//        int counter = elements.size();
//        System.out.println("*********** " + elements.size());
//
//
//        for (WebElement element : elements) {
//            try {
////                WebDriverWait wait=new WebDriverWait(driver,10);
//                SeleniumUtils.elementHighlighter(driver, element);
//                element.click();
//                Thread.sleep(2000);
//                deleteElement = driver.findElement(By.xpath(deleteIconXpath));
//                SeleniumUtils.elementHighlighter(driver, deleteElement);
//                Thread.sleep(1000);
//                deleteElement.click();
//                Thread.sleep(1000);
//                confirm.click();
//                Thread.sleep(1000);
//                confirm.click();
//                counter--;
//                driver.navigate().refresh();
////                wait.until(ExpectedConditions.elementToBeClickable(element));
//            } catch (Exception ex) {
//                elements = driver.findElements(By.xpath(xpath));
//                SeleniumUtils.elementHighlighter(driver, elements.get(0));
//                elements.get(0).click();
//                Thread.sleep(2000);
//                deleteElement = driver.findElement(By.xpath(deleteIconXpath));
//                Thread.sleep(2000);
//                SeleniumUtils.elementHighlighter(driver, deleteElement);
//                Thread.sleep(1000);
//                deleteElement.click();
//                Thread.sleep(1000);
//                confirm.click();
//                Thread.sleep(1000);
//                confirm.click();
//                counter--;
//                driver.navigate().refresh();
//                if (counter == 0) {
//                    break;
//                }
//            }
//        }
//    }

    public void deleteExistingItems() throws InterruptedException {
        Thread.sleep(2000);
        String xpath = "//table[contains(@class,'datatable-resultset')]/tr//span[contains(text(),'Automation')]/../../following-sibling::td[5]";
        String deleteIconXpath = "//*[contains(@id,'__BV_popover') ]//div[@class='action-icons-container']/span[3]";

        WebElement element=SeleniumUtils.getElementsOnPageRefresh(driver,xpath);

        do {

            element.click();
            WebElement deleteIcon=SeleniumUtils.getElementsOnPageRefresh(driver,deleteIconXpath);
            Thread.sleep(1000);
            deleteIcon.click();
            Thread.sleep(1000);
            confirm.click();
            Thread.sleep(1000);
            confirm.click();
            driver.navigate().refresh();
            element=SeleniumUtils.getElementsOnPageRefresh(driver,xpath);
        }while (element!=null);

    }


    public void waitForPageLoaded() {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);
        } catch (Throwable error) {
//            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    public void errorUpdatingValue(){
        SeleniumUtils.waitForElementToLoad(driver,permissionDeniedForRoleLoc);
        Assert.assertTrue(permissionDeniedForRoleLoc.isDisplayed(),"No Access to feature popup was not shown");
//        SeleniumUtils.waitAndClick(driver,sweetAlert.sweetAlertConfirmOK);

    }


}
