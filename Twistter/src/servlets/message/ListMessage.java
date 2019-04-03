package servlets.message;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class ListMessage extends HttpServlet{

	public ListMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recuperer les parametres
		Map<String, String[]> pars = request.getParameterMap();
		String key = pars.get("key")[0];
		String[] list_idUser = pars.get("listIds");
		JSONObject ret = new JSONObject();
		// On appelle le service
		try {
			ret = services.Message.listMessage(key, list_idUser);
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		out.print(ret.toString());
	}
}
