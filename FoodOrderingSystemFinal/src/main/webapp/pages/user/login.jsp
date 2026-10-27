<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<main class="container py-5">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-4">
            <div class="card shadow">
                <div class="card-header text-center bg-primary text-white">
                    <h4>User login</h4>
                </div>
                <div class="card-body">
                    <c:if test="${not empty param.error}">
                        <div class="alert alert-danger">Incorrect username or password, please re-enter</div>
                    </c:if>
                    <c:if test="${param.registered == '1'}">
                        <div class="alert alert-success">Registration successful! Please log in.</div>
                    </c:if>
                    <form action="${basePath}loginServlet" method="post" id="loginForm">
                        <div class="mb-3">
                            <label class="form-label">Username</label>
                            <input type="text" name="username" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Password</label>
                            <input type="password" name="password" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-primary w-100">Login</button>
                        <p class="text-center mt-3">
                            Don't have an account yet?<a href="register.jsp">Register Now</a>
                        </p>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</main>
<%@ include file="../common/footer.jsp" %>