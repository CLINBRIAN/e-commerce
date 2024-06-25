package cn.shoppingcart.Dao;

import java.sql.*;
import java.util.*;

import cn.shoppingcart.model.*;

public class OrderDao {
	private Connection conn;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;

	public OrderDao(Connection conn) {
		this.conn = conn;
	}

	public boolean insertOrder(Order order) {
		boolean result = false;
		try {
			pst = this.conn.prepareStatement("INSERT INTO orders(p_id, u_id, o_quantity,o_date) VALUES(?,?,?,?)");
			pst.setInt(1, order.getOrderId());
			pst.setInt(2, order.getuId());
			pst.setInt(3, order.getQuantity());
			pst.setString(4, order.getDate());
			pst.executeUpdate();

			result = true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public List<Order> userOrders(int  id) {
		List<Order> list = new ArrayList<>();

		try {
			pst = this.conn.prepareStatement("SELECT * FROM orders WHERE u_id=? order by orders.o_id desc");
			pst.setInt(1, id);
			rs = pst.executeQuery();

			while (rs.next()) {
				Order order = new Order();
				ProductDao productDao = new ProductDao(this.conn);
				int pId = rs.getInt("p_id"); // we need to get the product by id from the database

				Product product = productDao.getSingleProduct(pId);
				order.setOrderId(rs.getInt("o_id"));
				order.setId(pId);
				order.setName(product.getName());
				order.setCategory(product.getCategory());
				order.setPrice(product.getPrice() * rs.getInt("o_quantity"));
				order.setQuantity(rs.getInt("o_quantity"));
				order.setDate(rs.getString("o_date"));

				list.add(order);

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return list;
	}
	
	public void cancelOrder(int id) {
		try {
			pst = this.conn.prepareStatement("DELETE FROM orders WHERE o_id=?");
			pst.setInt(1, id);
			 pst.execute();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		}
	}


