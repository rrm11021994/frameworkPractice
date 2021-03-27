package com.clevertap.utils.RestApiUtil;

import com.clevertap.BaseTest;
import com.google.gson.*;
import com.jayway.restassured.response.Response;
import org.apache.commons.lang.RandomStringUtils;
import org.joda.time.LocalDate;
import org.testng.Reporter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.clevertap.BaseTest.getValue;

public class ApiUtility {
    private static String accountId ="";
    private static String passcode ="";
    private static boolean  flag=true;
    private static String parentMemberName;
    private static JsonObject orignalJsonObject;

    public static void setCommonHeader(String accountName){
        accountName=accountName.replaceAll(" ","");
        accountId=getValue(accountName+"_accountId");
        passcode=getValue(accountName+"_passcode");

    }

    public static Map<String, String> getCommonHeader() {
        setCommonHeader(BaseTest.getValue("AccountName"));
        Map<String, String> commonHeader = new HashMap<String, String>();
        commonHeader.put("X-CleverTap-Account-Id",accountId);
        commonHeader.put("X-CleverTap-Passcode",passcode);
        return commonHeader;
    }

    /**
     * returns the base URL of the configured env.
     * Possible values are sk1,sk2,sk3,sk4,sk5,eu1
     * @param env
     * @return {@link String}
     */
    public static String getApiBaseUrl(String env)
    {
        if(env.toLowerCase().contains("eu")){
            return getValue("eu1_api");
        }else if(env.toLowerCase().contains("sk")){
            return getValue("sk1_api");
        }else if(env.toLowerCase().contains("in1")){
        return getValue("in1_api");
    }
        Reporter.log("Wrong value was sent for environment in config file values can be 'eu1-1,eu1,in1-1,in1,sk1-1,sk1'",true);
        return null;
    }

    public static JsonElement getMemberValue(JsonObject jObj, String memberXPath) {

        if (memberXPath.contains("/")) {
            String[] members = memberXPath.split("/");
            int index = getJsonArrayIndex(members[0]);
            String memberName = members[0].split("\\[")[0];
            if (jObj.getAsJsonObject().get(memberName).isJsonArray()) {
                JsonArray jArray = jObj.getAsJsonObject().get(memberName).getAsJsonArray();
                JsonObject jObjTemp = jArray.get(index).getAsJsonObject();
                return getMemberValue(jObjTemp, memberXPath.replaceFirst("/*[^/]*/", ""));
            } else {
                if (jObj.get(members[0]).isJsonObject())
                    return getMemberValue(jObj.get(members[0]).getAsJsonObject(),
                            memberXPath.replaceFirst("/*[^/]*/", ""));
            }
        } else {
            if (memberXPath.contains("[")) {
                int index = getJsonArrayIndex(memberXPath);
                memberXPath = memberXPath.split("\\[")[0];
                return jObj.getAsJsonArray(memberXPath).get(index);
            }
            return jObj.get(memberXPath);
        }
        return null;
    }

    public static int getJsonArrayIndex(String value) {
        Pattern pattern = Pattern.compile("\\[(.*?)\\]+");
        Matcher matcher = pattern.matcher(value);
        if (matcher.find())
            return Integer.parseInt(matcher.group(1));
        else
            return -1;
    }

    public static String getMemberValueAsString(Response res, String memberName) {
        String memberValue = res.jsonPath().getString(memberName);
        if (memberValue != null) {
            memberValue = memberValue.replaceAll("\"", "");
        }
        return memberValue;
    }

    public static String getMemberValueAsString(JsonObject jObject, String memberName) {
        String memberValue = jObject.get(memberName).getAsString();
        if (memberValue != null) {
            memberValue = memberValue.replaceAll("\"", "");
        }
        return memberValue;
    }

    public static JsonElement getMemberValue(Response res, String memberXPath) {
        JsonObject jObj = getJsonObject(res);
        return getMemberValue(jObj, memberXPath);
    }

    public static JsonObject getJsonObject(Response res) {
        JsonElement jElement = new JsonParser().parse(res.asString());
        JsonObject jObject = jElement.getAsJsonObject();
        return jObject;
    }

    public static JsonObject getJsonObject(String json) {
        JsonElement jElement = new JsonParser().parse(json);
        JsonObject jObject = jElement.getAsJsonObject();
        return jObject;
    }

