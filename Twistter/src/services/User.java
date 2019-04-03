package services;

import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

public class User {
	
	/**
	 * 
	 * @param user
	 * @param motDePasse
	 * @param nom
	 * @param prenom
	 * @return
	 * @throws SQLException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws ClassNotFoundException
	 * @throws JSONException
	 */
	public static JSONObject createUser(String user, String motDePasse, String nom, String prenom) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException, JSONException{
		JSONObject rep = new JSONObject();
		// Si l'un des parametres de connexion est egal a null alors on revoie une erreur.
		if(user == null || motDePasse == null  || nom == null || prenom == null) {
			rep = servicesTools.ServiceRefused.serviceRefused("Wrong arguments! ", -1);
			return rep;
		}
		// Recuperer l'id de l'utilisateur s'il existe dans la bd.
		int idUser = bd.UserTools.get_userId(user);
		if(bd.UserTools.userExists(idUser)){
				rep = servicesTools.ServiceRefused.serviceRefused("User already exists!", -1);
		}
		else{
			try {
				bd.UserTools.addToBDUser(user, motDePasse, nom, prenom); // Renvoyer la cle de cooexion egalement!!!
				rep.put("status", "OK");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return rep;
		
	}
	
	/**
	 * Service de connexion.
	 * @param user: le login de l'utilisateur
	 * @param mdp: mot de passe de l'utilisateur.
	 * @return: une cle de connexion pour l'utilisateur.
	 * @throws JSONException
	 * @throws SQLException 
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static JSONObject login (String user, String mdp) throws JSONException, SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
		JSONObject rep = new JSONObject();
		// Si l'un des parametres de connexion (user ou password) est egal a null alors on revoie une erreur.
		if(user == null || mdp == null) {
			rep = servicesTools.ServiceRefused.serviceRefused("Wrong arguments! ", -1);
			return rep;
		}
		int idUser = bd.UserTools.get_userId(user); // Recuperer l'id de l'utilisateur a partir de son login
		// Si l'utilsateur n'existe pas.
		if(!bd.UserTools.userExists(idUser)){
			rep = servicesTools.ServiceRefused.serviceRefused("User does not exist! ", -1);
			return rep;
		}
		// Si le mot de passe saisi par l'utilisateur est errone.
		if(!bd.UserTools.checkPasswd(user,mdp)){
			rep = servicesTools.ServiceRefused.serviceRefused("Wrong password! ", -1);
			return rep;
		}
		// Si l'utilisateur est administrateur.
		boolean root = bd.UserTools.isRoot(user);
		// Creation de la cle de connexion.
		String key = bd.UserTools.insererConnexion(user, root);
		rep.accumulate("status", "OK");
		rep.accumulate("key", key);
		rep.accumulate("id", user);
		return rep;
	}
	
	/**
	 * 
	 * @param key: Clé de connexion envoyée par l'utilisateur pour se déconnecter de l'application
	 * @return
	 * @throws JSONException
	 * @throws SQLException
	 */
	public static JSONObject logout(String key) throws JSONException, SQLException{
		JSONObject rep = new JSONObject();
		if(key == null) {
			rep = servicesTools.ServiceRefused.serviceRefused("Wrong argument! ", -1);
			return rep;
		}
		if(!bd.UserTools.deconnexion(key)) rep = servicesTools.ServiceRefused.serviceRefused("Logout impossible!", 1000); // A revoir!!! cle inexistante or user not connected
		else{
			rep.put("status", "OK");
		}
	    return rep;
	}

}