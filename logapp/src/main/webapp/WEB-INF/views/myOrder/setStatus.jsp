<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Change status</title>
    <link href="../webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
</head>
<body>
<springForm:form action="${pageContext.request.contextPath}/myOrder/setStatus" method="POST" modelAttribute="driverDto">

<c:forEach items="${statusEnum}" var="status">
    <c:choose>
        <c:when test="${status=='CARGO_HANDLING'}">
            <input type="radio" name="driverStatus" value="${'CARGO_HANDLING'}"/> Cargo handling
            <br>
        </c:when>
        <c:otherwise>
            <c:choose>
                <c:when test="${status=='REST_ON_SHIFT'}">
                    <input type="radio" name="driverStatus" value="${'REST_ON_SHIFT'}"/> Rest
                    <br>
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${status=='DRIVING'}">
                            <input type="radio" name="driverStatus" value="${'DRIVING'}"/> Driving
                            <br>
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${status=='CO_DRIVER'}">
                                    <input type="radio" name="driverStatus" value="${'CO_DRIVER'}"/> Co-Driver
                                    <br>
                                </c:when>
                                <c:otherwise></c:otherwise>
                            </c:choose>
                        </c:otherwise>
                    </c:choose>

                </c:otherwise>
            </c:choose>

        </c:otherwise>
    </c:choose>

</c:forEach>
    <input type="submit" value="Change work status"/>

</springForm:form>

</body>
</html>
