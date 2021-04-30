$(document).ready(function () {
    var errorCat = $("#errorCategory").text();
    var errorTypr = $("#errorType").text();
    var errorClass = $("#errorClass").text();
    if (errorCat != "" || errorClass != "" || errorTypr != "") {
        $('.modal-title').text("Ajouter un nouveau Instrument Categorie");
        $('#ModalAddUpdateCategoryRate').modal('show');
    }
});

//Modal add new Caegory rate
$(document).on('click', '#addNewCategoryRatebtn', function () {
    $('#categoryInput').val("");
    $('#instrumentClass').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $('.modal-title').text("Ajouter un nouveau Instrument Categorie");
    $('#ModalAddUpdateCategoryRate').modal('show');
});

//Modal update Caegory rate
$(document).on('click', '.editCategoryRatebtn', function () {
    $("#errorCategory").empty();
    $('#categoryInput').val("");
    $('#categoryInput').removeClass();
    $('#categoryInput').addClass("form-control");
    $('.modal-title').text("Editer un Instrument Categorie");
    var id = $(this).attr('id');
    $('#categorieRate_id').val(id);
    $.ajax({
        type: 'get',
        url: "/categorieRate/" + id,
        dataType: "json",
        success: function (data) {
            $('#categoryInput').val(data.category);
            $('#instrumentClass').val(data.instrumentType.instrumentClass.id)
            $('#ModalAddUpdateCategoryRate').modal('show');
            $('#instrumentTypeDiv').removeClass("hide")
            $('#instrumentType').empty();
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

// class instrument change to show instrument type
$('#instrumentClass').change(function () {
    var id = $(this).val();
    $('#instrumentType').empty();
    $('#instrumentType').prepend('<option value="" selected disabled hidden>Choisissez ici</option>');
    $('#instrumentCategorieDiv').addClass("hide");

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

// show modal and delete Caegory rate
$(document).on('click', '.showdeleteCategoryRatebtn', function () {
    var id = $(this).attr('id');
    var textWithId = " " + id;
    $('.deleteMessage span').text(textWithId);
    $('#deletebtnCategorieRate').attr('href', '/gestion-commission/category-rate/' + id)
    $('#ModalDeleteCategoryRate').modal('show');
});

$(document).ready(function () {
    $('#instrumentCategorieTable').DataTable();
});
