$(document).ready(function(){
    var id = $("#id").val();

    $.ajax({
        type: "GET",
        url: $.root + "/api/sys/accessLog/" + id,
        success: function(rst) {
            var checkRst = $.common.onAjaxSuccess(rst);
            if (checkRst.success) {
                $("#userName").html($.common.escapeHTML(rst.data.userName));
                $("#orgName").html($.common.escapeHTML(rst.data.orgName));
                $("#traceId").html(rst.data.traceId);
                $("#environment").html(rst.data.environment);
                $("#url").html($.common.escapeHTML(rst.data.url));
                $("#httpMethod").html(rst.data.httpMethod);
                $("#permissionCode").html(rst.data.permissionCode);
                $("#clientIp").html(rst.data.clientIp);
                $("#userAgent").html($.common.escapeHTML(rst.data.userAgent));
                $("#className").html(rst.data.className);
                $("#methodName").html(rst.data.methodName);
                $("#serverIp").html(rst.data.serverIp);
                $("#startTime").html(rst.data.startTime);
                $("#endTime").html(rst.data.endTime);
                $("#duration").html(rst.data.duration);
                $("#success").html((rst.data.success) ? $.msg("common.yes") : $.msg("common.no"));
            }
        }
    });

});