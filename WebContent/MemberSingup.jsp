<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/js" var="JS_PATH"/>
<c:url value="/css" var="CSS_PATH"/>
<!DOCTYPE html >
<html>
<head>
	<meta http-equiv="Content-Language" content="zh-tw">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>MemberSingup</title>
	<link type="text/css" rel="stylesheet" href="${CSS_PATH}/bootstrap.min.css" />
	<link type="text/css" rel="stylesheet" href="${CSS_PATH}/signin.css" />
	<script type="text/javascript" src="${JS_PATH}/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${JS_PATH}/popper.min.js"></script>
	<script type="text/javascript" src="${JS_PATH}/bootstrap.min.js"></script>
	<script>
		// Example starter JavaScript for disabling form submissions if there are invalid fields
		(function() {
		  'use strict';
		  window.addEventListener('load', function() {
		    // Fetch all the forms we want to apply custom Bootstrap validation styles to
		    var forms = document.getElementsByClassName('needs-validation');
		    // Loop over them and prevent submission
		    var validation = Array.prototype.filter.call(forms, function(form) {
		      form.addEventListener('submit', function(event) {
		    	form.classList.add('was-validated');
		    	event.preventDefault(); // 關閉網頁預設表格送交跳頁行為
		        if (form.checkValidity() == false) {		          	
		          	event.stopPropagation();
		        }else{		        	
		        	$("#singupForm").submit();
		        }
		      }, false);
		    });
		  }, false);
		})();
		
		$(document).ready(function(){
			var msg='${requestScope.singupMsg}';
			
			if(msg!=""){
				 $("#msgShow").html(msg);
				 $('#exampleModalCenter').modal('show');				 
			}
		});
	</script>
</head>
<body>
	<form class="needs-validation" action="LoginAction.do" id="singupForm" method="post" >
	<input type="hidden" name="action" value="singup" >
	<div class="text-center">
		<img class="mb-4" src="https://getbootstrap.com/docs/4.0/assets/brand/bootstrap-solid.svg" alt="" width="72" height="72">
	    <h1 class="h3 mb-3 font-weight-normal">註冊</h1>
    </div>
		<div class="form-row">
   			<div class="form-group col-md-12 mx-auto">
   				<label for="id">帳號</label>
  				<input type="text" id="id" name="id" class="form-control" placeholder="請輸入帳號" required>
  				<div class="invalid-feedback">
   					請輸入帳號
 				</div>
   			</div>
   		</div>	        		
   		<div class="form-row">
   			<div class="form-group col-md-12 mx-auto">
   				<label for="pwd">密碼</label>
     			<input type="password" id="pwd" name="pwd" class="form-control" placeholder="請輸入密碼" required>
     			<div class="invalid-feedback">
   					請輸入密碼
 				</div>
   			</div>
   		</div>
   		<div class="form-row">
   			<div class="form-group col-md-12 mx-auto">
   				<label for="pwdCheck">確認密碼</label>
   				<input type="password" id="pwdCheck" name="pwdCheck" class="form-control" placeholder="請再次輸入密碼" required>
   				<div class="invalid-feedback">
 					請再次輸入帳號
 				</div>
   			</div>
   		</div>
   		<div class="form-row">
   			<div class="form-group col-md-12 mx-auto">
   				<label for="name">姓名</label>
     			<input type="text" id="name" name="name" class="form-control" placeholder="請輸入姓名" required>
     			<div class="invalid-feedback">
   					請輸入姓名
 				</div>
   			</div>
   		</div>
   		<div class="form-row">
        	<div class="form-group col-md-12 mx-auto">
          		<label for="updateSubmit"></label>
          		<input type="submit" value="註冊" class="btn btn-outline-dark form-control">
        	</div>
        </div>
        <a href="MemberLogin.jsp">
      		<p class="text-muted mx-auto">回登入頁</p>
      	</a>      	
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