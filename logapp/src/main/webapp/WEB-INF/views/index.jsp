
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <link href="../webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
    <title>Welcome</title>
    <h3>Welcome to CargoTrans Inc.!</h3>
    <br>
    <br>
</head>
<body>

<a class="btn btn-danger" href="${pageContext.request.contextPath}/logout" role="button">Logout</a>

<br>

<a class="btn btn-primary" href="${pageContext.request.contextPath}/trucks/addTruck" role="button">Add truck</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/trucks/allTrucks" role="button">See all
    trucks</a><br><br><br>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/drivers/addDriver" role="button">Add driver</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/drivers/allDrivers" role="button">See all
    drivers</a><br><br><br>
<%--<a class="btn btn-primary" href="${pageContext.request.contextPath}/orders/addOrder" role="button">Add new order</a>--%>

<a class="btn btn-primary" href="${pageContext.request.contextPath}/orders/allOrders" role="button">See order details</a>
<%--<a class="btn btn-primary" href="${pageContext.request.contextPath}/orders/orderStatus" role="button">Get order--%>
<%--    status</a>--%>

</body>
</html>
