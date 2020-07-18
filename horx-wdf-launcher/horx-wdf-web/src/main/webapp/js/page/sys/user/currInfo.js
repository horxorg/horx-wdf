$(document).ready(function(){
    $.ajax({
        type : "GET",
        url : $.root + "/api/sys/user/getCurrUser",
        success : function(rst) {
            var checkRst = $.common.onAjaxSuccess(rst);
            if (!checkRst.success) {
                layer.msg(checkRst.msg);
                return;
            }

            showUserInfo(rst.data);
        }
    });

    function showUserInfo(data) {
        $("#name").html($.common.escapeHTML(data.name));
        $("#username").html($.common.escapeHTML(data.username));
        $("#org").html($.common.escapeHTML(data.orgName));
        $("#mobile").html($.common.escapeHTML(data.mobile));
        $("#phone").html($.common.escapeHTML(data.phone));
        $("#email").html($.common.escapeHTML(data.email));
    }
});