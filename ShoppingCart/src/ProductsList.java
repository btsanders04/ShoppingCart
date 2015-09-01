import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
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
 * Servlet implementation class ProductsList
 */
@WebServlet("/ProductsList")
public class ProductsList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ProductsList() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		boolean loggedIn = (boolean)session.getAttribute("loggedIn");
		String display = "";
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT p FROM Product p ORDER BY p.productId ";
		TypedQuery<model.Product> q = em.createQuery(qString,
				model.Product.class);
		List<model.Product> products = q.getResultList();
		for (model.Product p : products) {
			display += displayProduct(p, loggedIn);
		}
		request.setAttribute("productList", display);
		getServletContext().getRequestDispatcher("/ProductList.jsp").forward(
				request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		model.Userprofile user = (Userprofile) session.getAttribute("User");
		addToCart(Long.parseLong(request.getParameter("addCart")),user.getUserId(),Integer.parseInt(request.getParameter("amount")));
		//System.out.println(shoppingCart.size());
		doGet(request, response);
	}

	public static String displayProduct(model.Product product, boolean loggedIn) {
		String display = "<div class = \"container\"> "
				+ "<div style = \"border: 2px solid black\">"
				+ "<div class = \"row\"><div class = \"col-sm-2\">"
				+ "<p><a href=\"ProductDetails?ProductId="+product.getProductId()+"\" >" + product.getName()
				+ "</a></p></div><div class =\"col-sm-2\"><p>"
				+ product.getPrice() + "</p></div>"
		/*
		 * + product.getDescription() +"<br></br>" + "<img src=\"" +
		 * product.getPicture() +
		 * "\" class=\"img-rounded\" alt=\"#\" width=\"304\" height=\"236\">"
		 */;
		if (loggedIn) {
			display += "<form action=\"ProductsList\" method = \"POST\">"
					+ "<div class=\"col-sm-2\"><div class=\"form-group\">"
					+ "<input type=\"number\"class=\"form-control\" name=\"amount\" id=\"amount\"  min=\"0\">"
					+ "</div></div><div class=\"col-sm-2\"><button name =\"addCart\" value =\""
					+ product.getProductId()
					+ "\" type=\"submit\" class=\"btn btn-info\">Add to cart</button></div></form>";

		}
		display += "</div></div></div></div>";
		return display;
	}

	public void addToCart(long pId, long uId, int amount) {
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		String qString = "select s FROM Shoppingcart s where s.productId = :pId and s.userId = :uId";
		TypedQuery<model.Shoppingcart> q = em.createQuery(qString,
				model.Shoppingcart.class).setParameter("pId", pId).setParameter("uId",uId);
		trans.begin();
		model.Shoppingcart s;
		List<model.Shoppingcart> shop = q.getResultList();
		if(shop.size()>0){
		s = shop.get(0);
		updateShoppingCart(s,amount);
		}
		else{
			s = new model.Shoppingcart();
			s.setAmount(amount);
			s.setProductId(pId);
			s.setUserId(uId);
			try{
			em.merge(s);
			trans.commit();
			}
			catch(Exception e){
			trans.rollback();
			}
			finally{
				em.close();
			}
			
		}
	}
	
	public void updateShoppingCart(model.Shoppingcart s, int amount){
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		String qString = "update Shoppingcart s set s.amount = :amount where s.productId = :pId and s.userId = :uId";
		TypedQuery<model.Shoppingcart> q = em.createQuery(qString,
				model.Shoppingcart.class).setParameter("pId", s.getProductId()).setParameter("uId",s.getUserId()).setParameter("amount",amount+s.getAmount());
		trans.begin();
		try{
			q.executeUpdate();
			trans.commit();
		}
		catch(Exception e){
			trans.rollback();
		}
		finally{
			em.close();
		}
	}

}
