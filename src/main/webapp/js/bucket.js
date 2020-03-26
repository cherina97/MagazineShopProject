$(window).on("load resize ", function () {
    var scrollWidth = $('.tbl-content').width() - $('.tbl-content table').width();
    $('.tbl-header').css({'padding-right': scrollWidth});
}).resize();

function addListenerToRemoveButton(){
    $("button.remove-from-bucket")
        .click(function (event) {
            event.preventDefault();

            var productId = event.target.attributes["product-id"].value;

            $.ajax({
                    url: 'api/buckets?productId' + productId,
                    type: 'delete'
            })
                .done(function (pruducts) {
                    alert("Products is being successfully deleted from the bucket");
                    location.reload();
                })
                .fail(function () {
                    alert("Error while deleting product from a bucket");
                });

        })

};

$.get("api/buckets")
    .done(function (products) {
        var tableContent = "";
        jQuery.each(products, function (i, product) {
            var number = i + 1;
            tableContent +=
                "<tr>" +
                "<th scope=\"row\">" + number + "</th>" +
                "<td>" + product.name + "</td>" +
                "<td>" + product.description + "</td>" +
                "<td>" + product.price + "</td>" +
                "<td>" +
                "<button type=\"button\" class=\"remove-from-bucket\" product-id = '"
                + product.id + "'> Remove </button>" + "<td>" +
                "</tr>";
        });
        $('.tbl-content').html(tableContent);
        addListenerToRemoveButton();
    })
    .fail(function () {
        alert("Can't remove from bucket");
    });

