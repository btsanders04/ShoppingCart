

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
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
 * Servlet implementation class Confirmation
 */
@WebServlet("/Confirmation")
public class Confirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private HashMap<Integer,Integer> shoppingCart;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Confirmation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		model.Userprofile user = (Userprofile) session.getAttribute("User");
		String display = confirmPurchase(user);
		request.setAttribute("display", display);
		getServletContext().getRequestDispatcher("/ShoppingCart.jsp").forward(request, response);

	}
	
	public String confirmPurchase(model.Userprofile user){
		String display = "";
		double total=0;
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String sString = "Select s from Shoppingcart s where s.userId = :uId";
		TypedQuery<model.Shoppingcart> shoppingQuery = em.createQuery(sString,
				model.Shoppingcart.class);
		List<model.Shoppingcart> shoppingCart = shoppingQuery.setParameter(
				"uId", user.getUserId()).getResultList();

		for (model.Shoppingcart s : shoppingCart) {
			String qString = "SELECT p FROM Product p where p.productId = :id";
			TypedQuery<model.Product> q = em.createQuery(qString,
					model.Product.class).setParameter("id", s.getProductId());
			if (q.getMaxResults() > 0) {
				model.Product p = q.getSingleResult();
				display += displayProduct(p, s.getAmount());
				BigDecimal nextTotal = p.getPrice().multiply(new BigDecimal(s.getAmount()));
				total+=nextTotal.doubleValue();
			}
		}
		display+= "<div class = \"container\"><h1>Your total is : " + total +"</h1>";
		return display;
	}
	
	public String displayProduct(model.Product product, int amount) {
		String display = "<div class = \"container\"> "
				+ "<div style = \"border: 2px solid black\">"
				+ "<div class = \"row\"><div class = \"col-sm-2\">"
				+ "<p><a href=\"ProductDetails?ProductId="+product.getProductId()+"\" >"
				+ product.getName()
				+ "</a></p></div><div class =\"col-sm-2\"><p>"
				+ product.getPrice().multiply(new BigDecimal(amount))
				+ "</p></div>";
		display += "</div></div></div></div>";
		return display;
	}

}
