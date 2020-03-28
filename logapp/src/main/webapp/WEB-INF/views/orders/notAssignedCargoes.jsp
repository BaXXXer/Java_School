<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <link href="/webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>

    <title>Cargoes</title>
</head>
<body>
<h3>Not assigned cargoes</h3>

<c:if test="${!empty cargoes}">
    <table class="table table-striped">
        <tr>
            <thead>

            <th scope="col">Id</th>

            <th scope="col">Code</th>
            <th scope="col">Title</th>
            <th scope="col">Weight (kg)</th>
            <th scope="col">Status</th>
            <th scope="col">Current city</th>
            <th scope="col">Where to go</th>
            <th scope="col">Add to current order</th>

            </thead>
        </tr>

        <c:forEach items="${cargoes}" var="cargo">
            <springForm:form action="../notAssignedCargoes/${order.orderId}/${cargo.cargoId}" method="POST">

                <tr>

                    <td>${cargo.cargoId}</td>
                        <%--                    <input type="text" name="cargoId" value="${cargoPoint.id}" hidden>--%>


                    <td>${cargo.cargoName}</td>
                    <td>${cargo.title}</td>
                    <td>${cargo.cargoWeightKilos}</td>
                    <td>${cargo.cargoStatus}</td>
                    <td>${cargo.currentCity.cityName}</td>
                    <td>
                        <select name="destCity" id="destCity">

                            <option label="---Select city---">
                                <c:forEach items="${points}" var="point">
                            <option value=${point.id}>${point.name} in ${point.destCity.cityName}</option>
                            </c:forEach>

                        </select>
                    </td>

                        <%--                <td><a href="<c:url value='${order.orderId}/${cargoPoint.id}' />">Add to this--%>
                        <%--                    order</a></td>--%>

                    <td><input type="submit" value="Submit"/></td>
                </tr>


                </tr>
            </springForm:form>
        </c:forEach>
    </table>
</c:if>


<%--<script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>--%>
<%--<script scr="../webjars/jquery/3.0.0/jquery.js"></script>--%>
<script src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>


</body>
</html>
