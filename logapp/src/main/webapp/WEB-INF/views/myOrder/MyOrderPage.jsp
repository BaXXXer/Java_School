<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="shortcut icon" href="/assets/img/favicon.ico" type="image/png">
    <title>LogApp</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="generator" content="Jekyll v3.8.6">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">

    <link rel="canonical" href="https://getbootstrap.com/docs/4.4/examples/dashboard/">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">

    <style>
        .bd-placeholder-img {
            font-size: 1.125rem;
            text-anchor: middle;
            -webkit-user-select: none;
            -moz-user-select: none;
            -ms-user-select: none;
            user-select: none;
        }

        @media (min-width: 768px) {
            .bd-placeholder-img-lg {
                font-size: 3.5rem;
            }
        }
    </style>

    <link href="/assets/css/dashboard.css" rel="stylesheet">

    <!-- Favicons -->
    <link rel="apple-touch-icon" href="/docs/4.4/assets/img/favicons/apple-touch-icon.png" sizes="180x180">
    <link rel="icon" href="/docs/4.4/assets/img/favicons/favicon-32x32.png" sizes="32x32" type="image/png">
    <link rel="icon" href="/docs/4.4/assets/img/favicons/favicon-16x16.png" sizes="16x16" type="image/png">
    <link rel="manifest" href="/docs/4.4/assets/img/favicons/manifest.json">
    <link rel="mask-icon" href="/docs/4.4/assets/img/favicons/safari-pinned-tab.svg" color="#563d7c">
    <link rel="icon" href="/docs/4.4/assets/img/favicons/favicon.ico">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <meta name="msapplication-config" content="/docs/4.4/assets/img/favicons/browserconfig.xml">
    <meta name="theme-color" content="#563d7c">
    <meta name="viewport" content="width=device-width, initial-scale=1">


</head>
<body>
<nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0">LogApp Driver</a>
    <img src="/assets/img/acc.jpg" width="30" height="30" class="imgUpstairs">

    <div class="dropdown"  >
        <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown" style="padding-left: 1rem;
        background-color: #212529; color: lightgrey;font-size: 18px; border-color: #212529;left: 35em; width: auto;height: auto ">
            <spring:message code="chooseLanguage"/>
        </button>
        <div class="dropdown-menu" style="text-align: center">
            <a class="dropdown-item" href="/?lang=en"><img src="/assets/img/GB-flag.png" width="15" height="15" class="flagimg" > English </a>
            <a class="dropdown-item" href="/?lang=de"> <img src="/assets/img/DEflag.png" width="15" height="15" class="flagimg" >Deutsch</a>
        </div>
    </div>

    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link">
                <spring:message code="logout"/></a>
        </li>
    </ul>
</nav>


<div class="container-fluid">

    <div class="row">

        <nav class="col-md-2 d-none d-md-block bg-light sidebar">
            <div class="sidebar-sticky">

                <ul class="nav flex-column">
                    <li>

                        <a href="/myOrder/">
                            <span data-feather="home" class="dropDownWord"><spring:message code="myAccount"/></span></a>
                        </a>
                    </li>
                    <div id="trucks" class="menu">

                        <li class="nav-item">
                            <a href="/myOrder/editOrder/${driver.assignedOrder.orderId}">
                                <p id="truckDropDown" class="dropDownWord"><spring:message code="myOrder"/></p>
                            </a>

                        </li>
                    </div>
                </ul>
            </div>
        </nav>
    </div>
</div>

