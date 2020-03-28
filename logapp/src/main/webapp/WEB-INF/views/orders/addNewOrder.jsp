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


</head>
<body>
<h3>Enter order details below:</h3>

<springForm:form action="./addOrder" method="POST">


    <table>



        <p>
            Is order completed?
            <input type="radio" name="orderIsDone" value="Yes">
            Yes
            <input type="radio" name="orderIsDone" value="No">
            No
        </p>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="action_id">Action Id</label>
            <div class="col-md-5">
                <input id="action_id" name="action_id" type="text" placeholder="" class="form-control input-md">

            </div>
        </div>
        <br><br>
        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="action_name">Action Name</label>
            <div class="col-md-5">
                <input id="action_name" name="action_name" type="text" placeholder="" class="form-control input-md">

            </div>
        </div>
        <br><br>
        <!-- File Button -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="action_json">Action JSON File</label>
            <div class="col-md-4">
                <input type="file" id="action_json" name="action_json" class="input-file" accept=".txt,.json">
                <div id="action_jsondisplay"></div>
            </div>
        </div>

        </div>
        </div>
        <!-- Button -->
        <div class="form-group">
            <div class="col-md-4">
                <button id="add-more" name="add-more" class="btn btn-primary">Add More</button>
            </div>
        </div>
        <br><br>

        </div>
        </div>
        </div>



                <tr>
                    <td>Point 1:</td>
                    <td>
                        <select name="pointFromId">
                            <option label="---Select city---">
                                <c:forEach items="${pointList}" var="point">
                            <option value=${point.id}>${point.name}</option>
                            </c:forEach>

                        </select>
                    </td>
                </tr>

                <tr>
                    <td>Point 2:</td>
                    <td>
                        <select name="pointToId">
                            <option label="---Select point---">
                                <c:forEach items="${pointList}" var="point">
                            <option value=${point.id}>${point.name}</option>
                            </c:forEach>

                        </select>
                    </td>
                </tr>


                <tr>
                    <td>Select Truck:</td>
                    <td>
                        <label>
                            <select name="truckId">
                                <option label="---Select truck---">
                                    <c:forEach items="${truckList}" var="truck">
                                <option value=${truck.id}>${truck.regNumber}</option>
                                </c:forEach>
                            </select>
                        </label>
                    </td>
                </tr>

<%--                <tr>--%>
<%--                <td>Select first driver:</td>--%>
<%--                <td>--%>
<%--                    <select name="driverId1">--%>
<%--                        <option label="---Select driver---">--%>
<%--                            <c:forEach items="${driverList}" var="driver">--%>
<%--                        <option value=${driver.driverId}>${driver.driverSurname}</option>--%>
<%--                        </c:forEach>--%>
<%--                    </select>--%>
<%--                </td>--%>
<%--            </tr>--%>

<%--                <tr>--%>
<%--                    <td>Select second driver:</td>--%>
<%--                    <td>--%>
<%--                        <select name="driverId2">--%>
<%--                            <option label="---Select driver---">--%>
<%--                                <c:forEach items="${driverList}" var="driver">--%>
<%--                            <option value=${driver.driverId}>${driver.driverSurname}</option>--%>
<%--                            </c:forEach>--%>
<%--                        </select>--%>
<%--                    </td>--%>
<%--                </tr>--%>





        <tr>
            <td><input type="submit" value="Submit"/></td>
        </tr>




    </table>
</springForm:form>

<a class="btn btn-primary" href="
    ${pageContext.request.contextPath}./allOrders" role="button">Get all orders</a>
<a class="btn btn-primary" href="${pageContext.request.contextPath}/" role="button">Main page</a>


<script scr="../webjars/bootstrap/4.3.1/js/bootstrap.min.js"></script>
<script scr="../webjars/jquery/3.0.0/jquery.js"></script>
<script type="text/javascript" src="scripts/repeater.js"></script>
</body>
</html>
