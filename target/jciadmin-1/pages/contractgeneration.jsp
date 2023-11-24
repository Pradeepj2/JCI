<!DOCTYPE html>


<%@page import="java.util.HashMap"%>
<%@page import="java.util.*"%>
<%@page import="java.io.File"%>

<html lang="en">

<head>
<meta charset="UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width initial-scale=1.0">
<title>JCI | CMS</title>
<!-- GLOBAL MAINLY STYLES-->
<link href="./assets/vendors/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="./assets/vendors/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" />
<link href="./assets/vendors/themify-icons/css/themify-icons.css"
	rel="stylesheet" />
<!-- PLUGINS STYLES-->
<!-- THEME STYLES-->
<link href="assets/css/main.min.css" rel="stylesheet" />
<link rel="stylesheet" href="assets/css/chosen.css">
<script src="./assets/vendors/jquery/dist/jquery.min.js"
	type="text/javascript"></script>
<script src="./assets/vendors/popper.js/dist/umd/popper.min.js"
	type="text/javascript"></script>
<script src="./assets/vendors/bootstrap/dist/js/bootstrap.min.js"
	type="text/javascript"></script>
<script src="./assets/vendors/metisMenu/dist/metisMenu.min.js"
	type="text/javascript"></script>
<script
	src="./assets/vendors/jquery-slimscroll/jquery.slimscroll.min.js"
	type="text/javascript"></script>
<!-- PAGE LEVEL PLUGINS-->
<!-- CORE SCRIPTS-->
<script src="assets/js/app.min.js" type="text/javascript"></script>

<!-- PAGE LEVEL STYLES-->
<style>
.required:after {
	content: " *";
	color: red;
}
</style>


</head>

<body class="fixed-navbar">
	<div class="page-wrapper">
		<!-- START HEADER-->
		<%@ include file="header.jsp"%>
		<!-- END HEADER-->
		<!-- START SIDEBAR-->
		<%@ include file="sidebar.jsp"%>
		<!-- END SIDEBAR-->
		<div class="content-wrapper">
			<!-- START PAGE CONTENT-->
			<div class="page-heading">
				<h1 class="page-title">Contract Generation PCSO Wise</h1>
			</div>

			<div class="page-content fade-in-up">
				<div class="row">
					<div class="col-md-11">
						<div class="ibox">

							<div class="ibox-body">
								<span>${msg}</span>
								<form method="POST">

									<div class="row">

										<div class="col-sm-4 form-group">
											<label >Delivery Type</label> <select
												class="form-control pcso" name="deliveryType"
												id="deliveryType" required>
												<option disabled selected>-Select-</option>
												<option value="Ex-Godown">Ex-Godown</option>
												<option value="Mill-Delivery">Mill Delivery</option>

											</select>
										</div>

										<div class="col-sm-4 form-group">
											<label>Grade Composition</label> <select
												class="form-control pcso" name="gradeComp" id="gradeComp"
												required>
												<option disabled selected>-Select-</option>
												<%
												List<String> gradeCompositionLable = (List<String>) request.getAttribute("gradeCompositionLable");
												for (String s : gradeCompositionLable) {
												%>
												<option><%=s%></option>
												<%
												}
												%>


											</select>
										</div>
										<div class="col-sm-4 form-group">
											<label class="required">PCSO Date</label>

											<%
											List<Date> pcsoDates = (List<Date>) request.getAttribute("pcsoDates");
											%>
											<select data-placeholder='Choose Dates..'
												class='chosen-select form-control pcso' multiple tabindex='3'
												name="pcso_date" id="pcso_date" required>
												<option disabled>-Select-</option>
												<%
												for (int i = 0; i < pcsoDates.size(); i++) {
												%>
												<option value="<%=pcsoDates.get(i)%>"><%=pcsoDates.get(i)%>
												</option>
												<%
												}
												%>
											</select>
										</div>


									</div>


									<div class="row">
										<div class="col-sm-4 form-group">
											<label class="required">Contract identification No.</label> <input
												class="form-control" name="contractIdn" id="contractIdn"
												type="text" placeholder="Contract identification No."
												required> <span id="contractIdnMsg"
												class="text-danger"></span>

										</div>
										<div class="col-sm-4 form-group">

											<label class="required">Contact Date</label> <input
												class="form-control" name="contractDate" id="contactDate"
												type="text" readonly
												value="<%=new java.text.SimpleDateFormat("dd-MM-yyyy").format(new java.util.Date())%>">
										</div>

										<div class="col-sm-4 form-group">
											<label class="required">Contract Qty.</label> <input
												class="form-control" name="contract_qty" id="contract_qty"
												type="number" readonly>
										</div>


									</div>
									<div class="row">
										<div class="col-sm-4 form-group">
											<label class="required">Contract Value</label> <input
												class="form-control" name="contractValue" id="contractValue"
												type="number" readonly>
										</div>
										<input class="form-control" type="hidden" name="count"
											id="count">

									</div>
									<div class="row">
										<div class=" col-sm-4 form-group">
											<button class="btn btn-success" type="submit" value="Submit"
												id="submit">Submit</button>
										</div>
									</div>
								</form>

								<div id="list"></div>
							</div>
						</div>
					</div>

				</div>
			</div>
			<!-- END PAGE CONTENT-->
			<%@ include file="footer.jsp"%>
		</div>
	</div>

	<div class="sidenav-backdrop backdrop"></div>



	<!-- PAGE LEVEL SCRIPTS-->
