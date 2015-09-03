<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<title>Products</title>
</head>
<body>
	<jsp:include page="NavBar.jsp" />
	<div class="container">
		<div style="border: 2px solid black">
			<div align="center">
				<div class="row">
					<div class="col-sm-2">
						<p>Product</p>
					</div>
					<div class="col-sm-2">
						<p>Price</p>
					</div>
					<div class="col-sm-2">
						<p>Quantity</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	${productList}
</body>
</html>