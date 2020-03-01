<%--
  Created by IntelliJ IDEA.
  User: Андрей
  Date: 01.03.2020
  Time: 19:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>

<html>
<head>
    <title>Add Truck</title>
</head>
<body>
<form action="addTruck" method="post">
    Registration Number:<input type="text" name="regNumber"/><br/>
    Driver Working Hours:<input type="text" name="driverWorkingHours"/><br/>
    Capacity:<input type="text" name="capacityKg"/><br/>
    Condition:<input type="text" name="condition"/><br/>
    Current City Id:<input type="text" name="currentCityId"/><br/>

</form>

</body>
</html>
