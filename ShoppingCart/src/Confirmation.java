

import java.io.IOException;
import java.math.BigDecimal;
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
		HttpSession session = request.getSession();
		String display = "";
		double total=0;
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
			BigDecimal nextTotal = p.getPrice().multiply(new BigDecimal(pair.getValue()));
			total+=nextTotal.doubleValue();
			}	
		}
		display+= "<div class = \"container\"><h1>Your total is : " + total +"</h1>";
		request.setAttribute("display", display);
		getServletContext().getRequestDispatcher("/ShoppingCart.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
