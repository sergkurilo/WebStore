<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="jspf/lang_bundle.jspf" %>
<%@include file="jspf/main_fmt.jspf" %>
<html>
<head>
    <title>Welcome</title>
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
                Users
            </h1>
        </div>
    </div>
    <div class="row">
        <div class="col-md-12">

            <div class="row">
                <table class="table table-condensed">
                    <thead>
                    <tr>
                        <th>Login</th>
                        <th>Firstname</th>
                        <th>Lastname</th>
                        <th>Blacklist</th>
                        <th>Address</th>
                        <th>Email</th>
                        <th></th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="users" items="${users}">
                    <tr>
                        <td><c:out value="${users.login}"/></td>
                        <td><c:out value="${users.firstName}"/></td>
                        <td><c:out value="${users.lastName}"/></td>
                        <td><c:out value="${users.blackList}"/></td>
                        <td><c:out value="${users.address}"/></td>
                        <td><c:out value="${users.email}"/></td>
                        <td style="width: 110px; padding-bottom: 0px">
                            <%@include file="jspf/users_blacklist_forms.jspf" %>
                        </td>
                        <td style="width: 70px; padding-bottom: 0px">
                            <%@include file="jspf/users_delete_form.jspf" %>
                        </td>
                    </tr>
                    </c:forEach>
                    <tbody>
                </table>

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