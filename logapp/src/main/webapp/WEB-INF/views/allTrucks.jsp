<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="p" uri="http://primefaces.prime.com.tr/ui" %>--%>
<html>

<%@ page session="false" %>
<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 01.03.2020
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>
    <title>All Trucks in DB</title>
</head>
<body>

<h3>Truck List</h3>
<c:if test="${!empty trucks}">
    <table class="tg">
        <tr>

            <th width="120">Truck Registration Number</th>
            <th width="120">Driver Working Hours</th>
            <th width="120">Truck capacity</th>
            <th width="120">Condition</th>
            <th width="60">City id</th>
        </tr>
        <c:forEach items="${trucks}" var="truck">
            <tr>
                <td>${truck.regNumber}</td>
                <td>${truck.driverWorkingHours}</td>
                <td>${truck.capacityKg}</td>
                <td>${truck.condition}</td>
                <td>${truck.currentCityId}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<a href="/addTruck">Add new truck</a>
<a href="/">Main page</a>

</body>
</html>
