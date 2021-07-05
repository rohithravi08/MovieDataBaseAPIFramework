package stepDefinitions;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import pages.API;
import resources.Utils;

import java.io.IOException;


public class Hooks extends Utils {

    @Before("@api")
    public void preSetup() throws IOException {
        RestAssured.baseURI= getGlobalProperty("APIenv");
        API.msgBody.clear();
        GenericAPICallSteps.customHeaders.clear();
    }

}
