<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 01.03.2020
  Time: 19:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Trucks in DB</title>
</head>
<body>

<table>
    <c:forEach var="truck" items="${trucks}">
        <tr>
            <td>${truck.regNumber}</td>
            <td>${truck.driverWorkingHours}</td>
            <td>${truck.capacityKg}</td>
            <td>${truck.condition}</td>
            <td>${truck.currentCityId}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
