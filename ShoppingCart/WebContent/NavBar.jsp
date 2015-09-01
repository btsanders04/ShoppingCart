<!DOCTYPE html>
<html lang="en">
<head>
<title>BullHorn</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>

	<nav class="navbar navbar-inverse">
		<div class="container-fluid">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle" data-toggle="collapse"
					data-target="#myNavbar">
					<span class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.jsp?logOut=false">ShopTillUDrop</a>
			</div>
			<div class="collapse navbar-collapse" id="myNavbar">
				<ul class="nav navbar-nav">
					<li><a href="ProductsList">Products</a></li>
					
					<%
						if ((Boolean) session.getAttribute("loggedIn")) {
					%>
					<li><a href="ShoppingCart">Shopping Cart</a></li>
					<%
						}
					%>
					<%
						if ((Boolean) session.getAttribute("admin")) {
					%>
					<li><a href="AllPurchases">All Purchases</a></li>
					<%	
						}
					%>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<%
						if ((Boolean) session.getAttribute("loggedIn")) {
					%>
					<li><a href="index.jsp?logOut=true"><span
							class="glyphicon glyphicon-log-in"></span> Sign Out</a></li>
					<li><a href="EditProfile"><span
							class="glyphicon glyphicon-user"></span> Edit Profile</a></li>
					<%
						} else {
					%>
					<li><a href="SignUp"><span
							class="glyphicon glyphicon-user"></span> Sign Up</a></li>

					<li><a href="SignIn"><span
							class="glyphicon glyphicon-log-in"></span> Login</a></li>
					<%
						}
					%>
					
				</ul>
			</div>
		</div>
	</nav>

</body>
</html>
