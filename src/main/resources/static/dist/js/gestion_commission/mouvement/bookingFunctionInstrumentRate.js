$(document).ready(function () {
    var errorFeeType = $("#errorFeeType").text();
    var errorBookFunction = $("#errorBookFunction").text();
    var errorInstrumentBasis = $("#errorInstrumentBasis").text();
    var errorCreditDebit = $("#errorCreditDebit").text();
    var errorfeeRate = $("#errorfeeRate").text();
    if (errorFeeType != "" || errorBookFunction != "" || errorCreditDebit != "" || errorfeeRate != "" || errorInstrumentBasis != "") {
        $('#ModalAddUpdateBookingInstrument').modal('show');
    }
    var errorFeeType_update = $("#errorFeeType_update").text();
    var errorBookFunction_update = $("#errorBookFunction_update").text();
    var errorInstrumentBasis_update = $("#errorInstrumentBasis_update").text();
    var errorCreditDebit_update = $("#errorCreditDebit_update").text();
    var errorfeeRate_update = $("#errorfeeRate_update").text();
    if (errorFeeType_update != "" || errorBookFunction_update != "" || errorCreditDebit_update != "" || errorfeeRate_update != "" || errorInstrumentBasis_update != "") {
        $('#ModalUpdateBookingInstrument').modal('show');
        var id =$('#bookingInstrument_id_update_all').val();
        getupdatedItem(id)
    }
});

//Modal add new mouvement rate
$(document).on('click', '#addNewBookingInstrumentbtn', function () {
    $('#errors').empty()
    $('#errors').removeClass("alert alert-danger")
    $('#feeRateInput').removeClass();
    $('#feeRateInput').addClass("form-control");

    $('#feeRateInput').val("");
    $('.modal-title').text("Ajouter un nouveau Mouvement Rate");
    $('#ModalAddBookingInstrument').modal('show');
});
//****************************************** All select input change add new Booking instrument basis***************************************************
// class instrument change to show instrument type
$('#instrumentClassSelect').change(function () {
    var id = $(this).val();
    $('#instrumentTypeSelect').empty();
    $('#instrumentTypeSelect').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $.ajax({
        type: 'get',
        url: "/instrumentTypeByClass/" + id,
        dataType: "json",
        success: function (data) {
            $('#instrumentTypeDiv').removeClass("hide")
            $.each(data, function (key, value) {
                $('#instrumentTypeSelect')
                    .append($('<option>', {value: value.id})
                        .text(value.instrumentTypeName));
            });
        },
        error: function (data) {
            console.log(data);
        }
    });
})

// instrument type change to show instrument Basis
$('#instrumentTypeSelect').change(function () {
    var id = $(this).val();
    $('#instrumentBasisSelect ').empty();
    $('#instrumentBasisSelect').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $.ajax({
        type: 'get',
        url: "/instrumentBasisByInstrumentType/" + id,
        dataType: "json",
        success: function (data) {
            $('#instrumentBasisDiv').removeClass("hide")
            $.each(data, function (key, value) {
                $('#instrumentBasisSelect')
                    .append($('<option>', {value: value.id})
                        .text(value.name));
            });
            $('#feeRatediv').removeClass('hide');
        },
        error: function (data) {
            console.log(data);
        }
    });
})

// FeeCategorie change to show fee Type
$('#feeCategorieSelect').change(function () {
    var id = $(this).val();
    $('#feeTypeSelect ').empty();
    $('#feeTypeSelect').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $.ajax({
        type: 'get',
        url: "/feeTypesByCategorieFees/" + id,
        dataType: "json",
        success: function (data) {
            $('#feeTypeDiv').removeClass("hide")
            $.each(data, function (key, value) {
                $('#feeTypeSelect')
                    .append($('<option>', {value: value.id})
                        .text(value.typeName));
            });
        },
        error: function (data) {
            console.log(data);
        }
    });
})

