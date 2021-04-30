$(document).ready(function () {
    var errorBookingFuncton = $("#errorNameBookingFunction").text();
    var errorinstrumentBasis = $("#errorNameinstrumentBasis").text();
    if (errorBookingFuncton != "") {
        $('.modal-title').text("Ajouter un nouveau Booking Function");
        $('#ModalAddUpdateBookingFunction').modal('show');
    }
    if (errorinstrumentBasis != "") {
        $('.modal-title').text("Ajouter un nouveau Instrument Class of Basis Instrument");
        $('#ModalAddUpdateInstrumentBasis').modal('show');
    }
});

//Modal add new Booking function
$(document).on('click', '#addNewBookingfunctionbtn', function () {
    $('#bookingfunctionInput').val("");
    $('.modal-title').text("Ajouter un nouveau Booking Function");
    $('#ModalAddUpdateBookingFunction').modal('show');
});

//Modal update Booking function
$(document).on('click', '.editBookingFunctionbtn', function () {
    $("#errorNameBookingFunction").empty();
    $('#bookingfunctionInput').val("");
    $('#bookingfunctionInput').removeClass();
    $('#bookingfunctionInput').addClass("form-control");
    $('.modal-title').text("Editer un Booking Function");
    var id = $(this).attr('id');
    $('#bookingFunction_id').val(id);

    $.ajax({
        type: 'get',
        url: "/bookingFunction/" + id,
        dataType: "json",
        success: function (data) {
            $('#bookingfunctionInput').val(data.name);

            $('#ModalAddUpdateBookingFunction').modal('show');
        },
        error: function (data) {
            console.log(data);
        }
    });
});

// show modal and delete Booking function
$(document).on('click', '.showDeleteBookingfunctionbtn', function () {
    var id = $(this).attr('id');
    var textWithId = " " + id;
    $('.deleteMessage span').text(textWithId);
    $('.modal-title').text("delete un Booking Function");
    $('#deletebtnBookingFunction').attr('href', '/gestion-commission/bookingFunction/' + id)
    $('#ModalDeleteBookingFunction').modal('show');
});


//Modal add new Instrument Basis
$(document).on('click', '#addNewInstrumentBasisbtn', function () {
    $('#instrumentBasisInput').val("");
    $('#instrumentClass').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $('.modal-title').text("Ajouter Instrument Class of Basis Instrument");
    $('#ModalAddUpdateInstrumentBasis').modal('show');
});

//Modal update Instrument Basis
$(document).on('click', '.editInstrumentBasisbtn', function () {
    $("#errorNameinstrumentBasis").empty();
    $('#instrumentBasisInput').val("");
    $('#instrumentBasisInput').removeClass();
    $('#instrumentBasisInput').addClass("form-control");
    $('.modal-title').text("Editer Instrument Class of Basis Instrument");
    var id = $(this).attr('id');
    $('#instrumentBasis_id').val(id);

    $.ajax({
        type: 'get',
        url: "/instrumentBasis/" + id,
        dataType: "json",
        success: function (data) {
            $('#instrumentBasisInput').val(data.name);
            console.log(data.instrumentType);
            $('#instrumentClass').val(data.instrumentType.instrumentClass.id);
            $('#ModalAddUpdateInstrumentBasis').modal('show');
            $('#instrumentTypeDiv').removeClass("hide")
            // get Instrument Type
            $.ajax({
                type: 'get',
                url: "/instrumentTypeByClass/" + data.instrumentType.instrumentClass.id,
                dataType: "json",
                success: function (instrumentTypes) {
                    $.each(instrumentTypes, function (key, value) {
                        $('#instrumentType')
                            .append($('<option>', {value: value.id})
                                .text(value.instrumentTypeName + " , " + value.instrumentTypeCode));
                        $('#instrumentType').val(data.instrumentType.id)
                    });
                },
                error: function (data) {
                    console.log(data);
                }
            });
        },
        error: function (data) {
            console.log(data);
        }
    });
});

// show modal and delete Instrument Basis
$(document).on('click', '.showDeleteInstrumentBasisbtn', function () {
    var id = $(this).attr('id');
    var textWithId = " " + id;
    $('.deleteMessage span').text(textWithId);
    $('#deletebtnInstrumentBasis').attr('href', '/gestion-commission/instrumentBasis/' + id)
    $('#ModalDeleteInstrumentBasis').modal('show');
});

$(document).ready( function () {
    $('#bookingFunctionTable').DataTable();
} );

$(document).ready( function () {
    $('#instrumentclassBasisTable').DataTable();
} );


// class instrument change to show instrument type
$('#instrumentClass').change(function () {
    var id = $(this).val();
    $('#instrumentType').empty();
    $('#instrumentType').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');

    $.ajax({
        type: 'get',
        url: "/instrumentTypeByClass/" + id,
        dataType: "json",
        success: function (data) {
            $('#instrumentTypeDiv').removeClass("hide")
            var count = data.length
            if (count == 0) {
                $('#instrumentType')
                    .append($('<option>', {value: "-"})
                        .text("-"));
            }
            $.each(data, function (key, value) {
                count--;
                if (count == 0 && value.instrumentTypeCode != "-") {
                    $('#instrumentType')
                        .append($('<option>', {value: "-"})
                            .text("-"));
                }
                $('#instrumentType')
                    .append($('<option>', {value: value.id})
                        .text(value.instrumentTypeName + " , " + value.instrumentTypeCode));
            });
        },
        error: function (data) {
            console.log(data);
        }
    });
})
