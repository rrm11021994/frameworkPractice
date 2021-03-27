
package com.clevertap.ui.pages.settings_menu_page;

import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.Data;
import com.clevertap.utils.SeleniumUtils;
import com.clevertap.utils.TableUtility;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import java.util.*;

public class Users {

    WebDriver driver;
    WebDriverWait wait;
    private SweetAlert swal;

    @FindBy(xpath = "//ol[contains(@class,'breadcrumb')]/li")
    private List<WebElement> headerList;
    private String emailID;

    public static final String TABLECONTAININGUSERS = "//div[@class='datatable-collection']/table";

    @FindBy(xpath = "//*[@class='filters-right']//button")
    private WebElement addBtn;

    @FindBy(id = "emailInput")
    private WebElement emailIdInputBox;

    @FindBy(xpath = "//button[text()='Invite']")
    private WebElement inviteBtn;
    @FindBy(xpath = "(//button[@type='submit'])[1]")
    private WebElement saveEditedRolesBtn;
    @FindBy(xpath = "//div[@data-at-select='user-filter-role']")
    private WebElement filterRolesButton;
    @FindBy(xpath = "//div[@data-at-select='user-filter-status']")
    private WebElement filterStatusButton;
    @FindBy(xpath = "//div[@data-at-select='user-filter-passcode']")
    private WebElement filterPasscodeButton;

    @FindBy(xpath = "//div[@class='selected-role']//span[2]")
    private List<WebElement> listofSelectedRolesDeleteBtn;
    @FindBy(xpath = "//*[@data-at-select='user-edit-roles']")
    private WebElement editRoles;

    @FindBy(name = "search")
    private WebElement searchBar;

    @FindBy(xpath = "//div[contains(@class,'action-icons-container')]/span")
    private List<WebElement> listPopOverItems;
    @FindBy(xpath = "//*[contains(@class,'action-icons-container')]/span[@title='Edit Role']")
    private WebElement editRolePopOverBtn;

    @FindBy(xpath = "//*[contains(@class,'action-icons-container')]/span[contains(@title,'Revoke Us')]")
    private WebElement revokeUserAccessPopOverBtn;

    @FindBy(xpath = "//*[contains(@class,'action-icons-container')]/span[contains(@title,'Reset')]")
    private WebElement resetPasscodePopOverBtn;

    @FindBy(xpath = "//*[contains(@class,'action-icons-container')]/span[contains(@title,'Acc')]")
    private WebElement grantUserAccessPopOverBtn;

    @FindBy(xpath = "//*[contains(@class,'action-icons-container')]/span[contains(@title,'Grant Pass')]")
    private WebElement grantPasscodePopOverBtn;

    @FindBy(xpath = "//*[contains(@class,'action-icons-container')]/span[contains(@title,'Revoke Pa')]")
    private WebElement revokePasscodePopOverBtn;

    @FindBy(xpath = "//button[text()='Grant']")
    private WebElement grantPassCodeSwAl;

    @FindBy(xpath = "//*[contains(@class,'action-icons-container')]/span[contains(@title,'Re-in')]")
    public WebElement reInviteUserPopOverBtn;

    @FindBy(xpath = "//div[@data-at-select='user-add-roles']")
    private WebElement addRolesButton;

    @FindBy(xpath = "//div[@class='Cp']//table")
    private WebElement emailsTable;
    @FindBy(xpath = "//a[text()='Complete your signup'][1]")
    private WebElement cTSignUpButton;
    @FindBy(name = "userName")
    private WebElement cTSignUpUName;
    @FindBy(name = "password")
    private WebElement cTSignUpPassword;
    @FindBy(xpath = "//*[@type='submit']")
    private WebElement cTLogInSubmitButton;
    @FindBy(id = "submitBtn")
    private WebElement acceptTermCond;
    @FindBy(xpath = "//div[@role='listitem']")
    private List<WebElement> listSingUpInvites;
    @FindBy(xpath = "//div[@class='table-container']//table[contains(@class,'datatable-resultset')]/tr[1]/td")
    private List<WebElement> columnsUsersTable;

    @FindBy(xpath = "//div[@role='main']//div[@class='Cp']//tbody/tr")
    private List<WebElement> searchedMails;

    String emailTitle = "//div[@class='Cp']//tbody/tr[~]/td[5]/div[2]/span";

