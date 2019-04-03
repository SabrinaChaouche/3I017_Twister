package servicesTools;

import org.json.JSONException;
import org.json.JSONObject;

public class ServiceRefused {
	
	public static JSONObject serviceRefused(String n, int idError){
		try{
			JSONObject response = new JSONObject();
			response.put("status", "KO");
			response.put("message", n);
			response.put("idError", idError);
			return response;
		}
		catch(JSONException e){
			e.printStackTrace();
			return null;
		}
		
	}

}