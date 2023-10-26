<%@page import="com.jci.model.UserRoleModel"%>
<%@page import="com.jci.model.ZoneModel"%>
<%@page import="java.util.List"%>
<!DOCTYPE html>
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
<link
	href="https://stackpath.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<link
	href="<%=request.getContextPath()%>/resources/css/styleUserReg.css"
	rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<script type="text/javascript"
	src='<%=request.getContextPath()%>/resources/js/responsivevoice.js'></script>
<script type="text/javascript"
	src='<%=request.getContextPath()%>/resources/js/custom.js'></script>
<script type="text/javascript"
	src='<%=request.getContextPath()%>/resources/js/jquery.mCustomScrollbar.concat.min.js'></script>
<script type="text/javascript"
	src='<%=request.getContextPath()%>/resources/js/jquery.validate.min.js'></script>
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
.field-icon {
	float: right;
	margin-left: -25px;
	margin-top: -25px;
	position: relative;
	z-index: 2;
}

.container {
	padding-top: 50px;
	margin: auto;
}

.required:after {
	content: " *";
	color: red;
}
</style>
<script>
	var flag;
	var hasError = false;
	var hasError1 = false;
	var hasError2 = false;
</script>
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
				<h1 class="page-title">Generation of PCSO Request Letter</h1>
			</div>
			<div class="page-content fade-in-up">
				<div class="row">
					<div class="col-md-11">
						<div class="ibox">
							<div class="ibox-head">

								<span>${msg}</span>
							</div>
							<div class="ibox-body">
								<form action="generatePCSORequest.obj" method="POST"
									name="myForm" onsubmit="return validate()">

									<div class="row">

										<div class="col-sm-4 form-group">
											<label class="required">JCI Reference No.</label>
											<!--  &nbsp;&nbsp;&nbsp; <span
												id="errName" name="errName" class="text-danger"> </span> -->
											<input class="form-control" name="referenceno"
												id="referenceno" type="text" placeholder="Reference No."
												value="" required>
										</div>

									</div>
									<div class="row">

										<div class="col-sm-4 form-group">
											<label class="required">System Available Qty.</label> <input
												class="form-control" name="sysAvailQty" id="sysAvailQty"
												type="number" value="12345" readonly>
										</div>
										<div class="col-sm-4 form-group">
											<label class="required">Requested Qty.</label> <input
												class="form-control" name="reqQty" id="reqQty" type="number"
												placeholder="Requested Qty." value="" min="0" required/> <span
												id="errMsg" class="text-danger"></span>
										</div>
									</div>
									<div class="row">


										<div class="col-sm-4 form-group">
											<label class="required">Date</label> <input
												class="form-control" name="date" id="date" type="text"
												readonly>
										</div>
									</div>


									<div class="row">
										<div class="col-sm-12 form-group">
											<input type="submit" value="Submit" id="submit"
												class="btn btn-success">
										</div>
									</div>
								</form>
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


	<script type="text/javascript">
		$(document)
				.ready(

						function() {

							// to not enter a value less than 0
							var inputField = $("#reqQty");
							var submitButton = $("#submit");
							var refNo = $("#referenceno");
							var reqErr = $("#errMsg");
							var sAQty = $("#sysAvailQty");

							// to set ref number to alphanumeric
							refNo.on("input", function() {
								var inputVal = $(this).val();
								var refVal = inputVal.replace(/[^a-zA-Z0-9]/g,
										"");
								$(this).val(refVal);
							});

							// to set requested quantity as needed

							inputField
									.on(
											"input",
											function() {
												var inputVal = parseInt($(this)
														.val());
												var max = parseInt(sAQty.val());
												if (inputVal > max) {
													submitButton.prop(
															"disabled", true);
													$("#errMsg")
															.html(
																	"Requested quantity must be less than Available Quantity!");
												} else {
													submitButton.prop(
															"disabled", false);
													reqErr.html("");

												}
											});

							// to prevent + and - from being input
							inputField.on("keydown", function(event) {
								if (event.key === "-" || event.key === "+") {
									event.preventDefault();
								}
							});

						});
	</script>
	<script>
		const currentDate = new Date();
		const day = String(currentDate.getDate()).padStart(2, '0');
		const month = String(currentDate.getMonth() + 1).padStart(2, '0'); // Months are zero-based
		const year = String(currentDate.getFullYear());
		const formattedDate = day + "-" + month + "-" + year;

		$("#date").val(formattedDate);
	</script>


	<!-- END PAGA BACKDROPS-->
	<!-- CORE PLUGINS-->
</body>
</html>
