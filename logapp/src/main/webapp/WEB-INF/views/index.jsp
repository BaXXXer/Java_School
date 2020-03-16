<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 03.03.2020
  Time: 15:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <link href = "../webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" />
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
    <title>Welcome</title>
    <h3>Welcome to CargoTrans Inc.!</h3>
    <br>
    <br>
</head>
<body>

    <a class="btn btn-primary" href="/addTruck" role="button">Add truck</a>
    <a class="btn btn-primary" href="/allTrucks" role="button">See all trucks</a><br><br><br>
    <a class="btn btn-primary" href="/addDriver" role="button">Add driver</a>
    <a class="btn btn-primary" href="/allDrivers" role="button">See all drivers</a><br><br><br>
    <a class="btn btn-primary" href="/addOrder" role="button">Add new order</a>
    <a class="btn btn-primary" href="/allOrders" role="button">See all orders</a>
    <a class="btn btn-primary" href="/orderStatus" role="button">Get order status</a>

</body>
</html>
