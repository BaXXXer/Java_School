<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 28.02.2020
  Time: 16:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result</title>
</head>
<body>
<h2>Submitted Truck Information</h2>
<table>
    <tr>
        <td>Registration Number</td>
        <td>${regNumber}</td>
    </tr>
    <tr>
        <td>Working Hours</td>
        <td>${drivaerWorkingHours}</td>
    </tr>
    <tr>
        <td>Capacity</td>
        <td>${capacity}</td>
    </tr>
    <tr>
        <td>Condition</td>
        <td>${condition}</td>
    </tr>
    <tr>
        <td>Current city</td>
        <td>${currentCity}</td>
    </tr>
</table>
</body>
</html>
