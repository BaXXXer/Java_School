<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>

<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <title>Add Truck</title>
    <link href="../webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
</head>
<body>
<h3>Enter the truck details below:</h3>
<style>
    .error {
        color: #ff0000;
        font-style: italic;
        font-weight: bold;
    }
</style>
<springForm:form action="./addTruck" method="POST" modelAttribute="truckToAdd">


    <table>


        <p>Registration Number (format AA00000):<input name="regNumber"
                                                       required minlength="7"
                                                       placeholder="Registration Number"
                                                       pattern="[A-Z]{2}\d{5}">
            <form:errors path="regNumber" cssClass="error"/>
        </p>

                <tr>
            <td><springForm:label path="condition">Condition</springForm:label></td>
            <td>

                <springForm:select path="condition">
                    <springForm:option value="" label="Choose Type.."/>
                    <springForm:options items="${enumCondition}"/>
                </springForm:select>

            </td>

        <tr>
            <td>Select City:</td>
            <td>
                <select name="currentCityId">
                    <option label="---Select city---">
                        <c:forEach items="${cityList}" var="city">
                    <option value=${city.cityId}>${city.cityName}</option>
                    </c:forEach>
                    <form:errors path="currentCityId" cssClass="error"/>

                </select>
            </td>
        </tr>


        <p>Capacity:<input type="number"
                           name="capacityTons"
                           min="5"
                           max="25"
                           placeholder="Capacity">
            <form:errors path="capacityTons" cssClass="error"/>
        </p>

        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>


    </table>
</springForm:form>

<a class="btn btn-primary" href="
    ${pageContext.request.contextPath}./allTrucks" role="button">Get all trucks</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button">Main page</a>

</body>

</html>
