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

/**
 * Servlet implementation class RemoveFromCartServlet
 */
@WebServlet("/remove-from-cart")
public class RemoveFromCartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; Charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
			String id = request.getParameter("id");
			// out.print("Product id: "+id);

			if (id != null) {
				ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
				if(cart_list != null) {
					for(Cart c: cart_list) {
						if(c.getId() == Integer.parseInt(id)) {
							cart_list.remove(cart_list.indexOf(c));
							break;			
						}
					}
					response.sendRedirect("cart.jsp");
				}
			
			} else {
				response.sendRedirect("cart.jsp");
			}

		}
	}

}
