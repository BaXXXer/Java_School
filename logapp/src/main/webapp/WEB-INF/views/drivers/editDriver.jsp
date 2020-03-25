<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 09.03.2020
  Time: 17:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <title>Edit Driver</title>
    <link href="/webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
</head>
<body>
<springForm:form action="../editDriver/{id}" method="POST" modelAttribute="driverToEdit">
    <table>
        <tr>

            <td>
                <form:label path="driverId">
                    <spring:message text="ID"/>
                </form:label>
            </td>
            <td>
                <form:input path="driverId" readonly="true" size="8" disabled="true" />
                <form:hidden path="driverId"/>
            </td>
        </tr>

        <tr>
            <td><springForm:label path="driverStatus">Status:</springForm:label></td>
            <td>

                <springForm:select path="driverStatus">
                    <springForm:option value="" label="Choose Type.." />
                    <springForm:options items="${enumStatus}"/>
                </springForm:select>

            </td>

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

        <tr>
            <td>Select Truck:</td>
            <td>
                <select name="driversTruckId">
                    <option label="---Select city---">
                        <c:forEach items="${truckList}" var="truck">
                    <option value=${truck.id}>${truck.regNumber}</option>
                    </c:forEach>

                </select>
            </td>
        </tr>




        <p>First Name<input name="driverFirstName"
                            required minlength="2"
                            placeholder="First Name"
                            width="100" value=${driverToEdit.driverFirstName}></p>

        <p>Surname:<input type = "text"
                          name="driverSurname"
                          required minlength="2"
                          placeholder="Surname" value=${driverToEdit.driverSurname}></p>

        <p>Personal Number (8 digits): <input
                name="driverPrivateNum"
                required minlength="8"
                required maxlength="8"
                placeholder="Private Number" value=${driverToEdit.driverPrivateNum} ></p>
        <p>Worked Hours: <input
                name="driverWorkedHours"
                min="0"
                max="176"
                placeholder="Worked hours" value="${driverToEdit.driverWorkedHours}" ></p>



<%--            <p>Truck Id: <input--%>
<%--                    name="driversTruckId"--%>
<%--                    min="0"--%>
<%--                    max="176"--%>
<%--                    placeholder="Truck Id" value=${driverToEdit.driversTruckId} ></p>--%>

<%--            <p>City Id: <input--%>
<%--                    name="driverCityId"--%>
<%--                    min="0"--%>
<%--                    max="176"--%>
<%--                    placeholder="City Id" value=${driverToEdit.driverCityId}></p>--%>


        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>


    </table>
</springForm:form>

<a class="btn btn-primary" href="
        ${pageContext.request.contextPath}../allDrivers" role="button">Get all drivers</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button">Main page</a>

</body>
</html>
