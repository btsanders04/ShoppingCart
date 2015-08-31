<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>SignIn</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<jsp:include page="NavBar.jsp"/>

	<div class="container">
		<h2>Login</h2>
		<form class="form-horizontal" role="form" action="SignIn"
			method="POST">
			<div class="form-group">
				<label class="control-label col-sm-2" for="">Email:</label>
				<div class="col-sm-10">
					<input type="email" class="form-control" id="email" name="email"
						placeholder="Enter email">
				</div>
			</div>
			<div class="form-group">
				<label class="control-label col-sm-2" for="pwd">Password:</label>
				<div class="col-sm-10">
					<input type="password" class="form-control" id="pwd" name="pwd"
						placeholder="Enter password">
				</div>
			</div>
			<div class="form-group">
				<div class="col-sm-offset-2 col-sm-10">
					<button type="submit" class="btn btn-default">Submit</button>
				</div>
			</div>
		</form>
	</div>


</body>
</html>