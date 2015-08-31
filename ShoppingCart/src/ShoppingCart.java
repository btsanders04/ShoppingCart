

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Product;

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
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String display="";
		ArrayList<model.Product> shoppingCart = (ArrayList<Product>) session.getAttribute("shoppingCart");
		for(model.Product p : shoppingCart){
			display+=displayProduct(p);
		}
		request.setAttribute("display", display);
		getServletContext().getRequestDispatcher("/ShoppingCart.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
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
				+ "\" class=\"img-rounded\" alt=\"#\" width=\"304\" height=\"236\"></div></div>" ;
		return display;
	}
}
