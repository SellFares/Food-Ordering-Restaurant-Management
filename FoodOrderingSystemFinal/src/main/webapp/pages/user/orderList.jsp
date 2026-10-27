<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container mt-4 mb-5">
    <h3>My Orders</h3>

    <c:choose>
        <c:when test="${empty orders}">
            <div class="alert alert-info mt-3">
                You have no orders yet. <a href="${basePath}menuServlet">Browse the menu</a> to place one.
            </div>
        </c:when>
        <c:otherwise>
            <table class="table table-bordered mt-3 align-middle">
                <thead class="table-light">
                    <tr>
                        <th>Order No.</th>
                        <th>Placed On</th>
                        <th>Total</th>
                        <th>Status</th>
                        <th>Operation</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="o" items="${orders}">
                    <tr>
                        <td>${o.orderNo}</td>
                        <td><fmt:formatDate value="${o.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                        <td>RM <fmt:formatNumber value="${o.totalAmount}" pattern="0.00"/></td>
                        <td><span class="badge bg-secondary">${o.statusText}</span></td>
                        <td>
                            <a href="${basePath}orderServlet?action=detail&orderNo=${o.orderNo}"
                               class="btn btn-sm btn-outline-primary">View Details</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="../common/footer.jsp" %>
