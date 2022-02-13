package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BaseApi {

    public Response getPostResponse(Map<String,String> header, Map<String,Object> body, String url){
        return RestAssured.given()
                .headers(header)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    public Response getPostResponse(Map<String,String> header, String body, String url){
        return RestAssured.given()
                .headers(header)
                .body(body)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    public Response getPostResponse(Map<String,Object> body, String url){
        return RestAssured.given()
                .body(body)
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

     public Response getRelaxedPostResponse(Map<String,String> header, Map<String,Object> body, String url){
        return RestAssured.given()
                .headers(header)
                .formParams(body)
                .relaxedHTTPSValidation()
                .when()
                .post(url)
                .then()
                .extract()
                .response();
    }

    public Response patch(Map<String,String> header, String body, String url){
        return RestAssured.given()
                .headers(header)
                .body(body)
                .when()
                .patch(url)
                .then()
                .extract()
                .response();
    }

    public Response patch(Map<String,String> header, Map<String,Object> body, String url){
        return RestAssured.given()
                .headers(header)
                .body(body)
                .when()
                .patch(url)
                .then()
                .extract()
                .response();
    }

    public Response getResponse(Map<String,String> header, String url){
        return RestAssured.given()
                .headers(header)
                .when()
                .get(url)
                .then()
                .extract()
                .response();
    }

    public Response put(Map<String,String> header, Map<String,Object> body, String url){
        return RestAssured.given()
                .headers(header)
                .body(body)
                .when()
                .put(url)
                .then()
                .extract()
                .response();
    }

    public void assertStatusCode(Response res, int value){
        Assert.assertEquals(res.getStatusCode(), value);
    }

    public void assertStatusCode(Response res, int lowerVal, int upperVal){
        Assert.assertTrue(res.getStatusCode() >= lowerVal && res.getStatusCode() < upperVal );
    }

    public String getValueFromResponseBody(Response res, String key){
        return res.body().jsonPath().getString(key);
    }


    public List<List<String>> getValueFromResponseBodyList(Response res, String key){
        return res.body().jsonPath().getList(key);
    }

    public List<List<List<String>>> getValueFromResponseBodyListOfLists(Response res, String key){
        return res.body().jsonPath().getList(key);
    }

    public String getValueFromResponse(Response res, String key){
        String statusLine =  res.getStatusLine();
        String headers = res.getHeaders().toString();
        String body = res.getBody().toString();
        return body;
    }

    public boolean validateParameterValueInResponseBodyHook(Response res, String param, String expectedValue, Boolean simple){
        List<List<List<String>>> complex;
        List<List<String>> med;
        List<String> flat;
        if(simple){
            med = getValueFromResponseBodyList(res,param);
        }else {
            complex = getValueFromResponseBodyListOfLists(res,param);
            med = complex.stream()
                    .flatMap(List::stream)
                    .collect(Collectors.toList());
        }
        flat = med.stream()
                .flatMap(List::stream)
                .collect(Collectors.toList());

            for (String value : flat) {
                if (value.contains(expectedValue)) {
                    return true;
                }
            }

        return false;
    }

    public void validateParameterValueInResponseBody(Response res, String param, String expectedValue){
        String value = getValueFromResponseBody(res,param);
        Assert.assertTrue(value.contains(expectedValue));
    }

    public void validateParameterValueInResponseBodyEquals(Response res, String param, String expectedValue){
        String value = getValueFromResponseBody(res,param);
        Assert.assertEquals(value,expectedValue);
    }

    public void validateParameterValueInResponse(Response res, String param, String expectedValue){
        String value = getValueFromResponse(res,param);
        Assert.assertTrue(value.contains(expectedValue));
    }

    public void validateResponseError(Response res, String expectedValue){
        String responseAsString = ((RestAssuredResponseImpl) res).getBody().asPrettyString();
        Assert.assertTrue(responseAsString.contains(expectedValue));
    }

    public void validateResponseMissing(Response res, String expectedValue){
        String responseAsString = ((RestAssuredResponseImpl) res).getBody().asPrettyString();
        Assert.assertFalse(responseAsString.contains(expectedValue));
    }


}
