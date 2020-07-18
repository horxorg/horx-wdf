$(document).ready(function(){
    var form = layui.form;
    form.render();

    $.common.formEnterToClick($(".layui-form"), $("button[type='submit']"));

    form.on('submit(login-submit)', function(data){
        $(".login-waiting").show();
        $.ajax({
            type: "POST",
            url: $.root + "/identify",
            data: data.field,
            success: function (rst) {
                var ajaxResult = $.common.onAjaxSuccess(rst);
                if (!ajaxResult.success) {
                    layer.msg(ajaxResult.msg);
                    return;
                }
                window.location = $.root + "/" + rst.data;
            },
            error: function (jqXHR, textStatus, errorThrown) {alert(jqXHR)
                layer.msg("登录出现错误，请稍后重试");
            },
            complete: function () {
                $(".login-waiting").hide();
            }
        });
    });
});