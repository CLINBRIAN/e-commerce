<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	<%@ page import="cn.shoppingcart.model.*"%>
	<%@ page import="java.util.*"%>
	<%@ page import="cn.shoppingcart.connection.*"%>
	<%
Users auth = (Users) request.getSession().getAttribute("auth");
if (auth != null) {
	//request.setAttribute("auth", auth);
	response.sendRedirect("index.jsp");
	
}

ArrayList<Cart> cart_List =(ArrayList<Cart>) session.getAttribute("cart-list");
if(cart_List !=null){
	request.setAttribute("cart_List", cart_List);
}
%>
<!DOCTYPE html>
<html>
<head>
<%@include file="includes/header.jsp"%>
<title>Shopping cart Login</title>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>
	<div class="container">
		<div class="card w-50 mx-auto my-5">
			<div class="card-header text-center ">User Login</div>
			<div class="card-body">
				<form action="user-login" method="post">

					<div class="form-group">
						<label>Email Address</label> <input type="email"
							class="form-control" name="login-email"
							placeholder="Enter your email" required>
					</div>

					<div class="form-group">
						<label>Password</label> <input type="password"
							class="form-control" name="login-password" placeholder="********"
							required>
					</div>

					<div class="text-center">
						<button type="submit" class="btn btn-primary ">Login</button>
					</div>
				</form>
			</div>
		</div>
	</div>
	<%@include file="includes/footer.jsp"%>
</body>
</html>