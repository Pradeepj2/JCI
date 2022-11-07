<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width initial-scale=1.0">
    <title>JCI | CMS</title>
    <!-- GLOBAL MAINLY STYLES-->
    <link href="./assets/vendors/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link href="./assets/vendors/font-awesome/css/font-awesome.min.css" rel="stylesheet" />
    <link href="./assets/vendors/themify-icons/css/themify-icons.css" rel="stylesheet" />
    <!-- PLUGINS STYLES-->
    <!-- THEME STYLES-->
    <link href="assets/css/main.min.css" rel="stylesheet" />
    <!-- PAGE LEVEL STYLES-->
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
                <h1 class="page-title">Ruling Market Rate at DPC</h1>
            </div>
            <div class="page-content fade-in-up">
                <div class="row">
                    <div class="col-md-11">
                        <div class="ibox">
                            
                            <div class="ibox-body">
                                <form action="saveRulingMarketMid.obj" method="POST">
                                    <div class="row">
                                        <div class="col-sm-4 form-group">
                                            <label>DPC Name</label>
                                            <input class="form-control" type="text" name="dpcname" placeholder="DPC Name" required>
                                        </div>
                                        <div class="col-sm-4 form-group">
                                            <label>Number of Arrival</label>
                                            <input class="form-control" type="text" name="noofarrival" placeholder="Number of Arrival" required>
                                        </div>
                                        <div class="col-sm-4 form-group">
											<label>Date of Effect</label> 
											<input class="form-control" type="date" name="dateofarrival" placeholder="Date of Arrival" required>
										</div>
                                    </div>
                                    
                                    <div class="row">
                                        <div class="col-sm-4 form-group">
                                             <label>Jute Variety</label>
                                        	 <input class="form-control" name="jutevariety" type="text" placeholder="Jute Variety" required>
                                        </div>
                                       
										<div class="col-sm-4 form-group">
                                            <label>Crop Year</label> 
											<input class="form-control" name="cropyear" type="text" placeholder="Crop Year" required>
                                        </div>
                                        <div class="col-sm-4 form-group">
                                             <label>Arrived Quantity</label>
                                        <input class="form-control" name="arrivedquantity" type="text" placeholder="Arrived Quantity" required>
                                        </div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-4 form-group">
											<label>Minimum Moisture</label>
                                        	<input class="form-control" name="minmoisture" type="text" placeholder="Minimum Moisture" required>
										</div>
										<div class="col-sm-4 form-group">
											<label>Maximum Moisture</label>
                                        	<input class="form-control" name="maxmoisture" type="text" placeholder="Maximum Moisture" required>
										</div>
										<div class="col-sm-4 form-group">
											<label>Grade Wise Field</label>
                                        	<input class="form-control" name="gradewisefield" type="text" placeholder="Grade Wise Field" required>
										</div>
                                    </div>
                                    <div class="row">
                                        <div class="col-sm-4 form-group">
											<label>Grade Wise Rate</label>
                                        	<input class="form-control" name="gradewiserate" type="text" placeholder="Grade Wise Rate" required>
										</div>
										<div class="col-sm-4 form-group">
											<label>Estimated Grade Composition</label>
                                        	<input class="form-control" name="estimatedgradecomposition" type="text" placeholder="Maximum Moisture" required>
										</div>
                                    </div>                                    
                                    <div class="form-group col-sm-12">
                                        <button class="btn btn-default" type="submit">Submit</button>
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
     
    <!-- END PAGA BACKDROPS-->
    <!-- CORE PLUGINS-->
    <script src="./assets/vendors/jquery/dist/jquery.min.js" type="text/javascript"></script>
    <script src="./assets/vendors/popper.js/dist/umd/popper.min.js" type="text/javascript"></script>
    <script src="./assets/vendors/bootstrap/dist/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="./assets/vendors/metisMenu/dist/metisMenu.min.js" type="text/javascript"></script>
    <script src="./assets/vendors/jquery-slimscroll/jquery.slimscroll.min.js" type="text/javascript"></script>
    <!-- PAGE LEVEL PLUGINS-->
    <!-- CORE SCRIPTS-->
    <script src="assets/js/app.min.js" type="text/javascript"></script>
    <!-- PAGE LEVEL SCRIPTS-->
</body>
</html>