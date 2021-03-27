package com.clevertap.ui.pages;

import com.clevertap.ui.pages.widget.SweetAlert;
import com.clevertap.utils.Mocha;
import com.clevertap.utils.NavigateCTMenuEnums;
import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.*;

public class RoleBasedAccess {

    private static final String PROFILE = "Profile";
    private static final String CLASS_DATATABLE_COLLECTION_SPAN_TEXT = "//*[@class='datatable-collection']//span[text()='";
    private static final String CLASS = "class";
    private static final String SHOW_TICK = "showTick";
    boolean status = true;
    private WebDriver driver;
    SweetAlert sweetAlert = new SweetAlert(driver);
    private List<String> systemRoles = new ArrayList<>();
    private WebDriverWait wait;
    Logger logger = Logger.getLogger(RoleBasedAccess.class);

    @FindBy(name = "search")
    private WebElement searchBar;
    @FindBy(xpath = "//button[contains(@class,'js-createCampaign')]")
    private WebElement campaignCreateBtn;
    @FindBy(xpath = "//*[contains(text(),'Select a channel for this campaign')]")
    private WebElement campaignPageText;
    @FindBy(xpath = "//*[contains(@class,'js-pane-container_close')]")
    private WebElement cancelCampaignPage;
    @FindBy(xpath = "//a[contains(@class,'journeyBtn')]")
    private WebElement journeyCreateBtn;
    @FindBy(id = "js-exit-cap")
    private WebElement journeyPageText;
    @FindBy(xpath = "//*[contains(@style,'ellipsis;')]")
    private static WebElement roleTypes;
    @FindBy(xpath = "//*[contains(@class,'js-create-custom-cg-btn')]")
    private WebElement customControlGroupPlusButton;
    @FindBy(xpath = "//div[@id='createCustomControlGroup']//div[contains(text(),'Purpose')]")
    private WebElement controlGroupPageText;
    @FindBy(xpath = "//div[@id='createCustomControlGroup']//a[contains(text(),'Cancel')]")
    private WebElement cancelCustomGroupButton;
    @FindBy(xpath = "//button[contains(@class,'createRecommendation')]")
    private WebElement recommendationCreateBtn;
    @FindBy(xpath = "//div[contains(@class,'grid-title')]")
    private WebElement recommendationPageText;
    @FindBy(xpath = "//*[contains(@for,'fileInput')]")
    private WebElement catalogCreateButton;
    @FindBy(xpath = "//button[@class='btn btn-success is-disabled']")
    private WebElement catalogButton;
    @FindBy(xpath = "//a[contains(@class, 'btn-success')]")
    private WebElement newBoard;
    @FindBy(xpath = "//p[contains(text(),'Create')]")
    private WebElement boardDailogueBoxText;
    @FindBy(xpath = "//*[@class='campaign-controls']//li")
    private List<WebElement> controlGroupTabs;
    @FindBy(xpath = "//*[contains(@class,'btn-success')]")
    private WebElement csvUploadButton;
    @FindBy(xpath = "(//button[@type='submit'])[2]")
    private WebElement inviteBtn;
    @FindBy(xpath = "//*[contains(@class,'icon success')]")
    private static WebElement sweetAlertSuccess;
    @FindBy(xpath = "//*[contains(@class,'showSweetAlert')]/button[@class='confirm']")
    private static WebElement sweetAlertConfirmOK;
    @FindBy(xpath = "//*[contains(@class,'showSweetAlert')]/div[contains(@class,'icon error')]")
    private static WebElement sweetAlertError;
    @FindBy(xpath = "//*[@class='filters-right']//button")
    private WebElement addBtn;
    @FindBy(xpath = "//div[@data-at-select='user-add-roles']")
    private WebElement addRolesButton;
    @FindBy(id = "emailInput")
    private WebElement emailIdInputBox;
    @FindBy(id = "ellipsis_icon")
    private WebElement ellipsis_icon;
    @FindBy(xpath = "//*[text()='Logout']")
    private WebElement logOut;
    @FindBy(xpath = "//span[contains(text(),'Account')]")
    private WebElement accountPageText;
    @FindBy(xpath = "//h5[contains(text(),'Invite')]")
    private WebElement userDailogueBoxText;
    @FindBy(xpath = "//span[contains(text(),'Properties')]")
    private WebElement eventsUserPageText;
    @FindBy(xpath = "//span[contains(text(),'Profile')]")
    private WebElement myProfilePageText;

