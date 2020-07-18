$(document).ready(function(){
    var laydate = layui.laydate;
    laydate.render({elem:"#establishDate"});
    laydate.render({elem:"#cancelDate"});

    $.common.editPage({
        loadApi:$.root + "/api/sys/org/{0}",
        createApi:$.root + "/api/sys/org",
        modifyApi:$.root + "/api/sys/org/{0}",
        onLoad:function(data) {
            $("#type").listSelect({
                multi:false,
                value:(data == null) ? null : data.type,
                data: dict.orgType,
                idProp: "code",
                search: false
            });

            if (data == null) {
                return;
            }

            if (data.parentName != null) {
                $("#parentName").html($.common.escapeHTML(data.parentName));
            }

            if (!data.enabled) {
                $("#cancelDateRow").removeClass("layui-hide");
            }
        },
        beforeSubmit : function (data) {
            if ($("#id").val() == "") {
                data["parentId"] = $("#parentId").val();
            }

            var type = $("#type").listSelect().getValue();
            data.type = type;
        },
        onSaveSuccess:function () {
            parent["reloadTreeTable"]();
        }
    });

    var form = layui.form;
    form.on("switch(enabled)", function () {
        if (this.checked) {
            $("#cancelDateRow").addClass("layui-hide");
        } else {
            $("#cancelDateRow").removeClass("layui-hide");
        }
    });
});