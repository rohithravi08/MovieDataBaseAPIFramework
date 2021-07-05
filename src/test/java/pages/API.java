package pages;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;

public class API {
    public static JSONObject msgBody = new JSONObject();

    public static void readApiMsgBody(String fileName){
        JSONParser parser = new JSONParser();
        String path= "src/test/java/resources";

        try{
            Object obj = parser.parse(new FileReader(path+"/"+fileName+".json"));
            msgBody = (JSONObject) obj;
        }catch(Exception e){
            e.printStackTrace(System.out);
        }

    }
}
