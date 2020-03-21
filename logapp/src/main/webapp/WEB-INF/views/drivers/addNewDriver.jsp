<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <title>Add Driver</title>
    <link href="../webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
</head>
<body>
<h3>Enter the driver details below:</h3>
<style>
    .error {
        color: #ff0000;
        font-style: italic;
        font-weight: bold;
    }
</style>
<springForm:form action="./addDriver" method = "POST" modelAttribute="driverToAdd" >

    <table>


        <p>First Name<input name="driverFirstName"
                            required minlength="2"
                            placeholder="First Name"
                            width="100"></p>

        <p>Surname:<input type = "text"
                                name="driverSurname"
                                required minlength="2"
                                placeholder="Surname"></p>

        <p>Personal Number: <input
            name="driverPrivateNum"
            required minlength="8"
            required maxlength="8"
            placeholder="Private Number" ></p>
        <p>Worked Hours: <input
                name="driverWorkedHours"
                min="0"
                max="176"
                placeholder="Worker hours" ></p>

<%--        <tr>--%>
<%--            <td><springForm:label path="driverStatus">Status:</springForm:label></td>--%>
<%--            <td>--%>

<%--                <springForm:select path="driverStatus">--%>
<%--                    <springForm:option value="" label="Choose Type.." />--%>
<%--                    <springForm:options items="${enumStatus}"/>--%>
<%--                </springForm:select>--%>

<%--            </td>--%>
<%--        </tr>--%>

        <tr>
            <td>Select Status:</td>
            <td>
                <select name="driverStatus">
                    <option label="---Select status---">
                        <c:forEach items="${enumStatus}" var="status">
                    <option value=${status}>${status}</option>
                    </c:forEach>

                </select>
            </td>
        </tr>
        <br>


        <tr>
        <td>Select Truck:</td>
            <td>
            <select name="driversTruckId">
                <option label="---Select truck---">
                <c:forEach items="${truckList}" var="truck">
                    <option value=${truck.id}>${truck.regNumber}</option>
                </c:forEach>

            </select>
            </td>
        </tr>


        <tr>
            <td>Select City:</td>
            <td>
                <select name="driverCityId">
                    <option label="---Select city---">
                        <c:forEach items="${cityList}" var="city">
                    <option value=${city.cityId}>${city.cityName}</option>
                    </c:forEach>

                </select>
            </td>
        </tr>



<%--            <p>City Id: <input--%>
<%--                    name="driverCityId"--%>
<%--                    min="0"--%>
<%--                    max="176"--%>
<%--                    placeholder="City Id" ></p>--%>



<%--        <tr>--%>
<%--            <td><springForm:label path="currentTruck">Truck:</springForm:label></td>--%>
<%--            <td>--%>

<%--                <springForm:select path="currentTruck">--%>
<%--                    <springForm:option value="" label="Choose Truck..." />--%>
<%--                    <springForm:options items="${truckList}"/>--%>
<%--                </springForm:select>--%>

<%--            </td>--%>


<%--        <tr>--%>
            <td><input type="submit" value="Submit"/></td>
        </tr>

    </table>
</springForm:form>
<springForm:form method="POST" modelAttribute="driverToAdd">
    <table>

    </table>
</springForm:form>

<a class="btn btn-primary" href="
    ${pageContext.request.contextPath}./allDrivers" role="button">Get all drivers</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button">Main page</a>

</body>

</html>
