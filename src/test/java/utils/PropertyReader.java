package utils;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class PropertyReader {

    JSONParser parser = new JSONParser();

    public Map<String,String> getProperties(String filename) throws IOException, ParseException {
        HashMap<String,String> properties = new HashMap<String,String>();

        Object obj = parser.parse(new FileReader("src\\test\\resources\\"+filename));

        JSONObject jsonObject = (JSONObject) obj;

        properties.put("dbURL", (String) jsonObject.get("dbURL"));
        properties.put("user", (String) jsonObject.get("user"));
        properties.put("pass", (String) jsonObject.get("pass"));
        properties.put("dbClass", (String) jsonObject.get("dbClass"));
        properties.put("schema",(String) jsonObject.get("schema"));

        return null;
    }
}
