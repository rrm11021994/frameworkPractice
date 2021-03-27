package com.clevertap.ui.pages.widget;

import com.clevertap.utils.SeleniumUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class CommonPropertyProfileWidget {

    private WebDriver localDriver;
    private static final String LI_TEXT = "]//li[text()='";
    private static final String DATA_AT_SELECT_PROFILE_ROW_DIV_CLASS_EVT_OUTER = "(//*[@data-at-select='profileRow']//div[@class='evtOuter'])[";


    public void enterCommonPropertyFilters(List<String> eventPropertiesCommonProperty) {

        int size = eventPropertiesCommonProperty.size();

        for (int i = 0; i < size; i++) {

            int j = i + 1;

            SeleniumUtils.scrollDown(localDriver);

            if (SeleniumUtils.getVisibility(By.id("addAnotherStepFromUI"), 10, localDriver) != null) {
                SeleniumUtils.performClick(localDriver, SeleniumUtils.getVisibility(By.id("addAnotherStepFromUI"), 10, localDriver));

                try {

                    localDriver.findElement(By.xpath(DATA_AT_SELECT_PROFILE_ROW_DIV_CLASS_EVT_OUTER + j + "]")).click();

                    localDriver.findElement(By.xpath(DATA_AT_SELECT_PROFILE_ROW_DIV_CLASS_EVT_OUTER + j + LI_TEXT + eventPropertiesCommonProperty.get(i) + "']")).click();

                } catch (Exception e) {

                    localDriver.findElement(By.xpath(DATA_AT_SELECT_PROFILE_ROW_DIV_CLASS_EVT_OUTER + j + "]")).click();

                    localDriver.findElement(By.xpath(DATA_AT_SELECT_PROFILE_ROW_DIV_CLASS_EVT_OUTER + j + LI_TEXT + eventPropertiesCommonProperty.get(i) + "']")).click();


                }
            }
        }
    }


    public CommonPropertyProfileWidget(WebDriver previousBrowserDriver) {
        localDriver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, this);
    }

}
