<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url value="/js" var="JS_PATH"/>
<c:url value="/" var="WEB_PATH"/>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Language" content="zh-tw">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>販賣機-後臺-商品列表</title>
		<script src="${JS_PATH}/jquery-1.11.1.min.js"></script>
		<script src="${JS_PATH}/goodsList.js"></script>
	</head>
	<body>
		<div class="container-lg">
			<input type="hidden" id="pageID" value="GoodsList">
			<%@ include file="BackEnd_FunMenu.jsp" %>		
			<div class="container-fluid">
				<br/><h2>商品列表</h2>
			    <form id="goodsSearchFrom">
<!-- 			    <input type="hidden" name="page" id="page" value="1"> -->
			    	<div class="form-row">
				    	<div class="form-group col-md-6">
				        	<label for="inputGoodsID">商品編號</label>
				        	<input type="number" name="goodsID" class="form-control" id="inputGoodsID" placeholder="商品編號">          
				        </div>
				        <div class="form-group col-md-6">
				        	<label for="inputGoodsName">商品名稱(不區分大小寫)</label>
				        	<input type="text" name="goodsName" class="form-control" id="inputGoodsName" placeholder="商品名稱(不區分大小寫)">
				        </div>
			      	</div>
			      	<div class="form-row">
			        	<div class="form-group col-md-4">
			          		<label for="inputLowestPrice">商品價格最低價</label>
			          		<input type="number" name="lowestPrice" class="form-control" id="inputLowestPrice" placeholder="商品價格最低價">          
			        	</div>
			        	<div class="form-group col-md-4">
					        <label for="inputHighestPrice">商品價格最高價</label>
					        <input type="number" name="highestPrice" class="form-control" id="inputHighestPrice" placeholder="商品價格最高價">
			        	</div>
			        	<div class="form-group col-md-4">
			          		<label for="inputPriceOrderby">價格排序</label>
			          		<select id="inputPriceOrderby" name="priceOrderby" class="form-control">
			            		<option value="0">無</option>
								<option value="asc">由低到高</option>
								<option value="desc">由高到低</option>
			          		</select>
			        	</div>
			      	</div>
			      	<div class="form-row">
			        	<div class="form-group col-md-4">
			          		<label for="inputGoodsQuantity">商品低於庫存量</label>
			          		<input type="number" name="goodsQuantity" class="form-control" id="inputGoodsQuantity" placeholder="商品低於庫存量">          
			        	</div>
			        	<div class="form-group col-md-4">
			          		<label for="inputSstatus">商品狀態</label>
			          		<select id="inputSstatus" name="status" class="form-control">
			          			<option value="2">ALL</option>
								<option value="1">上架</option>
								<option value="0">下架</option>				
					  		</select>
			        	</div>
			        	<div class="form-group col-md-4">
			          		<label for="inputGoodsSearchBtn">　</label>
			          		<input type="button" name="goodsSearchBtn" value="查詢" onclick="goodsSearch()" class="btn btn-outline-dark form-control" >
			        	</div>        
			    	</div>
			    </form>
				<div id="noGoodsShow"></div>
				<table class="table table-striped">
					<thead id="goodsShowHeader" class="thead-dark">		    
					</thead>
					<tbody id="goodsShow">		    
					</tbody>
				</table>		  
				<ul class="pagination" id="pageShow">    
				</ul>
			 </div>
		</div>
	</body>
</html>