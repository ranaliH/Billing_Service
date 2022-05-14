package com;

import java.sql.*; 
//import com.sun.jersey.api.client.*;

public class Bills {

	//DB connection
	private Connection connect()
	 {
	 Connection con = null;
	 try
	 {
	 Class.forName("com.mysql.jdbc.Driver");

	 //Provide the correct details: DBServer/DBName, username, password
	 con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/electro_grid", "root", "");
	 }
	 catch (Exception e)
	 {e.printStackTrace();
	 }
	 return con;
	} 
	
	public String insertBill(String date, String cusName, String accNo, String noOfUnits, String unitPrice, String tax, String totalAmount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for inserting.";
			}
			// create a prepared statement

			String query = " insert into reg_cus_billing (`invoiceNo`,`date`,`cusName`,`accNo`,`noOfUnits`,`unitPrice`,`tax`,`totalAmount`)"
					 + " values (?, ?, ?, ?, ?, ?, ?, ?)"; 
			PreparedStatement preparedStmt = con.prepareStatement(query);
			
			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, date);
			preparedStmt.setString(3, cusName);
			 preparedStmt.setString(4, accNo);
			 preparedStmt.setString(5, noOfUnits); 
			 preparedStmt.setDouble(6, Double.parseDouble(unitPrice)); 
			 preparedStmt.setDouble(7, Double.parseDouble(tax));
			 preparedStmt.setDouble(8, Double.parseDouble(totalAmount));
			 
			// execute the statement
			preparedStmt.execute();
			con.close();
			
			String newBills = readBills();
			output = "{\"status\":\"success\", \"data\": \"" + newBills + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	} 

	// read
	public String readBills() {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			
			// Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Invoice No</th>"+
			 "<th>Date</th>" +
			 "<th>Customer Name</th>" + 
			 "<th>Account No</th>" +
			 "<th>Units</th>"+
			 "<th>Unit Price</th>"+
			 "<th>Tax</th>"+
			 "<th>Total Amount</th>"+
			 "<th>Update</th>"+
			 "<th>Remove</th> </tr>";
			
			String query = "select * from reg_cus_billing";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			
			// iterate through the rows in the result set
			while (rs.next()) {
				String invoiceNo = Integer.toString(rs.getInt("invoiceNo")); 
				 String date = rs.getString("date");
				 String cusName = rs.getString("cusName"); 
				 String accNo = Integer.toString(rs.getInt("accNo"));
				 String noOfUnits = Integer.toString(rs.getInt("noOfUnits")); 
				 String unitPrice = Double.toString(rs.getDouble("unitPrice")); 
				 String tax = Double.toString(rs.getDouble("tax")); 
				 String totalAmount = Double.toString(rs.getDouble("totalAmount"));
				
				// Add into the html table
				 output += "<tr><td>" + invoiceNo + "</td>"; 
				 output += "<td>" + date + "</td>"; 
				 output += "<td>" + cusName + "</td>"; 
				 output += "<td>" + accNo + "</td>"; 
				 output += "<td>" + noOfUnits + "</td>"; 
				 output += "<td>" + unitPrice + "</td>"; 
				 output += "<td>" + tax + "</td>"; 
				 output += "<td>" + totalAmount + "</td>"; 
				 
				// buttons
				output += "<td><input name='btnUpdate' type='button' value='Update' "
						+ "class='btnUpdate btn btn-secondary' data-itemid='" + invoiceNo + "'></td>"
						+ "<td><input name='btnRemove' type='button' value='Remove' "
						+ "class='btnRemove btn btn-danger' data-itemid='" + invoiceNo + "'></td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the bills.";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
	
	public String updateBill(String invoiceNo, String date, String cusName, String accNo, String noOfUnits, String unitPrice, String tax, String totalAmount) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE reg_cus_billing SET date=?, cusName=?, accNo=?, noOfUnits=?, unitPrice=?, tax=?, totalAmount=? WHERE invoiceNo=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, date); 
			 preparedStmt.setString(2, cusName); 
			 preparedStmt.setString(3, accNo);
			 preparedStmt.setString(4, noOfUnits); 
			 preparedStmt.setDouble(5, Double.parseDouble(unitPrice)); 
			 preparedStmt.setDouble(6, Double.parseDouble(tax));
			 preparedStmt.setDouble(7, Double.parseDouble(totalAmount));
			 preparedStmt.setInt(8, Integer.parseInt(invoiceNo));

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newItemsBills = readBills();
			output = "{\"status\":\"success\", \"data\": \"" + newItemsBills + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\": \"Error while updating the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	
	
	public String deleteBill(String invoiceNo) {
		String output = "";
		try {
			Connection con = connect();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from reg_cus_billing where invoiceNo=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(invoiceNo));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newBills = readBills();
			output = "{\"status\":\"success\", \"data\": \"" + newBills + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting the item.\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}
	
}
