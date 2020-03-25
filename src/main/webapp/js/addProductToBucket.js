$("button.btn-addToBucket")
    .click
    (function (event) {
    event.preventDefault();

    var productId = $("button.btn-addToBucket").attr("product-id");

    $.post("api/buckets", {productId})
        .done(function () {
            alert("Product is being successfully added to bucket");
        })
        .fail(function () {
            alert("error");
        });
});