<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <link href="../webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
    <title>All orders</title>
</head>
<body>
<c:forEach items="${orders}" var="order">
    <table class="table table-striped">
        <h3>Order #${order.orderId}</h3>
        <a href="<c:url value='/readyToGoTrucks/${order.orderId}' />">Get ready trucks</a><br>
        <a href="<c:url value='/readyForTripDrivers/${order.orderId}' />">Get ready drivers</a><br>
<%--        <a class="btn btn-danger" href="<c:url value='/removeOrder/${order.orderId}' />" role="button">Delete order</a>--%>
        <br>
        <th scope="col">Order Id</th>
        <th scope="col">Point</th>
        <th scope="col">Status</th>
        <th scope="col">Assigned drivers</th>
        <c:if test="${!empty orders}">


            <c:forEach items="${order.wayPointsIds}" var="wpId">
                <tr>

                    <td>${order.orderId}</td>


                    <td>${pointMap.get(wpId)}</td>

                    <c:choose>
                        <c:when test="${order.orderIsDone==true}">
                            <td>Completed</td>
                        </c:when>
                        <c:otherwise>
                            <td>Not completed</td>
                        </c:otherwise>
                    </c:choose>
                    <td>
                        <c:forEach items="${order.driversOnOrderIds}" var="driverId">

                            ${driverService.getDriverById(driverId).driverSurname}
                        </c:forEach>


                </tr>
            </c:forEach>
        </c:if>


    </table>
</c:forEach>

<a class="btn btn-primary" href="/addOrder" role="button">Add order</a>
<a class="btn btn-primary" href="/" role="button">Main page</a>


</body>
</html>
