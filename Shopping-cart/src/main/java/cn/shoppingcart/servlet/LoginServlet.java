package cn.shoppingcart.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.shoppingcart.Dao.UserDao;
import cn.shoppingcart.connection.DBConnection;
import cn.shoppingcart.model.Users;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/user-login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

//	protected void doGet(HttpServletRequest request, HttpServletResponse response)
//			throws ServletException, IOException {
//		response.sendRedirect("login.jsp");
//	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html; charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {

			String email = request.getParameter("login-email");
			String password = request.getParameter("login-password");

			try {
				UserDao dao = new UserDao(DBConnection.getConnection());
				Users user = dao.userLogin(email, password); 
				
				if(user != null) {
					request.getSession().setAttribute("auth", user);
					response.sendRedirect("index.jsp");
					
				}else {
					out.print("Login failed");
					
				}
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}

		

		}

	}

}