    private static JsonElement getJsonBody(JsonObject jObj, String memberXPath,Object value) {

        if (memberXPath.contains("/")) {
            String[] members = memberXPath.split("/");
            int index = getJsonArrayIndex(members[0]);
            String memberName = members[0].split("\\[")[0];
            if(!jObj.getAsJsonObject().keySet().contains(memberName)){

                if(members[0].contains("[")){
                    jObj.getAsJsonObject().add(memberName,new JsonArray());
                }else{
                    jObj.getAsJsonObject().add(memberName,new JsonObject());
                }

            }

            if (jObj.getAsJsonObject().get(memberName).isJsonArray()) {
                JsonArray jArray = jObj.getAsJsonObject().get(memberName).getAsJsonArray();


                while(index >= jArray.size() && jArray.size()>0){

                    JsonObject jObjTemp = jArray.get(jArray.size()-1).getAsJsonObject();
                    JsonObject jObjCopy = jObjTemp.deepCopy();
                    jArray.add(jObjCopy);
                }

                if(jArray.size()==0){
                    jArray.add(new JsonObject());
                }

                JsonObject jObjTemp = jArray.get(index).getAsJsonObject();
                JsonObject updatedJobj = (JsonObject) getJsonBody(jObjTemp, memberXPath.replaceFirst("/*[^/]*/", ""),value);
                jArray.set(index,updatedJobj);
                jObj.remove(memberName);
                jObj.add(memberName,jArray);
                return jObj;

            } else {
                if (jObj.get(members[0]).isJsonObject())
                    jObj.add(members[0],getJsonBody(jObj.get(members[0]).getAsJsonObject(),
                            memberXPath.replaceFirst("/*[^/]*/", ""),value));
                    return jObj;

            }
        } else {
            if (memberXPath.contains("[")) {
                int index = getJsonArrayIndex(memberXPath);
                memberXPath = memberXPath.split("\\[")[0];
                JsonArray ja = null;
                if(jObj.keySet().contains(memberXPath)){
                    ja = jObj.getAsJsonArray(memberXPath);
                }else{
                    jObj.add(memberXPath,new JsonArray());
                    ja= jObj.getAsJsonArray(memberXPath);
                }

                if(value instanceof  Integer){
                    ja.add((Number)value);
                }else if(value instanceof  String){
                    ja.add(value.toString());
                }else if(value instanceof Boolean){
                    ja.add((Boolean) value);
                }else if(value instanceof Character){
                    ja.add((Character) value);
                }
            return jObj;

            }
            if(value instanceof  Integer){
                jObj.addProperty(memberXPath,(Number)value);
            }else if(value instanceof  String){
                jObj.addProperty(memberXPath,value.toString());
            }else if(value instanceof Boolean){
                jObj.addProperty(memberXPath,(Boolean) value);
            }else if(value instanceof Character){
                jObj.addProperty(memberXPath,(Character) value);
            }else if(value == null){
                jObj.remove(memberXPath);
            }
            return jObj;
        }

    }

    public static String getJsonBody(JsonObject jsonObject,Object... keys){

        for(int i=0;i<keys.length;i++ ){
            jsonObject = (JsonObject) getJsonBody( jsonObject,keys[i].toString(),(Object) keys[++i]);
        }
        return jsonObject.toString();
    }

    public static JsonObject getJsonObjectFromJsonFile(String jsonFileName) {
        FileReader file = null;

        try {
            file = new FileReader("./resources/test_bed/jsondata/"+ jsonFileName + ".json");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(file,JsonObject.class);

        return jsonObject;
    }

    public static String getJsonBody(String... str) {
        Map<String, String> m = new HashMap<String, String>();
        for (int i = 0; i < str.length; i += 2) {
            m.put(str[i], str[i + 1]);
        }
        String jsonString = new GsonBuilder().disableHtmlEscaping().create().toJson(m);
        return jsonString;

    }

    /***
     * Generates a jsonBody from the passed Map
     *
     * @param map - Map to be Converted to jsonBody
     * @return Generated jsonBody in the form of String
     */
    public static String mapToJson(Map<? extends Object, ? extends Object> map) {
        // Gson gson = new Gson();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        String json = gson.toJson(map);
        return json.replaceAll("\\\\", "");
    }

    /***
     * Generates jsonbody with one list as a value in a key value pair
     *
     * @param list - Pass list which is needed as a value
     * @param str  - pass key for list as the first string and then key value pairs
     * @return - jsonBody with one list as a value in a key value pair
     */
    public static String getJsonBody(List<String> list, String... str) {

        Map<String, Object> m = new HashMap<String, Object>();

        m.put(str[0], list);

        for (int i = 1; i < str.length; i += 2) {
            m.put(str[i], str[i + 1]);
        }
        String jsonString = new GsonBuilder().disableHtmlEscaping().create().toJson(m);
        jsonString = jsonString.replaceAll("\\\\", "");
        // jsonString = jsonString.replaceAll("\\[\"\\{","\\[\\{");
        // jsonString = jsonString.replaceAll("\\}\"\\]", "\\}\\]");
        jsonString = jsonString.replaceAll("\"\\{", "\\{");
        jsonString = jsonString.replaceAll("\\}\"", "\\}");
        return jsonString;
    }

    public static int getOneMothBackDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        Date date = calendar.getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return Integer.valueOf(formatter.format(date));
    }
    public static int getCurrentDateAsPerPostmanBody() {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return Integer.valueOf(formatter.format(date));
    }

