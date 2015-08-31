
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Userprofile;
import customTools.DBUtil;

/**
 * Servlet implementation class SignUp
 */
@WebServlet("/SignUp")
public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SignUp() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String header = "Create Profile";
		request.setAttribute("headName",header);
		request.setAttribute("action","SignUp");
		request.setAttribute("name","Enter name");
		request.setAttribute("email","Enter email");
		request.setAttribute("password","Enter password");
		request.setAttribute("zip","Enter Zip Code");
		
		getServletContext().getRequestDispatcher("/SignUp.jsp").forward(request,
				response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		String name = request.getParameter("name");
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		String zip = request.getParameter("zip");
		
		System.out.println(name + " " + email + " " + pwd + " " + zip);
		Userprofile user = new Userprofile(); 
		user.setUserEmail(email);
		user.setUserName(name);
		user.setUserPass(pwd);
		user.setUserZipcode(zip);
		user.setJoinDate(new Date());
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		trans.begin();
		try{
		em.persist(user);
		trans.commit();
		}catch (Exception e){
			System.out.println(e);
			trans.rollback();
		} finally{
			em.close();
		}	
			request.setAttribute("alertMessage", "<div class=\"container\"><div class=\"alert alert-success\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>"+
			    "<strong>Success!</strong> You have Signed Up.</div></div>");		
		doGet(request,response);
		//getServletContext().getRequestDispatcher("/SignUp.jsp").forward(request,
		//		response);
	}
}
