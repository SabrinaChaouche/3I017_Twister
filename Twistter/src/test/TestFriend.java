package test;

import java.sql.SQLException;

import org.json.JSONObject;

public class TestFriend {

public static void main(String[] args) throws SQLException {
	// TODO Auto-generated method stub
 // boolean b= UserTools.userExists(6);
  //System.out.println(b);
	JSONObject ret = new JSONObject();
	// On appelle le service
	try{
		ret= services.Friend.addFriend("b78321af1d4b4b8ba11475d91beb0804","sar", "user1");	
		 
		ret= services.Friend.listFriend("b78321af1d4b4b8ba11475d91beb0804", "sar");
	    //ret = services.Friend.listFriend("", "user1");
	    //ret = services.Friend.removeFriend("","user1","user2");
	    //ret = services.Friend.removeFriend("","user1","user3");
	    //ret = services.Friend.listFriend("", "user1");
     }
     catch(Exception e){
	   e.printStackTrace();
     }
     System.out.println(ret);
   
}
}


