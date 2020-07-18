$(document).ready(function(){
    var dictId = $("#dictId").val();
    var id = $("#id").val();
    var treeData = $("#treeData").val();

    $.common.editPage({
        loadApi: $.root + "/api/sys/dict/" + dictId + "/item/{0}",
        createApi: $.root + "/api/sys/dict/" + dictId + "/item",
        modifyApi: $.root + "/api/sys/dict/" + dictId + "/item/{0}",
        modifyMethod: "PUT",
        onLoad: onLoad,
        beforeSubmit: beforeSubmit,
        onSaveSuccess: onSaveSuccess
    });

    function onLoad(data) {
        if (treeData == "1") {
            $("#parent-row").removeClass("layui-hide");

            var parentId = (id == "") ? $("#parentId").val() : data.parentId;
            if (parentId != null && parentId != "" && parentId != "-1") {
                $.ajax({
                    url: $.root + "/api/sys/dict/" + dictId + "/item/" + parentId,
                    success: function(rst) {
                        var checkRst = $.common.onAjaxSuccess(rst);
                        if (checkRst.success) {
                            $("#parent-div").html(rst.data.code + "&nbsp;&nbsp;" + $.common.escapeHTML(rst.data.name));
                        }
                    }
                });
            }
        }
    }

    function beforeSubmit(data) {
        if (id == "" && treeData == "1") {
            data["parentId"] = $("#parentId").val();
        }
    }

    function onSaveSuccess() {
        if (treeData == "1") {
            parent["reloadTreeTable"]();
        } else {
            parent.layui.table.reload("grid");
        }
    }

    $("#name").on("keyup", function () {
        var name = $(this).val();
        $("#fullCode").val(pinyin.getFullChars(name));
        $("#simpleCode").val(pinyin.getCamelChars(name));
    });
});