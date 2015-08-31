
import java.io.IOException;
import java.util.ArrayList;
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
 * Servlet implementation class ProductsList
 */
@WebServlet("/ProductsList")
public class ProductsList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ArrayList<model.Product> shoppingCart = new ArrayList<model.Product>();
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
		session.setAttribute("shoppingCart", shoppingCart);
		String display = "";
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT p FROM Product p ORDER BY p.productId";
		TypedQuery<model.Product> q = em.createQuery(qString,
				model.Product.class);
		List<model.Product> products = q.getResultList();
		for (model.Product p : products) {
			display += displayProduct(p);
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
		addToCart(Integer.parseInt(request.getParameter("addCart")));
		System.out.println(shoppingCart.size());
		doGet(request,response);
		}

	public String displayProduct(model.Product product) {
		String display = "<div class = \"container\">"
				+ "<div style = \"border: 2px solid black\"><h1> "
				+ product.getName()
				+ "</h1><h2>"
				+ product.getPrice()
				+ "</h2><p>"
				+ product.getDescription()
				+"<br></br>"
				+ "<img src=\""
				+ product.getPicture()
				+ "\" class=\"img-rounded\" alt=\"#\" width=\"304\" height=\"236\">" 
				+"<form action=\"ProductsList\" method = \"POST\">"
				+"<button name =\"addCart\" value =\""+product.getProductId()+"\" type=\"submit\" class=\"btn btn-info\">Add to cart</button></form></div></div>";
		return display;
	}
	
	public void addToCart(int id){
		EntityManager em = DBUtil.getEmFactory().createEntityManager();
		String qString = "SELECT p FROM Product p where p.productId = :id";
		TypedQuery<model.Product> q = em.createQuery(qString,
				model.Product.class).setParameter("id", id);
		shoppingCart.add(q.getSingleResult());
	}
	
	
}
