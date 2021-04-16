$(document).ready(function () {
    var errorFeeCat = $("#errorFeeCategorie").text();
    var errorFeeType = $('#errorFeeType').text();
    if (errorFeeCat != "") {
        $('#ModalAddUpdateFeeCategorie').modal('show');
    }
    if (errorFeeType != "") {
        $('#ModalAddUpdateFeeType').modal('show');
    }

});

//********************Categorie Fee************************
//Modal add new Categorie Fee
$(document).on('click', '#addNewFeeCategoriebtn', function () {
    $('#feeCategorieNameInput').val("");
    $('.modal-FeeCategorie-title').text("Ajouter un nouveau Fee Categorie");
    $('#ModalAddUpdateFeeCategorie').modal('show');
});
//Modal update Categorie Fee
$(document).on('click', '.editFeeCategoriebtn', function () {
    $("#errorFeeCategorie").empty();
    $('#feeCategorieNameInput').val("");
    $('#feeCategorieNameInput').removeClass();
    $('#feeCategorieNameInput').addClass("form-control");
    $('.modal-FeeCategorie-title').text("Editer un Fee Categorie");
    var id = $(this).attr('id');
    $('#feeCategorie_id').val(id);

    $.ajax({
        type: 'get',
        url: "/categorieFees/" + id,
        dataType: "json",
        success: function (data) {
            $('#feeCategorieNameInput').val(data.categorieFeeName);
            $('#ModalAddUpdateFeeCategorie').modal('show');
        },
        error: function (data) {
            console.log(data);
        }
    });
});

// show modal and delete Categorie Fee
$(document).on('click', '.showdeleteFeeCategoriebtn', function () {
    var id = $(this).attr('id');
    var textWithId = " " + id;
    $('.deleteMessage span').text(textWithId);
    $('#deletebtnFeeCat').attr('href', '/gestion-commission/feeCategorie/' + id)
    $('#ModalDeleteFeeCategorie').modal('show');
});

//********************Type Fee************************
//Modal add new Type Fee
$(document).on('click', '#addNewFeeTypebtn', function () {
    $('#feeTypeNameInput').val("");
    $('.modal-FeeCategorie-title').text("Ajouter un nouveau Fee Type");
    $('#ModalAddUpdateFeeType').modal('show');
});

//Modal update type Fee
$(document).on('click', '.editFeeTypebtn', function () {
    $("#errorFeeCategorie").empty();
    $('#feeTypeNameInput').val("");
    $('#feeTypeNameInput').removeClass();
    $('#feeTypeNameInput').addClass("form-control");
    $('.modal-FeeCategorie-title').text("Editer un Fee Type");
    var id = $(this).attr('id');
    $('#feeType_id').val(id);

    $.ajax({
        type: 'get',
        url: "/feeType/" + id,
        dataType: "json",
        success: function (data) {
            console.log(data);
            $('#profiles').val(data.categorieFees.id)
            $('#feeTypeNameInput').val(data.typeName);
            $('#ModalAddUpdateFeeType').modal('show');
        },
        error: function (data) {
            console.log(data);
        }
    });
});

// show modal and delete type Fee
$(document).on('click', '.showdeleteFeeTypebtn', function () {
    var id = $(this).attr('id');
    var textWithId = " " + id;
    $('.deleteMessage span').text(textWithId);
    $('#deletebtnFeeType').attr('href', '/gestion-commission/feeType/' + id)
    $('#ModalDeleteFeeType').modal('show');
});

$(document).ready( function () {
    $('#FeeCategorieTable').DataTable();
} );

$(document).ready( function () {
    $('#FeeTypeTable').DataTable();
} );
