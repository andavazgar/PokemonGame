/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package models;

import java.util.Hashtable;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

public class JSONObject {
	private Map<String, Object> jsonObj = new Hashtable<>();
	
	public void put(String key, Object value) {
		jsonObj.put(key, value);
	}
	
	public Map<String, Object> getContents() {
		return jsonObj;
	}
	
	/*
	 * Source: https://stackoverflow.com/a/28187904
	 * 
	 * Printing pretty JSON
	 */
	public String getJSON() {
		String uglyJSON = new Gson().toJson(jsonObj);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jsonParser = new JsonParser();
		JsonElement jsonElement = jsonParser.parse(uglyJSON);
		String prettyJSON = gson.toJson(jsonElement);
		
		return prettyJSON;
	}
}