$('#instrumentBasisSelect').change(function () {
    $('#bookingFunctionDiv').removeClass('hide');
})
$('#feeTypeSelect').change(function () {
    $('#creditDebitDiv').removeClass('hide');
})

//Modal Update  rate of Mouvement
$(document).on('click', '.editbookingInstrumentFeeRatebtn', function () {
    var id = $(this).attr('id');
    $('.modal-title').text("editer un Mouvement Rate");

    $('#bookingInstrument_id_update').val(id);

    $('#feeRateInputUpdate').removeClass();
    $('#feeRateInputUpdate').addClass("form-control");
    $.ajax({
        type: 'get',
        url: "/getBookingInstrumentBasisById/" + id,
        dataType: "json",
        success: function (data) {
            $('#bookingFunctionLabel').text(data.bookFunction.name)
            $('#instrumentClassLabel').text(data.instrumentClassBasisInstrument.instrumentType.instrumentClass.instrementClass)
            $('#instrumentTypeLabel').text(data.instrumentClassBasisInstrument.instrumentType.instrumentTypeName)
            $('#instrumentBasisLabel').text(data.instrumentClassBasisInstrument.name)
            $('#categorieFeeLabel').text(data.feeType.categorieFees.categorieFeeName)
            $('#feeTypeLabel').text(data.feeType.typeName)
            $('#creditDebitLabel').text(data.creditDebit)
            $('#feeRateLabel').text(data.feeRate)
            $('#feeRateInputUpdate').val(data.feeRate)

            $('#bookFunction_update').val(data.bookFunction.id)
            $('#instrumentClassBasisInstrument_update').val(data.instrumentClassBasisInstrument.id)
            $('#feeType_update').val(data.feeType.id)
            $('#creditDebit_update').val(data.creditDebit)

            $('#ModalEditBookingInstrumentRate').modal('show');
        },
        error: function (data) {
            console.log(data);
        }
    });
});

//**************************************Update Boooking instrument basis*********************************************************************
//Modal Update  all mouvement
$(document).on('click', '.editbookingInstrumentbtn', function () {
    $('#errors_update').empty()
    $('#errors_update').removeClass("alert alert-danger")
    $('#feeRateInput_update').removeClass();
    $('#feeRateInput_update').addClass("form-control");
    $('.modal-title').text("modifier un Mouvement Rate");

    var id = $(this).attr('id');
    //get all Booking instrument
    $('#bookingInstrument_id_update_all').val(id);
    getupdatedItem(id)
})

// class instrument change to show instrument type
$('#instrumentClassSelect_update').change(function () {
    var id = $(this).val();
    $('#instrumentTypeSelect_update').empty();
    $('#instrumentTypeSelect_update').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $('#instrumentBasisSelect_update').empty();
    $('#instrumentBasisSelect_update').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $.ajax({
        type: 'get',
        url: "/instrumentTypeByClass/" + id,
        dataType: "json",
        success: function (data) {
            $.each(data, function (key, value) {
                $('#instrumentTypeSelect_update')
                    .append($('<option>', {value: value.id})
                        .text(value.instrumentTypeName));
            });
        },
        error: function (data) {
            console.log(data);
        }
    });
})

// instrument type change to show instrument Basis
$('#instrumentTypeSelect_update').change(function () {
    var id = $(this).val();
    $('#instrumentBasisSelect_update').empty();
    $('#instrumentBasisSelect_update').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $.ajax({
        type: 'get',
        url: "/instrumentBasisByInstrumentType/" + id,
        dataType: "json",
        success: function (data) {
            $.each(data, function (key, value) {
                $('#instrumentBasisSelect_update')
                    .append($('<option>', {value: value.id})
                        .text(value.name));
            });
        },
        error: function (data) {
            console.log(data);
        }
    });
})

