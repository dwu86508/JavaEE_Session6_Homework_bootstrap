<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:url value="/js" var="JS_PATH" />
<c:url value="/css" var="CSS_PATH" />
 <link type="text/css" rel="stylesheet" href="${CSS_PATH}/bootstrap.min.css" />
 <script type="text/javascript" src="${JS_PATH}/jquery-3.2.1.min.js"></script>
 <script type="text/javascript" src="${JS_PATH}/popper.min.js"></script>
 <script type="text/javascript" src="${JS_PATH}/bootstrap.min.js"></script>
<nav class="navbar navbar-expand-lg navbar-dark  bg-dark">
  <h1 class="navbar-brand">Front End Service</h1>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>

  <div class="collapse navbar-collapse" id="navbarSupportedContent">
    <ul class="navbar-nav mr-auto">
      <li class="nav-item" id="GoodsList" value="123">      	
        <a class="nav-link" href="BackendServlet.do?action=queryGoods">後檯管理</a>
      </li>      
    </ul>
    
    <p class="navbar-brand" >
		Hello ${sessionScope.membersInf.membersName}!!!
	</p>
	<a href="LoginAction.do?action=logout" align="left">
		<button type="button" class="btn btn-light">登出</button>
	</a>

  </div>
</nav>