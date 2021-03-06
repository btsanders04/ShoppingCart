import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import customTools.DBUtil;
import oracle.net.aso.e;
import oracle.net.aso.q;
import model.Product;
import model.Userprofile;

/**
 * Servlet implementation class ShoppingCart
 */
@WebServlet("/ShoppingCart")
public class ShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ShoppingCart() {
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
		model.Userprofile user = (Userprofile) session.getAttribute("User");
		String display = displayShoppingCart(user);
		cleanShoppingCart(user.getUserId());
		request.setAttribute("display", display);
		getServletContext().getRequestDispatcher("/ShoppingCart.jsp").forward(
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
		
		removeFromCart(Long.parseLong(request.getParameter("removeCart")),
				user.getUserId(),
				Integer.parseInt(request.getParameter("amount")));
		doGet(request, response);
	}

	public String displayProduct(model.Product product, int amount) {
		String display = "<div class = \"container\"> "
				+ "<div style = \"border: 2px solid black\">"
				+ "<div class = \"row\"><div class = \"col-sm-2\">"
				+ "<p><a href=\"ProductDetails?ProductId="
				+ product.getProductId()
				+ "\" >"
				+ product.getName()
				+ "</a></p></div><div class =\"col-sm-2\"><p>"
				+ product.getPrice().multiply(new BigDecimal(amount))
				+ "</p></div>"
				+ "<form action=\"ShoppingCart\" method = \"POST\">"
				+ "<div class=\"col-sm-2\"><div class=\"form-group\">"
				+ "<input type=\"number\"class=\"form-control\" name=\"amount\" id=\"amount\" value=\""
				+ amount
				+ "\" max=\""
				+ amount
				+ "\" min=\"0\">"
				+ "</div></div><div class=\"col-sm-2\"><button name =\"removeCart\" value =\""
				+ product.getProductId()
				+ "\" type=\"submit\" class=\"btn btn-info\">Remove from Cart</button></div></form>";

		display += "</div></div></div></div>";
		return display;
	}

	public void removeFromCart(long pId, long uId, int amount) {
		String qString = "select s FROM Shoppingcart s where s.productId = :pId and s.userId = :uId";
		TypedQuery<model.Shoppingcart> q = DBUtil.createQuery(qString, model.Shoppingcart.class)
				.setParameter("pId", pId).setParameter("uId", uId);
		model.Shoppingcart s = q.getSingleResult();
		if (s != null) {
			updateShoppingCart(s, amount);
		}
		
		
	}

	private void updateShoppingCart(model.Shoppingcart s, int amount) {
		int totalAmount = s.getAmount() - amount;
	//	if (totalAmount > 0) {
			String qString = "update Shoppingcart s set s.amount = :amount where s.productId = :pId and s.userId = :uId";
			TypedQuery<model.Shoppingcart> q = DBUtil.createQuery(qString, model.Shoppingcart.class)
					.setParameter("pId", s.getProductId())
					.setParameter("uId", s.getUserId())
					.setParameter("amount", totalAmount);
			DBUtil.updateDB(q);
	}

	public void cleanShoppingCart(long id){
			String qString = "delete from Shoppingcart s where s.userId = :uId and s.amount = 0";
			TypedQuery<model.Shoppingcart> tQuery = DBUtil.createQuery(qString,model.Shoppingcart.class)
					.setParameter("uId", id);
			DBUtil.updateDB(tQuery);
	}
		
	
	public String displayShoppingCart(model.Userprofile user){
		String display="";
		String sString = "Select s from Shoppingcart s where s.userId = :uId";
		TypedQuery<model.Shoppingcart> shoppingQuery = DBUtil.createQuery(sString,
				model.Shoppingcart.class).setParameter(
						"uId", user.getUserId());
		List<model.Shoppingcart> shoppingCart = shoppingQuery.getResultList();
		for (model.Shoppingcart s : shoppingCart) {
			if (s.getAmount() > 0) {
				String qString = "SELECT p FROM Product p where p.productId = :id";
				TypedQuery<model.Product> q = DBUtil.createQuery(qString,
						model.Product.class).setParameter("id",
						s.getProductId());
				List<model.Product> products = q.getResultList();
				if (products.size() > 0) {
					model.Product p = products.get(0);
					display += displayProduct(p, s.getAmount());
				}
			}
		}

		if (shoppingCart.size() > 0) {

			display += "<div class=\"container\"><div align=\"right\">"
					+ "<form role = \"form\" action=\"Confirmation\" method=\"GET\"><button type=\"submit\" class=\"btn btn-default\">CheckOut</button></form>"
					+ "</form></div></div>";
		} else {
			display = "<div class=\"container\"><div align=\"center\"><h1> Sorry You have no items in your Shopping Cart </h1></div></div>";
		}
		return display;
	}

}
