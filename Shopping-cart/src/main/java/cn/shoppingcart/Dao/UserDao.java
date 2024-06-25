package cn.shoppingcart.Dao;

import java.sql.*;

import cn.shoppingcart.model.Users;

public class UserDao {

	private Connection conn;
	private String query;
	private PreparedStatement pst;
	private ResultSet rs;

	public UserDao(Connection conn) {
		this.conn = conn;
	}

	public Users userLogin(String email, String password) {
		Users user = null;
	
		try {
			query = "SELECT * FROM users WHERE email=? and password=?";
			pst= this.conn.prepareStatement(query);
			pst.setString(1, email);
			pst.setString(2, password);
			rs= pst.executeQuery();
			
			if(rs.next()) {
				user = new Users();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setEmail(rs.getString("email"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println();
		}
		
		return user;
		
		
	}

}
