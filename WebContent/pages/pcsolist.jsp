<%@page import="com.jci.model.EntryofpcsoModel"%>
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
<link href="./assets/vendors/DataTables/datatables.min.css"
	rel="stylesheet" />
<!-- THEME STYLES-->
<link href="assets/css/main.min.css" rel="stylesheet" />

    <script src="./assets/vendors/DataTables/datatables.min.js" type="text/javascript"></script>

<!-- PAGE LEVEL STYLES-->
<style>
.scrollmenu {
	overflow: auto;
	white-space: nowrap;
}

.scrollmenu a {
	display: inline-block;
	color: white;
	text-align: center;
	padding: 14px;
	text-decoration: none;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {
		$("#example-table").DataTable({
			scrollX : true,
			"pageLength" : 50
		});
	});
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
				<h1 class="page-title">PCSO List</h1>
			</div>
			<%
			List<EntryofpcsoModel> pcsoList = (List<EntryofpcsoModel>) request.getAttribute("pcsolist");
			%>
			<div class="page-content fade-in-up">
				<div class="ibox">

					<div class="ibox-head">
						<span>${msg}</span>
					</div>

					<div class="ibox-body">
						<div class="scrollmenu">
							<table class="table table-striped table-bordered table-hover"
								id="example-table" cellspacing="0" width="100%">
								<thead>
									<tr>
										<th>S.N.</th>
										<th>Reference Date</th>
										<th>JCI letter Ref.</th>
										<th>PCSO Date</th>
										<th>PCSO Qty. (M.T)</th>
										<th>Delivery Period</th>
										<th>Mill Code</th>
										<th>Mill Name</th>
										<th>Allocation</th>
		
										<!-- 										<th>creation_date</th> -->
								</thead>
								<tbody>
									<%
									int i = 1;
									for (EntryofpcsoModel pcsoel : pcsoList) {
									%>
									<tr>
										<td><%=i%></td>
										
										<td><%=pcsoel.getPcso_date()%></td>
										<td><%=pcsoel.getLetterRef()%></td>
										<td><%=pcsoel.getPcso_date()%></td>
										<td><%=pcsoel.getPcsoQty()%></td>
										<td><%=pcsoel.getDeliveryPeriod()%></td>
										<td><%=pcsoel.getMill_code()%></td>
										<td><%=pcsoel.getMill_name()%></td>
										<td><%=pcsoel.getAllocatedQty()%></td>
										<%-- 										<td><%=pcsoel.getCreated_date()%></td> --%>

										<td><a
											href="updatePcso.obj?pcsorefid=<%=pcsoel.getPcsorefid()%>"
											class="btn btn-warning btn-sm btn-block"> <i
												class="fa fa-pencil" aria-hidden="true"
												style="font-size: 15px;"></i></a></td>
										<td><a
											onclick="return confirm('Are you sure you want to delete this item?');"
											href="pcsoDelete.obj?pcsorefid=<%=pcsoel.getPcsorefid()%>"
											class="btn btn-danger btn-sm btn-block"> <i
												class="fa fa-trash" aria-hidden="true"
												style="font-size: 15px;"></i></a></td>

									</tr>
									<%
									i++;
									}
									%>
								</tbody>

							</table>
						</div>
					</div>
				</div>
			</div>
			<!-- END PAGE CONTENT-->
			<%@ include file="footer.jsp"%>
		</div>
	</div>
	<!-- BEGIN THEME CONFIG PANEL-->

	<!-- END THEME CONFIG PANEL-->
	<!-- BEGIN PAGA BACKDROPS-->
	<div class="sidenav-backdrop backdrop"></div>

	<!-- END PAGA BACKDROPS-->
	<!-- CORE PLUGINS-->
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
	<script src="./assets/vendors/DataTables/datatables.min.js"
		type="text/javascript"></script>
	<!-- CORE SCRIPTS-->
	<script src="assets/js/app.min.js" type="text/javascript"></script>
	<!-- PAGE LEVEL SCRIPTS-->
	<script type="text/javascript">
		$(function() {
			$('#example-table').DataTable({

				fixedHeader : true

			});
		})
	</script>

</body>

</html>
