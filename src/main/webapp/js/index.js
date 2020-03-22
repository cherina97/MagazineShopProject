$(".switcher").click(function () {
    formSwitcher(this);
});

function formSwitcher(obj){
    var btnId = $(obj).attr("id");

    if (btnId == "sign-in-btn"){
        $("#sign-up-btn").parent().toggleClass("is-active");
        $("#sign-in-btn").parent().addClass('is-active');
    } else if (btnId == "sign-up-btn"){
        $("#sign-in-btn").parent().toggleClass("is-active");
        $("#sign-up-btn").parent().addClass('is-active');
    }
}

$("button.btn-signup")
    .click(
        function (event) {
            event.preventDefault();
            var firstName = $("form.form-signup input#signup-firstname").val();
            var lastName = $("form.form-signup input#signup-lastname").val();
            var email = $("form.form-signup input#signup-email").val();
            var password = $("form.form-signup input#signup-password").val();
            if (firstName == '' || lastName == '' || email == '' || password == '') {
                alert("Please fill all fields!");
            } else if ((password.length) < 8) {
                alert("Password should at least 8 character in length!");
            } else {
                var userRegistration = {
                    firstName,
                    lastName,
                    email,
                    password
                };

                $.post("register", userRegistration)
                    .done(function (data, textStatus, xhr) {
                        if (xhr.status === 201) {
                            $("form")[0].reset();
                            $("form")[1].reset();
                           formSwitcher($("#sign-in-btn"));
                        } else {
                            alert("Error while creating a user!");
                        }
                    })
                    .fail(function () {
                        alert("Error while creating a user!");
                    });
                ;
            }
        });


$("button.btn-login").click(function (event) {
    // need to prevent default behaviour of the button which caused page reload
    event.preventDefault();

    var email = $("form.form-login input#login-email").val();
    var password = $("form.form-login input#login-password").val();

    if (email == '' || password == '') {
        alert("Please fill login form!");
    } else {
        var userLogin = {
            email,
            password
        };
        $.post("login", userLogin)
            .done(function (data, textStatus, xhr) {
                if (xhr.status === 200) {
                    window.location = "http://localhost:8081/magazine_shop_project_war/cabinet.jsp";
                } else {
                    alert("Error while authorizing the user!");
                }
            })
            .fail(function () {
                alert("Error while authorizing the user!");
            });
    }
});