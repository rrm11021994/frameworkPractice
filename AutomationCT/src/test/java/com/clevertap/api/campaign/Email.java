package com.clevertap.api.campaign;

import com.clevertap.BaseTest;
import com.clevertap.utils.Data;
import com.google.gson.JsonObject;
import com.jayway.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.clevertap.utils.RestApiUtil.ApiUtility.*;

public class Email extends BaseTest{


    private String campaignName;
    private int currentDate;//currentDate in YYYYMMdd format
    private int previousDate; // currentDate - 10 days in YYYYMMdd format


    @BeforeClass
    public void beforeClass(){

        currentDate = Integer.parseInt(addDateToCurrentDate(0,"YYYYMMdd"));
        previousDate = Integer.parseInt(addDateToCurrentDate(-10,"YYYYMMdd"));
    }

    @BeforeMethod
    public void beforeMethod(){

        campaignName = Data.randomAlphaNumeric("AutomationSMSCampaign",5);

    }



    @Test(description = "since value of 'estimate_only=false' campaign should be created")
    public void verifyCampShouldNotBeCreatedWhenEstimateIsTrue(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate);
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));
        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName));
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId));
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success");
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped");
    }




}
