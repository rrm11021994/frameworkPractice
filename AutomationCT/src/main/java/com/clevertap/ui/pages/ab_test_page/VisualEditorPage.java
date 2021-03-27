package com.clevertap.ui.pages.ab_test_page;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class VisualEditorPage extends ABTestCommon {

    public VisualEditorPage(WebDriver previousBrowserDriver) {
        driver = previousBrowserDriver;
        PageFactory.initElements(previousBrowserDriver, ABTestCommon.class);
        PageFactory.initElements(previousBrowserDriver, this);
    }
}
