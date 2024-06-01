<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Отпустить товар</title>
</head>
<body>
<h1>Отпустить товар</h1>
<form action="sellProduct" method="post">
    ID товара: <input type="number" name="productId" required><br>
    Количество: <input type="number" name="quantity" required><br>
    Имя клиента: <input type="text" name="clientName" required><br>
    Телефон клиента: <input type="text" name="clientPhone" required><br>
    <input type="submit" value="Отпустить товар">
</form>
<c:if test="${not empty error}">
    <p style="color: red;">${error}</p>
</c:if>
<a href="index.jsp">Назад</a>
</body>
</html>
