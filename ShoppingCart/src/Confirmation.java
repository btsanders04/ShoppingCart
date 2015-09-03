import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
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

import model.CreditInfo;
import model.Userprofile;
import customTools.DBUtil;

/**
 * Servlet implementation class Confirmation
 */
@WebServlet("/Confirmation")
public class Confirmation extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HashMap<Integer, Integer> shoppingCart;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Confirmation() {
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
		model.CreditInfo creditCard = getCreditInfo(user.getUserId());
		String display = confirmPurchase(user);
		request.setAttribute("display", display);
		String credit = "Enter Credit Card Number";
		String bill = "Enter Billing Address";
		String ship = "Enter Shipping Address";
		if (creditCard != null) {
			credit = String.valueOf(creditCard.getCard());
			bill = creditCard.getBillAdd();
			ship = creditCard.getShipAdd();
		}
		request.setAttribute("credit", credit);
		request.setAttribute("bill", bill);
		request.setAttribute("ship", ship);
		getServletContext().getRequestDispatcher("/Confirmation.jsp").forward(
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

		int credit = Integer.parseInt(request.getParameter("credit"));
		int ccv = Integer.parseInt(request.getParameter("ccv"));
		String bill = request.getParameter("bill");
		String diffAdd = request.getParameter("diffAdd");
		String remember = request.getParameter("remember");
		String ship = request.getParameter("ship");
		model.CreditInfo creditCard = new model.CreditInfo();
		creditCard.setUserId(user.getUserId());
		creditCard.setCard(credit);
		creditCard.setSecure(ccv);
		creditCard.setBillAdd(bill);
		creditCard.setShipAdd(ship);
		System.out.println(remember);
		if (remember!=null) {
			storeCreditInfo(creditCard);
		}
		
		getServletContext().getRequestDispatcher("/index.jsp").forward(
				request, response);

	}

	public String confirmPurchase(model.Userprofile user) {
		String display = "";
		double subTotal = 0;
		double tax = 0.06;
		String sString = "Select s from Shoppingcart s where s.userId = :uId";
		TypedQuery<model.Shoppingcart> shoppingQuery = DBUtil.createQuery(
				sString, model.Shoppingcart.class);
		List<model.Shoppingcart> shoppingCart = shoppingQuery.setParameter(
				"uId", user.getUserId()).getResultList();

		for (model.Shoppingcart s : shoppingCart) {
			String qString = "SELECT p FROM Product p where p.productId = :id";
			TypedQuery<model.Product> q = DBUtil.createQuery(qString,
					model.Product.class).setParameter("id", s.getProductId());
			if (q.getMaxResults() > 0) {
				model.Product p = q.getSingleResult();
				display += displayProduct(p, s.getAmount());
				BigDecimal nextTotal = p.getPrice().multiply(
						new BigDecimal(s.getAmount()));
				subTotal += nextTotal.doubleValue();
			}
		}
		DecimalFormat f = new DecimalFormat("#0.00");

		double taxTotal = tax * subTotal;
		double total = subTotal + taxTotal;

		display += "<div class = \"container\"><p>Subtotal : "
				+ f.format(subTotal) + "</p><p>Tax : " + f.format(taxTotal)
				+ "</p><p>Total : " + f.format(total) + "</p>";
		return display;
	}

	public String displayProduct(model.Product product, int amount) {
		String display = "<div class = \"container\"> "
				+ "<div style = \"border: 2px solid black\">"
				+ "<div class = \"row\"><div class = \"col-sm-2\">"
				+ "<p><a href=\"ProductDetails?ProductId="
				+ product.getProductId() + "\" >" + product.getName()
				+ "</a></p></div><div class =\"col-sm-2\"><p>"
				+ product.getPrice().multiply(new BigDecimal(amount))
				+ "</p></div>";
		display += "</div></div></div></div>";
		return display;
	}

	/*
	 * d public String printShipping(String dif) { String ship = ""; if
	 * (dif.equals("yes")) { ship +=
	 * " <div class=\"form-group\"><label class=\"control-label col-sm-2\" for=\"ship\">Shipping:</label>"
	 * +
	 * "<div class=\"col-sm-10\"><input type=\"text\" class=\"form-control\" id=\"ship\" name = \"ship\" placeholder=\"${ship}\">"
	 * + "</div></div>"; } return ship; }
	 */
	public model.CreditInfo getCreditInfo(long uId) {
		String qString = "select c from CreditInfo c where c.userId = :uId";
		TypedQuery<model.CreditInfo> credit = DBUtil.createQuery(qString,
				model.CreditInfo.class).setParameter("uId", uId);
		List<model.CreditInfo> creditCards = credit.getResultList();
		if (creditCards.size() > 0) {
			return creditCards.get(0);
		} else
			return null;
	}

	public void storeCreditInfo(model.CreditInfo c) {
		DBUtil.addToDB(c);
	}

}
