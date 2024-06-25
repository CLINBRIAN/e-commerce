package cn.shoppingcart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.shoppingcart.connection.DBConnection;
import cn.shoppingcart.model.*;
import cn.shoppingcart.Dao.*;


/**
 * Servlet implementation class CheckOutServlet
 */
@WebServlet("/cart-check-out")
public class CheckOutServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try(PrintWriter out = response.getWriter()) {
			//out.print("check out servlet");
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Date date = new Date();
			
			//retrieve all cart products
			ArrayList<Cart> cart_list = (ArrayList<Cart>) request.getSession().getAttribute("cart-list");
			
			//we need to check for authentication first to see if the user is login
			Users auth = (Users) request.getSession().getAttribute("auth");
			
			//check auth and cartlist
			if(auth!=null && cart_list!= null) {
				for(Cart c: cart_list) {
					Order order = new Order();
					order.setId(c.getId());
					order.setuId(auth.getId());
					order.setQuantity(c.getQuantity());
					order.setDate(formatter.format(date));
					
					OrderDao orderDao = new OrderDao(DBConnection.getConnection());
					boolean result = orderDao.insertOrder(order);
					
					if(!result)break;
				}
				
				cart_list.clear();
				response.sendRedirect("orders.jsp");
				
			}else {
				if(auth == null) {
					response.sendRedirect("login.jsp");
					}
				response.sendRedirect("cart.jsp");
			}

			
			
		}catch(Exception e) {
			e.getMessage();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
