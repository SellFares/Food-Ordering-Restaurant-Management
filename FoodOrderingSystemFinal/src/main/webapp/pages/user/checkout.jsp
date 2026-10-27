<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container mt-4 mb-5">
    <h3>Order Settlement</h3>

    <c:if test="${not empty checkoutError}">
        <div class="alert alert-danger mt-3">${checkoutError}</div>
    </c:if>

    <c:choose>
        <c:when test="${empty sessionScope.cart}">
            <div class="alert alert-info mt-3">
                Your cart is empty. <a href="${basePath}menuServlet">Browse the menu</a> first.
            </div>
        </c:when>
        <c:otherwise>
            <c:set var="cartTotal" value="${0}" />
            <c:forEach var="entry" items="${sessionScope.cart}">
                <c:set var="cartTotal" value="${cartTotal + entry.value.subtotal}" />
            </c:forEach>
            <c:set var="deliveryFee" value="${3.00}" />

            <form action="${basePath}orderServlet" method="post" id="checkoutForm">
                <input type="hidden" name="action" value="submit">
                <div class="row mt-3">
                    <div class="col-md-8">
                        <div class="card">
                            <div class="card-header">Delivery Information</div>
                            <div class="card-body">
                                <div class="mb-3">
                                    <label class="form-label">Recipient</label>
                                    <input type="text" name="recipientName" class="form-control"
                                           placeholder="Please enter the recipient's name" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Contact number</label>
                                    <input type="text" name="recipientPhone" class="form-control"
                                           placeholder="Please enter your phone number" required>
                                </div>
                                <div class="mb-3">
                                    <label class="form-label">Delivery address</label>
                                    <textarea name="address" class="form-control" rows="2"
                                              placeholder="Please enter detailed address" required></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-4">
                        <div class="card">
                            <div class="card-header">Order Summary</div>
                            <div class="card-body">
                                <c:forEach var="entry" items="${sessionScope.cart}">
                                    <p class="d-flex justify-content-between mb-1">
                                        <span>${entry.value.dishName} x ${entry.value.quantity}</span>
                                        <span>RM <fmt:formatNumber value="${entry.value.subtotal}" pattern="0.00"/></span>
                                    </p>
                                </c:forEach>
                                <hr>
                                <p>Subtotal: RM <fmt:formatNumber value="${cartTotal}" pattern="0.00"/></p>
                                <p>Delivery Fee: RM <fmt:formatNumber value="${deliveryFee}" pattern="0.00"/></p>
                                <hr>
                                <h5 class="text-danger">Total: RM <fmt:formatNumber value="${cartTotal + deliveryFee}" pattern="0.00"/></h5>
                                <button type="submit" class="btn btn-success w-100 mt-3">Submit Order</button>
                            </div>
                        </div>
                    </div>
                </div>
            </form>
        </c:otherwise>
    </c:choose>
</div>

<%@ include file="../common/footer.jsp" %>
