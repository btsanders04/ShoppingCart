
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import customTools.DBUtil;
import oracle.net.aso.e;
import model.Product;

/**
 * Servlet implementation class ShoppingCart
 */
@WebServlet("/ShoppingCart")
public class ShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<Integer,Integer> shoppingCart;
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
		String display = "";
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		
		shoppingCart = (HashMap<Integer,Integer>) session
				.getAttribute("shoppingCart");
		Iterator it = shoppingCart.entrySet().iterator();
		boolean checkout = it.hasNext();
		while(it.hasNext()){
			Map.Entry<Integer, Integer> pair = (Map.Entry<Integer, Integer>)it.next();
			String qString = "SELECT p FROM Product p where p.productId = :id";
			TypedQuery<model.Product> q = em.createQuery(qString,
					model.Product.class);
			model.Product p = q.setParameter("id", pair.getKey()).getSingleResult();
			if(pair.getValue()>0){
			display += displayProduct(p,pair.getValue());
			}	
		}
		if(checkout){
			display+="<div class=\"container\"><div align=\"right\">"
			  +"<button type=\"submit\" class=\"btn btn-default\"><a href=\"Confirmation\">CheckOut</a></button>"
			  +"</form></div></div>";
		}
		else{
			display="<div class=\"container\"><div align=\"center\"><h1> Sorry You have no items in your Shopping Cart </h1></div></div>";
		}
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
			removeFromCart(Integer.parseInt(request.getParameter("removeCart")),Integer.parseInt(request.getParameter("amount")));
			doGet(request,response);
	}

	public String displayProduct(model.Product product, int amount) {
		String display = "<div class = \"container\"> "
				+ "<div style = \"border: 2px solid black\">"
				+ "<div class = \"row\"><div class = \"col-sm-2\">"
				+ "<p><a href=\"ProductDetails?ProductId="+product.getProductId()+"\" >"
				+ product.getName()
				+ "</a></p></div><div class =\"col-sm-2\"><p>"
				+ product.getPrice().multiply(new BigDecimal(amount))
				+ "</p></div>"
				+ "<form action=\"ShoppingCart\" method = \"POST\">"
				+ "<div class=\"col-sm-2\"><div class=\"form-group\">"
				+ "<input type=\"number\"class=\"form-control\" name=\"amount\" id=\"amount\" value=\""+amount+"\" max=\""+amount+"\" min=\"0\">"
				+ "</div></div><div class=\"col-sm-2\"><button name =\"removeCart\" value =\""
				+ product.getProductId()
				+ "\" type=\"submit\" class=\"btn btn-info\">Remove from Cart</button></div></form>";

		display += "</div></div></div></div>";
		return display;
	}
	

	public void removeFromCart(int id, int amount) {
		if(shoppingCart.containsKey(id)){
			amount=shoppingCart.get(id)-amount;
			shoppingCart.remove(id);
		}
		shoppingCart.put(id,amount);
	}

}
