<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 20.03.2020
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <link href="../webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
    <title>My Account</title>
</head>
<body>
<div class="container">
    <h3>Hello, ${driver.driverFirstName}!</h3>
    <a href="${pageContext.request.contextPath}/logout">Logout</a>
    <br>
    <br>
    <table class="table table-condensed">
        <tr>
            <td>Your private Number:</td>
            <td>${driver.driverPrivateNum}</td>
        </tr>
        <tr>
            <td>Truck Registration Number:</td>
            <td>${driver.truckRegNumber}</td>
        </tr>
        <tr>
            <td>Current order:</td>
            <c:choose>
                <c:when test="${driver.assignedOrder!=null}">
                    <td>${driver.assignedOrder.orderId}</td>
                </c:when>
                <c:otherwise>
                    <td>No order</td>
                </c:otherwise>

            </c:choose>
        </tr>
        <c:choose>
        <c:when test="${driver.assignedOrder!=null}">
        <tr>
            <td>Order points:</td>
            <td>
                <c:forEach items="${pointList}" var="point">
                    ${pointMap.get(point)} <br>
                </c:forEach>
            </td>
        </tr>
            <%--        <tr>--%>
            <%--            <td>Where to go:</td>--%>
            <%--            <td>--%>
            <%--                <c:forEach items="${pointList}" var="point" >--%>
            <%--                    ${cityMap.get(point)}--%>
            <%--                </c:forEach>--%>
            <%--            </td>--%>
            <%--        </tr>--%>

        <tr>
            <td>Co-driver(s) private number(s):</td>
            <c:choose>
            <c:when test="${driverList.size()>1}">
            <td>
                <c:forEach items="${driverList}" var="driverId">
                    ${driverMap.get(driverId)} <br>
                </c:forEach>
                </c:when>
                <c:otherwise>
                    <td>You have no co-drivers for this trip!</td>
                </c:otherwise>
                </c:choose>
            </td>
                <%--            <td>${driver.order.driversOnOrder}</td>--%>

        </tr>

        </c:when>
        <c:otherwise>
        </c:otherwise>

        </c:choose>
        <br>
        <a href="/myOrder/editOrder/${driver.assignedOrder.orderId}">edit</a>
    </table>

</div>

</body>
</html>
