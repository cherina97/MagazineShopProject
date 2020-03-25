<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <link rel="stylesheet" href="css/cabinet.css">

    <title>Cabinet</title>
</head>
<body>

<div class="page-wrapper chiller-theme toggled">
    <!-- sidebar-wrapper  -->
    <jsp:include page="sidebar.jsp"></jsp:include>

    <main class="page-content">
        <div class="container-fluid">
            <form id="form1">
                <fieldset>
                    <div class="input-block">
                        <label for="productName">Name:</label>
                        <input id="productName" type="text" required>
                    </div>
                    <div class="input-block">
                        <label for="productDescription">Description:</label>
                        <input id="productDescription" type="text" required>
                    </div>
                    <div class="input-block">
                        <label for="productPrice">Price:</label>
                        <input id="productPrice" type="number" required>
                    </div>
                </fieldset>
                <button type="submit" class="btn-createProduct">Create product</button>
            </form>
        </div>
    </main>

</div>


<script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

<script src="js/addProduct.js"></script>

</body>
</html>