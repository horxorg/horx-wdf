$(document).ready(function(){
    var id = $("#id").val();
    var usable = $("#usable").val();

    var url = null;
    if (window.location.toString().indexOf("/sys/role/usable/detail") >= 0) {
        url = $.root + "/api/sys/role/usable/" + id;
    } else {
        url = $.root + "/api/sys/role/" + id + "?usable=" + usable;
    }

    $.ajax({
        type: "GET",
        url: url,
        success: function(rst) {
            var checkRst = $.common.onAjaxSuccess(rst);
            if (checkRst.success && rst.data != null) {
                $("#code-div").html($.common.escapeHTML(rst.data.code));
                $("#name-div").html($.common.escapeHTML(rst.data.name));
                $("#orgId-div").html($.common.escapeHTML(rst.data.orgName));
                $("#subUsable-div").html((rst.data.subUsable) ? $.msg("common.yes") : $.msg("common.no"));
                $("#enabled-div").html((rst.data.enabled) ? $.msg("common.yes") : $.msg("common.no"));
                $("#remarks-div").html($.common.escapeHTML(rst.data.remarks, true));
            }
        }
    });

});