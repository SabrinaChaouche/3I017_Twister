package servlets.user;

import java.io.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateUser extends HttpServlet{
	public CreateUser(){
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
		// Récupérer les paramètres
		String login = request.getParameter("login");
		String pwd = request.getParameter("pwd");
		String name = request.getParameter("name");
		String fname = request.getParameter("fname");
		
		// On appelle le service
		JSONObject ret = new JSONObject();
		try{
			ret = services.User.createUser(login, pwd, name, fname);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		PrintWriter out = response.getWriter();
		response.setContentType("text/plain");
		out.print(ret.toString());
	}
}