package servlets.message;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class addLike extends HttpServlet{
	public addLike() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recuperer les parametres.
			String idMessage = request.getParameter("idMessage");
				
				JSONObject ret = new JSONObject();
				// On appelle le service
				try{
					ret = services.Message.addLike(idMessage);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				PrintWriter out = response.getWriter();
				response.setContentType("text/plain");
				out.print(ret.toString());
	}

}
