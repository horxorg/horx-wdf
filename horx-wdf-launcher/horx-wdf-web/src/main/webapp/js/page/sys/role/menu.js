$(document).ready(function(){
    var detail = $("#detail").val();
    if (detail == "1") {
        $(".bottom-div").addClass("layui-hide");
    }

    var tree = layui.tree;

    var id = $("#id").val();
    var usable = $("#usable").val();
    var objType = $("#objType").val();

    $.ajax({
        type: "GET",
        url: $.root + "/api/sys/role/queryMenu?id=" + id + "&usable=" + usable + "&objType=" + objType,
        success: function(rst) {
            function convertData(data) {
                if (data == null) {
                    return null;
                }
                for (var i = 0; i < data.length; i++) {
                    var item = data[i];
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

            var data = rst.data;
            convertData(data);
            if (data != null && data.length == 1) {
                data[0]["spread"] = true;
            }

            tree.render({
                elem:$("#menuPermissions"),
                id:"menuPermissions",
                data:data,
                multiStatus:{baseClass:"iconfont power-tree-status", statusList:[{value:0, class:"iconfont-checkbox-off"},{value:1, class:"iconfont-checkbox-partial"},{value:2, class:"iconfont-checkbox-on"}]}

            });

            if (id != null) {
                queryMenuPermissions();
            }

        }
    });

    function queryMenuPermissions() {
        $.ajax({
            url: $.root + "/api/sys/role/menu/" + id + "?objType=" + objType + "&usable=" + usable,
            success: function (rst) {
                var data = rst.data;
                tree.setStatusValue("menuPermissions", data);
            }
        });
    }

    $("#save-btn").on("click", function () {
        $.ajax({
            type: "PUT",
            url: $.root + "/api/sys/role/menu/" + id,
            data: {"objType": objType, "menuPermissions": JSON.stringify(tree.getStatusValue("menuPermissions", [1,2]))},
            success: function (rst) {
                ajaxResult = $.common.onAjaxSuccess(rst);
                if (!ajaxResult.success) {
                    layer.msg(ajaxResult.msg);
                    return;
                }
                layer.msg($.msg("common.oper.ok"));
                parent.saveMenuSuccess();
            },
            error: function() {
                layer.msg($.msg("common.oper.fail"));
            }
        });
    });

    $("#cancel-btn").on("click", function () {
        parent.cancel();
    });

});