$(document).ready(function () {
    var errorInstrumentClass = $("#errorInstrumentClass").text();
    var errorInstrumentType = $("#errorInstrumentType").text();
    if (errorInstrumentClass != "") {
        $('.modal-title').text("Ajouter un nouveau Instrument Class");
        $('#ModalAddUpdateInstrumentClass').modal('show');
    }
    if (errorInstrumentType != "") {
        $('.modal-title').text("Ajouter un nouveau Instrument Type");
        $('#ModalAddUpdateInstrumentType').modal('show');
    }
});

//***********************instrument class*******************************
//Modal add new instrument class
$(document).on('click', '#addNewInstrumentClassbtn', function () {
    $('#instrumentClassInput').val("");
    $('.modal-title').text("Ajouter un nouveau Instrument Class");
    $('#ModalAddUpdateInstrumentClass').modal('show');
});

//Modal update instrument class
$(document).on('click', '.editInstrumentClassbtn', function () {
    $("#errorInstrumentClass").empty();
    $('#instrumentClassInput').val("");
    $('#instrumentClassInput').removeClass();
    $('#instrumentClassInput').addClass("form-control");
    $('.modal-title').text("Editer un Instrument Class");
    var id = $(this).attr('id');
    $('#instrumentClass_id').val(id);
    $.ajax({
        type: 'get',
        url: "/instrumentClass/" + id,
        dataType: "json",
        success: function (data) {
            $('#instrumentClassInput').val(data.instrementClass);
            $('#ModalAddUpdateInstrumentClass').modal('show');
        },
        error: function (data) {
            console.log(data);
        }
    });
});

// show modal and delete Categorie Fee
$(document).on('click', '.showdeleteInstrumentClassbtn', function () {
    var id = $(this).attr('id');
    $('.modal-title').text("Delete Instrument Class");
    var textWithId = " " + id;
    $('.deleteMessage span').text(textWithId);
    $('#deletebtnInstrumentClass').attr('href', '/gestion-commission/instrument-class/' + id)
    $('#ModalDeleteInstrumentClass').modal('show');
});

//***********************instrument class*******************************
//Modal add new instrument type
$(document).on('click', '#addNewInstrumentTypebtn', function () {
    $('#instrumentTypeInput').val("");
    $('#instrumentTypeCodeInput').val("");
    $('.modal-title').text("Ajouter un nouveau Instrument Type");

    $('#ModalAddUpdateInstrumentType').modal('show');
});

//Modal update instrument Type
$(document).on('click', '.editInstrumentTypebtn', function () {
    $("#errorInstrumentType").empty();
    $('#instrumentTypeInput').val("");
    $('#instrumentTypeCodeInput').val("");
    $('#instrumentTypeInput').removeClass();
    $('#instrumentTypeInput').addClass("form-control");
    $('.modal-title').text("Editer un Instrument Type");

    var id = $(this).attr('id');
    $('#instrumentType_id').val(id);
    $.ajax({
        type: 'get',
        url: "/instrumentType/" + id,
        dataType: "json",
        success: function (data) {
            $('#instrumentTypeInput').val(data.instrumentTypeName);
            $('#instrumentTypeCodeInput').val(data.instrumentTypeCode);
            $('#profiles').val(data.instrumentClass.id);

            $('#ModalAddUpdateInstrumentType').modal('show');
        },
        error: function (data) {
            console.log(data);
        }
    });
});

// show modal and delete instrument Type
$(document).on('click', '.showdeleteInstrumentTypebtn', function () {
    var id = $(this).attr('id');
    $('.modal-title').text("Delete Instrument Type ");
    var textWithId = " " + id;
    $('.deleteMessage span').text(textWithId);
    $('#deletebtnInstrumentType').attr('href', '/gestion-commission/instrumentType/' + id)
    $('#ModalDeleteInstrumentType').modal('show');
});

$(document).ready( function () {
    $('#instrumentClassTable').DataTable();
} );

$(document).ready( function () {
    $('#instrumentTypeTable').DataTable();
} );
