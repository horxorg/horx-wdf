$(document).ready(function(){
    var form = layui.form;
    form.render();

    $.common.formEnterToClick($(".layui-form"), $("button[type='submit']"));

    var initResult = null;
    $.ajax({
        type: "GET",
        url: $.root + "/public/api/loginInit",
        success: function (rst) {
            initResult = $.common.onAjaxSuccess(rst);
            if (!initResult.success) {
                layer.msg(initResult.msg);
                return;
            }
            processVcode(initResult.data.needVcode);
        }
    });

    form.on('submit(login-submit)', function(data){
        if (!initResult.success) {
            return false;
        }
        $(".login-waiting").show();
        $.ajax({
            type: "POST",
            url: initResult.data.identifyUrl,
            data: data.field,
            success: function (rst) {
                var ajaxResult = $.common.onAjaxSuccess(rst);
                if (!ajaxResult.success) {
                    layer.msg(ajaxResult.msg);

                    if (ajaxResult.data) {
                        processVcode(ajaxResult.data.needVcode);
                    }

                    return;
                }

                var returnUrl = initResult.data.returnUrl;
                if (!returnUrl) {
                    returnUrl = $("#returnUrl").val();
                }
                if (!returnUrl) {
                    returnUrl = $.root + "/main";
                }
                window.location = returnUrl;
            },
            error: function (jqXHR, textStatus, errorThrown) {alert(jqXHR)
                layer.msg("登录出现错误，请稍后重试");
            },
            complete: function () {
                $(".login-waiting").hide();
            }
        });
    });
    
    function processVcode(needVcode) {
        if (needVcode) {
            $("#vcode-row").removeClass("layui-hide");
            var timestamp =  new Date().getTime();
            $("#vcode-img").attr("src", initResult.data.vcodeUrl + "?" + timestamp);
            $("#login-vercode").attr("lay-verify", "required");
        } else {
            $("#vcode-row").addClass("layui-hide");
            $("#login-vercode").attr("lay-verify", "");
        }
    }

    $("#vcode-img").on("click", function () {
        var timestamp =  new Date().getTime();
        $(this).attr("src", initResult.data.vcodeUrl + "?" + timestamp);
    });
});