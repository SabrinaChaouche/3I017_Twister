package servlets.message;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 * Servlet implementation class AddMessage
 */
public class AddMessage extends HttpServlet {
       
    
    public AddMessage() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Recuperer les parametres.
			String key = request.getParameter("key");
			String message = request.getParameter("message");
				
				JSONObject ret = new JSONObject();
				// On appelle le service
				try{
					ret = services.Message.addMessage(key, message);
				}
				catch(Exception e){
					e.printStackTrace();
				}
				PrintWriter out = response.getWriter();
				response.setContentType("text/plain");
				out.print(ret.toString());
	}

}
