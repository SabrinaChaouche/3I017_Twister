package bd;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.*;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MessageTools {
	
	/**
	 * 
	 * @param key
	 * @param message
	 * @return
	 * @throws SQLException
	 * @throws UnknownHostException
	 * @throws JSONException 
	 */
	public static ObjectId addMessage(String key, String message) throws SQLException, UnknownHostException, JSONException {
		ObjectId id = new ObjectId();
		
		// Connexion a la BD Mongo.
		DBCollection collection = Database.getCollection("message");
		
		// Récupérer l'id de l'utilisateur à partir de la clé de connexion envoyée par ce dernier et vérifier s'il est toujours connecté.
		int id_user = UserTools.userConnected(key);
		
		// Construction du message a insérer dans la bd.
		BasicDBObject bdo = new BasicDBObject();
		bdo.put("id_user", id_user);
		bdo.put("content", message);
		GregorianCalendar c = new GregorianCalendar();
		bdo.put("date", c.getTime().toString());
		bdo.put("comments", new ArrayList<BasicDBObject>());
		bdo.put("nbLikes", 0);
		bdo.put("id_comments", 0);
		// Ajout du message
		collection.insert(bdo);

		// Construction de la réponse
		id = bdo.getObjectId("_id");
		
		return id;
	}	 

	/**
	 * 
	 * @param key
	 * @param idMessage
	 * @param comment
	 * @return
	 * @throws SQLException
	 * @throws UnknownHostException
	 * @throws JSONException
	 */
	public static int addComment(String key, String idMessage, String comment) throws SQLException, UnknownHostException, JSONException {
		int id_comment = -1;
		
		// Connexion a la BD Mongo.
		DBCollection collection = Database.getCollection("message");
		
		// Récupérer l'id de l'utilisateur à partir de la clé de connexion envoyée par ce dernier.
		int id_user = UserTools.userConnected(key);
		
		BasicDBObject query1 = new BasicDBObject();
		query1.put("_id", new ObjectId(idMessage));
		DBCursor cursor = collection.find(query1);
		int id = (Integer) cursor.next().get("id_comments");
		
		BasicDBObject comments = new BasicDBObject();
		comments.put("id", id);
		comments.put("auteur", id_user);
		comments.put("comment", comment);
		GregorianCalendar c = new GregorianCalendar();
		comments.put("date", c.getTime().toString());

		BasicDBObject query2 = new BasicDBObject("$push", new BasicDBObject("comments", comments));
		collection.update(query1, query2);

		int id_bis = id + 1;
		collection.update(query1, new BasicDBObject("$set", new BasicDBObject("id_comments", id_bis)));
		
		return id;
	}
	
	/**
	 * 
	 * @param key
	 * @param idMessage
	 * @return
	 * @throws JSONException
	 */
	public static boolean deleteMessage(String key, String idMessage) throws JSONException {
		try {
			// Connexion a la BD Mongo.
			DBCollection collection = Database.getCollection("message");
			
			try {
				// Récupérer l'id de l'utilisateur à partir de la clé de connexion envoyée par ce dernier.
				int id_user = UserTools.userConnected(key);
				
				// Tester si le message existe dans la bd Mongo et appartient à l'utilisateur
				BasicDBObject query = new BasicDBObject();
				ObjectId id = new ObjectId(idMessage);
				query.put("_id", id);
				query.put("id_user", id_user);
				DBCursor cursor = collection.find(query);
				
				// Si le message existe dans la bd Mongo et appartient à l'utilisateur: on le retire et on retourne true.
				if(cursor.hasNext()) {
					collection.remove(query);
					return true;
				}
				else return false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	
	/**
	 * 
	 * @param key
	 * @param idMessage
	 * @param idComment
	 * @return
	 * @throws JSONException
	 * @throws SQLException 
	 */
	public static int deleteComment(String key, String idMessage, String idComment)throws JSONException, SQLException {
		try {
			// Connexion a la BD Mongo.
			DBCollection collection = Database.getCollection("message");
			
			BasicDBObject query1 = new BasicDBObject("_id", new ObjectId(idMessage));
			BasicDBObject query2 = new BasicDBObject("comments", new BasicDBObject("id", Integer.parseInt(idComment)));
			/*
			BasicDBObject query3 = new BasicDBObject();
			query3.put("comments", new BasicDBObject("id", Integer.parseInt(idComment)));
			query3.put("comments", new BasicDBObject("auteur", UserTools.userConnected(key)));
			System.out.println("user " + UserTools.userConnected(key));
			
			DBCursor cursor = collection.find(query3); // no message user
			if(!cursor.hasNext()) return 1;
			*/
			
			collection.update(query1, new BasicDBObject("$pull", query2)); // ok
			return 0;

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	
	/**
	 * 
	 * @param key
	 * @param listIdUser
	 * @return
	 * @throws JSONException
	 */
	public static JSONObject listMessage(String key, String[] listIdUser) throws JSONException {
		
		// Création de l'objet de la réponse
		JSONObject reponse = new JSONObject();
		try {
			// Connexion à la BD Mongo.
			DBCollection collection = Database.getCollection("message");
			try {
				// Récupérer l'id de l'utilisateur à partir de la clé de connexion envoyée par ce dernier.
				int id_user = UserTools.userConnected(key);
				
				if(id_user != 0) {
					// S'il n'y a pas d'ID spécifié dans listIdUser alors on retourne tous les messages.
					if(listIdUser == null) {
						DBCursor cursor = collection.find();
						while(cursor.hasNext()) {
							JSONObject object = new JSONObject();
							BasicDBObject dbo = new BasicDBObject();
							dbo = (BasicDBObject) cursor.next();
							object.accumulate("id", dbo.get("_id"));
							object.accumulate("auteur", dbo.get("id_user"));
							object.accumulate("text", dbo.get("content"));
							object.accumulate("date", dbo.get("date"));
							object.accumulate("comments", dbo.get("comments"));
							reponse.accumulate("message", object);
						}
					}
					else {
						// Si la liste ne contient que l'id de l'utilisateur qui effectue la requete => on retourne la liste de ses messages.
						if(listIdUser.length == 1 && listIdUser[0].equals(Integer.toString(id_user))) {
							BasicDBObject query = new BasicDBObject();
							query.put("id_user", id_user);
							DBCursor cursor = collection.find(query);
							if(! cursor.hasNext()) reponse.put("message", "Vous n'avez aucun message!");
							else {
								while(cursor.hasNext()) {
									JSONObject object = new JSONObject();
									BasicDBObject dbo = new BasicDBObject();
									dbo = (BasicDBObject) cursor.next();
									object.accumulate("id", dbo.get("_id"));
									object.accumulate("auteur", dbo.get("id_user"));
									object.accumulate("text", dbo.get("content"));
									object.accumulate("date", dbo.get("date"));
									object.accumulate("comments", dbo.get("comments"));
									reponse.accumulate("message", object);
								}
							}
						}
						// Si la liste a plusieurs elements.
						else {
							List<Integer> list = new ArrayList<Integer>();
							for(int i = 0; i < listIdUser.length; i++){
								list.add(Integer.parseInt(listIdUser[i]));
							}
							BasicDBObject query = new BasicDBObject();
							query.put("id_user", new BasicDBObject ("$in", list));
							DBCursor cursor = collection.find(query);
							if(! cursor.hasNext()) reponse.put("message", "Les utilisateurs n'ont aucun message!");
							else {
								while(cursor.hasNext()) {
									JSONObject object = new JSONObject();
									BasicDBObject dbo = new BasicDBObject();
									dbo = (BasicDBObject) cursor.next();
									object.accumulate("id", dbo.get("_id"));
									object.accumulate("auteur", dbo.get("id_user"));
									object.accumulate("text", dbo.get("content"));
									object.accumulate("date", dbo.get("date"));
									object.accumulate("comments", dbo.get("comments"));
									reponse.accumulate("message", object);
								}
							}
						}
					}
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reponse;
	}
	public static void addLike(String idMessage) throws SQLException, UnknownHostException, JSONException {
		try {
			// Connexion a la BD Mongo.
			DBCollection collection = Database.getCollection("message");
			BasicDBObject query = new BasicDBObject("_id", new ObjectId(idMessage));
			BasicDBObject sel = new BasicDBObject("nbLikes",1);
			sel.append("_id",0);
			DBCursor cursor=collection.find(query,sel);
			if(cursor.hasNext()) {
				int cpt=(Integer) cursor.next().get("nbLikes");
				BasicDBObject query1 = new BasicDBObject("_id", new ObjectId(idMessage));
				BasicDBObject query2 = new BasicDBObject("$set", new BasicDBObject("nbLikes",cpt+1));
				collection.update(query1,query2); 
			}
			else {

			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static JSONObject listComments(String idMessage) throws SQLException, UnknownHostException, JSONException {
		JSONObject reponse = new JSONObject();
		try {
			// Connexion a la BD Mongo.
			DBCollection collection = Database.getCollection("message");
			BasicDBObject query = new BasicDBObject("_id", new ObjectId(idMessage));
			BasicDBObject sel = new BasicDBObject("comments",1);
			sel.append("_id",0);
			DBCursor cursor=collection.find(query,sel);
			if(cursor.hasNext()) {
				ArrayList comments= (ArrayList) cursor.next().get("comments");
				for(BasicDBObject comment :(ArrayList<BasicDBObject>) comments ){
					JSONObject object = new JSONObject();
					object.accumulate("id", comment.get("id"));
					object.accumulate("auteur", comment.get("auteur"));
					object.accumulate("comment",comment.get("comment"));
					object.accumulate("date",comment.get("date"));
					reponse.accumulate("com", object);
				}
			}
			else {

			}
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reponse;
	}
}