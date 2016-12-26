/*
 * Cerberus  Copyright (C) 2013  vertigo17
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This file is part of Cerberus.
 *
 * Cerberus is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Cerberus is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Cerberus.  If not, see <http://www.gnu.org/licenses/>.
 */

$.when($.getScript("js/pages/global/global.js")).then(function () {
    $(document).ready(function () {
        initPage();
    });
});

function initPage() {
    displayPageLabel();

    // handle the click for specific action buttons
    $("#editParameterButton").click(editEntryModalSaveHandler);

    //clear the modals fields when closed
    $('#editParameterModal').on('hidden.bs.modal', editEntryModalCloseHandler);

    //configure and create the dataTable
    var configurations = new TableConfigurationsServerSide("parametersTable", "ReadParameter?system1=" + getSys(), "contentTable", aoColumnsFunc(), [1, 'asc']);
    createDataTableWithPermissions(configurations, renderOptionsForApplication, "#parameterList");
}

function displayPageLabel() {
    var doc = new Doc();

    $("#title").html(doc.getDocLabel("page_parameter", "allParameters"));
    $("[name='editParameterField']").html(doc.getDocLabel("page_parameter", "editparameter_field"));
    $("[name='parameterField']").html(doc.getDocLabel("page_parameter", "parameter_field"));
    $("[name='cerberusField']").html(doc.getDocLabel("page_parameter", "cerberus_field"));
    $("[name='descriptionField']").html(doc.getDocLabel("page_parameter", "description_field"));
    $("[name='buttonClose']").html(doc.getDocLabel("page_parameter", "close_btn"));
    $("[name='buttonAdd']").html(doc.getDocLabel("page_parameter", "save_btn"));

    displayHeaderLabel(doc);

    $("[name='systemField']").html(doc.getDocLabel("page_parameter", "system_field") + " (" + getSys() + ")");

    displayFooter(doc);
    displayGlobalLabel(doc);
}

function renderOptionsForApplication(data) {
    if ($("#blankSpace").length === 0) {
        var contentToAdd = "<div class='marginBottom10' style='height:34px;' id='blankSpace'></div>";
        $("#parametersTable_wrapper div#parametersTable_length").before(contentToAdd);
    }
}

function editEntryClick(param) {
    clearResponseMessageMainPage();

    var jqxhr = $.getJSON("ReadParameter", "system1=" + getSys() + "&param=" + param);
    $.when(jqxhr).then(function (data) {
        var obj = data["contentTable"];

        var formEdit = $('#editParameterModal');

        formEdit.find("#parameter").prop("value", obj["param"]);
        formEdit.find("#cerberusValue").prop("value", obj["value"]);
        formEdit.find("#systemValue").prop("value", obj["system1value"]);
        formEdit.find("#description").html(obj["description"]);

        if (!(data["hasPermissions"])) { // If readonly, we only readonly all fields
            formEdit.find("#description").prop("readonly", "readonly");
            formEdit.find("#sort").prop("readonly", "readonly");

            $('#editParameterButton').attr('class', '');
            $('#editParameterButton').attr('hidden', 'hidden');
        }

        formEdit.modal('show');
    });
}

function editEntryModalSaveHandler() {
    clearResponseMessage($('#editParameterModal'));
    var formEdit = $('#editParameterModal #editParameterModalForm');

    var sa = formEdit.serializeArray();
    var data = {}
    for (var i in sa) {
        data[sa[i].name] = sa[i].value;
    }
    // Get the header data from the form.
    //var data = convertSerialToJSONObject(formEdit.serialize());
    showLoaderInModal('#editParameterModal');
    $.ajax({
        url: "UpdateParameter",
        async: true,
        method: "POST",
        data: {id: data.parameter,
            value: data.cerberusValue,
            system1Value: data.systemValue,
            system1: getSys()},
        success: function (data) {
            data = JSON.parse(data);
            hideLoaderInModal('#editParameterModal');
            if (getAlertType(data.messageType) === 'success') {
                var oTable = $("#parametersTable").dataTable();
                oTable.fnDraw(true);
                $('#editParameterModal').modal('hide');
                showMessage(data);
            } else {
                showMessage(data, $('#editParameterModal'));
            }
        },
        error: showUnexpectedError
    });

}

function editEntryModalCloseHandler() {
    // reset form values
    $('#editParameterModal #editParameterModalForm')[0].reset();
    // remove all errors on the form fields
    $(this).find('div.has-error').removeClass("has-error");
    // clear the response messages of the modal
    clearResponseMessage($('#editParameterModal'));
}

function getSys() {
    var sel = document.getElementById("MySystem");
    var selectedIndex = sel.selectedIndex;
    return sel.options[selectedIndex].value;
}

function aoColumnsFunc(tableId) {
    var doc = new Doc();
    var aoColumns = [
        {"data": null,
            "bSortable": false,
            "bSearchable": false,
            "title": doc.getDocLabel("page_parameter", "button_col"),
            "mRender": function (data, type, obj) {
                var hasPermissions = $("#" + tableId).attr("hasPermissions");

                var editParameter = '<button id="editParameter" onclick="editEntryClick(\'' + obj["param"] + '\');"\n\
                                        class="editApplication btn btn-default btn-xs margin-right5" \n\
                                        name="editParameter" title="' + doc.getDocLabel("page_parameter", "button_edit") + '" type="button">\n\
                                        <span class="glyphicon glyphicon-pencil"></span></button>';

                return '<div class="center btn-group width150">' + editParameter + '</div>';

            },
            "width": "50px"
        },
        {"data": "param", "sName": "par.param", "title": doc.getDocLabel("page_parameter", "parameter_col")},
        {"data": "value", "sName": "par.value", "title": doc.getDocLabel("page_parameter", "cerberus_col")},
        {"data": "system1value", "sName": "par1.value", "title": doc.getDocLabel("page_parameter", "system_col") + " (" + getSys() + ")"},
        {"data": "description", "sName": "par.description", "title": doc.getDocLabel("page_parameter", "description_col")}
    ];
    return aoColumns;
}
