<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 30.03.2020
  Time: 8:42
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="../webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
    <title>Success</title>
</head>
<body>
<h3>Truck was successfully added!</h3>


<a class="btn btn-primary" href="${pageContext.request.contextPath}/trucks/addTruck" role="button">Add another truck</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button">Back to main</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/trucks/allTrucks" role="button">See all
    trucks</a><br><br><br>

</body>
</html>
