<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <link href="/webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
    <title>Ready to go trucks</title>
</head>
<body>
<div class="container">

    <h3>Truck List</h3>
    <c:if test="${!empty truckList}">

        <table class="table table-striped">
            <tr>
                <thead>

                <th scope="col">Truck Id</th>

                <th scope="col">Truck Registration Number</th>
                <th scope="col">Driver Working Hours</th>
                <th scope="col">Truck capacity (Kg)</th>
                <th scope="col">Condition</th>
                <th scope="col">Current city</th>
                </thead>

                </tr>
                <c:forEach items="${truckList}" var="truck">
                <tr>

                    <td>${truck.id}</td>
                    <td>${truck.regNumber}</td>
                    <td>${truck.driverWorkingHours}</td>
                    <td>${truck.capacityTons*1000}</td>
                    <td>${truck.condition}</td>

                    <td>
                        <c:forEach var="hash" items="${cityMap}">
                            <c:if test="${hash.key == truck.currentCityId}">
                                ${hash.value}
                            </c:if>
                        </c:forEach>
                    </td>

                    <td><a href="<c:url value='${order.orderId}/${truck.id}'/>"> Assign to current order </a></td>
                </tr>

                </c:forEach>


                </thead>
            </tr>
        </table>
    </c:if>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button">Main page</a>
    <a class="btn btn-primary" href="/orders/allOrders" role="button">All orders</a>

</div>
</body>
</html>
