<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<%@ page session="false" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <link href = "../webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" />
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
    <title>All Orders</title>
</head>
<body>
<div class="container">

    <h3>Order List</h3>
    <c:if test="${!empty orders}">
    <table class="table table-striped">
        <tr>
            <thead>

            <th scope="col">Order Id</th>
            <th scope="col">Order status</th>
            <th scope="col">Waypoints</th>
            <th scope="col">Truck id</th>
            <th scope="col">Drivers</th>
            <th scope="col">Get ready trucks</th>


            </thead>
        </tr>
        <c:forEach items="${orders}" var="order">
            <tr>

                <td>${order.orderId}</td>

                <c:choose>
                    <c:when test="${order.orderIsDone}">
                        <td>Completed</td>
                    </c:when>
                    <c:otherwise>
                        <td>Not completed</td>
                    </c:otherwise>
                </c:choose>

                <td>
                    <c:forEach items="${order.wayPointsIds}" var="pointId">

                        ${pointId}
<%--                        ${pointService.getPointById(pointId).city.cityName}--%>
                    </c:forEach>

                <td>${order.truckId}</td>

                <td>
                    <c:forEach items="${order.driversOnOrderIds}" var="driverId">

<%--                        ${driverId}--%>
                        ${driverService.getDriverById(driverId).driverSurname}
                    </c:forEach>

                <td><a href = "<c:url value='/readyToGoTrucks/${order.orderId}' />">Get ready trucks</a></td>



            </tr>
        </c:forEach>
    </table>
    </c:if>
    <a class="btn btn-primary" href="/addOrder" role="button">Add order</a>
    <a class="btn btn-primary" href="/" role="button">Main page</a>

</body>
</html>
