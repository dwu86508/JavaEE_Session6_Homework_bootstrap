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
	<title>販賣機-後臺-新增商品</title>
	<script src="${JS_PATH}/jquery-1.11.1.min.js"></script>
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
		        	$("#addGoods").submit();
		        }
		      }, false);
		    });
		  }, false);
		})();
		
		$(document).ready(function(){
			var msg="<%=session.getAttribute("createMsg")%>";
			
			if(msg!="null"){
				 $("#msgShow").html(msg);
				 $('#exampleModalCenter').modal('show');				 
			}
		});
	</script>	
</head>
<body> 
	<%@ include file="BackEnd_FunMenu.jsp" %>
	<% session.removeAttribute("createMsg"); %>
	<div class="container">		
		<div class="form-row">
	        <div class="form-group col-md-6 mx-auto">		
				<h2>商品新增上架</h2>
			</div>
		</div>
		<form action="BackendServlet.do?action=addGoods" enctype="multipart/form-data" method="post" id="addGoods" class="needs-validation" novalidate>
			<input type="hidden" name ="goodsID" value="1" />
			<div class="form-row">
       			<div class="form-group col-md-6 mx-auto">
       				<label for="inputGoodsName">商品名稱</label>
         			<input type="text" id="goodsName" name="goodsName" size="10" class="form-control" placeholder="商品名稱" required>
         			<div class="invalid-feedback">
      					請輸入商品名稱
    				</div>
       			</div>
       		</div>
       		<div class="form-row">
       			<div class="form-group col-md-6 mx-auto">
       				<label for="inputGoodsPrice">商品價格</label>
         			<input type="text" id="goodsPrice" name="goodsPrice" size="10" class="form-control" placeholder="商品價格" required>
         			<div class="invalid-feedback">
      					請輸入商品價格
    				</div>
       			</div>
       		</div>	        		
			<div class="form-row">
       			<div class="form-group col-md-6 mx-auto">
       				<label for="inputGoodsQuantity">初始數量</label>
         			<input type="text" id="goodsQuantity" name="goodsQuantity" size="10" class="form-control" placeholder="初始數量" required>
         			<div class="invalid-feedback">
      					請輸入初始數量
    				</div>
       			</div>
       		</div>
       		<div class="form-row">
       			<div class="form-group col-md-6 mx-auto mb-3">
       				<label for="iformFile" class="form-label">商品圖片</label>
         			<input type="file" id="goodsImage" name="goodsImage" class="form-control" required>
         			<div class="invalid-feedback">
      					請選擇商品圖片
    				</div>
       			</div>
       		</div>			
			<div class="form-row">
       			<div class="form-group col-md-6 mx-auto">
	          		<label for="inputStatus">商品狀態</label>
	          		<select id="status" name="status" class="form-control" required>
	          			<option value="">----------請選擇----------</option>	          			
						<option value="1">上架</option>
						<option value="0">下架</option>				
			  		</select>
			  		<div class="invalid-feedback">
      					請選擇商品狀態
    				</div>
	        	</div>
			</div>
			<div class="form-row">
	        	<div class="form-group col-md-6 mx-auto">
	          		<label for="updateSubmit"></label>
	          		<input type="submit" value="送出" class="btn btn-outline-dark form-control">
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