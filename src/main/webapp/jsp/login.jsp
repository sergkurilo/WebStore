<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@include file="jspf/lang_bundle.jspf" %>
<%@include file="jspf/login_fmt.jspf" %>
<html>
<head><title>${head_login}</title>
    <c:set scope="session" var="url" value="/jsp/login.jsp"/>
    <link href="${pageContext.request.contextPath}/css/bootstrap.min.css" rel="stylesheet">
    <link href="${pageContext.request.contextPath}/css/modern-business.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <a class="navbar-brand">${shop}</a>
        </div>
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="language">
                        <input type="hidden" name="locale" value="ru">
                        <button type="submit" class="btn btn-link">${ru_button}</button>
                    </form>
                </li>
                <li>
                    <form action="controller" method="post">
                        <input type="hidden" name="command" value="language">
                        <input type="hidden" name="locale" value="en">
                        <button type="submit" class="btn btn-link">${en_button}</button>
                    </form>
                </li>
            </ul>
        </div>
    </div>
</nav>
<section class="container">
    <div class="login">
        <h1>${logstore}</h1>

        <form method="post" action="controller">
            <input type="hidden" name="command" value="login"/>

            <p><input type="text" name="login" value="" placeholder="${log}"></p>

            <p><input type="password" name="password" value="" placeholder="${pass}"></p>

            <p class="remember_me">
                <label>
                    <c:if test="${not empty message }">
                        <fmt:message key="${message}"/>
                    </c:if>

                </label>
            </p>

            <p class="submit"><input type="submit" name="commit" value="${Log}"></p>
        </form>
    </div>

    <div class="login-help">
        <p><a href="jsp/signup.jsp">${register}</a>.</p>
    </div>

</section>
</body>
</html>
