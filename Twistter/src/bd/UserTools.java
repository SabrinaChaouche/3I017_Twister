package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

import org.json.JSONException;

import com.mysql.jdbc.PreparedStatement;

public class UserTools {
	private final static int timeLimite = 1800;
	private final static String[] roots = {"sabrina", "sara"};

	public static boolean userExists(int idUser) throws SQLException {
		boolean retour = false;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection c = Database.getMySQLConnection();
			Statement stmt = c.createStatement();
			String query = "SELECT * FROM user WHERE id='"+idUser+"';";
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

	public static void addToBDUser(String user, String nom, String prenom,
			String motDePasse) throws SQLException, JSONException {
		try {

			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection c = Database.getMySQLConnection();
			Statement stmt = c.createStatement();
			String query = "INSERT INTO user (login,nom,prenom,pwd) VALUES ('"
					+ user + "', '" + nom + "', '" + prenom + "', '"
					+ motDePasse + "');";
			stmt.executeUpdate(query);
			stmt.close();
			c.close();
			services.User.login(user,motDePasse);
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

	public static int userConnected(String key) throws SQLException, JSONException {
		int retour = 0;
		try {
			// Connexion à la BD SQL.
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connexion = Database.getMySQLConnection();
			
			// Creation et execution de la requete.
			/*String query = "SELECT id_user FROM session WHERE keyCon='"+key+"';";
			Statement statement =  connexion.createStatement();
			statement.executeQuery(query);*/
			String query = "SELECT id_user FROM session WHERE keyCon=? ;";
			PreparedStatement statement = (PreparedStatement) connexion.prepareStatement(query);
	        statement.setString(1, key);
	        statement.executeQuery();
		
			// Récupération du résultat.
			ResultSet rs = statement.getResultSet();
			if(!rs.next()) return 0;
			else{
				String query2 = "SELECT id_user FROM session WHERE keyCon=? AND TIMESTAMPDIFF(SECOND, t, NOW()) < ?;";
				PreparedStatement statement2 = (PreparedStatement) connexion.prepareStatement(query2);
		        statement2.setString(1, key);
		        statement2.setString(2, Integer.toString(timeLimite));
		        statement2.executeQuery();
		        ResultSet rs2 = statement2.getResultSet();
		        if(!rs2.next()) {
		        	services.User.logout(key);
		        	return 0;
		        }

		        else {
		        	retour = rs2.getInt(1);
		        	String query3 = "UPDATE session SET t=NOW() WHERE keyCon=?";
					PreparedStatement statement3 = (PreparedStatement) connexion.prepareStatement(query3);
			        statement3.setString(1,key);
			        statement3.executeUpdate();
			        statement3.close();
		        }
		        statement2.close();
			}			
			 statement.close();
			 connexion.close();	
		} 
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return retour;
	}
	
	public static int get_userId(String login) throws SQLException
	{		
		int user_id=0;
		try {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection c=bd.Database.getMySQLConnection();
		Statement lecture = c.createStatement();
		String query="select * from user where login='"+login+"';";
		ResultSet cursor=lecture.executeQuery(query);
		while (cursor.next())
			user_id=cursor.getInt(1);
		lecture.close();
		c.close();
		}
	    catch (InstantiationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	   } catch (IllegalAccessException e) {
		// TODO Auto-generated catch block
	   	e.printStackTrace();
	  } catch (ClassNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
		return user_id;
	}

	public static boolean checkPasswd(String user, String mdp) throws SQLException {
		try {
			// Connexion � la BD SQL.
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connexion = Database.getMySQLConnection();
			
			// Creation et execution de la requete.
			String query = "SELECT id FROM user WHERE login=? AND pwd=?;";
			PreparedStatement statement = (PreparedStatement) connexion.prepareStatement(query);
	        statement.setString(1, user);
	        statement.setString(2, mdp);
	        statement.executeQuery();
	        
	        // R�cup�ration du r�sultat et construction de la r�ponse
	        ResultSet resultSet = statement.getResultSet();
	        boolean retour = resultSet.next();
	        
	        // Lib�ration des ressources.
	        statement.close();
	        connexion.close();
	        
	        return retour;
		} 
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 
	 * @param user
	 * @return
	 */
	public static boolean isRoot(String user) {
		return false;
	}
	/**
	 * 
	 * @param user
	 * @param root
	 * @return
	 * @throws SQLException
	 * @throws ClassNotFoundException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	public static String insererConnexion(String user, boolean root) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		
		// On g�n�re une cl� al�atoirement puis on v�rifie son existance dans la table "": si elle est d�j�
		// dans la table alors on recommence jusqu'� obtenir une cl� unique.
		String key = randomKey();
		/**
		 * V�rification dans la table session!!!
		 */
		// On ins�re un nouveau n-uplet dans la BD SQL repr�sentant la connexion de l'utilisateur.
		// Etablir la connexion � la BD SQL.
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection connexion = Database.getMySQLConnection();

		// Creation et execution de la requete.
		 String query = "INSERT INTO session (id_user,t, keyCon, is_root) Values (?, NOW(), ?, ?);";
		 PreparedStatement statement = (PreparedStatement) connexion.prepareStatement(query);
		 statement.setInt(1, get_userId(user));
		 statement.setString(2, key);
		 statement.setBoolean(3, root);
		 statement.executeUpdate();
	       
		 // Lib�rer les ressources.
		 statement.close();
		 connexion.close();
	     
		// Retourner la cl�.
		return key;
	}

	/**
	 * Méthode pour générer une clé aléatoirement en utilisant UUID de la bibliothèque JAVA.
	 * @return
	 */
	public static String randomKey() {
		String uuid = UUID.randomUUID().toString().replaceAll("-", "");
		return uuid;
	}

	public static boolean deconnexion(String key) throws SQLException {
		boolean retour=false;
		try {
			// Connexion à la BD SQL.
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection connexion = Database.getMySQLConnection();
			
			// Creation et execution de la requete.
			String query = "DELETE FROM session WHERE keyCon='"+key+"';";
			Statement statement =  connexion.createStatement();
			statement.executeUpdate(query);
			retour=true;
			statement.close();
			connexion.close();	
		} 
		catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retour;

	}

}