    public static String getJsonBody(Object... str) {
        Map<Object, Object> m = new HashMap<Object, Object>();
        for (int i = 0; i < str.length; i += 2) {
            m.put(str[i], str[i + 1]);
        }
        String jsonString = new GsonBuilder().disableHtmlEscaping().create().toJson(m);
        return jsonString;


    }

    public static JsonObject removeKeysFromJsonObject(JsonObject jObj, String... memberXPaths) {

        for (String xPath : memberXPaths){
            if(xPath.contains("/")){
                jObj = (JsonObject)removeKeyFromJsonObject(jObj,xPath);
            }else{
                jObj.remove(xPath);
            }


        }
        return  jObj;
    }


    public static JsonElement removeKeyFromJsonObject(JsonObject jObj, String memberXPath) {

        populateOnlyOnce(memberXPath.split("/")[0],jObj);
        if(memberXPath.split("/").length==1 && flag==false){
            flag=true;
        }

        if (memberXPath.contains("/")) {
            String[] members = memberXPath.split("/");
            int index = getJsonArrayIndex(members[0]);
            String memberName = members[0].split("\\[")[0];
            if (jObj.getAsJsonObject().get(memberName).isJsonArray()) {
                JsonArray jArray = jObj.getAsJsonObject().get(memberName).getAsJsonArray();
                JsonObject jObjTemp = jArray.get(index).getAsJsonObject();
                return removeKeyFromJsonObject(jObjTemp, memberXPath.replaceFirst("/*[^/]*/", ""));
            } else {
                if (jObj.get(members[0]).isJsonObject())
                    return removeKeyFromJsonObject(jObj.get(members[0]).getAsJsonObject(),
                            memberXPath.replaceFirst("/*[^/]*/", ""));
            }
        } else {
            if (memberXPath.contains("[")) {
                int index = getJsonArrayIndex(memberXPath);
                memberXPath = memberXPath.split("\\[")[0];
                jObj.getAsJsonArray(memberXPath).remove(index);
                orignalJsonObject.remove(parentMemberName);
                orignalJsonObject.add(parentMemberName,jObj);
                return orignalJsonObject;
            }
            jObj.remove(memberXPath);
            orignalJsonObject.remove(parentMemberName);
            orignalJsonObject.add(parentMemberName,jObj);
            return orignalJsonObject;
        }
        return null;
    }

    private static void populateOnlyOnce(String member,JsonObject jsonObject){
        if(flag){
            parentMemberName=member.split("\\[")[0];
            orignalJsonObject=jsonObject.deepCopy();
            flag=false;
        }
    }

    /***
     * Generates Epoch time in seconds for the date plus/minus current date
     *
     * @param days : pass no of days, eg pass 10 for 10 days after current date and
     *             pass -10 for 10 days before the current date.See
     *             :{@link org.joda.time.LocalDate#plusDays(int)}
     * @return returns generated epoch time in {@link java.lang.String}
     */
    public static String getEpochTimeForPassedDay(int days) {
        LocalDate ld = LocalDate.now().plusDays(days);
        return Long.toString(ld.toDate().getTime() / 1000);
    }

