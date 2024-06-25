package cn.shoppingcart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.shoppingcart.model.*;

/**
 * Servlet implementation class QuantityIncrDecrServlet
 */
@WebServlet("/Quantity-Incr-Decr")
public class QuantityIncrDecrServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		// out.print("inr decr servlet");
		response.setContentType("text/html: charset=UTF-8");
		try (PrintWriter out = response.getWriter();) {
			String action = request.getParameter("action");
			int id = Integer.parseInt(request.getParameter("id"));

			ArrayList<Cart> cart_List = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");

			if (action != null && id >= 1) {
				if (action.equals("inc")) {
					for (Cart c : cart_List) {
						if (c.getId() == id) {
							int quantity = c.getQuantity();
							quantity++;
							c.setQuantity(quantity);
							response.sendRedirect("cart.jsp");
						}

					}
				}
				if (action.equals("dec")) {
					{
						for (Cart c : cart_List) {
							if (c.getId() == id && c.getQuantity() >1) {
								int quantity = c.getQuantity();
								quantity--;
								c.setQuantity(quantity);
								break;
							}

						}
						response.sendRedirect("cart.jsp");
					}

				}

			}else {
				response.sendRedirect("cart.jsp");
			}

		}

	}

}
