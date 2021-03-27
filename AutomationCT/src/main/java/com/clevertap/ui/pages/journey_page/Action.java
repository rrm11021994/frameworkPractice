package com.clevertap.ui.pages.journey_page;

import com.clevertap.ui.pages.journey_attributes.When;
import com.clevertap.ui.pages.journey_attributes.Who;
import org.openqa.selenium.WebDriver;

public class Action {

    When when = new When();
    Who who = new Who();

    public When getWhen() {
        return when;
    }

    public void setWhen(When when) {
        this.when = when;
    }

    public Who getWho() {
        return who;
    }

    public void setWho(Who who) {
        this.who = who;
    }

    public void setActionPage(WebDriver driver){
        WhenPage whenPage = new WhenPage(driver);
        whenPage.setWhenPage(this);
    }

}
