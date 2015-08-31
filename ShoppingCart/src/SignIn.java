

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Userprofile;
import customTools.DBUtil;

/**
 * Servlet implementation class SignIn
 */
@WebServlet("/SignIn")
public class SignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SignIn() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		getServletContext().getRequestDispatcher("/SignIn.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	//	DBQuery.openConnection();
		
		String email = request.getParameter("email");
		String pwd = request.getParameter("pwd");
		//String sql = "select * from userprofile where user_email = '"+email+
		//		"' and user_pass = '"+pwd+"'";
		//System.out.println(sql);
		HttpSession session = request.getSession();
		String log="";
		
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString= "SELECT u FROM Userprofile u WHERE u.userEmail = :userEmail AND u.userPass = :userPass";
		TypedQuery<Userprofile> q = em.createQuery(qString, Userprofile.class);
		q.setParameter("userEmail", email).setParameter("userPass", pwd);
		Userprofile user = null;
		try{
		user = q.getSingleResult();
		}
		catch(NoResultException e){
			System.out.println(e);
		}finally{
			em.close();
		}
		if(user!=null){
			session.setAttribute("loggedIn", true);
			session.setAttribute("User",user);
			log="<div class=\"container\"><div class=\"alert alert-success\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>"+
				    "<strong>Success!</strong> You have logged in.</div></div>";

		}
		else{
			log="<div class=\"container\"><div class=\"alert alert-danger\"><a href=\"#\" class=\"close\" data-dismiss=\"alert\" aria-label=\"close\">&times;</a>"+
				    "<strong>Error!</strong> Your username/password can not be found.</div></div>";
		}
		
    	request.setAttribute("logIn", log);
		getServletContext().getRequestDispatcher("/index.jsp?logOut=false").forward(request,
				response);
	}
	
	

	

}
