<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="springForm" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
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

    <ul class="navbar-nav px-3">
        <li class="nav-item text-nowrap">
            <a href="${pageContext.request.contextPath}/logout" class="nav-link">Sign out</a>
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
                            <span data-feather="home" class="dropDownWord">Orders</span>
                        </a>
                    </li>
                    <div id="trucks" class="menu">

                        <li class="nav-item">
                            <a href="/trucks/allTrucks">
                                <p id="truckDropDown" class="dropDownWord">Trucks</p>
                            </a>
                            <%--                            <div class="dropMenu">--%>
                            <%--                                <ul class="subnav">--%>
                            <%--                                    <li><a href="/trucks/addTruck" class="subnav-link">Create new truck</a></li>--%>
                            <%--                                    <li><a href="/trucks/allTrucks" class="subnav-link">Get truck summary</a></li>--%>
                            <%--                                </ul>--%>
                            <%--                            </div>--%>
                        </li>
                    </div>

                    <div id="drivers" class="menu">

                        <li class="nav-item">
                            <a href="/drivers/allDrivers">
                                <p id="driverDropDown" class="dropDownWord">Drivers</p>
                            </a>
                            <%--                            <div class="dropMenu">--%>
                            <%--                                <ul class="subnav">--%>
                            <%--                                    <li><a href="/drivers/addDriver" class="subnav-link">Create new driver</a></li>--%>
                            <%--                                    <li><a href="/drivers/allDrivers" class="subnav-link">Get driver summary</a></li>--%>
                            <%--                                </ul>--%>
                            <%--                            </div>--%>
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

<c:choose>
    <c:when test="${!empty truckList}">

            <h1>Truck List</h1>

                <table class="table table-striped">
                    <tr>
                        <thead>

                        <th scope="col">Truck Id</th>

                        <th scope="col">Truck Registration Number</th>
                        <th scope="col">Driver Working Hours</th>
                        <th scope="col">Truck capacity (Kg)</th>
                        <th scope="col">Condition</th>
                        <th scope="col">Current city</th>
                        <th scope="col">Assign</th>
                        </thead>

                    </tr>
                    <c:forEach items="${truckList}" var="truck">
                        <tr>

                            <td>${truck.id}</td>
                            <td>${truck.regNumber}</td>
                            <td>${truck.driverWorkingHours}</td>
                            <td>${truck.capacityTons*1000}</td>
                            <td>${truck.condition}</td>

                            <td>
                                <c:forEach var="hash" items="${cityMap}">
                                    <c:if test="${hash.key == truck.currentCityId}">
                                        ${hash.value}
                                    </c:if>
                                </c:forEach>
                            </td>

                            <td>
                                <a href="<c:url value='${order.orderId}/${truck.id}'/>" class="btn btn-success"
                                   style="width: 100px;height: 30px;font-size: 12px"/>
                                Assign
                            </td>
                        </tr>

                    </c:forEach>


                    </thead>
                    </tr>
                </table>
            </c:when>
    <c:otherwise>
        <h1 style="text-align: center">There are no suitable trucks for this order at the moment!</h1>
    </c:otherwise>
</c:choose>


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
