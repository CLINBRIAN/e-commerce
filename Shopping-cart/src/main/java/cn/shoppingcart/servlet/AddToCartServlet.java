package cn.shoppingcart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import cn.shoppingcart.model.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddToCartServlet
 */
@WebServlet("/add-to-cart")
public class AddToCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; Charset=UTF-8");

		try (PrintWriter out = response.getWriter()) {

			ArrayList<Cart> cartList = new ArrayList<>();

			int id = Integer.parseInt(request.getParameter("id"));
			Cart cart = new Cart();
			cart.setId(id);
			cart.setQuantity(1);

			HttpSession session = request.getSession();
			ArrayList<Cart> cart_list = (ArrayList<Cart>) session.getAttribute("cart-list");

			if (cart_list == null) {
				cartList.add(cart);
				session.setAttribute("cart-list", cartList);
				//out.println("session created and added the list");
				response.sendRedirect("index.jsp");
			} else {
				cartList = cart_list;
				boolean exist = false;

				for (Cart c : cart_list) {
					if (c.getId() == id) {
						exist = true;
						//out.print("the product already exist");
						out.print("<h3 style='color: crimson; text-align:center'>Item malready exsist in Cart.<a href='cart.jsp'>Go to Cart page</a></h3>");
					}
					
				}
				if(!exist) {
					cartList.add(cart);
					//out.print("product added");
					response.sendRedirect("index.jsp");
				}
			}
//			for(Cart c: cart_list) {
//				out.print(c.getId());
//			}
		}

	}

}
