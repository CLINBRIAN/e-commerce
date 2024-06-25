<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="cn.shoppingcart.Dao.*"%>
<%@ page import="cn.shoppingcart.model.*"%>
<%@ page import="java.util.*"%>
<%@ page import="cn.shoppingcart.connection.*"%>
<%
DecimalFormat dcf = new DecimalFormat("#.##");
request.setAttribute("dcf", dcf);

Users auth = (Users) request.getSession().getAttribute("auth");
List<Order> orders = null;
if (auth != null) {
	//request.setAttribute("auth", auth);
	request.setAttribute("auth", auth);
	orders = new OrderDao(DBConnection.getConnection()).userOrders(auth.getId());
} else {
	response.sendRedirect("login.jsp");
}

ArrayList<Cart> cart_List = (ArrayList<Cart>) session.getAttribute("cart-list");
List<Cart> cartProducts = null;
if (cart_List != null) {

	ProductDao pDao = new ProductDao(DBConnection.getConnection());
	cartProducts = pDao.getCartProducts(cart_List);

	request.setAttribute("cart_List", cart_List);
}
%>
<!DOCTYPE html>
<html>
<head>
<title>Orders page</title>
<%@include file="includes/header.jsp"%>
</head>
<body>
	<%@include file="includes/navbar.jsp"%>
	<div class="container">
		<div class="card-header my-3">All Orders</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Date</th>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Quantity</th>
					<th scope="col">Price</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
				<%
				if (orders != null) {
					for (Order d : orders) {
				%>
				<tr>

					<td><%=d.getDate()%></td>
					<td><%=d.getName()%></td>
					<td><%=d.getCategory()%></td>
					<td><%=d.getQuantity()%></td>
					<td><%=dcf.format(d.getPrice())%></td>
					<td><a class="btn btn-danger btn-sm"
						href="cancel_order?id=<%=d.getOrderId()%>"> Cancel </a></td>
				</tr>
				<%
				}
				}
				%>
			</tbody>
		</table>
	</div>


	<%@include file="includes/footer.jsp"%>
</body>
</html>