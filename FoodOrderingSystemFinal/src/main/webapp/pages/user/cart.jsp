<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container mt-4 mb-5">
    <h3>My Shopping Cart</h3>

    <c:if test="${not empty param.cartError}">
        <div class="alert alert-danger mt-3">${param.cartError}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty sessionScope.cart}">
            <div class="alert alert-info mt-3">
                Your cart is empty. <a href="${basePath}menuServlet">Browse the menu</a> to add some dishes.
            </div>
        </c:when>
        <c:otherwise>
            <c:set var="cartTotal" value="${0}" />
            <table class="table table-bordered mt-3 align-middle">
                <thead class="table-light">
                    <tr>
                        <th>Dish</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Subtotal</th>
                        <th>Operation</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="entry" items="${sessionScope.cart}">
                    <c:set var="item" value="${entry.value}" />
                    <c:set var="cartTotal" value="${cartTotal + item.subtotal}" />
                    <tr>
                        <td>
                            <img src="${pageContext.request.contextPath}/images/${item.image}"
                                 alt="${item.dishName}" style="width:50px;height:50px;object-fit:cover;" class="me-2 rounded">
                            ${item.dishName}
                        </td>
                        <td>RM <fmt:formatNumber value="${item.price}" pattern="0.00"/></td>
                        <td style="width:220px;">
                            <form action="${basePath}cartServlet" method="post" class="d-flex align-items-center gap-1">
                                <input type="hidden" name="action" value="update">
                                <input type="hidden" name="dishId" value="${item.dishId}">
                                <input type="number" name="qty" value="${item.quantity}" min="1" class="form-control form-control-sm" style="width:70px">
                                <button type="submit" class="btn btn-sm btn-outline-secondary">Update</button>
                            </form>
                        </td>
                        <td>RM <fmt:formatNumber value="${item.subtotal}" pattern="0.00"/></td>
                        <td>
                            <form action="${basePath}cartServlet" method="post">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="dishId" value="${item.dishId}">
                                <button type="submit" class="btn btn-sm btn-danger">Delete</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div class="text-end">
                <h5>Total: RM <fmt:formatNumber value="${cartTotal}" pattern="0.00"/></h5>
                <a href="${basePath}pages/user/checkout.jsp" class="btn btn-primary">Checkout</a>
            </div>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="../common/footer.jsp" %>
