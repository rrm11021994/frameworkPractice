package com.clevertap.api;


import static com.clevertap.utils.RestApiUtil.ApiUtility.*;
import static com.clevertap.utils.RestApiUtil.ApiUtility.getJsonBody;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.jayway.restassured.response.Response;
import org.javatuples.Triplet;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ApiLibrary extends ResponseFactory{

    private List<String> mandtoryField=new ArrayList<String>();

    private List<String> populateMandtoryField(){
        if(mandtoryField.size()<0){
            mandtoryField.add("name");
            mandtoryField.add("when");
            mandtoryField.add("content");
            mandtoryField.add("content/title");
            mandtoryField.add("content/body");
            mandtoryField.add("content/subject");
            mandtoryField.add("content/sender_name");
            mandtoryField.add("target_mode");
            mandtoryField.add("where");

        }

        return mandtoryField;
    }


    /**
     * Generates jsonbody with passed Triplet value and gives reponse after processing request
     * @param eventDetails pass triplet as argument to this method in which 1st value should be 'identity' 2nd should be 'evtName' and 3rd should be 'Map' containing event data
     * @return reponse after uploading event
     */
    public Response uploadEventProperties(Triplet<String,String,Map<String,String>>...eventDetails){
        List<String> jsonList=new ArrayList<>();
        for (Triplet<String,String,Map<String,String>> eventDetail:eventDetails) {
            String jsonBody=getJsonBody("evtData",mapToJson(eventDetail.getValue2()),"identity",eventDetail.getValue0(),"type","event","evtName",eventDetail.getValue1());
            jsonList.add(jsonBody);
        }
        String finalJson=getJsonBody(jsonList,"d");
        return uploadEventProperties(finalJson);
    }

//    public Response createCampaign(String jsonFilepath,Object ...values){
//        populateMandtoryField();
//        JsonObject jsonObject=getJsonObjectFromJsonFile(jsonFilepath);
//
//        for(int i=0;i<values.length;i+=2){
//            getJsonBody(jsonObject,values[i].toString(),values[i+1]);
//        }
//
//        return createCampaign(jsonObject.toString());
//
//    }

    public Response stopCampaign(int campaignId){
        String jsonBody=getJsonBody((Object) "id",campaignId);
        return stopCampaign(jsonBody);
    }

    public Response getCampaignReport(int campaignId){
        String jsonBody=getJsonBody((Object) "id",campaignId);
        return getCampaignReport(jsonBody);
    }

    public Response getListOfCampaigns(int fromDate, int toDate){
        String jsonBody=getJsonBody((Object) "from",fromDate,"to",toDate);
        return getListOfCampaigns(jsonBody);
    }


    /***
     * This method travererses through the response provided of {@link #getListOfCampaigns(int, int)}
     * and serches for the given th campaign ID, if found return ths corresponding JSONObject else returns null
     * @param listOfCampRes
     * @param campId
     * @return JsonObject from the targets JsonArray present in the response.
     */
    public JsonObject getSDetailsOfCampaignFromGetCampaignListResponse(Response listOfCampRes,int campId){

        for(JsonElement campaign : getMemberValue(listOfCampRes,"targets").getAsJsonArray()){
            if(getMemberValue(campaign.getAsJsonObject(),"id").getAsInt()==campId){
                return campaign.getAsJsonObject();
            }
      }
        Reporter.log("CampaignId "+campId+" not found in the given targets.",true);
        return null;
    }

}
