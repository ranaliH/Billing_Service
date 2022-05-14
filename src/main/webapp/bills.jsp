<%@page import="com.Bills"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Bills Management</title>
<link rel="stylesheet" href="Views/bootstrap.min.css">
<script src="Components/jquery-3.6.0.js"></script>
<script src="Components/bills.js"></script>
</head>
<body>
	<div class="container">
		<div class="row">
			<div class="col-6">
				<h1>Bills Management</h1>
				<form id="formBill" name="formBill">
					Invoice Number:  
					<input id="invoiceno" name="invoiceno" type="text" class="form-control form-control-sm" placeholder="Auto Generated" readonly> <br> 
					Date:
					<input id="date" name="date" type="date" class="form-control form-control-sm"> <br> 
					Customer Name:
					<input id="cusname" name="cusname" type="text" class="form-control form-control-sm"> <br> Item
					Account No: 
					<input id="accno" name="accno" type="text" class="form-control form-control-sm"> <br> 
					Number of Units:  
					<input id="units" name="units" type="text" class="form-control form-control-sm"> <br> 
					Unit Price:
					<input id="price" name="price" type="text" class="form-control form-control-sm"> <br> 
					Tax:
					<input id="tax" name="tax" type="text" class="form-control form-control-sm"> <br> Item
					Total Amount: 
					<input id="total" name="total" type="text" class="form-control form-control-sm"> <br> 
							
					<input id="btnSave" name="btnSave" type="button" value="Save" class="btn btn-primary"> 
					<input type="hidden" id="hidInvoiceNoSave" name="hidInvoiceNoSave" value="">
				</form>
				<div id="alertSuccess" class="alert alert-success"></div>
				<div id="alertError" class="alert alert-danger"></div>
				<br>
				<div id="divBillsGrid">
					<%
					Bills itemObj = new Bills();
					out.print(itemObj.readBills());
					%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>