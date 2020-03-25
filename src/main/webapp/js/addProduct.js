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
