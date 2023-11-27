<%--
  Created by IntelliJ IDEA.
  User: tkach
  Date: 27.11.2023
  Time: 14:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<style>
    table, th, td {
        border: 1px solid black;
    }
</style>
<head>
    <title>Title</title>
</head>
<body>
<table>
    <tr>
        <th>uuid</th>
        <th>full_name</th>
    </tr>
    <tr>
        <td>${uuid1}</td>
        <td>${full_name1}</td>
    </tr>
    <tr>
        <td>${uuid2}</td>
        <td>${full_name2}</td>
    </tr>
    <tr>
        <td>${uuid3}</td>
        <td>${full_name3}</td>
    </tr>
</table>
</body>
</html>
