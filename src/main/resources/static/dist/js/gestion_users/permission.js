// add new role et affecte
$(document).on('click', '#newRole', function () {
    $("#role_id").val("");
    $("#name").val("");
    $("input[id='permissionUpdate']").each(function (index, val) {
        $("input[id='permissionUpdate']")[index].checked = false;
    })
    $('#roleModal').modal('show');
})

// edit Role et affecter le des permission
$(document).on('click', '.editRole', function () {
    $("#role_id").val("");
    $("#name").val("");
    $("input[id='permissionUpdate']").each(function (index, val) {
        $("input[id='permissionUpdate']")[index].checked = false;
    })
    var id = $(this).attr('id');
    $.ajax({
        type: 'get',
        url: "/role/" + id,
        dataType: "json",
        success: function (data) {
            $("#role_id").val(data.id);
            $("#name").val(data.name);

            $.each(data.permissions, function (i, v) {
                console.log(v);
                $("input[id='permissionUpdate']").each(function (index, val) {
                    if (v.id == $(this).val()) {
                        $("input[id='permissionUpdate']")[index].checked = true;
                    }
                })
            })
            $('#roleModal').modal('show');
        },
        error: function (data) {
            console.log(data);
        }
    });
});

// edit Role et affecter le des permission
$(document).on('click', '.deleteRole', function () {
    var id = $(this).attr('id');
    $('#deleteRoleBtn').attr('href', '/autorisation/deleteRole/' + id);
    $('#roleDeleteModal').modal('show');

});

// show all users of role
$(document).on('click', '.roleUsers', function () {
    var id = $(this).attr('id');
    $('#userByRole').empty();
    $.ajax({
        type: 'get',
        url: "/roles/" + id,
        dataType: "json",
        success: function (data) {
            console.log(data)
            console.log(id);
            $('#modalShowAllUserOfRole').modal('show');
            var tbody = ''
            console.log(data.length);
            if (data.length == 0) {
                tbody = '<tr class="justify-content-center"> Empty </tr>'
            } else {
                $.each(data, function (index, value) {
                    if (value.nom == null) {
                        value.nom = "-";
                    }
                    if (value.username == null) {
                        value.username = "-";
                    }
                    tbody += '<tr>' +
                        '<td>' + value.id + '</td>' +
                        '<td>' + value.nom + '</td>' +
                        '<td>' + value.username + '</td>' +
                        '</tr>'
                });
            }

            $('#userByRole').append(tbody);
        },
        error: function (data) {
            console.log(data);
        }
    });
});
