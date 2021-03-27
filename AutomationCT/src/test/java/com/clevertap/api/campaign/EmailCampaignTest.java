package com.clevertap.api.campaign;

import com.clevertap.BaseTest;
import com.clevertap.utils.Data;
import com.google.gson.JsonObject;
import com.jayway.restassured.response.Response;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static com.clevertap.utils.RestApiUtil.ApiUtility.*;

public class EmailCampaignTest extends BaseTest{


    private String campaignName;
    private int currentDate;//currentDate in YYYYMMdd format
    private int previousDate; // currentDate - 10 days in YYYYMMdd format


    /*pre-requisites
    *
    * custom control group with name Test-Automation is defined
    *
    *
    *
    * */


    @BeforeClass
    public void beforeClass(){

        currentDate = Integer.parseInt(addDateToCurrentDate(0,"YYYYMMdd"));
        previousDate = Integer.parseInt(addDateToCurrentDate(-10,"YYYYMMdd"));
    }

    @BeforeMethod
    public void beforeMethod(){

        campaignName = Data.randomAlphaNumeric("AutomationSMSCampaign",5);

    }

    @Test(description = "verify only estimates should be shown and campaign should not created when 'estimate_only' key is true")
    public void verifyCampaignShouldNotBeCreateWhenEstimateOnlyIsTrue(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate,"estimate_only",true);
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success","Failed to create campaign");
        Assert.assertNull(getMemberValue(campRes,"id"),"Since campaign is not created, response should not contain id as key");
        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertFalse(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
    }


    @Test(description = "verify user should be able to create campaign without passing custom control group and estimate only key as false")
    public void createCampWithoutCCGAndEstimateOnlyAsFalse(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"control_group");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate);
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success","Failed to create campaign");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campign Id was not present in getListOfCampaign Api response");
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");
    }


    @Test(description = "verify user should be able to create campaign without filtering user based on common profile properties")
    public void createCampWithoutCommonPropInWhere(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"where/common_profile_properties");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate);

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success","Failed to create campaign");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campign Id was not present in getListOfCampaign Api response");
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");
    }


    @Test(description = "verify user should be able to create campaign with respect_frequency_caps as false and respect_throttle as false")
    public void createCampWithRespectFrequencyCapsAsFalseAndRespectThrottleAsFalse(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"respect_frequency_caps",false,"respect_throttle",false);

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success","Failed to create campaign");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campign Id was not present in getListOfCampaign Api response");
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");
    }

    @Test(description = "verify user should be able to create campaign with out respect_frequency_caps and respect_throttle keys in the jsonBody of campaign creation")
    public void createCampWithoutRespectFrequencyCapsAndRespectThrottleKeysInJson(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"respect_frequency_caps","respect_throttle");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName);

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success","Failed to create campaign");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campign Id was not present in getListOfCampaign Api response");
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

//        apiLibrary.getCampaignReport(campId);  // discussion pending
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");
    }

    @Test(description = "verify user should be able to create campaign with system control group")
    public void verifyCampCanBeCreatedWithSystemControlGroup(){

        Reporter.log("checking when system control group is on");
        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"system_control_group_include",true);

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success","Failed to create campaign");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campign Id was not present in getListOfCampaign Api response");
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");
    }


    @Test(description = "verify user should be able to create campaign without system control group")
    public void verifyCampCantBeCreatedWithoutSystemControlGroup(){

        Reporter.log("checking when system control group is of");
        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"system_control_group_include",true);

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success","Failed to create campaign");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campign Id was not present in getListOfCampaign Api response");
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");
    }

    @Test(description = "user should be able to create campaign with custom control group")
    public void verifyUserShouldBeAbleToCreateCampaignWithCustomControlGroup(){

        Reporter.log("checking when custom control group is on");
        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"control_group/percentage");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"control_group/type","custom","control_group/name","Test-Automation");
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success","Failed to create campaign");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campign Id was not present in getListOfCampaign Api response");
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

