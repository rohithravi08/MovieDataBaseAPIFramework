package stepDefinitions;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.Assert;
import pages.API;
import resources.APIResources;
import resources.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GenericAPICallSteps{

    public static Map<String, String> customHeaders =new HashMap<String, String>();
    ResponseSpecification resspec;
    Response response;
    public String guestSession;

    @Given("I make REST service headers with the below fields")
    public void iMakeRESTServiceHeadersWithTheBelowFields(DataTable headerValues){
        customHeaders.clear();
        List<Map<String,String>> headers = headerValues.asMaps(String.class,String.class);
        for(Map<String,String> header :headers){
            customHeaders.putAll(header);
        }
    }

    @When("I read the message Body from the file {string}")
    public void iReadTheMessageBodyFromTheFile(String fileName){
        API.msgBody.clear();
        API.readApiMsgBody(fileName);
    }

    @When("user calls {string} with {string} http request and param {string}")
    public void user_calls_with_http_request(String resource, String method, String Parampath) {

        Map<String, String> queryParams = new HashMap<>();
        //Get query Params
        if(Parampath.contains("?")){
            String[] parampairs= ((Parampath.split("\\?"))[1]).split("&");
            for(String pair: parampairs){
                String[] myPair=pair.split("=");
                if(myPair.length==1){
                    queryParams.put(myPair[0],"");
                }else{
                    queryParams.put(myPair[0],myPair[1]);
                }
            }
        }
        //Get path param
        String pathParam= (Parampath.split("\\?"))[0];
        //Get resource from API resources
        APIResources resourceAPI=APIResources.valueOf(resource);
        String path = resourceAPI.getResource();
        //System.out.println(resourceAPI.getResource());


        resspec =new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();

        if(method.equalsIgnoreCase("POST")) {
            queryParams.put("guest_session_id",guestSession);
            response = RestAssured.given()
                    .headers(customHeaders)
                    .queryParams(queryParams)
                    .body(API.msgBody)
                    .log().all()
                    .when()
                    .post(path + pathParam)
                    .then()
                    .extract().response();

        }
        else if (method.equalsIgnoreCase("GET")){
            response = RestAssured.given()
                    .headers(customHeaders)
                    .queryParams(queryParams)
                    .log().all()
                    .when()
                    .get(path + pathParam)
                    .then()
                    .extract().response();
        }

    }

    @And("I should get the response code as {string}")
    public void iShouldGetTheResponseCodeAs(String respCode){
        Assert.assertTrue("The response code received: "+response.statusCode()+" is not equal to "+respCode,response.statusCode() == Integer.parseInt(respCode));

    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String keyValue, String Expectedvalue) {

         Assert.assertEquals(Utils.getJsonPath(response,keyValue),Expectedvalue);

    }

    @And("I update request message body with the below details")
    public void iUpdateTheMessageBodyWithTheBelowDetails(DataTable msgFields){
        List<Map<String,String>> fields = msgFields.asMaps(String.class,String.class);
        for(Map<String, String> field :fields){
            for(Map.Entry<String,String> entry: field.entrySet()){
                API.msgBody.put(entry.getKey(),entry.getValue());
            }
        }
    }

    @And("I get the guest session id using api key {string}")
    public void iGetTheGuestSessionIdUsingApiKey(String apikey){
        response = RestAssured.given()
                .when()
                .get("https://api.themoviedb.org/3/authentication/guest_session/new?api_key="+apikey)
                .then()
                .assertThat()
                .statusCode(200)
                .extract().response();
        guestSession=Utils.getJsonPath(response,"guest_session_id");
    }




}
