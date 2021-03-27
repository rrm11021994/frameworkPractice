package com.clevertap.api;

import com.jayway.restassured.response.Response;

import java.util.Map;

import static com.clevertap.utils.RestApiUtil.ApiUtility.getCommonHeader;

public class ResponseFactory extends ResponseService {
    private Map<String, String> commonHeader = getCommonHeader();

    public Response uploadEventProperties(String jsonBody){

        return getResponse("/1/upload", jsonBody, null,
                "application/json",commonHeader , RequestMethodType.POST,true);
    }

    public Response createCampaign(String jsonBody){

        return getResponse("/1/targets/create.json", jsonBody, null,
                "application/json",commonHeader , RequestMethodType.POST,true);
    }

    public Response stopCampaign(String jsonBody){
        return getResponse("/1/targets/stop.json", jsonBody, null,
                "application/json",commonHeader , RequestMethodType.POST,true);
    }

    public Response getCampaignReport(String jsonBody){
        return getResponse("/1/targets/result.json", jsonBody, null,
                "application/json",commonHeader , RequestMethodType.POST,true);
    }

    public Response getListOfCampaigns(String jsonBody){
        return getResponse("/1/targets/list.json", jsonBody, null,
                "application/json",commonHeader , RequestMethodType.POST,false);
    }

    public Response uploadUserProperty(String jsonBody){
        return getResponse("/1/upload", jsonBody, null,
                "application/json",commonHeader , RequestMethodType.POST,true);
    }


}
