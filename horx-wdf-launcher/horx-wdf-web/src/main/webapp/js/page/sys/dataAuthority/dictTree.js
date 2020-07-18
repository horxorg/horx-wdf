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
    var tree = layui.tree;

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

        $.ajax({
            type: "GET",
            url: $.root + "/api/sys/dataAuthority/" + authorityObjType + "/dict/tree/" + $("#dataPermissionId").val(),
            success: function(rst) {
                function convertData(data) {
                    if (data == null) {
                        return null;
                    }
                    for (var i = 0; i < data.length; i++) {
                        var item = data[i];
                        item.id = item.code;
                        if (item.title == null) {
                            item.title = item.name;
                        }
                        item.title = $.common.escapeHTML(item.title);

                        if (detail == "1") {
                            item.disabled = true;
                        }

                        if (item.children != null) {
                            convertData(item.children);
                        }
                    }

                }

                var rstData = rst.data;
                convertData(rstData);

                tree.render({
                    elem:  $authorityDetail,
                    id:"authorityDetail",
                    data: rstData,
                    multiStatus: {baseClass:"iconfont power-tree-status", statusList:[{value:0, class:"iconfont-checkbox-off"},{value:1, class:"iconfont-checkbox-partial"},{value:2, class:"iconfont-checkbox-on"}]}

                });

                if (data != null && data.detailList != null) {
                    var status = {};
                    for (var i = 0; i < data.detailList.length; i++) {
                        var detail = data.detailList[i];
                        status[detail.authorityValue] = detail.checkedType;
                    }
                    tree.setStatusValue("authorityDetail", status);
                }

            }
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
            var values = tree.getStatusValue("authorityDetail", [1,2]);
            var details = [];
            if (values != null) {
                for (var key in values) {
                    details.push({"authorityValue": key, "checkedType": values[key]});
                }
            }

            formData.detailList = JSON.stringify(details);
        } else {
            formData.detailList = "";s
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
        } else {
            $authorityDetailRow.addClass("layui-hide");
        }
    }
});