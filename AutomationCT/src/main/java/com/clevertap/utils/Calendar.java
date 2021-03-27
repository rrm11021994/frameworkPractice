package com.clevertap.utils;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class Calendar {

    private static final String LI = "]//li";
    private static final String CONTAINS_CLASS_CALENDAR_ABSOLUTE_VIEW_CONTAINER_BUTTON_TEXT_APPLY = "(//*[contains(@class,'calendar-absolute-view-container')]//button[text()='Apply'])[";
    private WebDriver localDriver;
    public static int globalCalendarInstance = 1;
    private int i = 0;
    private Logger logger=Logger.getLogger("Calendar");

    @FindBy(xpath = "//button[text()='Apply']")
    private WebElement applyButton;
    @FindBy(xpath = "//*[contains(@class,'calendar-header')]")
    private WebElement calendarHeader;
    @FindBy(xpath = "//*[contains(@class,'custom-header-back')]")
    private WebElement customeHeaderBackImg;
    @FindBy(xpath = "//*[@data-key='from']/following-sibling::div")
    private WebElement inTheLastFrom;
    @FindBy(xpath = "//*[@data-key='from']/following-sibling::div//li[text()='2']")
    private List<WebElement> inTheLastFromList;
    @FindBy(xpath = "//*[@data-key='type']/following-sibling::div")
    private WebElement inTheLastType;
    @FindBy(xpath = "//*[@data-key='type']/following-sibling::div//li")
    private List<WebElement> inTheLastTypeList;
    @FindBy(xpath = "(//*[@data-key='to']/following-sibling::div)[8]//li")
    private WebElement wasExactlyDay;
    @FindBy(xpath = "//*[contains(@class,'calendar-between-body')]//div[@data-id='from']//select[@data-handler='selectMonth']/following-sibling::div[position()=1]")
    private WebElement selectMonthBoxFrom;
    @FindBy(xpath = "//*[contains(@class,'calendar-between-body')]//div[@data-id='from']//select[@data-handler='selectYear']/following-sibling::div[position()=1]")
    private WebElement selectYearBoxFrom;
    @FindBy(xpath = "//*[contains(@class,'calendar-between-body')]//div[@data-id='to']//select[@data-handler='selectMonth']/following-sibling::div[position()=1]")
    private WebElement selectMonthBoxTo;
    @FindBy(xpath = "//*[contains(@class,'calendar-between-body')]//div[@data-id='to']//select[@data-handler='selectYear']/following-sibling::div[position()=1]")
    private WebElement selectYearBoxTo;
    @FindBy(xpath = "//*[contains(@class,'calendar-between-body')]//div[@data-id='from']//table//tbody//td")
    private List<WebElement> fromTableCells;
    @FindBy(xpath = "//*[contains(@class,'calendar-between-body')]//div[@data-id='to']//table//tbody//td")
    private List<WebElement> toTableCells;
    @FindBy(xpath = "//*[contains(@class,'calendar-before-body')]//table//td")
    private List<WebElement> beforeTableCells;
    @FindBy(xpath = "//*[contains(@class,'calendar-after-body')]//select[@data-handler='selectMonth']/following-sibling::div[position()=1]")
    private WebElement afterMonthBox;
    @FindBy(xpath = "//*[contains(@class,'calendar-after-body')]//table//td")
    private List<WebElement> afterTableCells;
    @FindBy(xpath = "//*[contains(@class,'calendar-on-body')]//table//td")
    private List<WebElement> onTableCells;


    public void openCalendar(int index) {
        localDriver.findElement(By.xpath("(//*[contains(@class,'calendar-header')])[" + index + "]")).click();
    }

    public void listDownCalendarItems(int index){

        localDriver.findElement(By.xpath("(//*[contains(@class,'custom-header-back')])[" + (index + i) + "]")).click();
        i = i + 1;
    }

    public void pickSpecificCalendarItem(String item, int index) {
        List<WebElement> calendarItems = localDriver.findElements(By.xpath("(//*[contains(@class,'calendar-main-container')])[" + index + LI));
        for (WebElement element : calendarItems) {
            if (element.getText().equalsIgnoreCase(item)) {
                element.click();
            }
        }
    }


    public void selectInTheLast(String whichDate, String whichType){
        //*[@data-key='from']/following-sibling::div
        inTheLastFrom.click();
        for (WebElement element : inTheLastFromList) {
            if (element.getText().contains(whichDate)) {
                element.click();
            }
        }

        inTheLastType.click();
        for (WebElement element : inTheLastTypeList) {
            if (element.getText().contains(whichType)) {
                element.click();
            }
        }

        apply();


    }

    public void selectWasExactlyDay(String wasExactlyDays) {
        wasExactlyDay.click();
        localDriver.findElement(By.xpath("(//*[@data-key='to']/following-sibling::div)[8]//li[text()='" + wasExactlyDays + "']")).click();
    }

    public void selectBetweenDate(String fromMonth, String fromYear, String toMonth, String toYear, String fromDate, String toDate) {
        boolean fromDateCLicked = false;
        boolean fromMonthDropdownCLicked = false;
        try {
            localDriver.findElement(By.xpath("(//*[contains(@class,'calendar-between-body')]//div[@data-id='from']//select[@data-handler='selectMonth']/following-sibling::div[position()=1])[" + globalCalendarInstance + "]")).click();
            List<WebElement> selectMonthBoxFromList = localDriver.findElements(By.xpath("(//*[contains(@class,'calendar-between-body')]//div[@data-id='from']//select[@data-handler='selectMonth']/following-sibling::div[position()=1])[" + globalCalendarInstance + LI));
            for (WebElement element : selectMonthBoxFromList) {
                if (element.getText().contains(fromMonth)) {
                    element.click();

                    fromMonthDropdownCLicked = true;
                    localDriver.findElement(By.xpath("(//*[contains(@class,'calendar-between-body')]//div[@data-id='from']//select[@data-handler='selectYear']/following-sibling::div[position()=1])[" + globalCalendarInstance + "]")).click();
                    List<WebElement> selectYearBoxFromList = localDriver.findElements(By.xpath("(//*[contains(@class,'calendar-between-body')]//div[@data-id='from']//select[@data-handler='selectYear']/following-sibling::div[position()=1])[" + globalCalendarInstance + LI));
                    for (WebElement element1 : selectYearBoxFromList) {
                        if (element1.getText().contains(fromYear)) {
                            element1.click();
                            for (WebElement element2 : fromTableCells) {
                                if (element2.getText() != null && element2.getText().contains(fromDate)) {
                                    element2.click();
                                    fromDateCLicked = true;
                                    localDriver.findElement(By.xpath("(//*[contains(@class,'calendar-between-body')]//div[@data-id='to']//select[@data-handler='selectMonth']/following-sibling::div[position()=1])[" + globalCalendarInstance + "]")).click();
                                    List<WebElement> selectMonthBoxToList = localDriver.findElements(By.xpath("(//*[contains(@class,'calendar-between-body')]//div[@data-id='to']//select[@data-handler='selectMonth']/following-sibling::div[position()=1])[" + globalCalendarInstance + LI));
                                    for (WebElement element4 : selectMonthBoxToList) {
                                        if (element4.getText().contains(toMonth)) {
                                            element4.click();
                                            localDriver.findElement(By.xpath("(//*[contains(@class,'calendar-between-body')]//div[@data-id='to']//select[@data-handler='selectYear']/following-sibling::div[position()=1])[" + globalCalendarInstance + "]")).click();
                                            List<WebElement> selectYearBoxToList = localDriver.findElements(By.xpath("(//*[contains(@class,'calendar-between-body')]//div[@data-id='to']//select[@data-handler='selectYear']/following-sibling::div[position()=1])[" + globalCalendarInstance + LI));
                                            for (WebElement element5 : selectYearBoxToList) {
                                                if (element5.getText().contains(toYear)) {
                                                    element5.click();
                                                    for (WebElement element6 : toTableCells) {
                                                        if (element6.getText() != null && element6.getText().contains(toDate)) {
                                                            element6.click();

                                                            setCalendarDate();
                                                            break;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    if (fromDateCLicked) {
                                        break;
                                    }
                                }
                            }
                        }
                    }

                    if (fromMonthDropdownCLicked) {
                        break;
                    }
                }
            }

        } catch (Exception e) {
            logger.error("Something went wrong "+ e.getMessage());
        }


    }

    private void setCalendarDate() {
        try {

            WebElement apply = localDriver.findElement(By.xpath(CONTAINS_CLASS_CALENDAR_ABSOLUTE_VIEW_CONTAINER_BUTTON_TEXT_APPLY + globalCalendarInstance + "]"));
            if (apply != null) {
                apply.click();
            } else {
                //do nothing
            }
        } catch (Exception e) {
            SeleniumUtils.scrollDown(localDriver);
            localDriver.findElement(By.xpath(CONTAINS_CLASS_CALENDAR_ABSOLUTE_VIEW_CONTAINER_BUTTON_TEXT_APPLY + globalCalendarInstance + "]")).click();
            logger.error("Something went wrong"+e.getMessage());
        }
    }

    public void selectBeforeDate(String beforeMonth, String beforeYear, String whichDate) {
        boolean beforeMonthClicked = false;
        boolean beforeYearClicked = false;
        localDriver.findElement(By.xpath("(//*[contains(@class,'calendar-before-body')]//select[@data-handler='selectMonth']/following-sibling::div[position()=1])[" + globalCalendarInstance + "]")).click();
        List<WebElement> beforeMonthBoxList = localDriver.findElements(By.xpath("(//*[contains(@class,'calendar-before-body')]//select[@data-handler='selectMonth']/following-sibling::div[position()=1])[" + globalCalendarInstance + LI));
        for (WebElement element : beforeMonthBoxList) {
            if (element.getText().contains(beforeMonth)) {
                element.click();
                beforeMonthClicked = true;
                localDriver.findElement(By.xpath("(//*[contains(@class,'calendar-before-body')]//select[@data-handler='selectYear']/following-sibling::div[position()=1])[" + globalCalendarInstance + "]")).click();
                List<WebElement> beforeYearBoxList = localDriver.findElements(By.xpath("(//*[contains(@class,'calendar-before-body')]//select[@data-handler='selectYear']/following-sibling::div[position()=1])[" + globalCalendarInstance + LI));
                for (WebElement element1 : beforeYearBoxList) {
                    if (element1.getText().contains(beforeYear)) {
                        element1.click();
                        beforeYearClicked = true;
                        for (WebElement element2 : beforeTableCells) {
                            if (element2.getText() != null && element2.getText().contains(whichDate)) {
                                element2.click();
                                try {
                                    localDriver.findElement(By.xpath(CONTAINS_CLASS_CALENDAR_ABSOLUTE_VIEW_CONTAINER_BUTTON_TEXT_APPLY + globalCalendarInstance + "]")).click();
                                } catch (Exception e) {
                                    localDriver.findElement(By.xpath(CONTAINS_CLASS_CALENDAR_ABSOLUTE_VIEW_CONTAINER_BUTTON_TEXT_APPLY + globalCalendarInstance + "]")).click();
                                }
                                break;
                            }
                        }
                        if (beforeYearClicked) {
                            break;
                        }
                    }
                }
                if (beforeMonthClicked) {
                    break;
                }
            }
        }
    }

    public void selectAfterDate(String afterMonth, String afterYear, String whichDate){

        boolean afterMonthClicked = false;
        boolean afterYearClicked = false;
        localDriver.findElement(By.xpath("(//*[contains(@class,'calendar-after-body')]//select[@data-handler='selectMonth']/following-sibling::div[position()=1])[" + globalCalendarInstance + "]")).click();
        List<WebElement> afterMonthBoxList = localDriver.findElements(By.xpath("(//*[contains(@class,'calendar-after-body')]//select[@data-handler='selectMonth']/following-sibling::div[position()=1])[" + globalCalendarInstance + LI));
        for (WebElement element : afterMonthBoxList) {
            if (element.getText().contains(afterMonth)) {
                element.click();
                afterMonthClicked = true;

                localDriver.findElement(By.xpath("(//*[contains(@class,'calendar-after-body')]//select[@data-handler='selectYear']/following-sibling::div[position()=1])[" + globalCalendarInstance + "]")).click();
                List<WebElement> afterYearBoxList = localDriver.findElements(By.xpath("(//*[contains(@class,'calendar-after-body')]//select[@data-handler='selectYear']/following-sibling::div[position()=1])[" + globalCalendarInstance + LI));
                for (WebElement element1 : afterYearBoxList) {
                    if (element1.getText().contains(afterYear)) {
                        element1.click();
                        afterYearClicked = true;

                        for (WebElement element3 : afterTableCells) {
                            if (element3.getText() != null && element3.getText().contains(whichDate)) {
                                element3.click();

                                try {
                                    localDriver.findElement(By.xpath(CONTAINS_CLASS_CALENDAR_ABSOLUTE_VIEW_CONTAINER_BUTTON_TEXT_APPLY + globalCalendarInstance + "]")).click();
                                } catch (Exception e) {
                                    localDriver.findElement(By.xpath(CONTAINS_CLASS_CALENDAR_ABSOLUTE_VIEW_CONTAINER_BUTTON_TEXT_APPLY + globalCalendarInstance + "]")).click();
                                }
                                break;
                            }
                        }

                        if (afterYearClicked) {
                            break;
                        }
                    }
                }

                if (afterMonthClicked) {
                    break;
                }

            }
        }
    }

    public void selectOnDate(String onMonth, String onYear, String whichDate) {

        boolean onMonthClicked = false;
        boolean onYearClicked = false;

        localDriver.findElement(By.xpath("(//*[contains(@class,'calendar-on-body')]//select[@data-handler='selectMonth']/following-sibling::div[position()=1])[" + globalCalendarInstance + "]")).click();
        List<WebElement> onMonthBoxList = localDriver.findElements(By.xpath("(//*[contains(@class,'calendar-on-body')]//select[@data-handler='selectMonth']/following-sibling::div[position()=1])[" + globalCalendarInstance + LI));
        for (WebElement element : onMonthBoxList) {
            if (element.getText().contains(onMonth)) {
                element.click();
                onMonthClicked = true;

                localDriver.findElement(By.xpath("(//*[contains(@class,'calendar-on-body')]//select[@data-handler='selectYear']/following-sibling::div[position()=1])[" + globalCalendarInstance + "]")).click();
                List<WebElement> onYearBoxList = localDriver.findElements(By.xpath("(//*[contains(@class,'calendar-on-body')]//select[@data-handler='selectYear']/following-sibling::div[position()=1])[" + globalCalendarInstance + LI));
                for (WebElement element1 : onYearBoxList) {
                    if (element1.getText().contains(onYear)) {
                        element1.click();
                        onYearClicked = true;

                        for (WebElement element2 : onTableCells) {
                            if (element2.getText() != null && element2.getText().contains(whichDate)) {
                                element2.click();
                                try {
                                    localDriver.findElement(By.xpath(CONTAINS_CLASS_CALENDAR_ABSOLUTE_VIEW_CONTAINER_BUTTON_TEXT_APPLY + globalCalendarInstance + "]")).click();
                                } catch (Exception e) {
                                    localDriver.findElement(By.xpath(CONTAINS_CLASS_CALENDAR_ABSOLUTE_VIEW_CONTAINER_BUTTON_TEXT_APPLY + globalCalendarInstance + "]")).click();
                                }
                                break;

                            }
                        }
                        if (onYearClicked) {
                            break;
                        }
                    }
                }

                if (onMonthClicked) {
                    break;
                }

            }
        }
    }


    public void apply()  {
        SeleniumUtils.scrollDown(localDriver);
        SeleniumUtils.performClick(localDriver, applyButton);
    }


    public Calendar(WebDriver previousBroserDriver) {
        localDriver = previousBroserDriver;
        PageFactory.initElements(previousBroserDriver, this);
    }
}
