<!DOCTYPE html>
<html lang="en">
<head>
<title>Gulp!</title>
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
<%if(session.getAttribute("loggedIn")==null){
		session.setAttribute("loggedIn", false); 
		session.setAttribute("admin",false);
}

if(request.getParameter("logOut")!= null){
	if(request.getParameter("logOut").equals("true")){
			session.setAttribute("admin",false);
			session.setAttribute("loggedIn",false);}}%>
<jsp:include page="NavBar.jsp"/>

<div class="container">
  <div class="jumbotron">
    <h1>Welcome To ShopTillUDrop!</h1>      
   </div>
</div>
${logIn}
</body>
</html>
