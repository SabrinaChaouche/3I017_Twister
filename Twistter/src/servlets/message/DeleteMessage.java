package servlets.message;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class DeleteMessage extends HttpServlet{

	public DeleteMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Récupérer les paramétres
			String key = request.getParameter("key");
			String idMessage = request.getParameter("idMessage");
				
				JSONObject ret = new JSONObject();
				// On appelle le service
				try{
					ret = services.Message.deleteMessage(key, idMessage);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				PrintWriter out = response.getWriter();
				response.setContentType("text/plain");
				out.print(ret.toString());
	}
}
