<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Sign In</title>
</head>
<body>

<form action="login" method="post">
    <label for="email">Email:</label> <input type="email" name="email">
    <br>
    <label for="password">Password:</label> <input type="password" name="password">
    <br>
    <input type="submit" value="Sign In">
</form>
<br>
<a href="index.jsp" class="btn">Back to index page</a>
</body>
</html>