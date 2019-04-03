package test;

import java.net.UnknownHostException;
import java.sql.SQLException;

import org.json.JSONException;
import org.json.JSONObject;

public class TestMessage {

	public static void main(String[] args) throws JSONException, UnknownHostException, SQLException {
		JSONObject ret = new JSONObject();
		// Ajout message
		System.out.println("Test de l'ajout d'un message");
		//ret = services.Message.addMessage("41c570101409400ea587931570af9d0a", "contenu1");
		//ret = services.Message.addMessage("d24ce135529144368b37f9ca74e39548", "contenu2");
		//ret = services.Message.addMessage("d24ce135529144368b37f9ca74e39548", "contenu3");
		//ret = services.Message.deleteMessage("d24ce135529144368b37f9ca74e39548", "1");
		/*ret = services.Message.addMessage("", "contenu2");
		ret = services.Message.addMessage("", "contenu3");
		ret = services.Message.addMessage("", "contenu4");
		System.out.println(ret.get("status"));
		// Suppression message
		System.out.println("Test de la suppression d'un message");
		ret = services.Message.deleteMessage("", "");
		System.out.println(ret.get("status"));*/
		// Afficher les messages de tous les utilisatuers
		//System.out.println("Afficher les messages de tous les utilisatuers");
		//ret = services.Message.listMessage("f9e091ceb138499fae28d83381d51ca9", null);
		//System.out.println(ret.get("message"));
		/// Afficher les messages de l'utilisateur
		//System.out.println("Afficher les messages de l'utilisateur");
		//String[] id = new String[1];  id [0]="26";
		//services.Message.addLike("5ad85e89e4b043c1d9b3c656");
		//bd.MessageTools.addComment("41fe029dc33c4de8862d56a8f264eebd","5ad9c16de4b0193c9dbcf2ae", "OK");
		ret = services.Message.listComments("5db555cf87e34c669d9ee237b3115a2d", "5ad9c16de4b0193c9dbcf2ae");
		//ret = services.Message.listMessage("73706084fd7e4d8094e699c441fc37b0", id);
		//System.out.println(ret.get("message"));
		// Afficher les messages de certaines utilisateurs
		//System.out.println("Afficher les messages de certaines utilisateurs");
		//String[] id1 = new String[1]; id1[0] = "27";
		//ret = services.Message.listMessage("fb64752ddd5e4c1593b22e2038c5a617", id1);
		System.out.println(ret);

	}

}
