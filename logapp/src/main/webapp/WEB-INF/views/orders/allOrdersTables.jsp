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
    <h3><u>Order #${order.orderId}</u></h3>
    <p><b>Order status:</b>
        <c:choose>
            <c:when test="${order.orderIsDone==true}">
                Completed
            </c:when>
            <c:otherwise>
                Not completed
            </c:otherwise>
        </c:choose>
    </p>
    <p><b>Assigned drivers:</b>
        <c:forEach items="${order.driversOnOrderIds}" var="driverId">

            ${driverService.getDriverById(driverId).driverPrivateNum}:
            ${driverService.getDriverById(driverId).driverFirstName}
            ${driverService.getDriverById(driverId).driverSurname}
            <br>
        </c:forEach></p>
    <p><b>Assigned truck:</b>
        <c:choose>
            <c:when test="${order.truckId!=0}">
                ${truckMap.get(order.truckId)}
            </c:when>
            <c:otherwise>
                No truck is assigned yet
            </c:otherwise>
        </c:choose>
    </p>

    <p><b>Current driver and truck city:</b>
        <c:choose>
            <c:when test="${order.truckId!=null}">
                ${cityService.getCityDtoById(truckService.getTruckById(order.truckId).currentCityId).cityName}
            </c:when>
            <c:otherwise>
                No truck is assigned yet
            </c:otherwise>
        </c:choose>
    </p>

    <table class="table table-striped">
        <th scope="col">Cargo code</th>
        <th scope="col">Cargo title</th>
        <th scope="col">Cargo weight kilos</th>
        <th scope="col">Cargo current city</th>
        <th scope="col">Cargo destination city</th>
        <th scope="col">Cargo status</th>

        <c:if test="${order.truckId==null}">
            <a class="btn btn-secondary btn-sm" href="<c:url value='./readyToGoTrucks/${order.orderId}' />">Get ready trucks</a><emsp>
        </c:if>
        <c:if test="${order.truckId!=null}">
            <a class="btn btn-secondary btn-sm" href="<c:url value='./readyForTripDrivers/${order.orderId}' />">Get ready drivers</a><emsp>
        </c:if>
        <a class="btn btn-secondary btn-sm" href="<c:url value='./notAssignedCargoes/${order.orderId}' />">Get Cargoes to assign</a><emsp>

        <br>

        <c:forEach items="${order.points}" var="point">
            <tr>
                <td>${point.cargo.cargoName}</td>
                <td>${point.cargo.title}</td>
                <td>${point.cargo.cargoWeightKilos}</td>
                <td>${point.cargo.currentCity.cityName}</td>
                <td>${point.destCity.cityName}</td>
                <td>${point.cargo.cargoStatus}</td>
            </tr>


        </c:forEach>
    </table>


    </c:forEach>

    <a class="btn btn-primary" href="/" role="button">Main page</a>
    <form action="${pageContext.request.contextPath}/orders/addOrder" method="post">

        <input type="submit" value="Add Empty Order"/>
    </form>


</body>
</html>
