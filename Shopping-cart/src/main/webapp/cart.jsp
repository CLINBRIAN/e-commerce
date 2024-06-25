<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page import="java.text.DecimalFormat"%>	
<%@ page import="cn.shoppingcart.model.*"%>
<%@ page import="cn.shoppingcart.Dao.*"%>
<%@ page import="cn.shoppingcart.connection.*"%>
<%@ page import="java.util.*"%>
<%
DecimalFormat dcf= new DecimalFormat("#.##");
request.setAttribute("dcf", dcf);

Users auth = (Users) request.getSession().getAttribute("auth");
if (auth != null) {
	//request.setAttribute("auth", auth);
	request.setAttribute("auth", auth);
}

ArrayList<Cart> cart_List =(ArrayList<Cart>) session.getAttribute("cart-list");
List<Cart> cartProducts = null;
if(cart_List !=null){
	
	ProductDao pDao = new ProductDao(DBConnection.getConnection());
	cartProducts = pDao.getCartProducts(cart_List);
	
	double total = pDao.getTotalCartPrice(cart_List);
	request.setAttribute("cart_List", cart_List);
	request.setAttribute("total", total);
}
%>
<!DOCTYPE html>
<html>
<head>
<title>Shopping Cart page</title>
<%@include file="/includes/header.jsp"%>
<style type="text/css">
.table tbody td {
	vertical-align: middle;
}

.btn-incre, .btn-decre {
	box-shadow: none;
	font-size: 25px;
}
</style>
</head>
<body>

<%@include file="includes/navbar.jsp"%>

	<div class="container">
		<div class=" d-flex py-3 ">
			<h3>Total Price: $ ${ (total>0)?dcf.format(total):0 }</h3>
			<a class="mx-3 btn btn-primary " href="cart-check-out">Check Out</a>
		</div>
		<table class="table table-light">
			<thead>
				<tr>
					<th scope="col">Name</th>
					<th scope="col">Category</th>
					<th scope="col">Price</th>
					<th scope="col">Buy Now</th>
					<th scope="col">Cancel</th>
				</tr>
			</thead>
			<tbody>
			<%
			if(cart_List !=null){
				for(Cart c: cartProducts){
					%>
					<tr>
					<td><%= c.getName() %></td>
					<td><%= c.getCategory() %></td>
					<td><%=dcf.format(c.getPrice()) %></td>
					<td>
						<form action="Order-now" method="post" class="form-inline">
							<input type="hidden" name="id" value="<%= c.getId() %>" class="form-input">
							<div class="form-group d-flex justify-content-between w-50">
								<a class="btn btn-sm btn-decre" href="Quantity-Incr-Decr?action=dec&id=<%= c.getId()%>"><i
									class="fas fa-minus-square"></i></a> 
									<input type="text"name="quantity" class="form-control w-50" value="<%= c.getQuantity() %>" readonly>
								<a class="btn btn-sm btn-incre" href="Quantity-Incr-Decr?action=inc&id=<%= c.getId()%>"><i
									class="fas fa-plus-square"></i></a>
							</div>
							<button type = "submit" class = "btn btn-primary btn-sm">Buy</button> 
						</form>
					</td>
					<td><a class="btn btn-sm btn-danger" href="remove-from-cart?id=<%= c.getId()%>">Remove</a></td>
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