    public boolean hasWriteAccess(String subComponentName, int access) {

        switch (subComponentName) {
            case "Campaigns":
                switch (access) {
                    case 1:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), null);
                        SeleniumUtils.performClick(driver, campaignCreateBtn);
                        return (isErrorPopUpDisplayed());
                    case 3:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), null);
                        SeleniumUtils.performClick(driver, campaignCreateBtn);
                        driver.switchTo().frame(0);
                        String campaignPageTxt = campaignPageText.getText().toLowerCase();
                        if (campaignPageTxt.contains(("Select a channel").toLowerCase())) {
                            status = true;
                        } else {
                            status = false;
                        }
                        driver.switchTo().parentFrame();
                        cancelCampaignPage.click();
                        return status;
                    case 6:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CAMPAIGNS.toString(), null);
                        return true;
                    default:
                        break;
                }
                break;
            case "Journeys":
                switch (access) {
                    case 1:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.JOURNEYS.toString(), null);
                        SeleniumUtils.performClick(driver, journeyCreateBtn);
                        return (isErrorPopUpDisplayed());
                    case 3:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.JOURNEYS.toString(), null);
                        SeleniumUtils.performClick(driver, journeyCreateBtn);
                        String journeyPageTxt = journeyPageText.getText().toLowerCase();
                        if (journeyPageTxt.contains(("Add goals").toLowerCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    case 6:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.JOURNEYS.toString(), null);
                        return true;
                    default:
                        break;
                }
                break;
            case "Control Groups":
                switch (access) {
                    case 1:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CONTROL_GROUPS.toString(), null);
                        for (WebElement controlGroupTab : controlGroupTabs) {
                            if (controlGroupTab.getText().equalsIgnoreCase("Custom control group")) {
                                SeleniumUtils.performClick(driver, controlGroupTab);
                                break;
                            }
                        }
                        customControlGroupPlusButton.click();
                        return (isErrorPopUpDisplayed());
                    case 3:
                        Mocha.forceNavigate = true;
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CONTROL_GROUPS.toString(), null);
                        for (WebElement controlGroupTab : controlGroupTabs) {
                            if (controlGroupTab.getText().equalsIgnoreCase("Custom control group")) {
                                SeleniumUtils.performClick(driver, controlGroupTab);
                                break;
                            }
                        }
                        customControlGroupPlusButton.click();
                        try {
                            if (sweetAlert.getSweetAlertErrorSignal() == false) {
                                status = true;
                            } else {
                                status = false;
                            }
                        } catch (NullPointerException ne) {
                            status = true;
                        }
                        cancelCustomGroupButton.click();
                        return status;
                    case 6:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CONTROL_GROUPS.toString(), null);
                        return true;
                    default:
                        break;
                }
                break;
            case "Recommendations":
                switch (access) {
                    case 1:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.RECOMMENDATIONS.toString(), null);
                        SeleniumUtils.performClick(driver, recommendationCreateBtn);
                        return (isErrorPopUpDisplayed());
                    case 3:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.RECOMMENDATIONS.toString(), null);
                        SeleniumUtils.performClick(driver, recommendationCreateBtn);
                        String recommendationPageTxt = recommendationPageText.getText().toLowerCase();
                        if (recommendationPageTxt.contains(("Recommendation").toLowerCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    case 6:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.RECOMMENDATIONS.toString(), null);
                        return true;
                    default:
                        break;
                }
                break;
            case "Catalogs":
                switch (access) {
                    case 1:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CATALOGS.toString(), null);
                        try {
                            if (catalogCreateButton.isEnabled()) {
                                status = false;
                            }
                        } catch (NoSuchElementException ne) {
                            status = true;
                        }
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.RECOMMENDATIONS.toString(), null);
                        return status;
                    case 3:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CATALOGS.toString(), null);
                        List<WebElement> userRows = driver.findElements(By.xpath("//div[@class='CT-table__left']//div[contains(@class, 'CT-table__row rowindex')]"));
                        int rowCount = userRows.size();
                        try {
                            if (rowCount == 6 && catalogButton.isDisplayed()) {
                                status = true;
                            } else {
                                status = false;
                            }
                        } catch (NoSuchElementException ne) {
                            status = false;
                        }
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.RECOMMENDATIONS.toString(), null);
                        return status;
                    case 6:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.ENGAGE.toString(), NavigateCTMenuEnums.Submenu.CATALOGS.toString(), null);
                        return true;
                    default:
                        break;
                }
                break;
            case "Custom Boards":
                switch (access) {
                    case 1:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.BOARDS.toString(), NavigateCTMenuEnums.Submenu.All_BOARDS.toString(), null);
                        SeleniumUtils.performClick(driver, newBoard);
                        return (isErrorPopUpDisplayed());
                    case 3:
                        Mocha.forceNavigate = true;
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.BOARDS.toString(), NavigateCTMenuEnums.Submenu.All_BOARDS.toString(), null);
                        SeleniumUtils.performClick(driver, newBoard);
                        SeleniumUtils.pause(5);
                        String boardPageTxt = boardDailogueBoxText.getText().toLowerCase();
                        if (boardPageTxt.contains(("Create").toLowerCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    case 6:
                        Mocha.forceNavigate = true;
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.BOARDS.toString(), NavigateCTMenuEnums.Submenu.All_BOARDS.toString(), null);
                        return true;
                    default:
                        break;
                }
                break;
            case "CSV Uploads":
                switch (access) {
                    case 1:
                        Mocha.forceNavigate = true;
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CSV_UPLOADS.toString(), null);
                        try {
                            if (isErrorPopUpDisplayed() == true) {
                                return true;
                            }
                        } catch (NoSuchElementException ne) {
                            return false;
                        }
                        break;
                    case 3:
                        Mocha.forceNavigate = true;
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CSV_UPLOADS.toString(), null);
                        SeleniumUtils.performClick(driver, csvUploadButton);
                        try {
                            if (isErrorPopUpDisplayed() == true) {
                                return false;
                            }
                        } catch (NoSuchElementException ne) {
                            return true;
                        }
                        break;
                    case 6:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.CSV_UPLOADS.toString(), null);
                        return true;
                    default:
                        break;
                }
                break;
            case "Account Settings":
                switch (access) {
                    case 1:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.ACCOUNT.toString(), null);
                        String accountPageTxt = accountPageText.getText().toLowerCase();
                        try {
                            if (accountPageTxt.contains(("Account").toLowerCase())) {
                                return true;
                            } else {
                                return false;
                            }
                        } catch (NullPointerException ne) {
                            return false;
                        }
                    case 3:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.ACCOUNT.toString(), null);
                        accountPageTxt = accountPageText.getText().toLowerCase();
                        if (accountPageTxt.contains(("Account").toLowerCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    case 6:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.ACCOUNT.toString(), null);
                        return true;
                    default:
                        break;
                }
                break;
            case "User Settings":
                switch (access) {
                    case 1:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), null);
                        SeleniumUtils.performClick(driver, addBtn);
                        return (isErrorPopUpDisplayed());
                    case 3:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), null);
                        SeleniumUtils.performClick(driver, addBtn);
                        try {
                            if (inviteBtn.isEnabled()) {
                                return true;
                            } else {
                                return false;
                            }
                        } catch (NoSuchElementException ne) {
                            return false;
                        }
                    case 6:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.USERS.toString(), null);
                        return true;
                    default:
                        break;
                }
                break;
            case "Event and Profile Properties":
                switch (access) {
                    case 1:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.EVENTS_USER_PROPERTIS.toString(), null);
                        try {
                            if (isErrorPopUpDisplayed() == true) {
                                return true;
                            }
                        } catch (NoSuchElementException ne) {
                            return false;
                        }
                        break;
                    case 3:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.EVENTS_USER_PROPERTIS.toString(), null);
                        String eventUserPageTxt = eventsUserPageText.getText().toLowerCase();
                        if (eventUserPageTxt.contains(("Properties").toLowerCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    case 6:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.EVENTS_USER_PROPERTIS.toString(), null);
                        return true;
                    default:
                        break;
                }
                break;
            case "My Profile And Password":
                switch (access) {
                    case 1:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.MYPROFILE.toString(), null);
                        String myProfilePageTxt = myProfilePageText.getText().toLowerCase();
                        if (myProfilePageTxt.contains((PROFILE).toLowerCase())) {
                            return true;
                        } else {
                            return false;

                        }
                    case 3:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.EVENTS_USER_PROPERTIS.toString(), null);
                        myProfilePageTxt = myProfilePageText.getText().toLowerCase();
                        if (myProfilePageTxt.contains((PROFILE).toLowerCase())) {
                            return true;
                        } else {
                            return false;
                        }
                    case 6:
                        Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.EVENTS_USER_PROPERTIS.toString(), null);
                        return true;
                    default:
                        break;
                }
                break;
            default:
                return true;
        }
        return status;
    }

    public enum access {
        READ(1),
        WRITE(2),
        APPROVE(3);

        private int value;

        access(int n) {
            this.value = n;
        }

        public int getValue() {
            return value;
        }
    }

    public Map<String, Integer> resultantRoleAccess(String userName) {

        systemRoles.add("Admin");
        systemRoles.add("Member");
        systemRoles.add("Creator");
        systemRoles.add("Agent");
        systemRoles.add("Approver");
        Map<String, Integer> map = new HashMap<String, Integer>();
        int runCounter = 0;

        driver.findElement(By.className("search-input")).sendKeys(Keys.chord(userName + Keys.RETURN));
        String dynamicXpath = "//*[text()='" + userName + "']/../../following-sibling::td[2]//span";
        String roles = driver.findElement(By.xpath(dynamicXpath)).getAttribute("innerHTML");
        logger.info(roles);
        String[] roleArray = roles.split(",");
        for (int i = 0; i < roleArray.length; i++) {
            Mocha.forceNavigate = true;
            Mocha.openLeftNavMenu(driver, false, NavigateCTMenuEnums.Menu.SETTINGS.toString(), NavigateCTMenuEnums.Submenu.ROLES.toString(), NavigateCTMenuEnums.SuperSubMenu.NOSUPERSUBMENU.toString());
            driver.findElement(By.xpath(CLASS_DATATABLE_COLLECTION_SPAN_TEXT + roleArray[i].trim() + "']")).click();
            List<WebElement> rows = driver.findElements(By.xpath("//*[@class='datatable-collection']//tr/td[2]/span"));
            /*getting name of all sub components*/
            List<String> subComponents = new ArrayList<>();
            for (WebElement row : rows) {
                subComponents.add(row.getText());
            }
            for (String subComponentName : subComponents) {
                int acessSum = 0;
                try {
                    WebElement subCOmpReadDiv = driver.findElement(By.xpath(CLASS_DATATABLE_COLLECTION_SPAN_TEXT + subComponentName + "']/../following-sibling::td[1]/div"));
                    if (subCOmpReadDiv.getAttribute(CLASS).contains(SHOW_TICK)) {
                        if (map.get(subComponentName) == null) {
                            map.put(subComponentName, access.READ.getValue());
                        } else {
                            acessSum = acessSum + access.READ.getValue();
                        }
                    }
                    try {
                        if (runCounter < 1) {
                            if (systemRoles.contains(roleArray[i].trim())) {
                                WebElement subCOmpWriteSystemDiv = driver.findElement(By.xpath(CLASS_DATATABLE_COLLECTION_SPAN_TEXT + subComponentName + "']/../following-sibling::td[2]/div"));
                                if (subCOmpWriteSystemDiv.getAttribute(CLASS).contains(SHOW_TICK)) {
                                    map.put(subComponentName, map.get(subComponentName) + access.WRITE.getValue());
                                }
                            } else {
                                WebElement subCOmpWriteCustomDiv = driver.findElement(By.xpath(CLASS_DATATABLE_COLLECTION_SPAN_TEXT + subComponentName + "']/../following-sibling::td[2]//input"));
                                if (subCOmpWriteCustomDiv.isSelected()) {
                                    map.put(subComponentName, map.get(subComponentName) + access.WRITE.getValue());
                                }
                            }
                        } else {
                            if (systemRoles.contains(roleArray[i].trim())) {
                                WebElement subCOmpWriteSystemDiv = driver.findElement(By.xpath(CLASS_DATATABLE_COLLECTION_SPAN_TEXT + subComponentName + "']/../following-sibling::td[2]/div"));
                                if (subCOmpWriteSystemDiv.getAttribute(CLASS).contains(SHOW_TICK)) {
                                    acessSum = acessSum + access.WRITE.getValue();
                                }
                            } else {
                                WebElement subCOmpWriteCustomDiv = driver.findElement(By.xpath(CLASS_DATATABLE_COLLECTION_SPAN_TEXT + subComponentName + "']/../following-sibling::td[2]//input"));
                                if (subCOmpWriteCustomDiv.isSelected()) {
                                    acessSum = acessSum + access.WRITE.getValue();
                                }
                            }
                        }
                    } catch (Exception e) {
                    }
                    if (runCounter < 1) {
                        if (systemRoles.contains(roleArray[i].trim())) {
                            WebElement subCompApprovSystemDiv = driver.findElement(By.xpath(CLASS_DATATABLE_COLLECTION_SPAN_TEXT + subComponentName + "']/../following-sibling::td[3]/div"));
                            if (subCompApprovSystemDiv.getAttribute(CLASS).contains(SHOW_TICK)) {
                                map.put(subComponentName, map.get(subComponentName) + access.APPROVE.getValue());
                            }
                        } else {
                            WebElement subCOmpApprovCustomDiv = driver.findElement(By.xpath(CLASS_DATATABLE_COLLECTION_SPAN_TEXT + subComponentName + "']/../following-sibling::td[3]//input"));
                            if (subCOmpApprovCustomDiv.isSelected()) {
                                map.put(subComponentName, map.get(subComponentName) + access.APPROVE.getValue());
                            }
                        }
                    } else {
                        if (systemRoles.contains(roleArray[i].trim())) {
                            WebElement subCompApprovSystemDiv = driver.findElement(By.xpath(CLASS_DATATABLE_COLLECTION_SPAN_TEXT + subComponentName + "']/../following-sibling::td[3]/div"));
                            if (subCompApprovSystemDiv.getAttribute(CLASS).contains(SHOW_TICK)) {
                                acessSum = acessSum + access.APPROVE.getValue();
                            }
                        } else {
                            WebElement subCOmpApprovCustomDiv = driver.findElement(By.xpath(CLASS_DATATABLE_COLLECTION_SPAN_TEXT + subComponentName + "']/../following-sibling::td[3]//input"));
                            if (subCOmpApprovCustomDiv.isSelected()) {
                                acessSum = acessSum + access.APPROVE.getValue();
                            }
                        }
                    }
                } catch (Exception e) {
                }
                if (runCounter >= 1) {
                    if (map.get(subComponentName) <= acessSum) {

                    } else {
                        map.put(subComponentName, acessSum);
                    }
                }
            }
            runCounter = runCounter + 1;
        }
        logger.info(map);
        return map;
    }

    public void addUsers(String roles, String emailID) {
        SeleniumUtils.performClick(driver, addBtn);
        SeleniumUtils.enterInputText(driver, emailIdInputBox, emailID);
        Set<String> rolesSet = new HashSet<>(Arrays.asList(roles.split("/")));
        SeleniumUtils.performClick(driver, addRolesButton.findElement(By.className("filterbtn")));
        SeleniumUtils.performClick(driver, addRolesButton.findElement(By.className("clear-all-filter")));
        SeleniumUtils.performClick(driver, addRolesButton.findElement(By.className("filterbtn")));
        List<WebElement> lisItems = addRolesButton.findElements(By.tagName("li"));
        for (String s : rolesSet) {
            for (WebElement ele : lisItems) {
                String eleText = ele.getText();
                if (s.equalsIgnoreCase(eleText) && !ele.isSelected()) {
                    wait.until(ExpectedConditions.elementToBeClickable(ele));
                    SeleniumUtils.performClick(driver, ele.findElement(By.tagName("label")));
                }
            }
        }
        SeleniumUtils.performClick(driver, addRolesButton.findElement(By.tagName("a")));
        SeleniumUtils.performClick(driver, inviteBtn);
    }

    public boolean isInvitationSent() {
        try {
            if (sweetAlertSuccess.getAttribute("style").contains("block")) {
                SeleniumUtils.performClick(driver, sweetAlertConfirmOK);
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException ne) {
            return false;
        }
    }

    public boolean isErrorPopUpDisplayed() {
        try {
            if (sweetAlertError.getAttribute("style").contains("block")) {
                SeleniumUtils.performClick(driver, sweetAlertConfirmOK);
                return true;
            } else {
                return false;
            }
        } catch (NoSuchElementException ne) {
            return false;
        }
    }

    public boolean isAccessDeniedPopUpDisplayed() {
        try {
            addBtn.click();
            return isErrorPopUpDisplayed();
        } catch (NoSuchElementException ne) {
            return false;
        }
    }

    public void logOutApplication() {
        ellipsis_icon.click();
        SeleniumUtils.pause(5);
        logOut.click();
    }

    public RoleBasedAccess(WebDriver driverFromPreviousPage) {
        this.driver = driverFromPreviousPage;
        PageFactory.initElements(driverFromPreviousPage, this);
        wait = new WebDriverWait(driver, 10);
    }
}