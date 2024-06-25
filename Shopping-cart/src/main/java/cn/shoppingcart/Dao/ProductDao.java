package cn.shoppingcart.Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import cn.shoppingcart.model.Cart;
import cn.shoppingcart.model.Product;

public class ProductDao {

	private Connection conn;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;

	public ProductDao(Connection conn) {
		this.conn = conn;
	}

	public List<Product> getAllProducts() {

		List<Product> products = new ArrayList<Product>();

		try {
			query = "SELECT * FROM products";
			pst = this.conn.prepareStatement(query);
			rs = pst.executeQuery();

			while (rs.next()) {
				Product row = new Product();
				row.setId(rs.getInt("id"));
				row.setName(rs.getString("name"));
				row.setCategory(rs.getString("category"));
				row.setPrice(rs.getDouble("price"));
				row.setImage(rs.getString("image"));

				products.add(row);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return products;
	}

	public List<Cart> getCartProducts(ArrayList<Cart> cartList) {

		List<Cart> products = new ArrayList<>();
		try {
			if (cartList.size() > 0) {
				for (Cart item : cartList) {
					pst = this.conn.prepareStatement("SELECT * FROM products WHERE id=?");
					pst.setInt(1, item.getId());
					rs = pst.executeQuery();
					while (rs.next()) {
						Cart cart = new Cart();
						cart.setId(rs.getInt("id"));
						cart.setName(rs.getString("name"));
						cart.setCategory(rs.getString("category"));
						cart.setPrice(rs.getDouble("price") * item.getQuantity());
						cart.setQuantity(item.getQuantity());
						products.add(cart);
					}
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

		}

		return products;
	}

	public double getTotalCartPrice(ArrayList<Cart> cartList) {

		double sum = 0;
		try {
			if (cartList.size() > 0) {
				for (Cart item : cartList) {
					pst = this.conn.prepareStatement("SELECT price FROM products WHERE id=?");
					pst.setInt(1, item.getId());
					rs = pst.executeQuery();

					while (rs.next()) {
						sum += rs.getDouble("price") * item.getQuantity();
					}

				}

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return sum;
	}

	public Product getSingleProduct(int id) {
		Product row = null;

		try {
			pst = this.conn.prepareStatement("SELECT * FROM products WHERE id=?");
			pst.setInt(1, id);
			rs = pst.executeQuery();

			while (rs.next()) {
				row = new Product();
				row.setId(rs.getInt("id"));
				row.setName(rs.getString("name"));
				row.setCategory(rs.getString("category"));
				row.setPrice(rs.getDouble("price"));
				row.setImage(rs.getString("image"));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return row;
	}
}