<div id="content-wrapper" class="d-flex flex-column">
    <div class="container-fluid">
        <div id="contentPanel" style="position:absolute; left:225px;">
            <table class="table table-condensed">
                <tr>
                    <td><spring:message code="privateNumber"/></td>
                    <td>${driver.driverPrivateNum}</td>
                </tr>
                <tr>
                    <td><spring:message code="currentStatus"/>:</td>
                    <td>
                        <c:choose>
                            <c:when test="${driver.driverStatus=='CARGO_HANDLING'}">
                                <spring:message code="cargoHandling"/>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${driver.driverStatus=='REST_ON_SHIFT'}">
                                        Rest
                                    </c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${driver.driverStatus=='DRIVING'}">
                                                <spring:message code="driving"/>
                                            </c:when>
                                            <c:otherwise>
                                                <c:choose>
                                                    <c:when test="${driver.driverStatus=='CO_DRIVER'}">
                                                        <spring:message code="co-driver"/>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <c:choose>
                                                            <c:when test="${driver.driverStatus=='REST'}">
                                                                <spring:message code="offWork"/>
                                                            </c:when>
                                                            <c:otherwise></c:otherwise>
                                                        </c:choose>
                                                    </c:otherwise>
                                                </c:choose>
                                            </c:otherwise>
                                        </c:choose>

                                    </c:otherwise>
                                </c:choose>

                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>

                <tr>
                    <td><spring:message code="truckNumber"/>:</td>
                    <td>${driver.truckRegNumber}</td>
                </tr>
                <tr>
                    <td><spring:message code="order"/>:</td>
                    <c:choose>
                        <c:when test="${driver.assignedOrder!=null}">
                            <td>${driver.assignedOrder.orderId}</td>
                        </c:when>
                        <c:otherwise>
                            <td><spring:message code="noOrders"/></td>
                        </c:otherwise>

                    </c:choose>
                </tr>
                <c:choose>
                <c:when test="${driver.assignedOrder!=null}">
                <tr>
                    <td><spring:message code="orderPoints"/>:</td>
                    <td>
                        <c:forEach items="${pointList}" var="point">
                            ${pointMap.get(point)} <br>
                        </c:forEach>
                    </td>
                </tr>

                <tr>
                    <td><spring:message code="coDriversPrivNum"/>:</td>
                    <c:choose>
                        <c:when test="${driverList.size()>1}">
                            <td>
                                <c:forEach items="${driverList}" var="driverId">
                                    <c:if test="${!driverId.equals(driver.driverId)}">
                                        ${driverMap.get(driverId)} <br>
                                    </c:if>
                                </c:forEach></td>
                        </c:when>
                        <c:otherwise>
                            <td><spring:message code="noCoDrivers"/></td>
                        </c:otherwise>

                    </c:choose>

                    </c:when>

                    </c:choose>
                </tr>


                <c:choose>
                    <c:when test="${driver.driverStatus == 'CARGO_HANDLING' || driver.driverStatus == 'REST_ON_SHIFT'
                || driver.driverStatus =='DRIVING' || driver.driverStatus == 'CO_DRIVER'}">
                        <td>
                            <form action="${pageContext.request.contextPath}/myOrder/setOnRest" method="post">
                                <input type="submit" value="<spring:message code="finishShift"/>"/>
                            </form>
                        </td>
                    </c:when>
                    <c:otherwise>
                        <td>
                            <form action="${pageContext.request.contextPath}/myOrder/setOnShift" method="post">
                                <input type="submit" value="<spring:message code="startShift"/>"/>
                            </form>
                        </td>

                    </c:otherwise>

                </c:choose>

                <c:if test="${driver.driverStatus!='REST'}">
                    <tr>
                        <td>
                            <spring:message code="setStatus"/>
                        </td>
                        <td>
                            <springForm:form action="${pageContext.request.contextPath}/myOrder/setNewStatus"
                                             method="POST"
                                             modelAttribute="driver">

                                <c:forEach items="${statusEnum}" var="status">
                                    <c:choose>
                                        <c:when test="${status=='CARGO_HANDLING'}">
                                            <input type="radio" name="driverStatus"
                                                   value="${'CARGO_HANDLING'}"/> <spring:message code="cargoHandling"/>
                                            <br>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${status=='REST_ON_SHIFT'}">
                                                    <input type="radio" name="driverStatus"
                                                           value="${'REST_ON_SHIFT'}"/> Rest
                                                    <br>
                                                </c:when>
                                                <c:otherwise>
                                                    <c:choose>
                                                        <c:when test="${status=='DRIVING'}">
                                                            <input type="radio" name="driverStatus"
                                                                   value="${'DRIVING'}"/> <spring:message code="driving"/>
                                                            <br>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <c:choose>
                                                                <c:when test="${status=='CO_DRIVER'}">
                                                                    <input type="radio" name="driverStatus"
                                                                           value="${'CO_DRIVER'}"/> <spring:message code="co-driver"/>
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
                </c:if>

            </table>

        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" type="text/javascript"></script>
<script src="/assets/js/currentURLScript.js" type="text/javascript"></script>


<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>

</body>

</body>
</html>
