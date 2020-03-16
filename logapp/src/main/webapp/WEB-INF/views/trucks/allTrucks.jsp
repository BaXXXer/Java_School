<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--<%@ taglib prefix="p" uri="http://primefaces.prime.com.tr/ui" %>--%>
<html>

<%@ page session="false" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <title>All Trucks</title>
    <link href = "../webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" />
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
</head>
<body>
<div class="container">

<h3>Truck List</h3>
<c:if test="${!empty trucks}">
    <table class="table table-striped">
        <tr>
            <thead>

                <th scope="col">Truck Id</th>

                <th scope="col">Truck Registration Number</th>
                <th scope="col">Driver Working Hours</th>
                <th scope="col">Truck capacity</th>
                <th scope="col">Condition</th>
                <th scope="col">Current city</th>
                <th scope="col">Edit</th>
                <th scope="col">Delete</th>

            </thead>
        </tr>
        <c:forEach items="${trucks}" var="truck">
            <tr>

                <td>${truck.id}</td>
                <td>${truck.regNumber}</td>
                <td>${truck.driverWorkingHours}</td>
                <td>${truck.capacityTons}</td>
                <td>${truck.condition}</td>

                <td>
                    <c:forEach var="hash" items="${cityMap}">
                        <c:if test="${hash.key == truck.currentCityId}">
                            ${hash.value}
                        </c:if>
                    </c:forEach>
                </td>

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
