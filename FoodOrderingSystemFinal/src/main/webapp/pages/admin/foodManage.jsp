<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="activePage" value="food" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Food Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <%@ include file="adminSidebar.jsp" %>
        <div class="col-md-10 py-4">
            <h3>Food Management</h3>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <%-- Find the dish being edited (if ?editId=N matches one of the loaded dishes) --%>
            <c:set var="editDish" value="${null}"/>
            <c:if test="${not empty param.editId}">
                <c:forEach var="d" items="${dishes}">
                    <c:if test="${d.id == param.editId}"><c:set var="editDish" value="${d}"/></c:if>
                </c:forEach>
            </c:if>

            <div class="card mb-4">
                <div class="card-header">${empty editDish ? 'Add New Dish' : 'Edit Dish'}</div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/foodServlet" method="post" class="row g-3">
                        <input type="hidden" name="action" value="${empty editDish ? 'add' : 'update'}">
                        <c:if test="${not empty editDish}">
                            <input type="hidden" name="id" value="${editDish.id}">
                        </c:if>
                        <div class="col-md-4">
                            <label class="form-label">Category</label>
                            <select name="categoryId" class="form-select" required>
                                <c:forEach var="cat" items="${categories}">
                                    <option value="${cat.id}" ${(not empty editDish and editDish.categoryId == cat.id) ? 'selected' : ''}>${cat.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Dish Name</label>
                            <input type="text" name="name" class="form-control" value="${editDish.name}" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Price (RM)</label>
                            <input type="number" step="0.01" name="price" class="form-control" value="${editDish.price}" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Image filename</label>
                            <input type="text" name="image" class="form-control" placeholder="dish1.jpg" value="${editDish.image}">
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Rating (0-10)</label>
                            <input type="number" step="0.1" name="rating" class="form-control" value="${empty editDish ? 8.0 : editDish.rating}" required>
                        </div>
                        <div class="col-md-4">
                            <label class="form-label">Stock</label>
                            <input type="number" name="stock" class="form-control" value="${empty editDish ? 100 : editDish.stock}" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Ingredients</label>
                            <input type="text" name="ingredients" class="form-control" value="${editDish.ingredients}">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Nutrition</label>
                            <input type="text" name="nutrition" class="form-control" value="${editDish.nutrition}">
                        </div>
                        <div class="col-12">
                            <label class="form-label">Description</label>
                            <textarea name="description" class="form-control" rows="2">${editDish.description}</textarea>
                        </div>
                        <div class="col-12">
                            <button type="submit" class="btn btn-primary">${empty editDish ? 'Add Dish' : 'Save Changes'}</button>
                            <c:if test="${not empty editDish}">
                                <a href="${pageContext.request.contextPath}/admin/foodServlet" class="btn btn-outline-secondary">Cancel</a>
                            </c:if>
                        </div>
                    </form>
                </div>
            </div>

            <table class="table table-bordered align-middle">
                <thead class="table-light">
                    <tr>
                        <th>ID</th><th>Image</th><th>Name</th><th>Category</th><th>Price</th><th>Rating</th><th>Stock</th><th>Operation</th>
                    </tr>
                </thead>
                <tbody>
                <c:forEach var="d" items="${dishes}">
                    <tr>
                        <td>${d.id}</td>
                        <td><img src="${pageContext.request.contextPath}/images/${d.image}" style="width:50px;height:50px;object-fit:cover;" class="rounded"></td>
                        <td>${d.name}</td>
                        <td>${d.categoryName}</td>
                        <td>RM <fmt:formatNumber value="${d.price}" pattern="0.00"/></td>
                        <td>${d.rating}</td>
                        <td>${d.stock}</td>
                        <td>
                            <a href="?editId=${d.id}" class="btn btn-sm btn-outline-primary">Edit</a>
                            <a href="${pageContext.request.contextPath}/admin/foodServlet?action=delete&id=${d.id}"
                               class="btn btn-sm btn-danger"
                               onclick="return confirm('Delete this dish?');">Delete</a>
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
