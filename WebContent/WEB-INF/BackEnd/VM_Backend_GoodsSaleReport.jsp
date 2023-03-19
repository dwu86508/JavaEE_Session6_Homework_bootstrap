<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Language" content="zh-tw">
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<title>販賣機-後臺-銷售報表</title>	
</head>
<body>
	<%@ include file="BackEnd_FunMenu.jsp" %>
	<div class="container">		
		<div class="form-row">
       		<div class="form-group col-md-6 mx-auto">		
				<h2 class="mx-auto">銷售報表</h2>
			</div>
		</div>
		<form action="BackendServlet.do" method="get">
			<input type="hidden" name="action" value="querySalesReport"/>
			<div class="form-row">
       			<div class="form-group col-md-4 mx-auto">
       				<label for="inputStartDate">起日</label>
         			<input type="date" name="queryStartDate" value="${param.queryStartDate}" class="form-control">
       			</div>
       			<div class="form-group col-md-4 mx-auto">
       				<label for="inputStartDate">迄日</label>
         			<input type="date" name="queryEndDate" value="${param.queryEndDate}" class="form-control">
       			</div>
       			<div class="form-group col-md-4">
	          		<label for="inputGoodsSearchBtn">　</label>
	          		<input type="submit" name="goodsSearchBtn" value="查詢" onclick="goodsSearch()" class="btn btn-outline-dark form-control" >
	        	</div>
       		</div>
		</form>
		<table class="table table-striped">
			<c:choose>
				<c:when test="${not empty reports}" >
					<thead id="goodsShowHeader" class="thead-dark" align="center">						
						<th scope="col"><b>訂單編號</b></th>
						<th scope="col"><b>顧客姓名</b></th>
						<th scope="col"><b>購買日期</b></th>
						<th scope="col"><b>飲料名稱</b></th> 
						<th scope="col"><b>購買單價</b></th>
						<th scope="col"><b>購買數量</b></th>
						<th scope="col"><b>購買金額</b></th>
					</thead>
				</c:when>
				<c:otherwise>
					無資料
				</c:otherwise>
			</c:choose>		
			<tbody id="goodsShow">
				<c:forEach items="${reports}" var="report">
				<tr height="30"  align="center">
					<td>${report.orderID}</td>
					<td>${report.customerName}</td>
					<td>${report.orderDate}</td>
					<td>${report.goodsName}</td>
					<td><fmt:formatNumber value="${report.goodsBuyPrice}" type="number" pattern="$ #,###"/></td>				
					<td>${report.buyQuantity}</td>					
					<td><fmt:formatNumber value="${report.buyAmount}" type="number" pattern="$ #,###"/></td>
				</tr>				
			</c:forEach>		    
			</tbody>
		</table>
	</div>
</body>
</html>