<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activePage" value="category" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Classification Management</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <%@ include file="adminSidebar.jsp" %>
        <div class="col-md-10 py-4">
            <h3>Classification Management</h3>

            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>

            <c:set var="editCat" value="${null}"/>
            <c:if test="${not empty param.editId}">
                <c:forEach var="cat" items="${categories}">
                    <c:if test="${cat.id == param.editId}"><c:set var="editCat" value="${cat}"/></c:if>
                </c:forEach>
            </c:if>

            <div class="card mb-4">
                <div class="card-header">${empty editCat ? 'Add New Category' : 'Edit Category'}</div>
                <div class="card-body">
                    <form action="${pageContext.request.contextPath}/admin/categoryServlet" method="post" class="row g-3">
                        <input type="hidden" name="action" value="${empty editCat ? 'add' : 'update'}">
                        <c:if test="${not empty editCat}">
                            <input type="hidden" name="id" value="${editCat.id}">
                        </c:if>
                        <div class="col-md-6">
                            <label class="form-label">Category Name</label>
                            <input type="text" name="name" class="form-control" value="${editCat.name}" required>
                        </div>
                        <div class="col-md-6 d-flex align-items-end">
                            <button type="submit" class="btn btn-primary me-2">${empty editCat ? 'Add' : 'Save Changes'}</button>
                            <c:if test="${not empty editCat}">
                                <a href="${pageContext.request.contextPath}/admin/categoryServlet" class="btn btn-outline-secondary">Cancel</a>
                            </c:if>
                        </div>
                    </form>
                </div>
            </div>

            <table class="table table-bordered align-middle">
                <thead class="table-light"><tr><th>ID</th><th>Name</th><th>Dishes in category</th><th>Operation</th></tr></thead>
                <tbody>
                <c:forEach var="cat" items="${categories}">
                    <tr>
                    
                        <td>   ${cat.id}</td>
                        
                        <td> ${cat.name}</td>
                        
                        <td> ${cat.dishCount}</td>
                        
                        <td>
                            <a href="?editId=${cat.id}" class="btn btn-sm btn-outline-primary">Edit</a>
                            <a href="${pageContext.request.contextPath}/admin/categoryServlet?action=delete&id=${cat.id}"
                               class="btn btn-sm btn-danger"
                               onclick="return confirm('Delete this category? This only works if no dishes use it.');">Delete</a>
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
