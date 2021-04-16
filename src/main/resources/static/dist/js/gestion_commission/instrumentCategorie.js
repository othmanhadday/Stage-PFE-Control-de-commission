$(document).ready(function () {
    var errorCat = $("#errorCategory").text();
    if (errorCat != "" || errorRate != "") {
        $('.modal-title').text("Ajouter un nouveau Instrument Categorie");
        $('#ModalAddUpdateCategoryRate').modal('show');
    }
});

//Modal add new Caegory rate
$(document).on('click', '#addNewCategoryRatebtn', function () {
    $('#categoryInput').val("");
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
    console.log(id);
    $.ajax({
        type: 'get',
        url: "/categorieRate/" + id,
        dataType: "json",
        success: function (data) {
            console.log(data);
            $('#categoryInput').val(data.category);
            $('#instrumentType').val(data.instrumentType.id)
            $('#ModalAddUpdateCategoryRate').modal('show');
        },
        error: function (data) {
            console.log(data);
        }
    });
});

// show modal and delete Caegory rate
$(document).on('click', '.showdeleteCategoryRatebtn', function () {
    var id = $(this).attr('id');
    var textWithId = " " + id;
    $('.deleteMessage span').text(textWithId);
    $('#deletebtnCategorieRate').attr('href', '/gestion-commission/category-rate/' + id)
    $('#ModalDeleteCategoryRate').modal('show');
});

$(document).ready( function () {
    $('#instrumentCategorieTable').DataTable();
} );
