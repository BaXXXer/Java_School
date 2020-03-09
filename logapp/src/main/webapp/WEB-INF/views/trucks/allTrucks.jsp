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
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <title>All Trucks in DB</title>
    <link href = "webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" />
    <script scr="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="webjars/jquery/3.0.0/jquery.js"></script>
</head>
<body>
<div class="container">

<h3>Truck List</h3>
<c:if test="${!empty trucks}">
    <table class="table table-striped">
        <tr>
            <thead>

                <th scope="col">Truck Registration Number</th>
                <th scope="col">Driver Working Hours</th>
                <th scope="col">Truck capacity</th>
                <th scope="col">Condition</th>
                <th scope="col">City id</th>
                <th scope="col">Edit</th>
                <th scope="col">Delete</th>

            </thead>
        </tr>
        <c:forEach items="${trucks}" var="truck">
            <tr>

                <td>${truck.regNumber}</td>
                <td>${truck.driverWorkingHours}</td>
                <td>${truck.capacityTons}</td>
                <td>${truck.condition}</td>
                <td>${truck.cityId}</td>
                <td><a href = "<c:url value='/editTruck/${truck.id}' />">Edit</a></td>
                <td><a href = "<c:url value='/removeTruck/${truck.id}' />">Delete</a></td>

            </tr>
        </c:forEach>
    </table>
</c:if>

    <a class="btn btn-primary" href="${pageContext.request.contextPath}/addTruck" role="button">Add truck</a>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button">Main page</a>
</div>

</body>
</html>
