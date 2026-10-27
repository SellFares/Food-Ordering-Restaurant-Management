<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/header.jsp" %>
<main class="container py-5">
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow">
                <div class="card-header text-center bg-success text-white">
                    <h4>User Registration</h4>
                </div>
                <div class="card-body">
                    <c:if test="${not empty error}">
                        <div class="alert alert-danger">${error}</div>
                    </c:if>
                    <form action="${basePath}registerServlet" method="post" id="registerForm">
                        <div class="mb-3">
                            <label class="form-label">Username</label>
                            <input type="text" name="username" class="form-control" value="${username}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Email</label>
                            <input type="email" name="email" class="form-control" value="${email}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Phonenumber</label>
                            <input type="text" name="phone" class="form-control" value="${phone}" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Password</label>
                            <input type="password" name="password" id="pwd" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Confirm Password</label>
                            <input type="password" id="rePwd" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-success w-100">Register</button>
                        <p class="text-center mt-3">
                            Already have an account?<a href="login.jsp">Log in now</a>
                        </p>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>
</main>
<%@ include file="../common/footer.jsp" %>