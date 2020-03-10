<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 01.03.2020
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
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
<springForm:form action="/addTruck" method = "POST" modelAttribute="truckToAdd" >


    <table>
<%--        <tr>--%>
<%--            <td><springForm:input path="regNumber" />Registration Number</td>--%>
<%--            <td><springForm:errors path="regNumber" cssClass="error" /></td>--%>
<%--        </tr>--%>

    <p>Registration Number (format AA00000):<input name="regNumber"
                                      required minlength="7"
                                      placeholder="Registration Number"
                                      pattern="[A-Z]{2}\d{5}" ></p>

    <p>Working Hours:<input type = "number"
                                  name="driverWorkingHours"
                                  required minlength="7"
                                  placeholder="Working Hours"></p>

                <tr>
                    <td><springForm:label path="condition">Condition</springForm:label></td>
                    <td>

                        <springForm:select path="condition">
                            <springForm:option value="" label="Choose Type.." />
                            <springForm:options items="${enumCondition}"/>
                        </springForm:select>

                    </td>


    <p>Capacity:<input type = "number"
                                  name="capacityTons"
                                  min = "10";
                                  max="30"
                                  placeholder="Capacity"></p>
    <p>City Id:<input type = "number"
                                  name="currentCityId"
                                  required maxength="2"
                                  placeholder="City ID"></p>


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
