package resources;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Utils {

    public static String getJsonPath(Response response, String key)
    {
        String resp=response.asString();
        JsonPath js = new JsonPath(resp);
        return js.get(key).toString();
    }

    public static String getGlobalProperty(String KeyValue) throws IOException {
        Properties prop= new Properties();
        FileInputStream fis= new FileInputStream("src/test/java/resources/global.properties");
        prop.load(fis);
        return prop.getProperty(KeyValue);

    }

}
