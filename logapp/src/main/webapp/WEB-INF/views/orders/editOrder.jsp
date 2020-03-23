<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <link href="/webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>

    <title>Current order status</title>
</head>
<body>
<div class="container">

    <table class="table table-striped">
        <h3>Order #${order.orderId}</h3><br>

        <th scope="col">Point Id</th>
        <th scope="col">Name</th>
        <th scope="col">Operation type</th>
        <th scope="col">City</th>
        <th scope="col">Cargo title</th>
        <th scope="col">Cargo weight</th>
        <th scope="col">Cargo status</th>


        <c:forEach items="${order.points}" var="point">

            <tr>

                <td>${point.id}</td>

                <td>
                        ${point.name}
                </td>
                <td>
                        ${point.operationType}
                </td>
                <td>
                        ${point.city.cityName}
                </td>

                <td>
                        ${point.cargo.title}
                </td>


                <td>
                        ${point.cargo.cargoWeightKilos}
                </td>

                <td>
                        ${point.cargo.cargoStatus}
                </td>


                <c:if test="${point.operationType.toString() == 'LOAD'}">
                    <td>
                        <form action="/myOrder/editOrder/${order.orderId}/${point.id}" method="post" >
                        <input type="submit" value="Set loaded" />
                        </form>
                    </td>
                </c:if>


            </tr>
        </c:forEach>
    </table>


</div>
<script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script scr="../webjars/jquery/3.0.0/jquery.js"></script>

</body>


</html>
