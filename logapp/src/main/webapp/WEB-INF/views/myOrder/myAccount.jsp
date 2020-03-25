<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 20.03.2020
  Time: 11:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html" charset="ISO-8859-10">
    <link href="../webjars/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet"/>
    <script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <script scr="../webjars/jquery/3.0.0/jquery.js"></script>
    <title>My Account</title>
</head>
<body>
<div class="container">
    <h3>Hello, ${driver.driverFirstName}!</h3>
    <a class="btn btn-danger" href="${pageContext.request.contextPath}/logout" role="button">Logout</a>
    <%--    <a href="${pageContext.request.contextPath}/logout">Logout</a>--%>
    <br>
    <br>
    <table class="table table-condensed">
        <tr>
            <td>Your private Number:</td>
            <td>${driver.driverPrivateNum}</td>
        </tr>
        <tr>
            <td>Currently you are:</td>
            <c:choose>
                <c:when test="${driver.driverStatus == 'CARGO_HANDLING' || driver.driverStatus == 'REST_ON_SHIFT'
                || driver.driverStatus =='DRIVING' || driver.driverStatus == 'CO_DRIVER'}">
                    <td>On a shift</td>

                </c:when>
                <c:otherwise>
                    <td>On a rest</td>
                </c:otherwise>

            </c:choose>
        </tr>
        <tr>
            <td>Working status:</td>
            <td><c:choose>
                <c:when test="${driver.driverStatus=='CARGO_HANDLING'}">
                    Cargo handling
                </c:when>
                <c:otherwise>
                    <c:choose>
                        <c:when test="${driver.driverStatus=='REST_ON_SHIFT'}">
                            Rest
                        </c:when>
                        <c:otherwise>
                            <c:choose>
                                <c:when test="${driver.driverStatus=='DRIVING'}">
                                    Driving
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${driver.driverStatus=='CO_DRIVER'}">
                                            Co-Driver
                                        </c:when>
                                        <c:otherwise></c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>

                        </c:otherwise>
                    </c:choose>

                </c:otherwise>
            </c:choose></td>
        </tr>

        <tr>
            <td>Truck Registration Number:</td>
            <td>${driver.truckRegNumber}</td>
        </tr>
        <tr>
            <td>Current order:</td>
            <c:choose>
                <c:when test="${driver.assignedOrder!=null}">
                    <td>${driver.assignedOrder.orderId}</td>
                </c:when>
                <c:otherwise>
                    <td>No order</td>
                </c:otherwise>

            </c:choose>
        </tr>
        <c:choose>
            <c:when test="${driver.assignedOrder!=null}">
                <tr>
                    <td>Order points:</td>
                    <td>
                        <c:forEach items="${pointList}" var="point">
                            ${pointMap.get(point)} <br>
                        </c:forEach>
                    </td>
                </tr>

                <tr>
                    <td>Co-driver(s) private number(s):</td>
                    <c:choose>
                        <c:when test="${driverList.size()>1}">
                            <td>
                            <c:forEach items="${driverList}" var="driverId">
                                ${driverMap.get(driverId)} <br>
                            </c:forEach>
                        </c:when>
                        <c:otherwise>
                            <td>You have no co-drivers for this trip!</td>
                        </c:otherwise>
                    </c:choose>
                    </td>
                        <%--            <td>${driver.order.driversOnOrder}</td>--%>

                </tr>

            </c:when>
            <c:otherwise>
            </c:otherwise>

        </c:choose>
        <c:choose>
            <c:when test="${driver.driverStatus == 'CARGO_HANDLING' || driver.driverStatus == 'REST_ON_SHIFT'
                || driver.driverStatus =='DRIVING' || driver.driverStatus == 'CO_DRIVER'}">
                <td>
                    <form action="${pageContext.request.contextPath}/myOrder/setOnRest" method="post">
                        <input type="submit" value="Finish shift"/>
                    </form>
                </td>
            </c:when>
            <c:otherwise>
                <td>
                    <form action="${pageContext.request.contextPath}/myOrder/setOnShift" method="post">
                        <input type="submit" value="Start shift"/>
                    </form>
                </td>

            </c:otherwise>

        </c:choose>

        <a class="btn btn-secondary btn-sm" href="/myOrder/editOrder/${driver.assignedOrder.orderId}" role="button">Edit
            order details</a>
        <br>

        <tr>
            <td>
            Set my status to
            </td>
            <td>
            <springForm:form action="${pageContext.request.contextPath}/myOrder/setNewStatus" method="POST" modelAttribute="driver">

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
                <input type="submit" value="Submit"/>

            </springForm:form>
            </td>
        </tr>
        </tr>

    </table>

</div>

</body>
</html>