    public String extractHeader() {
        String header = "";
        for (WebElement ele : headerList) {
            header += "/" + ele.getText();
        }
        return header;
    }

    public void userClick(WebElement element) {
        SeleniumUtils.waitAndClick(driver,element);
    }

    public void enterEmailId(String emailId) {
        SeleniumUtils.enterInputText(driver, emailIdInputBox, emailId);
    }

    public void enterTextInSearchBox(String textToSearch) {
        SeleniumUtils.enterInputText(driver, searchBar, textToSearch);
    }

    public void clickInviteBtn() {
        SeleniumUtils.performClick(driver, inviteBtn);
    }

    public void addUsers(String roles, String email) {
        Reporter.log("Adding user "+email+" with "+roles+" roles",true);
        userClick(addBtn);
        enterEmailId(email);
        Set<String> rolesSet = new HashSet<>(Arrays.asList(roles.split("/")));
        clearSelectedItemAddNew(rolesSet, addRolesButton);
        SeleniumUtils.performClick(driver, inviteBtn);
        swal.getSweetAlertSuccessSignal();
    }

    public boolean isTripleDotClickable() {
        TableUtility.InitializeTable(driver, TABLECONTAININGUSERS);
        List<WebElement> tripleDotButtons = TableUtility.getDataFromSpecificCell(6);
        for (WebElement ele : tripleDotButtons) {
            if (ele != null) {
                wait.until(ExpectedConditions.elementToBeClickable(ele));
                ele.click();
                if (listPopOverItems != null) {
                    return true;
                }
            }
        }
        return false;
    }

    public void editRolesTripleDot(String newRole) {
        TableUtility.InitializeTable(driver, TABLECONTAININGUSERS);
        List<WebElement> listOfTripleDotElements = TableUtility.getDataFromSpecificCell(6);
        if (listOfTripleDotElements != null) {
            listOfTripleDotElements.get(0).click();
            SeleniumUtils.performClick(driver, editRolePopOverBtn);

            for (WebElement e : listofSelectedRolesDeleteBtn) {
                SeleniumUtils.performClick(driver, e);
            }
            List<String> rolesList = Arrays.asList(newRole.split("/"));
            Set<String> rolesSet = new HashSet<>(rolesList);
            clearSelectedItemAddNew(rolesSet, editRoles);
            SeleniumUtils.performClick(driver, saveEditedRolesBtn);
            swal.getSweetAlertSuccessSignal();
        }
    }

    public boolean verifyRole( String role){

        String userRole = columnsUsersTable.get(3).getText();
        if (columnsUsersTable.size() == 1) {
            return false;
        } else if (userRole.equalsIgnoreCase(role)) {
            return true;
        }
        return false;
    }

    public void selectRoles(String roles) {
        List<String> rolesList = Arrays.asList(roles.split("/"));
        Set<String> rolesSet = new HashSet<>(rolesList);
        clearSelectedItemAddNew(rolesSet, filterRolesButton);
    }

    public void selectStatus(String status) {
        List<String> statusList = Arrays.asList(status.split("/"));
        Set<String> statusSet = new HashSet<>(statusList);
        clearSelectedItemAddNew(statusSet, filterStatusButton);
    }

    public void selectPasscodeStatus(String passcode) {
        List<String> statusList = Arrays.asList(passcode.split("/"));
        Set<String> statusSet = new HashSet<>(statusList);
        clearSelectedItemAddNew(statusSet, filterPasscodeButton);
    }

    public Users(WebDriver driverFromPreviousPage) {
        this.driver = driverFromPreviousPage;
        wait = new WebDriverWait(driver, 10);
        PageFactory.initElements(driverFromPreviousPage, this);
        swal = new SweetAlert(driver);
    }

    public String inviteNewUser(String userEmail){
        userClick(addBtn);
        String[] userNameDomain = userEmail.split("@");
        emailID = Data.randomAlphaNumeric(userNameDomain[0] + "+", 5);
        emailID = emailID + "@" + userNameDomain[1];
        Reporter.log("Inviting user with email Id : "+emailID,true);
        SeleniumUtils.enterInputText(driver, emailIdInputBox, emailID);
        Set<String> rolesSet = new HashSet<>();
        rolesSet.add("Creator");
        clearSelectedItemAddNew(rolesSet, addRolesButton);
        clickInviteBtn();
        if(swal.getSweetAlertSuccessSignal() != true){
            Reporter.log("Got error while inviting user ");
        }
        SeleniumUtils.pause(3);
        return emailID;
    }

