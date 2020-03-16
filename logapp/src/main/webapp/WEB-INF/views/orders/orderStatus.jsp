<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <link href="../webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
    <title>Current order status</title>
</head>
<body>
<div class="container">

    <h3>Order status list</h3>

    <c:forEach items="${orders}" var="order">
        <table class="table table-striped">
        <h3>Order #${order.orderId}</h3><br>
        <th scope="col">Order Id</th>
        <th scope="col">Order status</th>
        <th scope="col">Cargo names</th>
        <th scope="col">Cargo titles</th>
        <th scope="col">Cargo status</th>


        <c:if test="${!empty orders}">


            <c:forEach items="${order.cargoes}" var="cargo">

                <tr>

                    <td>${order.orderId}</td>

                    <c:choose>
                        <c:when test="${order.orderIsDone}">
                            <td>Completed</td>
                        </c:when>
                        <c:otherwise>
                            <td>Not completed</td>
                        </c:otherwise>
                    </c:choose>

                    <td>

                            ${cargo.cargoName}
                    </td>
                    <td>
                            ${cargo.title}
                    </td>
                    <td>
                            ${cargo.cargoStatus}
                    </td>

                </tr>
            </c:forEach>
            </table>
        </c:if>
    </c:forEach>

</div>

</body>


</html>
