<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Добавить товар</title>
</head>
<body>
<h1>Добавить товар</h1>
<form action="addProduct" method="post">
    Название: <input type="text" name="name" required><br>
    Тип: <input type="text" name="type" required><br>
    Количество: <input type="number" name="quantity" required><br>
    ID поставщика: <input type="number" name="supplierId" required><br>
    <input type="submit" value="Добавить товар">
</form>
<a href="index.jsp">Назад</a>
</body>
</html>
