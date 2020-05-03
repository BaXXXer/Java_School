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
<%--    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"--%>
<%--          integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">--%>

    <link rel="canonical" href="https://getbootstrap.com/docs/4.4/examples/dashboard/">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh"
          crossorigin="anonymous">

    <link href="/assets/css/dashboard.css" rel="stylesheet">

    <!-- Favicons -->
    <link rel="apple-touch-icon" href="/docs/4.4/assets/img/favicons/apple-touch-icon.png" sizes="180x180">
    <link rel="icon" href="/docs/4.4/assets/img/favicons/favicon-32x32.png" sizes="32x32" type="image/png">
    <link rel="icon" href="/docs/4.4/assets/img/favicons/favicon-16x16.png" sizes="16x16" type="image/png">
    <link rel="manifest" href="/docs/4.4/assets/img/favicons/manifest.json">
    <link rel="mask-icon" href="/docs/4.4/assets/img/favicons/safari-pinned-tab.svg" color="#563d7c">
    <link rel="icon" href="/docs/4.4/assets/img/favicons/favicon.ico">
<%--    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">--%>
    <meta name="msapplication-config" content="/docs/4.4/assets/img/favicons/browserconfig.xml">
    <meta name="theme-color" content="#563d7c">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src="http://maps.google.com/maps/api/js?key=AIzaSyDBKByqyWkhK4TSbqwOOXzFtWnXHwYrhfw&language=en&region=GB"
            type="text/javascript"></script>


</head>
<body>
<nav class="navbar navbar-dark fixed-top bg-dark flex-md-nowrap p-0 shadow">
    <a class="navbar-brand col-sm-3 col-md-2 mr-0">LogApp Driver</a>
    <img src="/assets/img/acc.jpg" width="30" height="30" class="imgUpstairs">
    <a href="?lang=en"><img src="/assets/img/GB-flag.png" width="20" height="20" class="flagimg"></a>
    <a href="?lang=de"><img src="/assets/img/DEflag.png" width="20" height="20" class="flagimg"></a>

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


            <table class="table table-striped">
                <h3><spring:message code="order"/> #${order.orderId}</h3><br>

                <th scope="col">Id</th>
                <th scope="col">Name</th>
                <th scope="col"><spring:message code="operationType"/></th>
                <th scope="col"><spring:message code="city"/></th>
                <th scope="col"><spring:message code="cargoTitle"/></th>
                <th scope="col"><spring:message code="cargoWeight"/></th>
                <th scope="col"><spring:message code="cargoStatus"/></th>
                <th scope="col"><spring:message code="edit"/></th>


                <c:forEach items="${order.points}" var="point">

                    <tr>

                        <td>${point.id}</td>

                        <td>
                                ${point.name}
                        </td>
                        <td>
                                ${point.operationType}
                        </td>
                        <td>
                                ${point.destCity.cityName}
                        </td>

                        <td>
                                ${point.cargo.title}
                        </td>


                        <td>
                                ${point.cargo.cargoWeightKilos}
                        </td>

                        <td>
                                ${point.cargo.cargoStatus}
                        </td>

                        <c:choose>


                            <c:when test="${point.operationType.toString() == 'LOAD'}">
                                <td>
                                    <form action="/myOrder/setLoaded/${order.orderId}/${point.id}" method="post">
                                        <input type="submit" value="<spring:message code="setLoaded"/>"/>
                                    </form>
                                </td>
                            </c:when>
                            <c:otherwise>
                                <c:choose>
                                    <c:when test="${point.cargo.cargoStatus!='DELIVERED'}">
                                        <td>
                                            <form action="/myOrder/setUnloaded/${order.orderId}/${point.id}" method="post">
                                                <input type="submit" value="<spring:message code="setUnloaded"/>"/>
                                            </form>
                                        </td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><spring:message code="pointIsComleted"/></td>
                                    </c:otherwise>
                                </c:choose>
                            </c:otherwise>
                        </c:choose>
                    </tr>

                </c:forEach>

            </table>
            <div id="googleMapAPI" style="width: auto; height: 300px;"></div>

            <script type="text/javascript">

                var cityList = ${cityJsonList};

                var map = new google.maps.Map(document.getElementById('googleMapAPI'), {
                    zoom: 6,
                    center: new google.maps.LatLng(cityList[0].lat, cityList[0].lng),
                    mapTypeId: google.maps.MapTypeId.ROADMAP
                });

                var infowindow = new google.maps.InfoWindow();

                var marker, i;

                for (i = 0; i < cityList.length; i++) {
                    marker = new google.maps.Marker({
                        position: new google.maps.LatLng(cityList[i].lat, cityList[i].lng),
                        map: map
                    });

                    google.maps.event.addListener(marker, 'click', (function(marker, i) {
                        return function() {
                            infowindow.open(map, marker);
                        }
                    })(marker, i));
                }
            </script>

        </div>
    </div>
</div>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js" type="text/javascript"></script>

<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
        integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo"
        crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
        integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6"
        crossorigin="anonymous"></script>

</body>

</body>
</html>
