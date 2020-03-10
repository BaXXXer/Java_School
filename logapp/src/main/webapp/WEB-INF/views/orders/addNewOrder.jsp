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
            <%--        <tr>--%>
            <%--            <td><springForm:input path="regNumber" />Registration Number</td>--%>
            <%--            <td><springForm:errors path="regNumber" cssClass="error" /></td>--%>
            <%--        </tr>--%>


        <p>Is order completed?<input type="radio" name="orderIsDone" value="Yes"> <input type="radio" name="orderIsDone" value="No"></p>

        <tr>
            <td><springForm:label path="condition">Condition</springForm:label></td>
            <td>

                <springForm:select path="condition">
                    <springForm:option value="" label="Choose Type.."/>
                    <springForm:options items="${enumCondition}"/>
                </springForm:select>

            </td>


            <p>Capacity:<input type="number"
                               name="capacityTons"
                               min="10" ;
                               max="30"
                               placeholder="Capacity"></p>
            <p>City Id:<input type="number"
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
