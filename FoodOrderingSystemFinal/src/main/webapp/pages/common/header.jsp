<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName()
            + ":" + request.getServerPort() + request.getContextPath() + "/";
    pageContext.setAttribute("basePath", basePath);
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Ordering System</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha3/dist/css/bootstrap.min.css" rel="stylesheet">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/common.css">
</head>
<body class="d-flex flex-column min-vh-100">

<nav class="navbar navbar-expand-lg navbar-dark custom-navbar">
    <div class="container">
        <a class="navbar-brand" href="${basePath}pages/user/index.jsp">Food Home</a>
        <div class="collapse navbar-collapse">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="${basePath}pages/user/index.jsp">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="${basePath}menuServlet">Menu</a></li>
                <li class="nav-item"><a class="nav-link" href="${basePath}pages/common/contact.jsp">Contact</a></li>
            </ul>
            <ul class="navbar-nav">
                <c:if test="${empty loginUser}">
                    <li class="nav-item"><a class="nav-link" href="${basePath}pages/user/login.jsp">Login</a></li>
                    <li class="nav-item"><a class="nav-link" href="${basePath}pages/user/register.jsp">Register</a></li>
                    <li class="nav-item"><a class="nav-link" href="${basePath}pages/admin/adminLogin.jsp">Admin</a></li>
                </c:if>
                <c:if test="${not empty loginUser}">
                    <li class="nav-item"><span class="nav-link">Welcome, ${loginUser.username}</span></li>
                    <li class="nav-item"><a class="nav-link" href="${basePath}pages/user/cart.jsp">Cart<c:if test="${not empty sessionScope.cart}"> (${sessionScope.cart.size()})</c:if></a></li>
                    <li class="nav-item"><a class="nav-link" href="${basePath}orderServlet?action=list">My Orders</a></li>
                    <li class="nav-item"><a class="nav-link" href="${basePath}logoutServlet">Logout</a></li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>