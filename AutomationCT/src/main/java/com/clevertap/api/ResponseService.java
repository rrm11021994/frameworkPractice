package com.clevertap.api;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import java.util.Arrays;
import java.util.Map;

import static com.clevertap.BaseTest.getValue;
import static com.clevertap.utils.RestApiUtil.ApiUtility.getApiBaseUrl;
import static com.jayway.restassured.RestAssured.given;

public class ResponseService {
    public static String baseURL="";

    static {
        baseURL = getApiBaseUrl(getValue("environment"));
    }

    /**
     * Enum used to define the request method type. Possible request method types
     * <b>
     * <li>POST</li>
     * <li>GET</li>
     * <li>DELETE</li>
     * <li>PUT</li>
     * </b> <br/>
     *
     *
     *
     */
    public enum RequestMethodType {
        POST, GET, DELETE, PUT
    }


    /**
     * Calls to the requested url with the request specifications using the request method type
     * @param url : A fully qualified url where the request specification to be pushed
     * @param request : A specification of the request generally contains the json string, content type, headers and query parameters
     * @param methodType : A method type used to the push the request specification
     * @return {@link Response}
     * @see {@link RequestSpecification}
     * @see {@link RequestMethodType}
     */
    public Response getResponse(String url, RequestSpecification request, RequestMethodType methodType) {
        switch (methodType) {
            case POST:
                return request.post(url);
            case GET:
                return request.get(url);
            case DELETE:
                return request.delete(url);
            case PUT:
                return request.put(url);
            default:
                return null;
        }
    }

    /**
     * Construct the request specifications using the given properties and performs the call to the
     * {@link #getResponse(String, RequestSpecification, RequestMethodType)}
     * @param url : URL of the api excluding the base URL
     * @param jsonBody : look at {@link RequestSpecification#body(String)}
     * @param queryParam : look at {@link RequestSpecification#queryParams(Map)}
     * @param contentType : look at {@link RequestSpecification#contentType(String)}
     * @param headers : look at {@link RequestSpecification#headers(Map)}
     * @param methodType : look at {@link RequestMethodType}
     * @param toLog : Default is true and will log the response but if dont want to log a response then pass false
     * @return {@link Response}
     */
    public Response getResponse(String url, String jsonBody, Map<String, ? extends Object> queryParam,
                                String contentType, Map<String, String> headers, RequestMethodType methodType,boolean toLog) {
        String requestURL = this.baseURL + url;
        RequestSpecification request = given();

        if (jsonBody != null) {
            if (!jsonBody.equals(""))
                request = request.body(jsonBody);
        }

        if (queryParam != null) {
            if (!queryParam.isEmpty()) {
                request = request.queryParams(queryParam);
            }
        }

        request = request.when();

        if (contentType != null) {
            if (!contentType.equals(""))
                request = request.contentType(contentType);
        }

        if (headers != null) {
            if (!headers.isEmpty())
                headers.put("Content-Type",contentType);
                request = request.headers(headers);
        }
        Response res = getResponse(requestURL, request, methodType);

        if(toLog == true){

            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            System.out.println("Response of " +stackTraceElements[2].getMethodName()+" :");
            System.out.println(res.asString());

        }

        return res;
    }


}
