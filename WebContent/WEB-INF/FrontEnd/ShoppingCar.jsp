<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:url value="/" var="WEB_PATH"/>
<c:url value="/js" var="JS_PATH"/>
<c:url value="/css" var="CSS_PATH" />
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Language" content="zh-tw">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>販賣機</title>
	<script src="${JS_PATH}/jquery-1.11.1.min.js"></script>	
	<script src="${JS_PATH}/shoppingCar.js"></script>
	<link type="text/css" rel="stylesheet" href="${CSS_PATH}/bootstrap.min.css" />
	<link rel="stylesheet" href="https://fonts.googleapis.com/css2?family=Material+Symbols+Sharp:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200" />
	<script type="text/javascript" src="${JS_PATH}/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${JS_PATH}/popper.min.js"></script>
	<script type="text/javascript" src="${JS_PATH}/bootstrap.min.js"></script>
	<script type="text/javascript">		
		$(document).ready(function() {
			//購物車明細					
			$.ajax({
				  url: 'ShoppingCarServlet.do?action=queryCartGoods', // 指定要進行呼叫的位址
				  type: "GET", // 請求方式 POST/GET				 
				  dataType :'JSON', // Server回傳的資料類型
				  
				  success: function(addRtn) {// 請求成功時執行函式				   	
				  	carShow(addRtn);
				  },
				  error: function(error) { // 請求發生錯誤時執行函式
				  	alert("Ajax Error!");
				  }
			});
		});
	</script>
</head>
<body>	
	<%@ include file="FrontEnd_FunMenu.jsp" %>
	<div class="container">
		<div class="row">			
			<div class="col-md-3 my-lg-3">
				<div class="row justify-content-center">
					<img border="0" src="DrinksImage/coffee.jpg" width="200" height="200" >
					<h4>歡迎光臨，Tomcat！</h4>
				</div>			    
			    <div class="form-row py-1">
			    	<div class="col-md-12">
			    	<a class="btn btn-outline-secondary form-control" href="FrontendServlet.do?action=buyGoodsView">繼續購物</a>
<!-- 		    			<input  type="button" onclick="checkoutWindows()" value="結帳"> -->
		    		</div>
		    	</div>
			</div>			
			<div class="col-md-9 row justify-content-center my-lg-3">
	            <div class="col-md-12">
	                <div class="accordion" id="accordionExample">                    
	                    <div id="collapseOne" class="collapse show " aria-labelledby="headingOne" data-parent="#accordionExample">
	                        
	                    </div>
	                    <div class="form-row py-1" id="checkout" >
					    	<div class="col-md-12">
				    			<input class="btn btn-outline-secondary form-control" type="button" onclick="checkoutWindows()" value="結帳">
				    		</div>
				    	</div>
	                </div>
               	</div>
            </div>			
		</div>
	</div>	
	<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	    <div class="modal-dialog modal-dialog-centered" role="document">
	        <div class="modal-content">
	        	<div class="modal-header">
	            	<h5 class="modal-title" id="exampleModalLongTitle"></h5>
	            	<div id="checkoutClose">
	            		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
	              			<span aria-hidden="true">&times;</span>
	            		</button>
            		</div>
	          	</div>
	          	<div class="modal-body" id="msgShow"></div>
	          	<div class="modal-footer">
	          		<div id="checkoutCf">
	            		<button type="button" class="btn btn-primary" data-dismiss="modal" id="btnCf"></button>
	            	</div>
	          	</div>
	        </div>
	    </div>
	</div>	
</body>
</html>