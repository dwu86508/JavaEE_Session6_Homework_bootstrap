<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/js" var="JS_PATH"/>
<c:url value="/css" var="CSS_PATH"/>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Language" content="zh-tw">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>MemberLogin</title>
	<link type="text/css" rel="stylesheet" href="${CSS_PATH}/bootstrap.min.css" />
	<link type="text/css" rel="stylesheet" href="${CSS_PATH}/signin.css" />
	<script type="text/javascript" src="${JS_PATH}/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${JS_PATH}/popper.min.js"></script>
	<script type="text/javascript" src="${JS_PATH}/bootstrap.min.js"></script>
	<script>		
		$(document).ready(function(){
			var msg='${requestScope.loginMsg}';
			
			if(msg!=""){
				 $("#msgShow").html(msg);
				 $('#exampleModalCenter').modal('show');				 
			}
		});
	</script> 
</head>
<body class="text-center">
	<form class="form-signin" action="LoginAction.do" method="post">
		<input type="hidden" name="action" value="login"/>
      	<img class="mb-4" src="https://getbootstrap.com/docs/4.0/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">
      	<h1 class="h3 mb-3 font-weight-normal">請登入</h1>
      	<label for="inputEmail" class="sr-only">帳號</label>
      	<input type="text" id="inputText" name="id" class="form-control" placeholder="帳號" required autofocus>
      	<label for="inputPassword" class="sr-only">密碼</label>
      	<input type="password" id="inputPassword" name="pwd" class="form-control" placeholder="密碼" required>      
      	<button class="btn btn-lg btn-primary btn-block" type="submit">登入</button>
      	<a href="MemberSingup.jsp">
      		<p class="text-muted mx-auto">還沒有帳號?立即註冊</p>
      	</a>
      	<p class="mt-5 mb-3 text-muted">&copy; 2017-2018</p>
    </form>
	<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	    <div class="modal-dialog modal-dialog-centered" role="document">
	        <div class="modal-content">
	        	<div class="modal-header">
	            	<h5 class="modal-title" id="exampleModalLongTitle">系統訊息</h5>
            		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
              			<span aria-hidden="true">&times;</span>
            		</button>
	          	</div>
	          	<div class="modal-body" id="msgShow"></div>
	          	<div class="modal-footer">
	            	<button type="button" class="btn btn-primary" data-dismiss="modal">確定</button>
	          	</div>
	        </div>
	    </div>
	</div>	
</body>
</html>