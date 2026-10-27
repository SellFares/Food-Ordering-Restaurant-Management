<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container mt-4 mb-5">
    <h3>Order Details</h3>

    <c:choose>
        <c:when test="${empty order}">
            <div class="alert alert-warning mt-3">Order not found.</div>
        </c:when>
        <c:otherwise>
            <div class="card mt-3">
                <div class="card-header d-flex justify-content-between">
                    <span>Order No: ${order.orderNo}</span>
                    <span class="badge bg-secondary">${order.statusText}</span>
                </div>
                <div class="card-body">
                    <p><strong>Placed on:</strong> <fmt:formatDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm"/></p>
                    <p><strong>Recipient:</strong> ${order.recipientName} (${order.recipientPhone})</p>
                    <p><strong>Delivery Address:</strong> ${order.address}</p>

                    <table class="table table-bordered mt-3">
                        <thead class="table-light">
                            <tr><th>Dish</th><th>Price</th><th>Quantity</th><th>Subtotal</th></tr>
                        </thead>
                        <tbody>
                        <c:forEach var="item" items="${order.items}">
                            <tr>
                                <td>${item.dishName}</td>
                                <td>RM <fmt:formatNumber value="${item.price}" pattern="0.00"/></td>
                                <td>${item.quantity}</td>
                                <td>RM <fmt:formatNumber value="${item.subtotal}" pattern="0.00"/></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>

                    <div class="text-end">
                        <p>Subtotal: RM <fmt:formatNumber value="${order.subtotal}" pattern="0.00"/></p>
                        <p>Delivery Fee: RM <fmt:formatNumber value="${order.deliveryFee}" pattern="0.00"/></p>
                        <h5 class="text-danger">Total: RM <fmt:formatNumber value="${order.totalAmount}" pattern="0.00"/></h5>
                    </div>
                </div>
            </div>
            <a href="${basePath}orderServlet?action=list" class="btn btn-outline-secondary mt-3">Back to My Orders</a>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="../common/footer.jsp" %>
