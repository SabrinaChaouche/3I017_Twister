package services;

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.bson.types.ObjectId;
import org.json.JSONException;
import org.json.JSONObject;

public class Message {

	/**
	 * 
	 * @param key
	 *            : la cle de connexion envoye par l'utilisateur
	 * @param message
	 *            : le message de l'utilisateur à insérer dans la base de
	 *            données.
	 * @return
	 * @throws SQLException
	 * @throws UnknownHostException
	 * @throws JSONException
	 */
	public static JSONObject addMessage(String key, String message)
			throws SQLException, UnknownHostException, JSONException {

		JSONObject object = new JSONObject();

		// Si la clé de connexion ou le message est nul.
		if (key.equals(null) || message.equals(null)) {
			object = servicesTools.ServiceRefused.serviceRefused(
					"Mauvais arguments!", -1);
		}

		// Si l'utilisateur n'est pas connecté
		if (bd.UserTools.userConnected(key) == -1) {
			object = servicesTools.ServiceRefused.serviceRefused(
					"Vous n'êtes pas connecté!", -1);
		}
		try {
			ObjectId id_message = bd.MessageTools.addMessage(key, message);
			if (id_message == null) {
				object = servicesTools.ServiceRefused.serviceRefused(
						"Le message n'a pas pu être ajouté!", -1);
			} else {
				try {
					object.put("status", "OK");
					object.put("id", id_message);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return object;
	}

	/**
	 * 
	 * @param key
	 * @param idMessage
	 * @return
	 * @throws JSONException
	 * @throws SQLException
	 */
	public static JSONObject deleteMessage(String key, String idMessage)
			throws JSONException, SQLException {

		JSONObject object = new JSONObject();

		// Si la clé de connexion ou le message est nul.
		if (key.equals(null) || idMessage.equals(null)) {
			object = servicesTools.ServiceRefused.serviceRefused(
					"Veuillez réessayer!", -1);
		}

		// Si l'utilisateur n'est pas connecté
		if (bd.UserTools.userConnected(key) == -1) {
			object = servicesTools.ServiceRefused.serviceRefused(
					"Vous n'êtes pas connecté!", -1);
		}

		if (!bd.MessageTools.deleteMessage(key, idMessage)) {
			object = servicesTools.ServiceRefused.serviceRefused(
					"Impossible de supprimer le message!", -1);
		} else {
			try {
				bd.MessageTools.deleteMessage(key, idMessage);
				object.put("status", "OK");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return object;
	}

	/**
	 * 
	 * @param key
	 * @param listIdUser
	 * @return
	 */
	public static JSONObject listMessage(String key, String[] listIdUser) {
		JSONObject object = new JSONObject();
		if (key == null) {
			object = servicesTools.ServiceRefused.serviceRefused(
					"Wrong arguments!", -1);
		} else {
			try {
				JSONObject object1 = bd.MessageTools.listMessage(key,
						listIdUser);

				object.put("message", object1.get("message"));
				// System.out.println(object1.get("message"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return object;
	}

	public static JSONObject addLike(String idMessage)
			throws UnknownHostException, SQLException {
		// boolean b= bd.MessageTools.addLike(idMessage);
		JSONObject rep = new JSONObject();
		JSONObject object = new JSONObject();
		if (idMessage == null) {
			rep = servicesTools.ServiceRefused.serviceRefused(
					"Wrong arguments! ", -1);
			return rep;
		} else {
			try {
				bd.MessageTools.addLike(idMessage);
				object.put("status", "OK");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return object;
	}

	public static JSONObject listComments(String key, String idMessage) throws UnknownHostException, SQLException, JSONException {
		JSONObject object = new JSONObject();
		// Si la clé de connexion ou le message est nul.
		if (key.equals(null) || idMessage.equals(null)) {
			object = servicesTools.ServiceRefused.serviceRefused("Veuillez réessayer!", -1);
		}
		// Si l'utilisateur n'est pas connecté
		if (bd.UserTools.userConnected(key) == -1) {
			object = servicesTools.ServiceRefused.serviceRefused("Vous n'êtes pas connecté!", -1);
		} else {
			try {
				JSONObject object1 = bd.MessageTools.listComments(idMessage);

				object.put("comments", object1);
				// System.out.println(object1.get("message"));
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return object;
	}

}
