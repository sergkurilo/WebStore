<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="jspf/lang_bundle.jspf" %>
<%@include file="jspf/main_fmt.jspf" %>
<html>
<head>
    <title>Welcome</title>
    <c:set scope="session" var="url" value="/jsp/basket.jsp"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/modern-business.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand">Shop</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <%@include file="jspf/change_lang.jspf" %>
                <%@include file="jspf/logout.jspf" %>
            </ul>
        </div>
    </div>
</nav>
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            <h1 class="page-header">
                Basket <c:if test="${sessionScope.basket eq null}">is empty</c:if>
            </h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">

            <div class="row">
                <c:if test="${not empty sessionScope.basket}">
                    <table class="table table-condensed">
                        <thead>
                        <tr>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Description</th>
                            <th>Amount</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${sessionScope.basket}">
                        <tr>
                            <td><c:out value="${item.name}"/></td>
                            <td><c:out value="${item.price}"/></td>
                            <td><c:out value="${item.description}"/></td>
                            <td><c:out value="${sessionScope.amount_map[item.productId]}"/></td>
                        </tr>
                        </c:forEach>
                        <tbody>
                    </table>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="make_order"/>
                        <button type="submit" class="btn btn-warning">Make Order</button>
                    </form>
                </c:if>
            </div>

        </div>

    </div>
    <hr>
    <!-- Footer -->
    <footer>
        <div class="row">
            <div class="col-lg-12">
                <p align="center">${copyright}</p>
            </div>
        </div>
    </footer>


</div>


</body>
</html>