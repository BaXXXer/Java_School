<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 01.03.2020
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <title>Add Truck</title>
    <link href="webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script scr="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="webjars/jquery/3.0.0/jquery.js"></script>
</head>
<body>
<h3>Enter the truck details below:</h3>
<form:form action="/addTruck" method = "POST" modelAttribute="addTruck" >

    <table>
        <tr>
            <td><form:label path="regNumber">Registration Number</form:label></td>
            <td><form:input path="regNumber"/></td>
        </tr>
        <tr>
            <td><form:label path="driverWorkingHours">Driver Working Hours</form:label></td>
            <td><form:input path="driverWorkingHours"/></td>
        </tr>
        <tr>
            <td><form:label path="capacityKg">Capacity (kg)</form:label></td>
            <td><form:input path="capacityKg"/></td>
        </tr>
        <tr>
            <td><form:label path="condition">Condition</form:label></td>
            <td>

                <form:select path="condition">
                    <form:option value="" label="Choose Type.." />
                    <form:options items="${enumCondition}"/>
                </form:select>

            </td>
        </tr>
        <tr>
            <td><form:label path="currentCityId">Current city (id)</form:label></td>
            <td><form:input path="currentCityId"/></td>
        </tr>
        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>

    </table>

<%--        <a href="">Get all trucks</a>--%>
<%--        <a href="/">Main page</a>--%>

    </form>

    <a class="btn btn-primary" href="/allTrucks" role="button">Get all trucks</a>
    <a class="btn btn-primary" href="/" role="button">Main page</a>

    </body>
</form:form>
</html>
