<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Добавить клиента</title>
</head>
<body>
<h1>Добавить клиента</h1>
<form action="addClient" method="post">
    Имя: <input type="text" name="name" required><br>
    Телефон: <input type="text" name="phone" required><br>
    <input type="submit" value="Добавить клиента">
</form>
<a href="index.jsp">Назад</a>
</body>
</html>
