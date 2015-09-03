

import java.io.IOException;
import java.math.BigDecimal;
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
 * Servlet implementation class AllPurchases
 */
@WebServlet("/AllPurchases")
public class AllPurchases extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AllPurchases() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String display = displayShoppingCart();
		
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
	}

	public String displayProduct(model.Product product, int amount, long id) {
		
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
				+ "<div class=\"col-sm-2\">"
				+ "<p>"+ amount +"</p></div>"
				+ "<div class=\"col-sm-2\">"
				+"<p>" + findUser(id)
				+ "</div>";

		display += "</div></div></div></div>";
		return display;
	}

	public String findUser(long id){
		String qString = "SELECT u FROM Userprofile u where u.userId = :id";
		TypedQuery<model.Userprofile> q = DBUtil.createQuery(qString,
				model.Userprofile.class).setParameter("id", id);
		return q.getSingleResult().getUserName();

	}
	
	public String displayShoppingCart(){
		String display="";
		String sString = "Select s from Shoppingcart s";
		TypedQuery<model.Shoppingcart> shoppingQuery = DBUtil.createQuery(sString,
				model.Shoppingcart.class);
		List<model.Shoppingcart> shoppingCart = shoppingQuery.getResultList();

		for (model.Shoppingcart s : shoppingCart) {
			String qString = "SELECT p FROM Product p where p.productId = :id";
			TypedQuery<model.Product> q = DBUtil.createQuery(qString,
					model.Product.class).setParameter("id", s.getProductId());
			List<model.Product> products = q.getResultList();
			if (products.size()>0) {
				model.Product p = q.getSingleResult();
				display += displayProduct(p, s.getAmount(), s.getUserId());
			}
		}
		return display;
	}
	


}
