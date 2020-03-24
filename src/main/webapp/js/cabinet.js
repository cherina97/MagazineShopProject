jQuery(function ($) {

    $(".sidebar-dropdown > a").click(function() {
        $(".sidebar-submenu").slideUp(200);
        if (
            $(this)
                .parent()
                .hasClass("active")
        ) {
            $(".sidebar-dropdown").removeClass("active");
            $(this)
                .parent()
                .removeClass("active");
        } else {
            $(".sidebar-dropdown").removeClass("active");
            $(this)
                .next(".sidebar-submenu")
                .slideDown(200);
            $(this)
                .parent()
                .addClass("active");
        }
    });

    $("#close-sidebar").click(function() {
        $(".page-wrapper").removeClass("toggled");
    });
    $("#show-sidebar").click(function() {
        $(".page-wrapper").addClass("toggled");
    });

});

//display form with adding a new product
function disp(form) {
    if (form.style.display == "none") {
        form.style.display = "block";
    } else {
        form.style.display = "none";
    }
}

//create new product
$("button.btn-createProduct")
    .click(
        function (event) {
            event.preventDefault();
            var name = $("input#productName").val();
            var description = $("input#productDescription").val();
            var price = $("input#productPrice").val();

            var product = {
                name: name,
                description: description,
                price: price
            };
            $.post("api/products", product)
                .done(function (data, textStatus, xhr) {
                    alert('Success');
                    $("form")[0].reset();
                })
                .fail(function (data, textStatus, xhr) {
                    alert(data.responseText);
                });
        });

//logout from cabinet
$("button.btn-logout")
    .click(
        function () {

    $.get("logout")
        .done(function (data, textStatus, xhr) {
            window.location = window.origin + "/MagazineShop_war/index.jsp";
        })
        .fail(function () {
            alert("Can't logout");
        });
});

//display div with all products
function disp(div) {
    if (div.style.display == "none") {
        div.style.display = "block";
    } else {
        div.style.display = "none";
    }
}

