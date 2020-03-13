<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>


<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <title>Add Order</title>
    <link href="../webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
</head>
<body>
<h3>Enter order details below:</h3>

<springForm:form action="/addOrder" method="POST" modelAttribute="orderToAdd">


    <table>



        <p>Is order completed?<input type="radio" name="orderIsDone" value="Yes">Yes <input type="radio" name="orderIsDone" value="No">No</p>




                <tr>
                    <td>From:</td>
                    <td>
                        <select name="pointFromId">
                            <option label="---Select city---">
                                <c:forEach items="${pointList}" var="point">
                            <option value=${point.id}>${point.city.cityName}</option>
                            </c:forEach>

                        </select>
                    </td>
                </tr>

                <tr>
                    <td>To:</td>
                    <td>
                        <select name="pointToId">
                            <option label="---Select city---">
                                <c:forEach items="${pointList}" var="point">
                            <option value=${point.id}>${point.city.cityName}</option>
                            </c:forEach>

                        </select>
                    </td>
                </tr>


                <tr>
                    <td>Select Truck:</td>
                    <td>
                        <select name="truckId">
                            <option label="---Select truck---">
                                <c:forEach items="${truckList}" var="truck">
                            <option value=${truck.id}>${truck.regNumber}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>

                <tr>
                <td>Select first driver:</td>
                <td>
                    <select name="driverId1">
                        <option label="---Select driver---">
                            <c:forEach items="${driverList}" var="driver">
                        <option value=${driver.driverId}>${driver.driverSurname}</option>
                        </c:forEach>
                    </select>
                </td>
            </tr>

                <tr>
                    <td>Select second driver:</td>
                    <td>
                        <select name="driverId2">
                            <option label="---Select driver---">
                                <c:forEach items="${driverList}" var="driver">
                            <option value=${driver.driverId}>${driver.driverSurname}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>





        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>




    </table>
</springForm:form>

<a class="btn btn-primary" href="
    ${pageContext.request.contextPath}/allTrucks" role="button">Get all trucks</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button">Main page</a>

</body>
</html>
