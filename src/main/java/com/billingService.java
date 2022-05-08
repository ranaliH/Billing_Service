package com;

import model.billingModel;

import java.sql.Date;

//For REST Service
import javax.ws.rs.*; 
import javax.ws.rs.core.MediaType; 
//For JSON
import com.google.gson.*; 
//For XML
import org.jsoup.*; 
import org.jsoup.parser.*; 
import org.jsoup.nodes.Document; 

@Path("/bills")
public class billingService {
	
	billingModel billingObj = new billingModel();
	
	//read 
	@GET
	@Path("/readBills") 
	@Produces(MediaType.TEXT_HTML) 
	public String readBills() 
	 { 
	 return billingObj.readBills(); 
	 } 
	
	@GET
	@Path("/readBills/{invoiceNo}") 
	@Produces(MediaType.TEXT_PLAIN) 
	public String readBill(@PathParam("invoiceNo") String invoiceNo) 
	 { 
	 return billingObj.readBills(); 
	 } 
	
	//insert
	@POST
	@Path("/insertBills") 
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED) 
	@Produces(MediaType.TEXT_PLAIN)
	public String insertBill(@FormParam("date") Date date, 
			 @FormParam("cusName") String cusName, 
			 @FormParam("accNo") String accNo, 
			 @FormParam("noOfUnits") String noOfUnits,
			 @FormParam("unitPrice") String unitPrice, 
			 @FormParam("tax") String tax,
			 @FormParam("totalAmount") String totalAmount)
	{ 
		
		//validation for the insert query
				if(cusName.isEmpty()||accNo.isEmpty()||noOfUnits.isEmpty()||unitPrice.isEmpty()||tax.isEmpty()) 
				 {
					 return "input fields cannot be empty";
				 }	
				
	 String output = billingObj.insertBill(date, cusName, accNo, noOfUnits, unitPrice, tax, totalAmount); 
	 return output; 
	}
	
	//update
	@PUT
	@Path("/updateBills") 
	@Consumes(MediaType.APPLICATION_JSON) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String updateBill(String billData) 
	{ 
	//Convert the input string to a JSON object 
	 JsonObject billObject = new JsonParser().parse(billData).getAsJsonObject(); 
	
	 //Read the values from the JSON object
	 String invoiceNo = billObject.get("invoiceNo").getAsString(); 
	 String date = billObject.get("date").getAsString();
	 String cusName = billObject.get("cusName").getAsString(); 
	 String accNo = billObject.get("accNo").getAsString(); 
	 String noOfUnits = billObject.get("noOfUnits").getAsString(); 
	 String unitPrice = billObject.get("unitPrice").getAsString(); 
	 String tax = billObject.get("tax").getAsString(); 
	 String totalAmount = billObject.get("totalAmount").getAsString(); 
	 
	//validation for the update query
		 if(date == null||cusName.isEmpty()||accNo.isEmpty()||noOfUnits.isEmpty()||unitPrice.isEmpty()||tax.isEmpty()) 
		 {
			 return "input fields cannot be empty";
		 }
	 
	 String output = billingObj.updateBill(invoiceNo, date, cusName, accNo, noOfUnits, unitPrice, tax, totalAmount); 
	return output; 
	}
	
	
	//Delete
	@DELETE
	@Path("/deleteBills") 
	@Consumes(MediaType.APPLICATION_XML) 
	@Produces(MediaType.TEXT_PLAIN) 
	public String deleteBill(String billData) 
	{ 
		//Convert the input string to an XML document
		Document doc = Jsoup.parse(billData, "", Parser.xmlParser()); 
			 
		//Read the value from the element <itemID>
		String invoiceNo = doc.select("invoiceNo").text(); 
		String output = billingObj.deleteBill(invoiceNo); 
		 
		return output; 
	}
	
	//inter service communication
//	@GET
//	@Path("/ReadAll/{cusID}")
//	@Produces(MediaType.TEXT_PLAIN)
//	public String readAll(@PathParam("cusID")int cusID){
//		return billingObj.readAllCusDetails(cusID);
//	}
	
}
