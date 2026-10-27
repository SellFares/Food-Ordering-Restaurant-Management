<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>

<div class="container mt-4 mb-5">
    <div class="row">

        <div class="col-md-2">
		    <div class="list-group">
		        <a href="${basePath}menuServlet"
		           class="list-group-item list-group-item-action ${empty selectedCategoryId ? 'active' : ''}">All Dishes</a>
		        <c:forEach var="cat" items="${categories}">
		            <a href="${basePath}menuServlet?categoryId=${cat.id}"
		               class="list-group-item list-group-item-action ${selectedCategoryId == cat.id ? 'active' : ''}">${cat.name}</a>
		        </c:forEach>
		    </div>
		</div>

        <div class="col-md-10">
            <h3 class="mb-3">Dish List</h3>
            <div class="row">
                <c:if test="${empty dishes}">
                    <p class="text-muted">No dishes in this category yet.</p>
                </c:if>
                <c:forEach var="d" items="${dishes}">
                    <div class="col-md-3 col-sm-6 mb-4">
                        <div class="card h-100">
                            <img src="${pageContext.request.contextPath}/images/${d.image}" class="card-img-top" alt="${d.name}">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${d.name}</h5>
                                <p class="text-danger fw-bold mb-1">RM <fmt:formatNumber value="${d.price}" pattern="0.00"/></p>
                                <p class="text-danger fw-bold">Rating: ${d.rating}/10.0</p>
                                <p class="card-text small mb-0">Ingredients: ${d.ingredients}</p>
                                <p class="card-text small">Nutrition: ${d.nutrition}</p>
                                <p class="card-text small">${d.description}</p>
                                <c:choose>
                                    <c:when test="${d.stock <= 0}">
                                        <button class="btn btn-secondary mt-auto" disabled>Out of Stock</button>
                                    </c:when>
                                    <c:otherwise>
                                        <form action="${basePath}cartServlet" method="post" class="d-flex align-items-center mt-auto">
                                            <input type="hidden" name="action" value="add">
                                            <input type="hidden" name="dishId" value="${d.id}">
                                            <div class="qty-control me-2">
                                                <input type="number" name="qty" class="qty-input" value="1" min="1" max="${d.stock}" style="width:50px">
                                            </div>
                                            <button type="submit" class="btn btn-outline-primary flex-grow-1">
                                                Add to Cart
                                            </button>
                                        </form>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>
