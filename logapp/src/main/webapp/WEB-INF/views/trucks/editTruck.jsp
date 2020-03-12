<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Edit truck</title>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <link href="../webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
</head>
<body>
<springForm:form action="/editTruck/{id}" method="POST" modelAttribute="truckToEdit">
    <table>
        <tr>

                    <td>
                        <form:label path="id">
                            <spring:message text="ID"/>
                        </form:label>
                    </td>
            <td>
                <form:input path="id" readonly="true" size="8" disabled="true" />
                <form:hidden path="id"/>
            </td>
        </tr>
        <tr>

            <td><springForm:label path="condition">Condition</springForm:label></td>
            <td>

                <springForm:select path="condition">
                    <springForm:option value="" label="Choose Type.."/>
                    <springForm:options items="${enumCondition}"/>
                </springForm:select>

            </td>
        </tr>

            <%--        <input name="id" hidden readonly>--%>


        <p>Registration Number (format AA00000):<input name="regNumber"
                                                       required minlength="7"
                                                       value=${truckToEdit.regNumber}
                                                               pattern="[A-Z]{2}\d{5}"></p>

        <p>Working Hours:<input type="number"
                                name="driverWorkingHours"
                                required minlength="7"
                                value=${truckToEdit.driverWorkingHours}></p>


        <p>Capacity:<input type="number"
                           name="capacityTons"
                           min="10" ;
                           max="30"
                           value=${truckToEdit.capacityTons }></p>
        <p>City Id:<input type="number"
                          name="currentCityId"
                          required maxength="2"
                          value=${truckToEdit.currentCityId}></p>


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
