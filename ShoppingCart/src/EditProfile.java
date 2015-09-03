

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import customTools.DBUtil;
import javax.persistence.TypedQuery;
/**
 * Servlet implementation class EditProfile
 */
@WebServlet("/EditProfile")
public class EditProfile extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private model.Userprofile user;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditProfile() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		user = (model.Userprofile) session.getAttribute("User");
		String header = "Edit Profile";
		request.setAttribute("headName",header);
		request.setAttribute("action","EditProfile");
		request.setAttribute("name",user.getUserName());
		request.setAttribute("email",user.getUserEmail());
		request.setAttribute("password",user.getUserPass());
		request.setAttribute("zip",user.getUserZipcode());
		String motto = "<div class=\"form-group\"><label class=\"control-label col-sm-2\" for=\"motto\">Motto:</label>"+
		"<div class=\"col-sm-10\"><input type=\"text\" class=\"form-control\" id=\"motto\" name=\"motto\""+
		"placeholder=\""+user.getMotto()+"\"></div></div>";
		String image = "<div class=\"form-group\"><label class=\"control-label col-sm-2\" for=\"image\">Background Image:</label>"+
				"<div class=\"col-sm-10\"><input type=\"text\" class=\"form-control\" id=\"image\" name=\"image\""+
				"placeholder=\""+user.getImageLink()+"\"></div></div>";
		request.setAttribute("image", image);
		request.setAttribute("motto",motto);
		getServletContext().getRequestDispatcher("/SignUp.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			if(!request.getParameter("name").equals(""))
			user.setUserName(request.getParameter("name"));
			if(!request.getParameter("email").equals(""))
			user.setUserEmail(request.getParameter("email"));
			if(!request.getParameter("pwd").equals(""))
			user.setUserPass(request.getParameter("pwd"));
			if(!request.getParameter("zip").equals(""))
			user.setUserZipcode(request.getParameter("zip"));
			if(!request.getParameter("motto").equals(""))
				user.setMotto(request.getParameter("motto"));
			if(!request.getParameter("image").equals(""))
				user.setImageLink(request.getParameter("image"));
			String q = "UPDATE Userprofile u SET u.userName =:name, u.userEmail=:email, u.userPass=:pass," 
					+ "u.userZipcode = :zip, u.motto = :mot, u.imageLink = :link WHERE u.userId = :id";
			TypedQuery<model.Userprofile> query = DBUtil.createQuery(q, model.Userprofile.class);
			query.setParameter("id", user.getUserId()).setParameter("name", user.getUserName())
			.setParameter("email",user.getUserEmail()).setParameter("pass",user.getUserPass())
			.setParameter("link", user.getImageLink())
			.setParameter("zip",user.getUserZipcode()).setParameter("mot",user.getMotto());
			DBUtil.updateDB(query);
			
			request.setAttribute("alertMessage", "<div class=\"container\"><div class=\"alert alert-success\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>"+
				    "<strong>Success!</strong> You have edited your profile.</div></div>");
			HttpSession session = request.getSession();
			session.setAttribute("User", user);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		request.setAttribute("alertMessage", "<div class=\"container\"><div class=\"alert alert-success\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>"+
					    "<strong>Success!</strong> You have edited your Profile.</div></div>");
		doGet(request,response);
		//getServletContext().getRequestDispatcher("/SignUp.jsp").forward(request,
		//		response);
	}

	
}
