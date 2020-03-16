<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>All Drivers</title>
    <link href = "webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet" />
    <script scr="webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="webjars/jquery/3.0.0/jquery.js"></script>
</head>
<body>
<div class="container">

    <h3>Driver List</h3>
    <c:if test="${!empty drivers}">
        <table class="table table-striped">
            <tr>
                <thead>

                <th scope="col">Id</th>

                <th scope="col">First Name</th>
                <th scope="col">Surname</th>
                <th scope="col">Private Number</th>
                <th scope="col">Worked hours</th>
                <th scope="col">Status</th>
                <th scope="col">Current truck</th>
                <th scope="col">Current city</th>
                <th scope="col">Edit</th>
                <th scope="col">Delete</th>

                </thead>
            </tr>
            <c:forEach items="${drivers}" var="driver">
                <tr>

                    <td>${driver.driverId}</td>


                    <td>${driver.driverFirstName}</td>
                    <td>${driver.driverSurname}</td>
                    <td>${driver.driverPrivateNum}</td>
                    <td>${driver.driverWorkedHours}</td>
                    <td>${driver.driverStatus}</td>
<%--                    <td>${driver.driversTruckId}</td>--%>
                    <td>
                        <c:forEach var="hash" items="${truckMap}">
                            <c:if test="${hash.key == driver.driversTruckId}">
                                ${hash.value}

                            </c:if>
                        </c:forEach>
                    </td>

                    <td>
                        <c:forEach var="hash" items="${cityMap}">
                            <c:if test="${hash.key == driver.driverCityId}">
                                ${hash.value}
                            </c:if>
                        </c:forEach>
                    </td>

                    <td><a href = "<c:url value='/editDriver/${driver.driverId}' />">Edit</a></td>
                    <td><a href = "<c:url value='/removeDriver/${driver.driverId}' />">Delete</a></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <a class="btn btn-primary" href="${pageContext.request.contextPath}/addDriver" role="button">Add driver</a>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button">Main page</a>
</div>

</body>
</html>
