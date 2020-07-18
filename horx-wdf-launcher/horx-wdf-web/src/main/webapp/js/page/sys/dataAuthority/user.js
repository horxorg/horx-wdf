$(document).ready(function(){
    var detail = $("#detail").val();
    if (detail == "1") {
        $(".bottom-div").addClass("layui-hide");
        $("#authorityType").addClass("layui-disabled");
        $("#enabled").attr("disabled", "disabled");
        $("#remarks").attr("disabled", "disabled");
        $("#remarks").addClass("layui-disabled");
    }

    var form = layui.form;

    $.common.editPage({
        data: data,
        createApi: $.root + "/api/sys/dataAuthority/" + authorityObjType,
        modifyApi: $.root + "/api/sys/dataAuthority/" + authorityObjType + "/{0}",
        onLoad: init,
        beforeSubmit: beforeSubmit,
        onSaveSuccess: function () {},
        showParentLayer: false
    });

    function init(data) {
        $("#authorityType").listSelect({
            multi:false,
            value: (data == null) ? null : data.authorityType,
            data: dict.authorityType,
            idProp: "code",
            search: false
        });
    }

    function beforeSubmit(formData) {
        if (authorityObjId != null) {
            formData.objId = authorityObjId;
        }
        var authorityType = $("#authorityType").listSelect().getValue();
        formData.authorityType = authorityType;
        formData.detailList = "";
    }

    if (authorityObjType != "default") {
        $("#save-btn").on("click", function () {
            $("#edit-form-submit").trigger("click");
        });
    }
});