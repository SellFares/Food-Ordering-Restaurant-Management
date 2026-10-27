<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<main class="container py-5">
<div class="container mt-4">
    <div class="card bg-dark text-white mb-5">
        <img src="${pageContext.request.contextPath}/images/banner.jpg" class="card-img banner-img" alt="banner">
        <div class="card-img-overlay d-flex align-items-center">
            <div class="ms-5">
                <h1 class="display-4">Delicious Cuisine,Swift Delivery</h1>
                <p text-white">Enjoy the best local restaurants with quick and easy online ordering</p>
                <a href="${basePath}menuServlet" class="btn btn-primary btn-lg">Order Now</a>
            </div>
        </div>
    </div>
</div>
</main>
<%@ include file="../common/footer.jsp" %>