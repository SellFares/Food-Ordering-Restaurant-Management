<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<div class="container mt-5 mb-5">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card shadow">
                <div class="card-header text-center bg-dark text-white">
                    <h4>Administrator backend login</h4>
                </div>
                <div class="card-body">
                    <c:if test="${param.error == '1'}">
                        <div class="alert alert-danger">Incorrect account or password, or this account is not an administrator.</div>
                    </c:if>
                    <form action="${basePath}loginServlet" method="post">
                        <input type="hidden" name="target" value="admin">
                        <div class="mb-3">
                            <label class="form-label">Account</label>
                            <input type="text" name="username" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Password</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-dark w-100">Login</button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="../common/footer.jsp" %>
