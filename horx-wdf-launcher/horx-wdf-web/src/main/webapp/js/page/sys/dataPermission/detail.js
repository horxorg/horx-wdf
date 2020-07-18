$(document).ready(function(){
    var tree = layui.tree;
    var id = $("#id").val();

    $.ajax({
        type: "GET",
        url: $.root + "/api/sys/dataPermission/" + id,
        success: function(rst) {
            var checkRst = $.common.onAjaxSuccess(rst);
            if (checkRst.success) {
                var data = rst.data;
                $("#name-div").html($.common.escapeHTML(data.name));
                $("#code-div").html($.common.escapeHTML(data.code));
                $("#objType-div").html($.common.escapeHTML($.common.convertByList(dict.dataPermissionType, data.objType, "", "code", "name")));
                if (data.objType == "dict") {
                    $("#objIdDiv").removeClass("layui-hide");
                    $("#objId-div").html($.common.escapeHTML(data.objName));
                }
                $("#enabled-div").html((data.enabled) ? $.msg("common.yes") : $.msg("common.no"));
                $("#displaySeq-div").html((data.displaySeq) ? data.displaySeq : "");
                $("#remarks-div").html($.common.escapeHTML(data.remarks, true));
            }
        }
    });

});