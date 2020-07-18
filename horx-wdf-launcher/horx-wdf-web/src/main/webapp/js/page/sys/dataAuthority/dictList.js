$(document).ready(function(){
    var detail = $("#detail").val();
    if (detail == "1") {
        $(".bottom-div").addClass("layui-hide");
        $("#authorityType").addClass("layui-disabled");
        $("#authorityDetail").addClass("layui-disabled");
        $("#enabled").attr("disabled", "disabled");
        $("#remarks").attr("disabled", "disabled");
        $("#remarks").addClass("layui-disabled");
    }

    var form = layui.form;

    var authorityDetailInited = false;
    var $authorityDetailRow = $("#authorityDetailRow");
    var $authorityDetail = $("#authorityDetail");

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
            search: false,
            onChange: changeAuthorityType
        });

        if (data != null) {
            changeAuthorityType(data.authorityType);
        }
    }

    function beforeSubmit(formData) {
        if (authorityObjId != null) {
            formData.objId = authorityObjId;
        }
        var authorityType = $("#authorityType").listSelect().getValue();
        formData.authorityType = authorityType;

        if (authorityType == "assigned") {
            var values = $authorityDetail.listSelect().getValue();
            var details = [];
            if (values != null && values.length > 0) {
                for (var i = 0; i < values.length; i++) {
                    details.push({"authorityValue": values[i]});
                }
            }

            formData.detailList = JSON.stringify(details);
        } else {
            formData.detailList = "";
        }
    }

    if (authorityObjType != "default") {
        $("#save-btn").on("click", function () {
            $("#edit-form-submit").trigger("click");
        });
    }


    function changeAuthorityType(authorityType) {
        if (authorityType == "assigned") {
            $authorityDetailRow.removeClass("layui-hide");
            if (!authorityDetailInited) {
                var items = null;
                if (data != null) {
                    var detailList = data.detailList;
                    if (detailList != null && detailList.length > 0) {
                        items = [];
                        for (var i = 0; i < detailList.length; i++) {
                            items.push(detailList[i].authorityValue);
                        }
                    }
                }

                $authorityDetail.listSelect({
                    multi:true,
                    value: (data == null) ? null : items,
                    load: {
                        type: "GET",
                        url: $.root + "/api/sys/dataAuthority/" + authorityObjType + "/dict/list/" + $("#dataPermissionId").val()
                    },
                    idProp: "code",
                    search: true,
                    searchProps: ["code", "name", "fullCode", "simpleCode"]
                });
                authorityDetailInited = true;
            }
        } else {
            $authorityDetailRow.addClass("layui-hide");
        }
    }
});