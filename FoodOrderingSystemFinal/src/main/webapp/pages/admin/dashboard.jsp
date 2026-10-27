<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="activePage" value="dashboard" scope="request"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Panel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <%@ include file="adminSidebar.jsp" %>
        <div class="col-md-10 py-4">
            <h3>Data Overview</h3>
            <div class="row mt-4">
                <div class="col-md-3">
                    <div class="card text-center bg-primary text-white">
                        <div class="card-body">
                            <h3>${todayOrderCount}</h3>
                            <p>Today's orders</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center bg-success text-white">
                        <div class="card-body">
                            <h3>${dishCount}</h3>
                            <p>Total number of dishes</p>
                        </div>
                    </div>
                </div>
                <div class="col-md-3">
                    <div class="card text-center bg-warning text-white">
                        <div class="card-body">
                            <h3>${userCount}</h3>
                            <p>Registered users</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>
