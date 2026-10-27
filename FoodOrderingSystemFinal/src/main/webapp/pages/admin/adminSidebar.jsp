<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="col-md-2 bg-dark text-white vh-100 py-3">
    <h4 class="text-center mb-4">Admin Panel</h4>
    <div class="list-group list-group-flush">
        <a href="${pageContext.request.contextPath}/admin/dashboardServlet"
           class="list-group-item list-group-item-action bg-dark text-white ${activePage == 'dashboard' ? 'active' : ''}">Data Overview</a>
        <a href="${pageContext.request.contextPath}/admin/foodServlet"
           class="list-group-item list-group-item-action bg-dark text-white ${activePage == 'food' ? 'active' : ''}">Food management</a>
        <a href="${pageContext.request.contextPath}/admin/categoryServlet"
           class="list-group-item list-group-item-action bg-dark text-white ${activePage == 'category' ? 'active' : ''}">Classification management</a>
        <a href="${pageContext.request.contextPath}/admin/orderServlet"
           class="list-group-item list-group-item-action bg-dark text-white ${activePage == 'order' ? 'active' : ''}">Order Management</a>
        <a href="${pageContext.request.contextPath}/pages/user/index.jsp"
           class="list-group-item list-group-item-action bg-dark text-white">Return to the front desk</a>
        <a href="${pageContext.request.contextPath}/logoutServlet"
           class="list-group-item list-group-item-action bg-dark text-white">Logout</a>
    </div>
</div>
