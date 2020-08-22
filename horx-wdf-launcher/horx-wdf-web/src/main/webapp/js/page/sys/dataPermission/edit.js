$(document).ready(function(){
    var form = layui.form;
    var id = $("#id").val();

    $.common.editPage({
        verify: {
            "objId": function (value) {
                var objType = $("#objType").listSelect().getValue();
                if (objType == "dict") {
                    var objId = $("#objId").listSelect().getValue();
                    if (objId == null || objId == "") {
                        return $.msg("common.err.param.required");
                    }
                }
                return null;
            }
        },
        loadApi:$.root + "/api/sys/dataPermission/{0}",
        createApi:$.root + "/api/sys/dataPermission",
        modifyApi:$.root + "/api/sys/dataPermission/{0}",
        onLoad: init,
        beforeSubmit:beforeSubmit
    });

    function init(data) {
        if (data == null) {
            $("#objType").listSelect({
                multi:false,
                value: (data == null) ? null : data.objType,
                data: dict.dataPermissionType,
                idProp: "code",
                search: false,
                searchProps: ["code", "name", "fullCode", "simpleCode"],
                onChange: function (value) {
                    if (value == "dict") {
                        toggleDict(true);
                    } else {
                        toggleDict(false);
                    }
                }
            });
        } else {
            $("#objType-div").html($.common.escapeHTML($.common.convertByList(dict.dataPermissionType, data.objType, "", "code", "name")));
            if (data.objType == "dict") {
                $("#objIdDiv").removeClass("layui-hide");
                $("#objId-div").html($.common.escapeHTML(data.objName));
            }
        }
    }

    var dictInited = false;
    function toggleDict(show) {
        if (show) {
            $("#objIdDiv").removeClass("layui-hide");
            if (!dictInited) {
                $("#objId").listSelect({
                    multi:false,
                    load: {
                        "type": "POST",
                        "url": $.root + "/api/sys/dict/paginationQuery",
                        "data": {pageSize: -1, pageIndex: 1}
                    },
                    search: true,
                    searchProps: ["code", "name"]
                });
                dictInited = true;
            }
        } else {
            $("#objIdDiv").addClass("layui-hide");
        }
    }

    function beforeSubmit(formData) {
        if (id) {
            return;
        }
        var objType = $("#objType").listSelect().getValue();
        formData.objType = objType;

        if (objType == "dict") {
            var objId = $("#objId").listSelect().getValue();
            formData.objId = objId;
        } else {
            formData.objId = "";
        }
    }
});