// FeeCategorie change to show fee Type
$('#feeCategorieSelect_update').change(function () {
    var id = $(this).val();
    $('#feeTypeSelect_update').empty();
    $('#feeTypeSelect_update').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $.ajax({
        type: 'get',
        url: "/feeTypesByCategorieFees/" + id,
        dataType: "json",
        success: function (data) {
            $.each(data, function (key, value) {
                $('#feeTypeSelect_update')
                    .append($('<option>', {value: value.id})
                        .text(value.typeName));
            });
        },
        error: function (data) {
            console.log(data);
        }
    });
})


// show modal and delete Mouvement rate
$(document).on('click', '.showdeletebookingInstrumentbtn', function () {
    var id = $(this).attr('id');
    var textWithId = " " + id;
    $('.modal-title').text("Delete un Mouvement Rate");
    $('.deleteMessage span').text(textWithId);
    $('#deletebtnInstrumentBasis').attr('href', '/gestion-commission/mouvement-rate/' + id)
    $('#ModalDeleteBookingInstrument').modal('show');
});

function getupdatedItem(id){
    $.ajax({
        type: 'get',
        url: "/getBookingInstrumentBasisById/" + id,
        dataType: "json",
        success: function (bookingInstrumentBasis) {
            $('#instrumentClassSelect_update').val(bookingInstrumentBasis.instrumentClassBasisInstrument.instrumentType.instrumentClass.id)
            $('#feeCategorieSelect_update').val(bookingInstrumentBasis.feeType.categorieFees.id)
            $('#BookingFunctionSelect_update').val(bookingInstrumentBasis.bookFunction.id)
            $('#feeRateInput_update').val(bookingInstrumentBasis.feeRate)
            $("input:radio[value='"+bookingInstrumentBasis.creditDebit+"']").prop('checked',true);
            $('#instrumentTypeSelect_update').empty();

            //get instrument Type by class id
            $.ajax({
                type: 'get',
                url: "/instrumentTypeByClass/" + bookingInstrumentBasis.instrumentClassBasisInstrument.instrumentType.instrumentClass.id,
                dataType: "json",
                success: function (instrumentType) {
                    $('#instrumentTypeDiv').removeClass("hide")
                    $.each(instrumentType, function (key, value) {
                        $('#instrumentTypeSelect_update')
                            .append($('<option>', {value: value.id})
                                .text(value.instrumentTypeName));
                        $('#instrumentTypeSelect_update').val(bookingInstrumentBasis.instrumentClassBasisInstrument.instrumentType.id)
                    });
                },
                error: function (data) {
                    console.log(data);
                }
            });

            //get instrument basis by instrument type
            $('#instrumentBasisSelect_update').empty();
            $.ajax({
                type: 'get',
                url: "/instrumentBasisByInstrumentType/" + bookingInstrumentBasis.instrumentClassBasisInstrument.instrumentType.id,
                dataType: "json",
                success: function (instrumentBasis) {
                    $.each(instrumentBasis, function (key, value) {
                        $('#instrumentBasisSelect_update')
                            .append($('<option>', {value: value.id})
                                .text(value.name));
                    });
                    $('#instrumentBasisSelect_update').val(bookingInstrumentBasis.instrumentClassBasisInstrument.id)
                },
                error: function (data) {
                    console.log(data);
                }
            });

            //get fee type by fee categorie
            $('#feeTypeSelect_update').empty();
            $.ajax({
                type: 'get',
                url: "/feeTypesByCategorieFees/" + bookingInstrumentBasis.feeType.categorieFees.id,
                dataType: "json",
                success: function (data) {
                    $.each(data, function (key, value) {
                        $('#feeTypeSelect_update')
                            .append($('<option>', {value: value.id})
                                .text(value.typeName));
                    });
                    $('#feeTypeSelect_update').val(bookingInstrumentBasis.feeType.id);
                },
                error: function (data) {
                    console.log(data);
                }
            });
            $('#ModalUpdateBookingInstrument').modal('show');
        }, error: function (data) {
            console.log(data)
        }
    })
}

$(document).ready( function () {
    $('#bookingFunctionInstrumentRateTable').DataTable();
} );
