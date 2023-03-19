<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:url value="/" var="WEB_PATH"/>
<c:url value="/js" var="JS_PATH"/>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Language" content="zh-tw">
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
		<title>販賣機-後臺-商品維護</title>
		<script src="${JS_PATH}/jquery-1.11.1.min.js"></script>
		<script src="${JS_PATH}/updateGoods.js"></script>	
	</head>
	<body>
		<%@ include file="BackEnd_FunMenu.jsp" %>		
			<div class="container">		
				<div class="form-row">
	        		<div class="form-group col-md-6 mx-auto">		
						<h2 class="mx-auto">商品維護作業</h2>
					</div>
				</div>				
		    	<form id="updateGoods">		    		
		    		<div class="form-row">
	        			<div class="form-group col-md-6 mx-auto">
	        				<label for="inputgoodsID">飲料名稱</label>
	          				<select id="goodsID" name="goodsID" class="form-control">
					          	<option value="">----- 請選擇 -----</option>
								<c:forEach items="${goodsLists}" var="goodsList">
									<option value="<c:out value="${goodsList.goodsID}"/>">
										<c:out value="${goodsList.goodsName}"/> 
									</option>
								</c:forEach>				
							</select>
	        			</div>
	        		</div>
	        		<div class="form-row">
	        			<div class="form-group col-md-6 mx-auto">
	        				<label for="inputgoodsID">更改價格</label>
	          				<input type="number" id="goodsPrice" name="goodsPrice" size="5" min="0" class="form-control">
	        			</div>
	        		</div>	        		
	        		<div class="form-row">
	        			<div class="form-group col-md-6 mx-auto">
	        				<label for="inputGoodsQuantityShow">商品庫存</label>
	          				<div id="goodsQuantityShow" ></div>
	        			</div>
	        		</div>
	        		<div class="form-row">
	        			<div class="form-group col-md-6 mx-auto">
	        				<label for="inputGoodsQuantity">補貨數量</label>
	          				<input type="number" id="goodsQuantity" name="goodsQuantity" size="5" min="0" class="form-control">
	        			</div>
	        		</div>
	        		<div class="form-row">
	        			<div class="form-group col-md-6 mx-auto">
			          		<label for="inputStatus">商品狀態</label>
			          		<select id="status" name="status" class="form-control">
			          			<option value="">----- 請選擇 -----</option>
								<option value="1">上架</option>
								<option value="0">下架</option>				
					  		</select>
			        	</div>
			        </div>
			        <div class="form-row">
			        	<div class="form-group col-md-6 mx-auto">
			          		<label for="updateSubmit">　</label>
			          		<input type="button" value="修改" onclick="submitCheck()" class="btn btn-outline-dark form-control" >
			        	</div>
			        </div>
				</form>
			</div>						
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