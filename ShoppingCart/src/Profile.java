
import java.io.IOException;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import customTools.DBUtil;

/**
 * Servlet implementation class Profile
 */
@WebServlet("/Profile")
public class Profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String imgLink;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Profile() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Integer id = Integer.parseInt(request.getParameter("user")); 
		
		String profile=printProfile(id);
		// Set response content type
		response.setContentType("text/html");

		// Actual logic goes here.
		request.setAttribute("imgLink", imgLink);
		request.setAttribute("profile", profile);
		getServletContext().getRequestDispatcher("/Profile.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	protected String printProfile(int id){
		String profile="";
		String qString = "SELECT u FROM Userprofile u where u.userId = :id";
		TypedQuery<model.Userprofile> q = DBUtil.createQuery(qString, model.Userprofile.class);
		q.setParameter("id", id);
		
		try{
			model.Userprofile user = q.getSingleResult();
			imgLink = user.getImageLink();
			profile += "<div class =\"jumbotron\" style =\"background-color: transparent\"><font color=\"white\"><h1>"+user.getMotto()+"</h1></font></div><div class=\"container\"><div class=\"item  col-xs-4 col-lg-4\">  <div class=\"panel panel-primary\"><div class=\"panel-heading\" style=\"background-color:black\">"+user.getUserId() 
					+"</div><div class=\"panel-body\"> <p class=\"group inner list-group-item-text\">"+user.getUserName() 
						+"</p> <p class=\"group inner list-group-item-text\"> "+user.getUserEmail() 
						+"</p> <p class=\"group inner list-group-item-text\"> "+user.getUserZipcode()
						+"</p> <p class=\"group inner list-group-item-text\"> "+new SimpleDateFormat("MM/dd/yyyy")
						.format(user.getJoinDate())
						+"</p></div></div></div></div>";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return profile;
	}
	
	
}
