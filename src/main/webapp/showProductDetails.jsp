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

        <div class="detailsWrapper">

            <p class="card-title">Product name:  <%= request.getAttribute("productName")%>
            </p>
            <p>Product description: <%= request.getAttribute("productDescription")%>
            </p>
            <p>Product price: <%= request.getAttribute("productPrice")%> грн</p>

            <button type="submit" class="btn-addToBucket"
                    product-id="<%=request.getAttribute("productId")%>">Add to bucket</button>
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <b>Success!</b> Product is being successfully added to bucket!
                <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <br>
            <a href="${pageContext.request.contextPath}/showAllProducts" class='card-link'>Back to all products</a>
        </div>


    </main>

</div>

<!-- Optional JavaScript -->
<!-- jQuery first, then Popper.js, then Bootstrap JS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q"
        crossorigin="anonymous"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
        crossorigin="anonymous"></script>


<script src="js/showAllProducts.js"></script>
<script src="js/addProductToBucket.js"></script>
</body>
</html>