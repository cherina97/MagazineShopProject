function showAlertSuccess() {
    $('.alert-success').show("slow");
}

$("button.btn-addToBucket")
    .click
    (function (event) {
    event.preventDefault();

    var productId = $("button.btn-addToBucket").attr("product-id");

    $.post("api/buckets", {productId})
        .done(function () {
            showAlertSuccess();
        })
        .fail(function () {
            alert("error");
        });
});