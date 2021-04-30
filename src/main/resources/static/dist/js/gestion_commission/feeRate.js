$(document).ready(function () {
    var id = $('#feeRate_id_update').val();
    var errorFeeType = $("#errorFeeType").text();
    var errorInstrumentCat = $("#errorInstrumentCategorie").text();
    var errorFeeRate = $("#errorFeeRate").text();
    var errorMontant = $("#errorMontant").text();

    var errorFeeType_update = $("#errorFeeType_update").text();
    var errorInstrumentCat_update = $("#errorInstrumentCategorie_update").text();
    var errorFeeRate_update = $("#errorFeeRate_update").text();
    var errorMontant_update = $("#errorMontant_update").text();
    if (id.length == 0) {
        if (errorFeeType != "" || errorInstrumentCat != "" || errorFeeRate != "" || errorMontant != "") {
            $('#ModalAddFeeRate').modal('show');
        }
    } else if (id.length > 0) {
        if (errorFeeType_update != "" || errorInstrumentCat_update != "" || errorFeeRate_update != "" || errorMontant_update != "") {
            $('#ModalUpdateFeeRate').modal('show');
            getUpdatedItemFeeRate(id)
        }
    }
});

//**********************************ajouter nouveau fee rate***********************************
$(document).on('click', '#addNewFeeRatebtn', function () {
    $('#instrumentClassSelect').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $("#feeRateInput").val("")
    $('#errors').empty()
    $('#errors').removeClass("alert alert-danger")
    $('#feeRateInput').removeClass();
    $('#feeRateInput').addClass("form-control");
    $('#feeMontantInput').removeClass();
    $('#feeMontantInput').addClass("form-control");
    $("#feeMontantInput").val("")
    $('input[type="radio"][class="tauxMontant"][value="M"]').attr('checked', false);
    $('input[type="radio"][class="tauxMontant"][value="T"]').attr('checked', false);

    $('#ModalAddFeeRate').modal('show');
});

// class instrument change to show instrument type
$('#instrumentClassSelect').change(function () {
    var id = $(this).val();
    $('#instrumentTypeSelect').empty();
    $('#instrumentTypeSelect').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $('#instrumentCategorieSelect').empty();
    $('#instrumentCategorieSelect').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $('#instrumentCategorieDiv').addClass("hide");

    $.ajax({
        type: 'get',
        url: "/instrumentTypeByClass/" + id,
        dataType: "json",
        success: function (data) {
            $('#instrumentTypeDiv').removeClass("hide")
            var count = data.length
            if (count == 0) {
                $('#instrumentTypeSelect')
                    .append($('<option>', {value: "-"})
                        .text("-"));
            }
            $.each(data, function (key, value) {
                count--;
                if (count == 0 && value.instrumentTypeCode != "-") {
                    $('#instrumentTypeSelect')
                        .append($('<option>', {value: "-"})
                            .text("-"));
                }
                $('#instrumentTypeSelect')
                    .append($('<option>', {value: value.id})
                        .text(value.instrumentTypeName + " , " + value.instrumentTypeCode));
            });
        },
        error: function (data) {
            console.log(data);
        }
    });
})

// instrument type change to show instrument Categorie
$('#instrumentTypeSelect').change(function () {
    var id = $(this).val();
    $('#instrumentCategorieSelect ').empty();
    $('#instrumentCategorieSelect').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');

    if (id == "-") {
        $('#instrumentCategorieDiv').removeClass("hide")
        $('#instrumentCategorieSelect')
            .append($('<option>', {value: "-"})
                .text("-"));
    } else {
        $.ajax({
            type: 'get',
            url: "/instrumentCatByInstrumentType/" + id,
            dataType: "json",
            success: function (data) {
                $('#instrumentCategorieDiv').removeClass("hide")
                var count = data.length;
                if (count == 0) {
                    $('#instrumentCategorieSelect')
                        .append($('<option>', {value: "-"})
                            .text("-"));
                }
                $.each(data, function (key, value) {
                    count--;
                    if (count == 0 && value.category != "-") {
                        $('#instrumentCategorieSelect')
                            .append($('<option>', {value: "-"})
                                .text("-"));
                    }
                    $('#instrumentCategorieSelect')
                        .append($('<option>', {value: value.id})
                            .text(value.category));
                });
            },
            error: function (data) {
                console.log(data);
            }
        });
    }

})

