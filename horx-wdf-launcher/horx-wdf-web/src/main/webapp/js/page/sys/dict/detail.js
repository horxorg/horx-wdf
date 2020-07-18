$(document).ready(function(){
    var id = $("#id").val();

    $.ajax({
        url: $.root + "/api/sys/dict/" + id,
        success: function(rst) {
            var checkRst = $.common.onAjaxSuccess(rst);
            if (checkRst.success) {
                $("#code-div").html($.common.escapeHTML(rst.data.code));
                $("#name-div").html($.common.escapeHTML(rst.data.name));
                $("#biz-type-div").html($.common.escapeHTML($.common.convertByList(dict.bizType, rst.data.bizType, "", "code", "name")));
                $("#tree-data-div").html((rst.data.treeData) ? $.msg("common.yes") : $.msg("common.no"));
                $("#remarks-div").html($.common.escapeHTML(rst.data.remarks, true));
            }
        }
    });

});