    public void acceptGmailInvite(String uName, String password) {
        driver.get("https://mail.google.com");
        SeleniumUtils.waitForElementToLoad(driver, emailsTable);
        String winHandleBefore = driver.getWindowHandle();
//        for (int i = 1; i < 6; i++) {
//            String xpathToMailHeader = emailTitle.replace("~", Integer.toString(i));
//            SeleniumUtils.waitForPageLoaded(driver);
//            String emailTitleStr = driver.findElement(By.xpath(xpathToMailHeader)).getText();
//            if (!emailTitleStr.isEmpty() && emailTitleStr.equals("CleverTap Signup")) {
//                SeleniumUtils.performClick(driver, driver.findElement(By.xpath(xpathToMailHeader)));
//                SeleniumUtils.pause(5);
//                List<WebElement> listEmailsWithSameHeader = listSingUpInvites;
//                if (listEmailsWithSameHeader.size() > 1) {
//                    SeleniumUtils.performClick(driver, listEmailsWithSameHeader.get(0).findElement(By.xpath("//div[@class='gs gt']")));
//                }
//                SeleniumUtils.waitForElementToClickable(driver,cTSignUpButton,10);
//                cTSignUpButton.click();
//                break;
//            }
//        }


//        SeleniumUtils.pause(5);
        SeleniumUtils.enterInputText(driver, driver.findElement(By.xpath("//input[@aria-label='Search mail']")), "CleverTap Signup");
        SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//button[@aria-label='Search Mail']")));
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("(//div[@role='main']//div[@class='Cp']//tbody/tr//span[text()='CleverTap Account Invitation'])[2]")),10);
        driver.findElement(By.xpath("//div[@role='main']//div[@class='Cp']//tbody/tr")).click();
        SeleniumUtils.pause(3);
        if(driver.findElement(By.xpath("//div[@data-tooltip='Expand all']")).isDisplayed()){
            driver.findElement(By.xpath("//div[@data-tooltip='Expand all']")).click();
            SeleniumUtils.pause(1);
        }
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("//a[contains(text(),'Complete your')]")),10);
        driver.findElement(By.xpath("//a[contains(text(),'Complete your')]")).click();
        SeleniumUtils.switchToLastWindowHandle(driver);
        SeleniumUtils.enterInputText(driver, cTSignUpUName, "Automation User");
        SeleniumUtils.enterInputText(driver, cTSignUpPassword, password);
        SeleniumUtils.performClick(driver, cTLogInSubmitButton);
        driver.manage().deleteAllCookies();
        SeleniumUtils.performClick(driver, acceptTermCond);
        SeleniumUtils.pause(1);
        driver.close();
        driver.switchTo().window(winHandleBefore);
        SeleniumUtils.pause(1);

    }

    public boolean verifyListOfItemsWithEmails(String emailId) {
        TableUtility.InitializeTable(driver, TABLECONTAININGUSERS);
        List<WebElement> listOfUsers = TableUtility.getDataFromSpecificCell(2);
        for (WebElement e : listOfUsers) {
            if (e != null && e.getText().equalsIgnoreCase(emailId)) {
                return true;
            }
        }
        return false;
    }

    public boolean verifyListOfItems(String output, int columnNumber) {
        List<String> rolesList = new ArrayList<>();
        TableUtility.InitializeTable(driver, TABLECONTAININGUSERS);
        List<WebElement> cells = TableUtility.getDataFromSpecificCell(columnNumber);

        List<String> expectedOutput = Arrays.asList(output.split("/"));
        for (WebElement cell : cells) {
            rolesList.add(cell.getText());
        }
        Set<String> setOfItems=new HashSet<>(rolesList);
        for (String item:setOfItems){
            if (expectedOutput.contains(item)){
                return true;
            }
        }

        return false;

    }

    public void clearSelectedItemAddNew(Set<String> itemsToSelect, WebElement parentList) {

        SeleniumUtils.performClick(driver, parentList.findElement(By.className("filterbtn")));
        SeleniumUtils.performClick(driver, parentList.findElement(By.className("clear-all-filter")));
        SeleniumUtils.performClick(driver, parentList.findElement(By.className("filterbtn")));
        List<WebElement> lisItems = parentList.findElements(By.tagName("li"));
        for (String s : itemsToSelect) {
            for (WebElement ele : lisItems) {
                String eleText = ele.getText();
                if (s.equalsIgnoreCase(eleText) && !ele.isSelected()) {
                    wait.until(ExpectedConditions.elementToBeClickable(ele));
                    SeleniumUtils.performClick(driver, ele.findElement(By.tagName("label")));
                }
            }
        }
        SeleniumUtils.performClick(driver, parentList.findElement(By.tagName("a")));
    }

    public boolean isUserActive(String s) {

        SeleniumUtils.enterInputText(driver, searchBar, s);
        if (columnsUsersTable.size() == 1) {
            ////
        } else if (columnsUsersTable.get(4).getText().equalsIgnoreCase("active")) {

            return true;
        }
        return false;
    }


    /***
     * This method revokes access of the user from Uses page in setting tab,
     * Searches the given emailID, clicks on the triple dot and clicks on revoke user access
     * Note : execution will still on USERS page with email ID searched inside the search text box,
     * caller needs to refresh the page if does not have any other details to be validated regarding the passed user.
     * @param emailID - emailId of the user who's access is to be revoked.
     */
    public void revokeUserAccessFromUser(String emailID){

        enterTextInSearchBox(emailID);
        TableUtility.InitializeTable(driver, TABLECONTAININGUSERS);
        List<WebElement> listOfTripleDotElements = TableUtility.getDataFromSpecificCell(6);
        if (listOfTripleDotElements.size() != 0) {
            listOfTripleDotElements.get(0).click();
            SeleniumUtils.performClick(driver, revokeUserAccessPopOverBtn);
            SeleniumUtils.pause(1);
//            SeleniumUtils.waitAndClick(driver,SweetAlert.sweetAlertConfirmOK);
            swal.getSweetAlertSuccessSignal();
            Reporter.log("Access successfully revoked for user : "+emailID,true);
        }else{
            Reporter.log("Failed to revoke access for user : "+emailID,true);
        }
    }


    /***
     * This method gets the count of Invite Emails from CleverTap for automation user
     * Pre-requisite : caller needs to be already logged in into gmail account
     * @return count of Invite Emails
     */
    public int getCountOfInviteEmailsFromCleverTap(){

        ((JavascriptExecutor) driver).executeScript("window.open()");
        ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        driver.get("https://mail.google.com");
        SeleniumUtils.waitForElementToLoad(driver, emailsTable);
        SeleniumUtils.enterInputText(driver, driver.findElement(By.xpath("//input[@aria-label='Search mail']")), "CleverTap Signup");
        SeleniumUtils.performClick(driver, driver.findElement(By.xpath("//button[@aria-label='Search Mail']")));
        SeleniumUtils.waitForElementToClickable(driver,driver.findElement(By.xpath("(//div[@role='main']//div[@class='Cp']//tbody/tr//span[text()='CleverTap Account Invitation'])[2]")),10);
        driver.findElement(By.xpath("//div[@role='main']//div[@class='Cp']//tbody/tr")).click();
        SeleniumUtils.pause(3);
        if(driver.findElement(By.xpath("//div[@data-tooltip='Expand all']")).isDisplayed()){
            driver.findElement(By.xpath("//div[@data-tooltip='Expand all']")).click();
            SeleniumUtils.pause(1);
        }
        int count = driver.findElements(By.xpath("//a[contains(text(),'Complete your')]")).size();
        Reporter.log("Count of email Invites from CT : "+count,true);
        driver.close();
        driver.switchTo().window(tabs.get(0));
        return count;

    }


    /***
     * This method re-invites user from User page in setting tab vie triple dot,
     * Searches the given emailID, clicks on the triple dot and clicks on Re-Invite user.
     * Note : execution will still on USERS page with email ID searched inside the search text box,
     * caller needs to refresh the page if does not have any other details to be validated regarding the passed user.
     * @param emailID - emailId of the user
     */
    public void reInviteUserViaTripleDot(String emailID){

        enterTextInSearchBox(emailID);
        TableUtility.InitializeTable(driver, TABLECONTAININGUSERS);
        List<WebElement> listOfTripleDotElements = TableUtility.getDataFromSpecificCell(6);
        if (listOfTripleDotElements.size() != 0) {
            listOfTripleDotElements.get(0).click();
            SeleniumUtils.performClick(driver, reInviteUserPopOverBtn);
            SeleniumUtils.pause(1);
            swal.getSweetAlertSuccessSignal();
            Reporter.log("Re-invited user : "+emailID);
        }
    }


    /***
     * This method grants user access from User page in setting tab vie triple dot,
     * Searches the given emailID, clicks on the triple dot and clicks on grant access to user
     * Note : execution will still on USERS page with email ID searched inside the search text box,
     * caller needs to refresh the page if does not have any other details to be validated regarding the passed user.
     * @param emailID - emailId of the user
     */
    public void grantUserAccessViaTripleDot(String emailId){

        enterTextInSearchBox(emailId);
        TableUtility.InitializeTable(driver, TABLECONTAININGUSERS);
        List<WebElement> listOfTripleDotElements = TableUtility.getDataFromSpecificCell(6);
        if (listOfTripleDotElements.size() != 0) {
            listOfTripleDotElements.get(0).click();
            SeleniumUtils.performClick(driver, grantUserAccessPopOverBtn);
            SeleniumUtils.pause(1);
            swal.sweetAlertConfirmOK();
            swal.getSweetAlertSuccessSignal();
            Reporter.log("Granted user access to : "+emailId,true);
        }
    }


    /***
     * This method grants passcode from User page in setting tab vie triple dot,
     * Searches the given emailID, clicks on the triple dot and clicks on grant passcode.
     * Note : execution will still on USERS page with email ID searched inside the search text box,
     * caller needs to refresh the page if does not have any other details to be validated regarding the passed user.
     * @param emailID - emailId of the user
     */
    public void grantPasscodeViaTripleDot(String emailId){

        enterTextInSearchBox(emailId);
        TableUtility.InitializeTable(driver, TABLECONTAININGUSERS);
        List<WebElement> listOfTripleDotElements = TableUtility.getDataFromSpecificCell(6);
        if (listOfTripleDotElements.size() != 0) {
            listOfTripleDotElements.get(0).click();
            SeleniumUtils.performClick(driver, grantPasscodePopOverBtn);
            SeleniumUtils.pause(1);
            SeleniumUtils.waitAndClick(driver,grantPassCodeSwAl);
            swal.getSweetAlertSuccessSignal();
            Reporter.log("Granted passcode to : "+emailId,true);
        }
    }


    /***
     * This method revokes passcode from User page in setting tab vie triple dot,
     * Searches the given emailID, clicks on the triple dot and clicks on revoke passcode
     * Note : execution will still on USERS page with email ID searched inside the search text box,
     * caller needs to refresh the page if does not have any other details to be validated regarding the passed user.
     * @param emailID - emailId of the user
     */
    public void revokePasscodeViaTripleDot(String emailId){

        enterTextInSearchBox(emailId);
        TableUtility.InitializeTable(driver, TABLECONTAININGUSERS);
        List<WebElement> listOfTripleDotElements = TableUtility.getDataFromSpecificCell(6);
        if (listOfTripleDotElements.size() != 0) {
            listOfTripleDotElements.get(0).click();
            SeleniumUtils.performClick(driver, revokePasscodePopOverBtn);
            SeleniumUtils.pause(1);
            swal.sweetAlertConfirmOK();
            swal.getSweetAlertSuccessSignal();
            Reporter.log("Revoked passcode to : "+emailId,true);
        }
    }


    /***
     * This method reset passcode user from Uses page in setting tab vie triple dot,
     * Searches the given emailID, clicks on the triple dot and clicks on reset passcode.
     * Note : execution will still on USERS page with email ID searched inside the search text box,
     * caller needs to refresh the page if does not have any other details to be validated regarding the passed user.
     * @param emailID - emailId of the user
     */
    public void resetPasscodeViaTripleDot(String emailId){

        enterTextInSearchBox(emailId);
        TableUtility.InitializeTable(driver, TABLECONTAININGUSERS);
        List<WebElement> listOfTripleDotElements = TableUtility.getDataFromSpecificCell(6);
        if (listOfTripleDotElements.size() != 0) {
            listOfTripleDotElements.get(0).click();
            SeleniumUtils.performClick(driver, resetPasscodePopOverBtn);
            SeleniumUtils.pause(1);
            swal.sweetAlertConfirmOK();
            swal.getSweetAlertSuccessSignal();
            Reporter.log("Passcode is reset for : "+emailId,true);
        }
    }
}

