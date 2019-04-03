package servlets.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class ListComments extends HttpServlet{

	public ListComments() {
        super();
        // TODO Auto-generated constructor stub
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recuperer les parametres

		String idMessage = request.getParameter("idMessage");
		String key = request.getParameter("key");
		JSONObject ret = new JSONObject();
		// On appelle le service
		try {
			ret = services.Message.listComments(key,idMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		out.print(ret.toString());
	}

}
