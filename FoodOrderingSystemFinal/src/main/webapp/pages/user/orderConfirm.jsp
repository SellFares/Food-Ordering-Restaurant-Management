<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container mt-5 text-center mb-5">
    <div class="card">
        <div class="card-body py-5">
            <c:choose>
                <c:when test="${not empty order}">
                    <h3 class="text-success">&#9989; Order submitted successfully!</h3>
                    <p class="mt-3">Order number: ${order.orderNo}</p>
                    <p>Total paid: RM <fmt:formatNumber value="${order.totalAmount}" pattern="0.00"/></p>
                    <p>We will deliver for you as soon as possible. Please keep your phone line open</p>
                    <a href="${basePath}orderServlet?action=list" class="btn btn-primary mt-3">View my orders</a>
                    <a href="${basePath}pages/user/index.jsp" class="btn btn-outline-secondary mt-3">Return to Home</a>
                </c:when>
                <c:otherwise>
                    <h3 class="text-warning">No order to display</h3>
                    <p>Please place an order from your cart first.</p>
                    <a href="${basePath}menuServlet" class="btn btn-primary mt-3">Browse Menu</a>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>
