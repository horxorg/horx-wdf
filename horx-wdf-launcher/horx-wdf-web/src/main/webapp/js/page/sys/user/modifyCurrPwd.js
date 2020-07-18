$(document).ready(function(){
    var form = layui.form;
    form.render();
    form.verify({
            repassword:function (value) {
                var pwd = $("input[name='password']").val();
                if (pwd != value) {
                    return $.msg("sys.user.pwdne");
                }
                return null;
            },
            minChar:function (value) {
                if (value == null || value.length < 6) {
                    return $.msg("common.minChar", $.msg("sys.user.password"), "6");
                }
                return null;
            }
    });

    form.on("submit(edit-form-submit)", function(data) {
        var field = data.field;

        var parentLayer = parent.layer;
        var index = parentLayer.getFrameIndex(window.name);

        $.ajax({
            type: "POST",
            url: $.root + "/api/sys/user/modifyCurrPwd",
            data: field,
            success: function(rst) {
                var checkRst = $.common.onAjaxSuccess(rst);
                if (!checkRst.success) {
                    layer.msg(checkRst.msg);
                    return;
                }

                parentLayer.close(index);
                parentLayer.msg($.msg("sys.user.password.modify.ok"));
            }
        });
    });
});