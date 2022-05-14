/**
 * 
 */
 $(document).ready(function() {
	if ($("#alertSuccess").text().trim() == "") {
		$("#alertSuccess").hide();
	}
	$("#alertError").hide();
});

$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateBillForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidInvoiceNoSave").val() == "") ? "POST" : "PUT";
	$.ajax(
		{
			url: "BillsAPI",
			type: type,
			data: $("#formBill").serialize(),
			dataType: "text",
			complete: function(response, status) {
				onBillSaveComplete(response.responseText, status);
			}
		});
});

function onBillSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#hidInvoiceNoSave").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidInvoiceNoSave").val("");
	$("#formBill")[0].reset();
}


// UPDATE==========================================
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidInvoiceNoSave").val($(this).data("invoiceno"));
	$("#invoiceno").val($(this).closest("tr").find('td:eq(0)').text());
	$("#date").val($(this).closest("tr").find('td:eq(1)').text());
	$("#cusname").val($(this).closest("tr").find('td:eq(2)').text());
	$("#accno").val($(this).closest("tr").find('td:eq(3)').text());
	$("#units").val($(this).closest("tr").find('td:eq(4)').text());
	$("#price").val($(this).closest("tr").find('td:eq(5)').text());
	$("#tax").val($(this).closest("tr").find('td:eq(6)').text());
	$("#total").val($(this).closest("tr").find('td:eq(7)').text());
});

// REMOVE==========================================
$(document).on("click", ".btnRemove", function(event) {
	$.ajax(
		{
			url: "BillsAPI",
			type: "DELETE",
			data: "invoiceNo=" + $(this).data("invoiceno"),
			dataType: "text",
			complete: function(response, status) {
				onBillDeleteComplete(response.responseText, status);
			}
		});
});

function onBillDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divBillsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}
// CLIENT-MODEL================================================================
function validateBillForm() {
	// Customer Name
	if ($("#cusName").val().trim() == "") {
		return "Insert Item Customer Name.";
	}
	// Account Number
	if ($("#accNo").val().trim() == "") {
		return "Insert Account Number.";
	}
	// Number of Units
	if ($("#noOfUnits").val().trim() == "") {
		return "Insert Number of Units.";
	}
	// PRICE-------------------------------
	if ($("#unitPrice").val().trim() == "") {
		return "Insert Unit Price.";
	}
	// is numerical value
	var tmpPrice = $("#unitPrice").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Unit Price.";
	}
	// convert to decimal price
	$("#unitPrice").val(parseFloat(tmpPrice).toFixed(2));
	// TAX-------------------------------
	if ($("#tax").val().trim() == "") {
		return "Insert Tax.";
	}
	// is numerical value
	var tmpPrice1 = $("#tax").val().trim();
	if (!$.isNumeric(tmpPrice1)) {
		return "Insert a numerical value for Tax.";
	}
	// convert to decimal price
	$("#tax").val(parseFloat(tmpPrice1).toFixed(2));
	return true;
}
