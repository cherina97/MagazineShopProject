
$(window).on("load resize ", function () {
    var scrollWidth = $('.tbl-content').width() - $('.tbl-content table').width();
    $('.tbl-header').css({'padding-right': scrollWidth});
}).resize();

function showAlertSuccess() {
    $('.alert-success').show();
}

$.get("api/buckets")
    .done(function (data) {
        var tableContent = "";
        jQuery.each(data, function (i, item) {
            var number = i + 1;
            tableContent +=
                "<tr>" +
                "<th scope=\"row\">" + number + "</th>" +
                "<td>" + item.product.name + "</td>" +
                "<td>" + item.product.description + "</td>" +
                "<td class=\"pricePrCount\">" + item.product.price + "</td>" +
                "<td>" + item.purchase_date + "</td>" +
                "<td>" +
                "<button type=\"button\" class=\"remove-from-bucket\" bucket-id = '"+ item.id + "'> Remove </button>" + "</td>" +
                "</tr>";
        });
        $('.tbl-content').html(tableContent);
        addListenerToRemoveButton();
    })
    .fail(function () {
        alert("Can't remove from bucket");
    });

function addListenerToRemoveButton(){
    $("button.remove-from-bucket")
        .click(function (event) {
            event.preventDefault();

            var bucketId = event.target.attributes["bucket-id"].value;

            $.ajax({
                url: 'api/buckets?bucketId=' + bucketId,
                type: 'DELETE'
            })
                .done(function () {
                    showAlertSuccess();
                    setTimeout(location.reload.bind(location), 600);
                })
                .fail(function () {
                    alert("Error while deleting product from a bucket");
                });
        })
};
