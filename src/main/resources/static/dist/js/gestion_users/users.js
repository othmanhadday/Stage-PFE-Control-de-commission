
$(document).ready(function () {
    var pwd = $("#passwordGenerer").val();
    var userNom = $("#userNom").val();
    if (pwd != null && userNom != null) {
        Swal.fire("", "username is : " + userNom + ' new password : ' + pwd, "success"
        )
    }
});

// show modal to add new user
$(document).on('click', '#newUser', function () {
    $("#user_id").val("");
    $("#name").val("");
    $("#userName").val("");
    $("#email").val("");
    $(".modal-title").text("Create nouveau utilisateur")
    $('#myModalCreate').modal('show');
})

// show modal and update user
$(document).on('click', '.editBtn', function () {
    $("#user_id").val("");
    $("#name").val("");
    $("#userName").val("");
    $("#email").val("");
    $(".modal-title").text("Modifier utilisateur")

    $('#myModalCreate').modal('show');
    var id = $(this).attr('id');
    $.ajax({
        type: 'get',
        url: "/user/" + id,
        dataType: "json",
        success: function (data) {
            $("#user_id").val(data.id);
            $("#name").val(data.nom);
            $("#userName").val(data.username);
            $("#email").val(data.email);
            $('#myModalCreate').modal('show');
        },
        error: function (data) {
            console.log(data);
        }
    });
});

// show modal and delete user item
$(document).on('click', '.deleteBtn', function () {
    var id = $(this).attr('id');
    var textWithId = " " + id;
    $(".modal-title").text("Supprimer un utilisateur");
    $('.deleteMessage span').text(textWithId);
    $('#deleteItem').attr('href', '/user/delete/' + id)
    $('#myModalDelete').modal('show');
});

//Activate User
$(document).on('click', '.activateRole', function () {
    var id = $(this).attr('id');
    $(".modal-title").text("Affectation les profiles");
    $.ajax({
        type: 'get',
        url: "/user/" + id,
        dataType: "json",
        success: function (data) {
            $("#showNom").text(data.nom);
            $("#showUsername").text(data.username);
            $("#activate_user_id").val(data.id);
            $('#showEmail').text(data.email);
            $('#modalActivateUser').modal('show');
        },
        error: function (data) {
            console.log(data);
        }
    });
});

// reinitialiser le mot de passe
$(document).on('click', '.reinitialiserPwd', function () {
    var id = $(this).attr('id');
    $(".modal-title").text("Réinitialiser mot de passe")
    $('#reinitialiserPwdBtn').attr('href', "/user/reinitialiser/" + id)
    $('#modalReinitialisePwd').modal('show');
});

// affecte permission to user
$(document).on('click', '.affectePermission', function () {
    var id = $(this).attr('id');
    $(".modal-title").text("Affectation les permissions")
    $("input[id='permissionUpdate']").each(function (index, val) {
        $("input[id='permissionUpdate']")[index].checked = false;
    })
    $.ajax({
        type: 'get',
        url: "/user/" + id,
        dataType: "json",
        success: function (data) {
            $("#showNomPemission").text(data.nom);
            $("#showUsernamePermission").text(data.username);
            $('#showEmailPermission').text(data.email);
            $("#permission_user_id").val(data.id);
            $('#modalPemissionUser').modal('show');

            $.each(data.permissions, function (i, v) {
                console.log(v);
                $("input[id='permissionUpdate']").each(function (index, val) {
                    if (v.id == $(this).val()) {
                        $("input[id='permissionUpdate']")[index].checked = true;
                    }
                })
            })
        },
        error: function (data) {
            console.log(data);
        }
    });
})
