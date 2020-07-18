$(document).ready(function(){
    var id = $("#id").val();

    $.ajax({
        type: "GET",
        url: $.root + "/api/sys/dataLog/" + id,
        success: function(rst) {
            var checkRst = $.common.onAjaxSuccess(rst);
            if (checkRst.success) {
                $("#userName").html($.common.escapeHTML(rst.data.userName));
                $("#orgName").html($.common.escapeHTML(rst.data.orgName));
                $("#traceId").html(rst.data.traceId);
                $("#bizType").html($.common.escapeHTML(rst.data.bizType));
                $("#bizName").html($.common.escapeHTML(rst.data.bizName));
                $("#operationType").html(rst.data.operationType);
                $("#data").html($.common.escapeHTML(rst.data.data));
                $("#description").html($.common.escapeHTML(rst.data.description));
                $("#className").html(rst.data.className);
                $("#methodName").html(rst.data.methodName);
                $("#serverIp").html(rst.data.serverIp);
                $("#startTime").html(rst.data.startTime);
                $("#endTime").html(rst.data.endTime);
                $("#duration").html(rst.data.duration);
            }
        }
    });

});