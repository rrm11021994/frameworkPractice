package com.clevertap.ui.RestApiUtil;

import com.clevertap.utils.SeleniumUtils;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import static com.clevertap.BaseTest.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;



public class RestApi {

    private static final String ACCOUNT_KEY = "X-CleverTap-Account-Id";
    private static final String PASSCODE_KEY = "X-CleverTap-Passcode";
    private static final String CONTENT_TYPE = "application/json";

    private HttpURLConnection con = null;
    private JSONObject object;
    private StringBuilder response = new StringBuilder();
    private InputStream inputStream;
    private InputStreamReader inputStreamReader;
    private BufferedReader br=null;
    private Logger logger = Logger.getLogger(RestApi.class.getSimpleName());

    private HttpURLConnection getHttpConnection(String whichMethod, String url)  {
        try {
            URL obj = new URL(url);
            con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(whichMethod);
        } catch (IOException e) {
            logger.error("IOException in creating HTTPConnection: getHttpConnection");
        }
        return con;
    }

    private void setUpHeader() {
        con.setRequestProperty(ACCOUNT_KEY, getValue(ACCOUNT_KEY));
        con.setRequestProperty(PASSCODE_KEY, getValue(PASSCODE_KEY));
        con.setRequestProperty("Content-Type", CONTENT_TYPE);
        con.setRequestProperty("Accept", CONTENT_TYPE);
        con.setDoOutput(true);

    }

    private void setUpHeader(String accountID, String passcode) {
        con.setRequestProperty(ACCOUNT_KEY, accountID);
        con.setRequestProperty(PASSCODE_KEY, passcode);
        con.setRequestProperty("Content-Type", CONTENT_TYPE);
        con.setRequestProperty("Accept", CONTENT_TYPE);
        con.setDoOutput(true);

    }

    private void setUpBody(Object object) {
        JSONObject jsonObject = new JSONObject();
        JSONParser parser = new JSONParser();

        try {
                if(object.getClass().equals(String.class)) {
                    FileReader jsonFileReader = new FileReader("./json_data/" + object);
                    jsonObject = (JSONObject) parser.parse(jsonFileReader);
                }
                else{
                    jsonObject = (JSONObject) object;
                }
                OutputStream os = con.getOutputStream();
                byte[] input = jsonObject.toString().getBytes();
                os.write(input, 0, input.length);
            } catch (FileNotFoundException e) {
                logger.error("FileNotFound Exception in method: setUpBody");
            } catch (IOException e) {
            logger.error("IOException in method: setUpBody");
            } catch (ParseException e) {
            logger.error("Parse Exception in method: setUpBody");
                //Do Nothing
            }
    }

    private JSONObject getResponse() {
        try {
            SeleniumUtils.pause(4);
            inputStream = con.getInputStream();
            inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            br= new BufferedReader(inputStreamReader);
            String responseLine;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try{
                if(!SeleniumUtils.isNullOrEmpty(inputStream)){
                    logger.error("Exception in closing inputStream");
                    inputStream.close();
                }
                if(!SeleniumUtils.isNullOrEmpty(inputStreamReader)){
                    logger.error("Exception in closing inputStreamReader");
                    inputStreamReader.close();
                }
                if(!SeleniumUtils.isNullOrEmpty(br)){
                    logger.error("Exception in closing BufferedReader");
                    br.close();
                }
            }catch(Exception e1){
                logger.error("Exception in finally statement while closing the handles.");
            }
        }

        JSONParser jsonParser = new JSONParser();
        try {
            object = (JSONObject) jsonParser.parse(response.toString());
        } catch (ParseException e) {
            logger.error("Parse Exception in getResponse");
            e.printStackTrace();
        }
        response=new StringBuilder();

        return object;
    }

    public JSONObject fetchDetails(String jsonFileName,String apiUrl,String whichMethod)  {
        getHttpConnection(whichMethod, apiUrl);
        setUpHeader();
        setUpBody(jsonFileName);
        return getResponse();
    }

    public Boolean pushAction(Object object ,String apiUrl,String whichMethod) {

        Boolean pushed = false;
        getHttpConnection(whichMethod, apiUrl);
        setUpHeader();
        setUpBody(object);
        JSONObject responseObject = getResponse();
        if (responseObject.get("status").toString().equalsIgnoreCase("success")) {
            pushed = true;
        }
        return pushed;
    }

    public Boolean pushAction(Object object ,String apiUrl,String whichMethod, String accountID, String passcode) {

        Boolean pushed = false;
        getHttpConnection(whichMethod, apiUrl);
        setUpHeader(accountID, passcode);
        setUpBody(object);
        JSONObject responseObject = getResponse();
        if (responseObject.get("status").toString().equalsIgnoreCase("success")) {
            pushed = true;
        }
        return pushed;
    }
}