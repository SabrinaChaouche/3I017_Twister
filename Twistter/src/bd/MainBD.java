package bd;

import java.sql.Connection; 
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.Statement;

public class MainBD {

	public static void main(String[] args) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		Connection c= Database.getMySQLConnection();
		String query="SELECT * FROM User";
		Statement st=(Statement) c.createStatement();
		st.executeQuery(query);
		ResultSet rs=st.getResultSet();
		if(rs.next()){
			System.out.println(rs.getString(1));
		}
		else{
			System.out.println("erreur!");
		}
		st.close();
		c.close();
	}

}