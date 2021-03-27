package com.clevertap.utils;

import com.clevertap.ui.pages.campaign_fields.Campaign;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.Map;

public class LoadYamlFile {

    private static final String CREATE_CAMPAIGN = "./resources/test_bed/jsondata/campaignInput.yaml";
    private static ObjectMapper mapper;
    public static Campaign campaignMeta;
    private static Logger logger = Logger.getLogger(LoadYamlFile.class);
//    public static Map<String, String> customKeyValuePair;
    public static Map<String,Map<String,String>> segmentQueryCSVMap=null;
    public static Map<String,Map<String,String>> changeSegmentQueryCSVMap=null;
    public static Map<String,Map<String,String>> abTestCSVMap=null;
    public static Map<String,Map<String,String>> messageOnUserPropTestCSVMap=null;
    public static Map<String,Map<String,String>> whatPageAdvanceSettingsCSVMap=null;
    public static Map<String,Map<String,String>> whenPageAdvanceSettingsCSVMap=null;
    public static Map<String,Map<String,String>> journeyCSVMap=null;



    static {
        mapper = new ObjectMapper(new YAMLFactory());
        try {
            campaignMeta = mapper.readValue(new File(CREATE_CAMPAIGN), Campaign.class);
            segmentQueryCSVMap= FileUtility.readCSVForSegmentQuery("./resources/test_bed/Segment_Query.csv");
            changeSegmentQueryCSVMap= FileUtility.readCSVForSegmentQuery("./resources/test_bed/Change_Segment_Query.csv");
            abTestCSVMap= FileUtility.readCSVForSegmentQuery("./resources/test_bed/AB_Test.csv");
            messageOnUserPropTestCSVMap= FileUtility.readCSVForSegmentQuery("./resources/test_bed/MessageOnUserProp.csv");
            whatPageAdvanceSettingsCSVMap= FileUtility.readCSVForSegmentQuery("./resources/test_bed/What_Page_Advance_Settings.csv");
            whenPageAdvanceSettingsCSVMap= FileUtility.readCSVForSegmentQuery("./resources/test_bed/When_Page_Advance_Settings.csv");
            journeyCSVMap= FileUtility.readCSVForSegmentQuery("./resources/test_bed/Journey/Journey.csv");

        } catch (Exception e) {
            logger.error("Error :::: " + e.getMessage());
        }
//        customKeyValuePair = new HashMap<String, String>();
//        customKeyValuePair.put("key1", "value1");
//        customKeyValuePair.put("key2", "value2");
//        customKeyValuePair.put("key3", "value3");

    }


}
