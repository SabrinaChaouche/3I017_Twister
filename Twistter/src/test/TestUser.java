package test;

import java.io.PrintWriter;
import java.sql.SQLException;

import org.json.JSONObject;

import bd.UserTools;

public class TestUser {

	public static void main(String[] args) throws SQLException {
		// TODO Auto-generated method stub
     // boolean b= UserTools.userExists(6);
      //System.out.println(b);
		JSONObject ret = new JSONObject();
		// On appelle le service
		try{
			ret = services.User.createUser("user1","a","b","admin");
			ret = services.User.createUser("user2","c","d","admin");
			ret = services.User.createUser("user7","e","f","admin");
			// ret = services.User.logout("d24ce135529144368b37f9ca74e39548");
			ret = services.User.login("user7", "admin");
		}
		catch(Exception e){
			e.printStackTrace();
		}
        System.out.println(ret);
       
	}

}