// FeeCategorie change to show fee Type
$('#feeCategorieSelect').change(function () {
    var id = $(this).val();
    $('#feeTypeSelect ').empty();
    $('#feeTypeSelect').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $('#TauxMontantDiv').removeClass('hide');
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

$('.tauxMontant').click(function () {
    if ($(this).val() == 'T') {
        $("#feeRatediv").removeClass('hide')
        $("#feeMontantdiv").addClass('hide')
        $("#feeMontantInput").val('')
    }
    if ($(this).val() == 'M') {
        $("#feeMontantdiv").removeClass('hide')
        $("#feeRatediv").addClass('hide')
        $("#feeRateInput").val('')
    }
})

//*******************************Update Fee Rate***********************************

$('.tauxMontant_update').click(function () {
    if ($(this).val() == 'T') {
        $("#feeRatediv_update").removeClass('hide')
        $("#feeMontantdiv_update").addClass('hide')
    }
    if ($(this).val() == 'M') {
        $("#feeMontantdiv_update").removeClass('hide')
        $("#feeRatediv_update").addClass('hide')
    }
})


$(document).on('click', '.editFeeRatebtn', function () {
    var id = $(this).attr('id');
    $('#feeRate_id_update').val(id);

    $('#errors_update').empty()
    $('#errors_update').removeClass("alert alert-danger")
    $("#feeRatediv_update").addClass('hide')
    $("#feeMontantdiv_update").addClass('hide')
    $('#feeRateInput_update').val("")
    $('#feeRateInput_update').removeClass()
    $('#feeRateInput_update').addClass('form-control')
    $('#feeMontantInput_update').val("")
    $('#feeMontantInput_update').removeClass()
    $('#feeMontantInput_update').addClass('form-control')
    $('.tauxMontant_update').each(function () {
        $('input[type="radio"]').prop('checked', false);
    })
    $('#instrumentClassSelect_update').val("")
    getUpdatedItemFeeRate(id);
})


// class instrument change to show instrument type
$('#instrumentClassSelect_update').change(function () {
    var id = $(this).val();
    $('#instrumentTypeSelect_update').empty();
    $('#instrumentTypeSelect_update').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $('#instrumentCategorieSelect_update').empty();
    $('#instrumentCategorieSelect_update').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');

    $.ajax({
        type: 'get',
        url: "/instrumentTypeByClass/" + id,
        dataType: "json",
        success: function (data) {
            var count = data.length;
            if (count == 0) {
                $('#instrumentTypeSelect_update')
                    .append($('<option>', {value: "-"})
                        .text("-"));
            }
            $.each(data, function (key, value) {
                count--;
                if (count == 0 && value.instrumentTypeCode != "-") {
                    $('#instrumentTypeSelect_update')
                        .append($('<option>', {value: "-"})
                            .text("-"));
                }
                $('#instrumentTypeSelect_update')
                    .append($('<option>', {value: value.id})
                        .text(value.instrumentTypeName + " , " + value.instrumentTypeCode));
            });
        },
        error: function (data) {
            console.log(data);
        }
    });
})

// instrument type change to show instrument Categorie
$('#instrumentTypeSelect_update').change(function () {
    var id = $(this).val();
    $('#instrumentCategorieSelect_update').empty();
    $('#instrumentCategorieSelect_update').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');

    if (id == "-") {
        $('#instrumentCategorieDiv_update').removeClass("hide")
        $('#instrumentCategorieSelect_update')
            .append($('<option>', {value: "-"})
                .text("-"));
    } else {
        console.log(id);
        $.ajax({
            type: 'get',
            url: "/instrumentCatByInstrumentType/" + id,
            dataType: "json",
            success: function (data) {
                var count = data.length
                if (count == 0) {
                    $('#instrumentCategorieSelect_update')
                        .append($('<option>', {value: "-"})
                            .text("-"));
                }
                $.each(data, function (key, value) {
                    count--;
                    if (count == 0 && value.category != "-") {
                        $('#instrumentCategorieSelect_update')
                            .append($('<option>', {value: "-"})
                                .text("-"));
                    }
                    $('#instrumentCategorieSelect_update')
                        .append($('<option>', {value: value.id})
                            .text(value.category));
                });
            },
            error: function (data) {
                console.log(data);
            }
        });
    }

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


function getUpdatedItemFeeRate(id) {
    $.ajax({
        type: 'get',
        url: "/feeRateById/" + id,
        dataType: "json",
        success: function (data) {
            if (data.tauxMontant == "M") {
                $('input[type="radio"][class="tauxMontant_update"][value="M"]').prop('checked', true);
                $("#feeMontantdiv_update").removeClass('hide')
                $('#feeMontantInput_update').val(data.montant)
            } else if (data.tauxMontant == "T") {
                $('input[type="radio"][class="tauxMontant_update"][value="T"]').prop('checked', true);
                $("#feeRatediv_update").removeClass('hide')
                $('#feeRateInput_update').val(data.feeRate)
            } else {
            }
            $('#instrumentClassSelect_update').val(data.instrumentCategorie.instrumentType.instrumentClass.id);
            $('#feeCategorieSelect_update').val(data.feeType.categorieFees.id);
            $('#instrumentTypeSelect_update').empty();
            $('#instrumentCategorieSelect_update').empty();
            $('#feeTypeSelect_update').empty();

            // get Instrument Type
            $.ajax({
                type: 'get',
                url: "/instrumentTypeByClass/" + data.instrumentCategorie.instrumentType.instrumentClass.id,
                dataType: "json",
                success: function (instrumentTypes) {
                    $.each(instrumentTypes, function (key, value) {
                        $('#instrumentTypeSelect_update')
                            .append($('<option>', {value: value.id})
                                .text(value.instrumentTypeName + " , " + value.instrumentTypeCode));
                        $('#instrumentTypeSelect_update').val(data.instrumentCategorie.instrumentType.id)
                    });
                },
                error: function (data) {
                    console.log(data);
                }
            });

            // get Categorie instrument
            $.ajax({
                type: 'get',
                url: "/instrumentCatByInstrumentType/" + data.instrumentCategorie.instrumentType.id,
                dataType: "json",
                success: function (instrumentCat) {
                    $.each(instrumentCat, function (key, value) {
                        $('#instrumentCategorieSelect_update')
                            .append($('<option>', {value: value.id})
                                .text(value.category));
                        $('#instrumentCategorieSelect_update').val(data.instrumentCategorie.id);
                    });
                },
                error: function (data) {
                    console.log(data);
                }
            });

            //get Fee Type
            $.ajax({
                type: 'get',
                url: "/feeTypesByCategorieFees/" + data.feeType.categorieFees.id,
                dataType: "json",
                success: function (feeTypes) {
                    $.each(feeTypes, function (key, value) {
                        $('#feeTypeSelect_update')
                            .append($('<option>', {value: value.id})
                                .text(value.typeName));
                    });
                    $('#feeTypeSelect_update').val(data.feeType.id);
                },
                error: function (data) {
                    console.log(data);
                }
            });
            $("#ModalUpdateFeeRate").modal('show');

        },
        error: function (err) {
            console.log(err);
        }
    })
}

//***********************************Delete Fee Rate************************************************
$(document).on('click', '.showdeleteFeeRatebtn', function () {
    var id = $(this).attr('id');
    console.log(id);
    var textWithId = " " + id;
    $('.deleteMessage span').text(textWithId);
    $('#deletebtnFeeRate').attr('href', '/gestion-commission/feeRate/' + id)
    $('#ModalDeleteFeeRate').modal('show');
})

$(document).ready(function () {
    $('#FeeRateTable').DataTable();
});
