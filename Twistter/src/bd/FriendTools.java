package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FriendTools {

	public static void addFriend(int idUser, int idFriend) throws SQLException {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection c = Database.getMySQLConnection();
			Statement stmt = c.createStatement();
			String query = "INSERT INTO friend (id_user,id_friend,timestamp) VALUES ('"
					+ idUser + "', '" + idFriend + "', NOW() );";
			String query2 = "INSERT INTO friend (id_user,id_friend,timestamp) VALUES ('"
					+ idFriend + "', '" + idUser + "', NOW());";
			stmt.executeUpdate(query);
			stmt.executeUpdate(query2);
			stmt.close(); 
			c.close();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static void removeFriend(int idUser, int idFriend) throws SQLException {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection c = Database.getMySQLConnection();
			Statement stmt = c.createStatement();
			GregorianCalendar calendar=new java.util.GregorianCalendar();
			Date time = calendar.getTime();
			String query = "DELETE FROM friend WHERE id_user='"+idUser+"' AND id_friend='"+ idFriend+"';";
			String query2 = "DELETE FROM friend WHERE id_user='"+idFriend+"' AND id_friend='"+ idUser+"';";
			stmt.executeUpdate(query);
			stmt.executeUpdate(query2);
			stmt.close();
			c.close();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public static boolean areFriends(int idUser, int idFriend) throws SQLException {
		boolean retour= false;
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection c = Database.getMySQLConnection();
			Statement stmt = c.createStatement();
			String query = "SELECT * FROM friend WHERE id_user='"+idUser+"' AND id_friend='"+ idFriend+"';";
			stmt.executeQuery(query);
			ResultSet rs = stmt.getResultSet();
			if (rs.next())
				retour = true;
			stmt.close();
			c.close();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retour;
	}

	public static JSONObject listFriends(int idUser) throws SQLException, JSONException {
		List<String> l=new ArrayList();
		JSONObject res=new JSONObject();
		JSONArray amis=new JSONArray();
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection c = Database.getMySQLConnection();
			Statement stmt = c.createStatement();
			String query = "SELECT nom,prenom,login FROM user JOIN friend ON user.id= friend.id_friend WHERE friend.id_user='"+idUser+"';";
			stmt.executeQuery(query);
			ResultSet rs = stmt.getResultSet();
			JSONArray res1=new JSONArray();
			int cpt=0;
			List<String> l2 = new ArrayList();
			String s="";
			
		    while (rs.next()){
				/*s+="Nom: "+rs.getString(1)+" ;";
	            s+="Prenom: "+rs.getString(1)+" ;";
	            s+="Prenom: "+rs.getString(1)+" ";
	            l.add(s);
	            s="";*/
		    	cpt++;
		    	amis.put(rs.getString(3));
		    }
		    res.put("amis",amis);
		    res.accumulate("nbr",cpt);
			stmt.close();
			c.close();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return res;
	}

	

}
