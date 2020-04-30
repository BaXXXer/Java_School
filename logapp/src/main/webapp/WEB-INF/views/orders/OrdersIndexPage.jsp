<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
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
    <a class="navbar-brand col-sm-3 col-md-2 mr-0">LogApp Manager</a>
    <img src="/assets/img/truck1.png" width="50" height="30" class="imgUpstairs">
    <a href="/?lang=en"><img src="/assets/img/GB-flag.png" width="20" height="20" class="flagimg"></a>
    <a href="/?lang=de"><img src="/assets/img/DEflag.png" width="20" height="20" class="flagimg"></a>

    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link"><spring:message code="logout" /></a>
        </li>
    </ul>
</nav>


<div class="container-fluid">

    <div class="row">

        <nav class="col-md-2 d-none d-md-block bg-light sidebar">
            <div class="sidebar-sticky">

                <ul class="nav flex-column">
                    <li>

                        <a href="${pageContext.request.contextPath}/orders/allOrders">
                            <span data-feather="home" class="dropDownWord"><spring:message code="ordersNavLink" /></span>
                        </a>
                    </li>
                    <div id="trucks" class="menu">

                        <li class="nav-item">
                            <a href="/trucks/allTrucks">
                                <p id="truckDropDown" class="dropDownWord"><spring:message code="trucksNavLink" /></p>
                            </a>

                        </li>
                    </div>

                    <div id="drivers" class="menu">

                        <li class="nav-item">
                            <a href="/drivers/allDrivers">
                                <p id="driverDropDown" class="dropDownWord"><spring:message code="driverNavLink" /></p>
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
        <div id="contentPanel" style="position:relative;">
            <h1>
                <spring:message code="ordersDashboard" />

                <form action="${pageContext.request.contextPath}/orders/addOrder" method="post"
                      style="width:200px;height:48px;margin:0px">

                    <input type="submit" value="<spring:message code="addEmptyOrder" />" id="addNewOrderButton"/>
                </form>
            </h1>
            <br>
            <c:if test="${orders.size()==0}">
                <h3><spring:message code="noOrders" /></h3>
            </c:if>

            <c:forEach items="${orders}" var="order">
            <table class="table table-hover" style="background-color:lightgrey">
                <h3 style="text-align: center"><u><spring:message code="order" /> #${order.orderId}</u></h3>
                <p><i class="fa fa-check-square-o"
                      style="font-size:24px;color:royalblue;text-shadow:2px 2px 4px #000000"></i>
                    <b><spring:message code="orderStatus" /></b>
                    <c:choose>
                        <c:when test="${order.orderIsDone==true}">
                            <b style="color: green; font-style: italic"> <spring:message code="completed" /> </b>
                        </c:when>
                        <c:otherwise>
                            <b style="color: red; font-style: italic"><spring:message code="notCompleted" /></b>
                        </c:otherwise>
                    </c:choose>
                </p>
                <p><i class="fa fa-vcard-o" style="font-size:24px;color:royalblue;text-shadow:2px 2px 4px #000000"></i>
                    <b><spring:message code="assignedDrivers" /></b>
                    <c:forEach items="${order.driversOnOrderIds}" var="driverId">
                        ${driverService.getDriverById(driverId).driverPrivateNum}:
                        ${driverService.getDriverById(driverId).driverFirstName}
                        ${driverService.getDriverById(driverId).driverSurname}
                        <br>
                    </c:forEach></p>
                <p><i class="fa fa-truck" style="font-size:24px;color:royalblue;text-shadow:2px 2px 4px #000000"></i></i>
                    <b><spring:message code="assignedTruck" /></b>
                    <c:choose>
                        <c:when test="${order.truckId!=0}">
                            ${truckMap.get(order.truckId)}
                        </c:when>
                        <c:otherwise>
                            <spring:message code="noAssignedTruck" />
                        </c:otherwise>
                    </c:choose>
                </p>

                <p><i class='fas fa-map-marker-alt'
                      style='font-size:24px;color:royalblue;text-shadow:2px 2px 4px #000000'></i>
                    <b><spring:message code="currentCity" /></b>
                    <c:choose>
                        <c:when test="${order.truckId!=null}">
                            ${cityService.getCityDtoById(truckService.getTruckById(order.truckId).currentCityId).cityName}
                        </c:when>
                        <c:otherwise>
                            <spring:message code="noAssignedTruck" />
                        </c:otherwise>
                    </c:choose>
                </p>

                <table class="table table-hover">
                    <th scope="col"><spring:message code="cargoCode" /></th>
                    <th scope="col"><spring:message code="cargoTitle" /></th>
                    <th scope="col"><spring:message code="cargoWeight" /></th>
                    <th scope="col"><spring:message code="cargoCity" /></th>
                    <th scope="col"><spring:message code="cargoDestCity" /></th>
                    <th scope="col"><spring:message code="cargoStatus" /></th>

                    <c:if test="${order.truckId==null}">
                    <a class="btn btn-secondary btn-sm" href="<c:url value='./readyToGoTrucks/${order.orderId}' />">
                        <spring:message code="getReadyTrucksButton" /> <i class="fa fa-truck" style="font-size:18px;color:white;"></i></a>
                    <emsp>
                        </c:if>
                        <c:if test="${order.truckId!=null}">
                        <a class="btn btn-secondary btn-sm" data-target="#myModal"
                           href="<c:url value='./readyForTripDrivers/${order.orderId}' />">
                            <spring:message code="getReadyDriversButton" /><i class="fa fa-vcard-o"
                                                style="font-size:18px;color:white;margin-left:4px"></i></a>
                        <emsp>

                            </c:if>
                            <a class="btn btn-secondary btn-sm"
                               href="<c:url value='./notAssignedCargoes/${order.orderId}' />">
                                <spring:message code="getCargoes" /><i
                                    class='fas fa-luggage-cart' style="font-size:18px;color:white;margin-left:2px"></i></a>
                            <emsp>

                                <br>

                                <c:forEach items="${order.points}" var="point">
                                <tr>
                                    <td>${point.cargo.cargoName}</td>
                                    <td>${point.cargo.title}</td>
                                    <td>${point.cargo.cargoWeightKilos}</td>
                                    <td>${point.cargo.currentCity.cityName}</td>
                                    <td>${point.destCity.cityName}</td>
                                    <td>${point.cargo.cargoStatus}</td>
                                </tr>


                                </c:forEach>
                </table>
                </c:forEach>
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
