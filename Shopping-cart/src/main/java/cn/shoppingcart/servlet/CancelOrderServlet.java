package cn.shoppingcart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import cn.shoppingcart.Dao.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.shoppingcart.connection.DBConnection;

/**
 * Servlet implementation class CancelOrderServlet
 */
@WebServlet("/cancel_order")
public class CancelOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try (PrintWriter out = response.getWriter()) {
			String id = request.getParameter("id");

			if (id != null) {

				OrderDao orderDao = new OrderDao(DBConnection.getConnection());
				orderDao.cancelOrder(Integer.parseInt(id));
			}
			response.sendRedirect("orders.jsp");
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
