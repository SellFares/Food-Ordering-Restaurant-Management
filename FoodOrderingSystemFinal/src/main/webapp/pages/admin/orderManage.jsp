<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="activePage" value="order" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Order Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <%@ include file="adminSidebar.jsp" %>
        <div class="col-md-10 py-4">
            <h3>Order Management</h3>

            <c:if test="${not empty selectedOrder}">
                <div class="card mb-4">
                    <div class="card-header d-flex justify-content-between">
                        <span>Order ${selectedOrder.orderNo} - customer #${selectedOrder.userId}</span>
                        <a href="${pageContext.request.contextPath}/admin/orderServlet" class="btn btn-sm btn-outline-secondary">Close</a>
                    </div>
                    <div class="card-body">
                        <p><strong>Recipient:</strong> ${selectedOrder.recipientName} (${selectedOrder.recipientPhone})</p>
                        <p><strong>Address:</strong> ${selectedOrder.address}</p>
                        <p><strong>Placed on:</strong> <fmt:formatDate value="${selectedOrder.createTime}" pattern="yyyy-MM-dd HH:mm"/></p>

                        <table class="table table-sm table-bordered">
                            <thead><tr><th>Dish</th><th>Price</th><th>Qty</th><th>Subtotal</th></tr></thead>
                            <tbody>
                            <c:forEach var="item" items="${selectedOrder.items}">
                                <tr>
                                    <td>${item.dishName}</td>
                                    <td>RM <fmt:formatNumber value="${item.price}" pattern="0.00"/></td>
                                    <td>${item.quantity}</td>
                                    <td>RM <fmt:formatNumber value="${item.subtotal}" pattern="0.00"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <p class="text-end"><strong>Total: RM <fmt:formatNumber value="${selectedOrder.totalAmount}" pattern="0.00"/></strong></p>

                        <form action="${pageContext.request.contextPath}/admin/orderServlet" method="get" class="d-flex align-items-center gap-2">
                            <input type="hidden" name="action" value="updateStatus">
                            <input type="hidden" name="id" value="${selectedOrder.id}">
                            <label class="form-label mb-0">Update status:</label>
                            <select name="status" class="form-select" style="width:200px">
                                <option value="0" ${selectedOrder.status == 0 ? 'selected' : ''}>Pending</option>
                                <option value="1" ${selectedOrder.status == 1 ? 'selected' : ''}>Confirmed</option>
                                <option value="2" ${selectedOrder.status == 2 ? 'selected' : ''}>Delivering</option>
                                <option value="3" ${selectedOrder.status == 3 ? 'selected' : ''}>Completed</option>
                                <option value="4" ${selectedOrder.status == 4 ? 'selected' : ''}>Cancelled</option>
                            </select>
                            <button type="submit" class="btn btn-primary">Update</button>
                        </form>
                    </div>
                </div>
            </c:if>

            <table class="table table-bordered align-middle">
                <thead class="table-light">
                    <tr><th>Order No.</th><th>User ID</th><th>Placed On</th><th>Total</th><th>Status</th><th>Operation</th></tr>
                </thead>
                <tbody>
                <c:forEach var="o" items="${orders}">
                    <tr>
                        <td>${o.orderNo}</td>
                        <td>${o.userId}</td>
                        <td><fmt:formatDate value="${o.createTime}" pattern="yyyy-MM-dd HH:mm"/></td>
                        <td>RM <fmt:formatNumber value="${o.totalAmount}" pattern="0.00"/></td>
                        <td><span class="badge bg-secondary">${o.statusText}</span></td>
                        <td>
                            <a href="${pageContext.request.contextPath}/admin/orderServlet?action=detail&id=${o.id}"
                               class="btn btn-sm btn-outline-primary">View / Update</a>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>
</body>
</html>
