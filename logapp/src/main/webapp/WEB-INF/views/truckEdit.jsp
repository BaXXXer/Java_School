<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 28.02.2020
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Truck information</title>
</head>
<body>
<h2>Truck Information</h2>
<form:form method = "POST" action = "/addTruck" modelAttribute="addTruck">
    <table>
        <tr>
            <td><form:label path = "regNumber">Registration Number</form:label></td>
            <td><form:input path = "regNumber" /></td>
        </tr>
        <tr>
            <td><form:label path = "driverWorkingHours">Working Hours</form:label></td>
            <td><form:input path = "driverWorkingHours" /></td>
        </tr>
        <tr>
            <td><form:label path = "capacityKg">Capacity (tons)</form:label></td>
            <td><form:input path = "capacityKg" /></td>
        </tr>
        <tr>
            <td><form:label path = "condition">Condition</form:label></td>
            <td><form:input path = "condition" /></td>
        </tr>
        <tr>
            <td><form:label path = "currentCity">Current city</form:label></td>
            <td><form:input path = "currentCity" /></td>
        </tr>
        <tr>
            <td colspan = "2">
                <input type = "submit" value = "Submit"/>
            </td>
        </tr>
    </table>
</form:form>

</body>
</html>
