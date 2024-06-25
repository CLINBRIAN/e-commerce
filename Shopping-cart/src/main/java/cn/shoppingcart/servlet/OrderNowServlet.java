package cn.shoppingcart.servlet;

import java.text.*;
import java.util.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import cn.shoppingcart.Dao.*;

import cn.shoppingcart.connection.DBConnection;
import cn.shoppingcart.model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class BuyNowServlet
 */
@WebServlet("/Order-now")
public class OrderNowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try (PrintWriter out = response.getWriter()) {
			// out.print("this is buy now servlet");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

			Date date = new Date();

			Users auth = (Users) request.getSession().getAttribute("auth");
			if (auth != null) {
				String productId = request.getParameter("id");
				int productQuantity = Integer.parseInt(request.getParameter("quantity"));

				if (productQuantity <= 0) {
					productQuantity = 1;
				}
				// to get order object
				Order ordermodel = new Order();
				ordermodel.setOrderId(Integer.parseInt(productId));
				ordermodel.setuId(auth.getId());
				ordermodel.setQuantity(productQuantity);
				ordermodel.setDate(formatter.format(date));
				
				OrderDao orderDao;
				
					orderDao = new OrderDao(DBConnection.getConnection());
					boolean result = orderDao.insertOrder(ordermodel);
					
					if(result) {
						ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
						if(cart_list != null) {
							for(Cart c: cart_list) {
								if(c.getId() == Integer.parseInt(productId)) {
									cart_list.remove(cart_list.indexOf(c));
									break;			
								}
							}
						}
						response.sendRedirect("orders.jsp");
					}else {
                          out.print("order failed");
					}
				

			} else {
				response.sendRedirect("login.jsp");
			}	

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