    /***
     * Generates a random string based on length and type
     *
     * @param length - pass the length of the random string required
     * @param type   - pass "alphanumeric" for alphanumeric string, "numeric" for
     *               numeric string and "alphabets" for alphabetic string
     * @return returns a random string
     */
    public static String generateRandomString(int length, String type) {
        if (type.toLowerCase().equals("alphanumeric"))
            return RandomStringUtils.randomAlphanumeric(length);
        if (type.toLowerCase().equals("numeric"))
            return RandomStringUtils.randomNumeric(length);
        if (type.toLowerCase().equals("alphabets"))
            return RandomStringUtils.randomAlphabetic(length);
        else
            return null;
    }

    /***
     * Adds no of days into the current date
     *
     * @param days   - No of days to be added to current date
     * @param format - Format in which date is required
     * @return Date in the passed format after adding the passed days
     */
    public static String addDateToCurrentDate(int days, String format) {
        final DateFormat dateFormat1 = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, days);
        return dateFormat1.format(c.getTime());
    }

    /***
     * This method compares two dates ( in same format)
     *
     * @param date1
     * @param date2
     * @param format- format of both the dates
     * @return 1 if date1 is smaller (earlier) than date2 else returns -1 if
     *         viceversa and returns 0 when both the dates are same
     */
    public static int compareTwoDates(String date1, String date2, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            if (sdf.parse(date1).before(sdf.parse(date2)))
                return 1;
            else if (sdf.parse(date2).before(sdf.parse(date1)))
                return -1;
            else
                return 0;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -2;
    }

    public static String addDaysToAGivenDate(int days, String date, String format) {
        final DateFormat dateFormat1 = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat1.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, days);
        Reporter.log("Obtained Date is " + dateFormat1.format(c.getTime()) + " after adding " + days + " in " + date);
        return dateFormat1.format(c.getTime());
    }

    /***
     * This method converts the format of the passed date
     *
     * @param date
     * @param originalFormat - in which date is already present
     * @param targetFormat   - required format
     * @return changes the format of the date and returns it
     */
    public static String convertFormatOfPassedDate(String date, String originalFormat, String targetFormat) {
        DateFormat ogDate = new SimpleDateFormat(originalFormat, Locale.ENGLISH);
        DateFormat targetDate = new SimpleDateFormat(targetFormat);
        try {
            Date newDate = ogDate.parse(date);
            String formattedDate = targetDate.format(newDate);
            return formattedDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * Adds no of days into the current date
     *
     * @param duration
     *            - time to be added in int
     *  @param time - unit of time which is to be modified
     *            eg. hours,mins,secs,days
     * @param format
     *            - Format in which date is required
     * @param timeZone - eg. UTC,GMT,IST
     * @return Date in the passed format after adding the given duration
     */
    public static String addTimeToCurrentDate(int duration,String time ,String format,String timeZone) {
        final DateFormat dateFormat1 = new SimpleDateFormat(format);
        dateFormat1.setTimeZone(TimeZone.getTimeZone(timeZone));
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        switch (time.toLowerCase()) {
            case "days":
                c.add(Calendar.DATE, duration);
                break;
            case "mins":
                c.add(Calendar.MINUTE, duration);
                break;
            case "secs":
                c.add(Calendar.SECOND, duration);
                break;
            case "hours":
                c.add(Calendar.HOUR, duration);
                break;
            default:
                break;
        }
        return dateFormat1.format(c.getTime());
    }

    /***
     * Adds no of days into the current date
     *
     * @param duration
     *            - time to be added in int
     *  @param time - unit of time which is to be modified
     *            eg. hours,mins,secs,days
     * @param date - date which is to be modified
     * @param format
     *            - Format in which date is required
     * @return Date in the passed format after adding the given duration
     */
    public static String addTimeToAGivenDate(int duration,String time, String date, String format) {
        final DateFormat dateFormat1 = new SimpleDateFormat(format);
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(dateFormat1.parse(date));
        } catch (ParseException e) {

            e.printStackTrace();
        }
        switch (time.toLowerCase()) {
            case "days":
                c.add(Calendar.DATE, duration);
                break;
            case "mins":
                c.add(Calendar.MINUTE, duration);
                break;
            case "secs":
                c.add(Calendar.SECOND, duration);
                break;
            case "hours":
                c.add(Calendar.HOUR, duration);
                break;
            default:
                break;
        }
        Reporter.log(
                "Obtained Date is " + dateFormat1.format(c.getTime()) + " after adding " + duration + " in " + date);
        return dateFormat1.format(c.getTime());
    }
}

