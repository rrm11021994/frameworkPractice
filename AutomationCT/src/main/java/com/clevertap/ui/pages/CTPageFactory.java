package com.clevertap.ui.pages;

import com.clevertap.ui.pages.analyze_page.EventsPage;
import com.clevertap.ui.pages.analyze_page.FunnelsPage;
import com.clevertap.ui.pages.login_page.LoginPageNegativeScenarios;
import com.clevertap.ui.pages.settings_menu_page.CampaignIntegration;
import com.clevertap.ui.pages.widget.SweetAlert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public final class CTPageFactory {

    public static Login login;
    public static  SweetAlert sweetAlert;
    public static CampaignIntegration integrate;
    public static EventsPage events ;
    public static FunnelsPage funnel;
    public static LoginPageNegativeScenarios loginPageNegativeScenarios;

    private static boolean initializedPages=false;


    public static void initializePageObjects(WebDriver driver){

        if(initializedPages ==  false) {
            initializedPages = true;

            login=PageFactory.initElements(driver,Login.class);
            sweetAlert=PageFactory.initElements(driver,SweetAlert.class);
            integrate=PageFactory.initElements(driver,CampaignIntegration.class);
            events=PageFactory.initElements(driver,EventsPage.class);
            funnel=PageFactory.initElements(driver,FunnelsPage.class);
            loginPageNegativeScenarios=PageFactory.initElements(driver,LoginPageNegativeScenarios.class);


        }

    }



}