//        apiLibrary.getCampaignReport(campId);  // discussion pending
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");
    }

    @Test(description = "verify campaign should not be created when custom control group with passed value does not exist")
    public void verifyCampShouldNotBeCreatedWhenCustomControlGroupProvidedIsNotPresent(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"control_group/percentage");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"control_group/type","custom","control_group/name","RamndomNm77");

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"fail","Failed to create campaign");
        Assert.assertEquals(getMemberValueAsString(campRes,"error"),"No custom control group with name RamndomNm77 is defined","wrong message was given in response");

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertFalse(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
    }

    @Test(description = "verify user should be able to create campaign with multiple profile properties")
    public void verifyCampShouldBeCreatedWithMultipleProfileFields(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);

        String jsonBody=getJsonBody(jsonObject,"name",campaignName,"where/from",previousDate,"where/to",currentDate,
                "where/common_profile_properties/profile_fields[1]/name","test.dot7","where/common_profile_properties/profile_fields[1]/operator","equals"
                ,"where/common_profile_properties/profile_fields[1]/value","test.value");

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success","Failed to create campaign");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campign Id was not present in getListOfCampaign Api response");
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");
    }

    @Test(description = "verify Campaign should not be created when value for where.from is greater than where.to")
    public void veirfyCampShouldNotBeCreatedWhenFromDateIsGreaterToDate(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/from",Integer.parseInt(addDateToCurrentDate(10,"YYYYMMdd")));

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"fail","Failed to create campaign");
        Assert.assertEquals(getMemberValueAsString(campRes,"error"),"Query parse failure - The from date cannot be after the to date","wrong message was given in response");

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertFalse(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
    }

    @Test(description = "verify user should be able to create campaign when only mandtory values are passed")
    public void verifyCampCanBeCreatedWithOnlyMandtoryField(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"estimate_only","where/common_profile_properties","where/from","where/to","where/event_name","respect_frequency_caps","system_control_group_include","control_group","respect_throttle","provider_nick_name","skip_estimate");
        String jsonBody = getJsonBody(jsonObject,"name",campaignName);

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"success","Failed to create campaign");
        int campId = Integer.parseInt(getMemberValueAsString(campRes,"id"));

        Response listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.name").contains(campaignName),"created campaignName was not present in getListOfCampaign Api response");
        Assert.assertTrue(listOfCamps.jsonPath().getList("targets.id").contains(campId),"created campign Id was not present in getListOfCampaign Api response");
        //apiLibrary.getCampaignReport(campId);
        Response stopCampRes = apiLibrary.stopCampaign(campId);
        Assert.assertEquals(getMemberValueAsString(stopCampRes,"status"),"success","Failed to stop campaign");

//        apiLibrary.getCampaignReport(campId);  // discussion pending
        listOfCamps = apiLibrary.getListOfCampaigns(currentDate,currentDate);

        JsonObject campJsonObject = apiLibrary.getSDetailsOfCampaignFromGetCampaignListResponse(listOfCamps,campId);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"name"),campaignName);
        Assert.assertEquals(getMemberValueAsString(campJsonObject,"status"),"stopped","status of campaign was not changed to stopped");
    }

    @Test(description = "verify user should not be able to create campaign with invalid event type")
    public void verifyCampCanNotBeCreatedWithInvalidEvent(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/event_name","","where/from",previousDate,"where/to",currentDate);

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"fail","Failed to create campaign");
    }

    @Test(description = "verify user should not be able to create campaign with from date after the to date")
    public void verifyCampCanNotBeCreatedWithFromDateAferToDate(){

        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        String jsonBody = getJsonBody(jsonObject,"name",campaignName,"where/event_name","App Launched","where/from",currentDate,"where/to",previousDate);
        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"fail","Failed to create campaign");
        Assert.assertEquals(getMemberValueAsString(campRes,"error"),"Query parse failure - The from date cannot be after the to date","wrong message is present in response");
    }

    @Test(description = "verify campaign cannot be created without mandtory value",dataProvider = "Mandtory Values")
    public void verifyCampCannotBeCreatedWithoutMandtoryValues(String keyToRemove,String errorMessage){
        String jsonBody ="";
        JsonObject jsonObject = getJsonObjectFromJsonFile("CampaignJson/EmailCampaign");
        Reporter.log("Campaign name used for creating : "+campaignName,true);
        jsonObject =  removeKeysFromJsonObject(jsonObject,"estimate_only","where/common_profile_properties","where/from","where/to","where/event_name","respect_frequency_caps","system_control_group_include","control_group","respect_throttle","provider_nick_name","skip_estimate",keyToRemove);

        if(keyToRemove!="name") {
            jsonBody = getJsonBody(jsonObject, "name", campaignName);
        }else {
            jsonBody=jsonObject.toString();
        }

        Response campRes = responseFactory.createCampaign(jsonBody);
        Assert.assertEquals(getMemberValueAsString(campRes,"status"),"fail","Failed to create campaign");
        Assert.assertEquals(getMemberValueAsString(campRes,"error"),errorMessage,"wrong message was given in response");
    }

    @DataProvider(name = "Mandtory Values")
    public Object[][] mandtoryValuesDataProvider() {
        return new Object[][]{
                {"name", "Field name is empty"},
                {"target_mode","Field title is empty (inside content)"},
                {"content","Field subject is empty (inside content)"},
                {"when","Field when is empty"},
                {"where","Query specified in where failed to parse"}
        };
    }

}
