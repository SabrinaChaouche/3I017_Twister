package services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Friend {

	public static JSONObject addFriend(String key,String user, String friend)
			throws SQLException, JSONException {
		JSONObject rep = new JSONObject();
		if(user == null || friend == null || key==null) {
			rep = servicesTools.ServiceRefused.serviceRefused("Wrong arguments! ", -1);
			return rep;
		}
		int idUser = bd.UserTools.get_userId(user);
		int idFriend = bd.UserTools.get_userId(friend);
		JSONObject object = new JSONObject();
		if (!bd.UserTools.userExists(idUser) || !bd.UserTools.userExists(idFriend)) {
			object = servicesTools.ServiceRefused.serviceRefused(
					"user doesn't exist", 1000);
		}
		else if(bd.UserTools.userConnected(key)==0){
			object = servicesTools.ServiceRefused.serviceRefused(
					"user is not connected", 1000);
		}
		else if(bd.FriendTools.areFriends(idUser, idFriend)){
			object = servicesTools.ServiceRefused.serviceRefused(
					"you are already friends", 1000);
		}
		else {
			try {
				bd.FriendTools.addFriend(idUser, idFriend);
				object.put("status", "OK");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return object;
	}

	public static JSONObject removeFriend(String key,String user, String friend)
			throws SQLException, JSONException {
		JSONObject rep = new JSONObject();
		if(user == null || friend == null || key==null) {
			rep = servicesTools.ServiceRefused.serviceRefused("Wrong arguments! ", -1);
			return rep;
		}
		int idUser = bd.UserTools.get_userId(user);
		int idFriend = bd.UserTools.get_userId(friend);
		JSONObject object = new JSONObject();
		if (!bd.UserTools.userExists(idUser) || !bd.UserTools.userExists(idFriend)) {
			object = servicesTools.ServiceRefused.serviceRefused(
					"user doesn't exist", 1000);
			System.out.println("ok");
		} else if (!bd.FriendTools.areFriends(idUser, idFriend)) {
			object = servicesTools.ServiceRefused.serviceRefused(
					"No such friend", 1000);
		} 
		else if(bd.UserTools.userConnected(key)==0){
			object = servicesTools.ServiceRefused.serviceRefused(
					"user is not connected", 1000);
		}
		else {
			try {
				bd.FriendTools.removeFriend(idUser, idFriend);
				object.put("status", "OK");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return object;
	}

	public static JSONObject listFriend(String key, String user) throws SQLException, JSONException {
		int idUser = bd.UserTools.get_userId(user);
		JSONObject object = new JSONObject();
		JSONObject res = new JSONObject();
		JSONObject rep = new JSONObject();
		if(user == null  || key==null) {
			res = servicesTools.ServiceRefused.serviceRefused("Wrong arguments! ", -1);
			return res;
		}
		if (!bd.UserTools.userExists(idUser) ) {
			res = servicesTools.ServiceRefused.serviceRefused(
					"user doesn't exist", 1000);
		}
		else if(bd.UserTools.userConnected(key)==0){
			res = servicesTools.ServiceRefused.serviceRefused(
					"user is not connected", 1000);
		}
		else {
			try {
				res=bd.FriendTools.listFriends(idUser);
				res.accumulate("status", "OK");
				//object.put("amis", res);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return res;
	}

}