</body>
<script src="assets/css/chosen.jquery.js" type="text/javascript"></script>


<script>
$("#pcso_date").chosen();
$("#pcso_date").addClass("chosen-select");
var parsedArray = null;
var count = 0;

	$(".pcso")
			.on(
					"change",
					function() {					
						var array = [];
						
						var gradeComp = $("#gradeComp").val();
						var deliveryType = $("#deliveryType").val();

						$("#pcso_date").find("option:selected").each(function() {
							array.push($(this).val());
						});

						var jsonArray = JSON.stringify(array);
					
						parsedArray = JSON.parse(jsonArray);
					    var lastIndex = parsedArray.length - 1;
					    
					    if(parsedArray.length == 0){
					    	
					    	$("#list").html("<div></div>");
					    	$("#contract_qty").val(0);
							
							$("#contractValue").val(0.0);
					    	return;
					    }
					    
					    if(gradeComp != null && deliveryType != null && parsedArray.length != 0 ){
					    	console.log("called");
					   
						 $
								.ajax({
									type : 'GET',
									url : 'pcso_details.obj',
									data : {
										"pcso_dates" : jsonArray,
										"gradeComp":gradeComp,
										"deliveryType":deliveryType
									},
									success : function(result) {
										var data1 = jQuery.parseJSON(result).model;
										var List = data1.List;
										var TotelContractedValue = data1.totelContractedValue;
										var contractedValueMillWise = data1.contractedValueMillWise;
										 count = List.length;
										 console.log(data1);
										var sizeOfSingleResultArray = List[0].length; 
										
								 	    var htmlTable = '<table border="3px" id="table_r" class="table table-hover table-striped">';
										var sum = 0;
										var dateStringAsColumnName = parsedArray.map(ele => {
											return '<th style="text-align:center">'+ ele + '</th>';
										})
										
									
										
										htmlTable += '<tr><th style="text-align:center">Mill code</th><th style="text-align:center">Mill Name</th>'+dateStringAsColumnName+'<th style="text-align:center">Total allocation</th></tr>';
									
										htmlTable += '<tbody id="body">';
										for (i = 0; i < List.length; i++) {
											htmlTable += '<tr border="2px"><td id="code'+i+'" style="text-align:center">'
													+ List[i][1]
													+ '</td><td id="name'+i+'"style="text-align:center">'
													+ List[i][0] + '</td>';
													
									   for(j = 2 ; j < sizeOfSingleResultArray-1 ; j++){
										htmlTable += '</td><td style="text-align:center">'
										+ List[i][j] + '</td>';
									   }
													
									   htmlTable +=	'<td id="allocated'+i+'" style="text-align:center">'
													+ List[i][sizeOfSingleResultArray-1]
													+ '</td></tr>';
									   htmlTable +="<input type='hidden' id='contractedValue"+i+"' value='"+contractedValueMillWise[i]+"'>";
													
											sum += List[i][sizeOfSingleResultArray-1];
										}
										
// 										htmlTable += '<tr border="2px"><td style="text-align:center"></td><td style="text-align:center"> Total Allocation </td><td style="text-align:center">' 
// 													+ sum + '</td></tr>';
										htmlTable += '</tbody></table>';
									 htmlTable += '<br><h4> Total Allocation = ' + sum + '</h6>';
										
										$("#list").html(htmlTable); 
										$("#contract_qty").val(sum);
										$("#count").val(count);
										$("#contractValue").val(TotelContractedValue);
								
									}
								});
					    }

					});

 $("#submit")
			.click(
					
					
					function() {

						var pcsoDate = parsedArray;
						var contractIdn = $("#contractIdn").val();
						var contractdate = $("#contactDate").val();
						var contractQty = $("#contract_qty").val();
						var deliveryType = $("#deliveryType").val();
						var gradeComp = $("#gradeComp").val();
						
						
						
						console.log(deliveryType,gradeComp,contractIdn);
						
						var millDetails = [];
				
						// Loop through the rows of the table (skipping the header row)
						$('#table_r #body tr').each(function(index, row) {
						  var cells = $(row).find('td');
						
						  // Assuming the structure of your cells matches the order: Mill Name, Mill Code, Quantity
						  var millName = $(cells[1]).text();
						  var millCode = $(cells[0]).text();
						  var contractedValue = $("#contractedValue"+index).val();
						  var Qty = $(cells[cells.length - 1]).text(); // Assuming Quantity is in the last cell
						  millDetails.push({
							  "millCode" : millCode,
							  "millName" : millName,
							  "contractedValue" : contractedValue,
							  "Qty" : Qty
						  })

						})
						
						console.log(millDetails,"millDetails");
						
						var data = {
								"pcsoDate" : pcsoDate,
								"contractIdn" : contractIdn,
								"contractdate" : contractdate,
								"contractQty" : contractQty,
								"deliveryType" : deliveryType,
								"gradeComp" : gradeComp,
								"millDetails":millDetails,	
						 };
						 
						console.log(data);

						
                  if(deliveryType != null && gradeComp != null){
							
					 $.ajax({
							type : "POST",
							url : "contractgenerationPcsoWiseSave.obj",
							data :JSON.stringify(data),
							contentType: "application/json",  // Set the correct Content-Type
						    dataType: "json", 
							success : function(result) {
								alert("data saved");
							   //location.reload(true)
							/* 	 $("#msg").html("<div class=\"alert alert-success\"><b>Success !</b> Record saved successfully.</div>\r\n");
								alert("Result Saved Succesfully"); */
							}
						}); 
					 
					 return true;
	
						}
                  
                  else{
                	  return false;
                  }
						
					 
						  	
						/* if (result) {
							for (var w = 0; w < document
									.getElementById("tableData").rows.length; w++) {

								$
										.ajax({
											type : "POST",
											url : "saveUpdatedQty.obj",
											data : {
												"millcode" : document
														.getElementById("tableData").rows[w].cells[0].innerHTML,
												"qty" : document
														.getElementById("tableData").rows[w].cells[1].innerHTML,
												"contactnumber" : contactnumber,
												"contractdate" : contractdate,
												"cropyear" : cropyear
											},
											success : function(result) {
												location.reload(true)
												$("#msg")
														.html(
																"<div class=\"alert alert-success\"><b>Success !</b> Table saved successfully.</div>\r\n");

											}
										});
							}
							alert("Result Saved Succesfully");
						} */
		 
					}); 
</script>

<script>

$("#contractIdn")
.on(
		"blur",
		function() {
			var val = $(this).val();

			$
					.ajax({
						type : "GET",
						url : "isValid_Identification_No.obj",
						data : {
							"contractIdn" : val
						},
						success : function(result){
							//console.log(result , "result");

							if (result === 'true') {
								document
										.getElementById("contractIdnMsg").innerHTML = "Contract Identification Number is already exist";
								
							}

							else {
								document
										.getElementById("contractIdnMsg").innerHTML = "";
							
							}
						}

					});
		});
</script>

<!--  
<script>
	function removeTableTr() {

		var seen = {};
		$('table#table_tr tr').each(function() {
			var txt = $(this).text();
			if (seen[txt])
				$(this).remove();
			else
				seen[txt] = true;
		});

		console.log(seen);

	};
</script> -->
</html>
