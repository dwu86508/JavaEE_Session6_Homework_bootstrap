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
	<script src="${JS_PATH}/pageSearch.js"></script>	
	<link type="text/css" rel="stylesheet" href="${CSS_PATH}/bootstrap.min.css" />
	<script type="text/javascript" src="${JS_PATH}/jquery-3.2.1.min.js"></script>
	<script type="text/javascript" src="${JS_PATH}/popper.min.js"></script>
	<script type="text/javascript" src="${JS_PATH}/bootstrap.min.js"></script>
</head>
<body>
	<input type="hidden" id="pageID" value="GoodsList">
	<%@ include file="FrontEnd_FunMenu.jsp" %>
	<div class="container">
		<div class="row">			
			<div class="col-md-3 my-lg-3">
				<div class="row justify-content-center">
					<img border="0" src="DrinksImage/coffee.jpg" width="200" height="200" >
					<h4>歡迎光臨，Tomcat！</h4>
				</div>
				 <div class="form-row py-1">
				 	<input type="hidden" name="pageNo" id="pageNo" value="1"/>
			    	<div class="col-md-8">	
			      		<input class="form-control" type="search" id="searchKeyword" placeholder="Search" aria-label="Search">
			      	</div>
			      	<div class="col-md-4">
			      		<input class="btn btn-outline-success form-control" type="button" id="search"  value="查詢" />		      		
			    	</div>
			    </div>			    
			    <div class="form-row py-1">
			    	<div class="col-md-6">			    
			    		<a href="FrontendServlet.do?action=shoppingCarView">
			    			<button class="btn btn-secondary form-control" id="shoppingCarBtn" name="shoppingCarBtn">購物車(${fn:length(sessionScope.shoppingCar)})</button>
			    		</a>
			    	</div>
			    	<div class="col-md-6">
			    		<button class="btn btn-secondary form-control" id="cleanShoppingCar" name="cleanShoppingCar">清空購物車</button>
			    	</div>
			    </div>			    
			</div>			
			<div class="col-md-9">				
				<div class="row justify-content-center" id="goodsShow">
			 	</div>
			 	<br>
			 	<div class="row justify-content-end px-5">
					<ul class="pagination" id="pageShow">    
					</ul>			
				</div>		 	
			</div>				
		</div>
	</div>	
	<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
	    <div class="modal-dialog modal-dialog-centered" role="document">
	        <div class="modal-content">
	        	<div class="modal-header">
	            	<h5 class="modal-title" id="exampleModalLongTitle"></h5>
            		<button type="button" class="close" data-dismiss="modal" aria-label="Close">
              			<span aria-hidden="true">&times;</span>
            		</button>
	          	</div>
	          	<div class="modal-body" id="msgShow"></div>
	          	<div class="modal-footer">
	            	<button type="button" class="btn btn-primary" data-dismiss="modal" id="btnCf"></button>
	          	</div>
	        </div>
	    </div>
	</div>	
</body>
</html>