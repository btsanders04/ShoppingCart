<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

${display}

<div class="container">
  <h2>Credit Info</h2>
  <form class="form-horizontal" role="form" method = "post" action = "Confirmation">
    <div class="form-group">
      <label class="control-label col-sm-2" for="credit">Credit Card:</label>
      <div class="col-sm-10">
        <input type="text" class="form-control" id="credit" name = "credit" placeholder="${credit}">
      </div>
    </div>
    <div class="form-group">
      <label class="control-label col-sm-2" for="ccv">CCV:</label>
      <div class="col-sm-10">          
        <input type="text" class="form-control" id="ccv" name = "ccv" placeholder="Enter CCV">
      </div>
    </div>
     <div class="form-group">
      <label class="control-label col-sm-2" for="bill">Billing Address:</label>
      <div class="col-sm-10">          
        <input type="text" class="form-control" id="bill" name = "bill" placeholder="${bill}">
      </div>
    </div>
    </div>
     <div class="form-group">
      <label class="control-label col-sm-2" for="ship">Shipping Address:</label>
      <div class="col-sm-10">          
        <input type="text" class="form-control" id="ship" name = "ship" placeholder="${ship}">
      </div>
    </div>
    <div class="form-group">        
      <div class="col-sm-offset-2 col-sm-10">
        <div class="checkbox">
          <label><input type="checkbox" name = "remember"> Remember me</label>
        </div>
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