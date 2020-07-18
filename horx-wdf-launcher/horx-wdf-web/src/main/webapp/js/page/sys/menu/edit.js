$(document).ready(function(){

    $.common.editPage({
        loadApi:$.root + "/api/sys/menu/{0}",
        createApi:$.root + "/api/sys/menu",
        modifyApi:$.root + "/api/sys/menu/{0}",
        onLoad:function(data) {
            if (data == null) {
                return;
            }

            if (data.parentName != null) {
                $("#parentName").html($.common.escapeHTML(data.parentName));
            }

            var iconContent = data["iconContent"];
            if (iconContent != null && iconContent != "") {
                $("input[name='iconContent']").val(iconContent);
                $("#menuIcon").addClass(iconContent);
            }
        },
        beforeSubmit : function (data) {
            if ($("#id").val() == "") {
                data["parentId"] = $("#parentId").val();
            }
        },
        onSaveSuccess:function () {
            parent["reloadTreeTable"]();
        }
    });

    var iconLayer = null;
    $("#btnSelect").on("click", function () {
        var win = $(window);
        iconLayer = layui.layer.open({
            type: 2,
            title: $.msg("sys.menu.icon"),
            content: $.root + "/page/sys/pub/icon",
            maxmin: true,
            area: [(win.width() - 100) + "px", (win.height() - 80) + "px"]
        });
    });

    $("#btnCancel").on("click", function () {
        var $iconContent = $("input[name='iconContent']")
        var $menuIcon = $("#menuIcon");
        var iconContent = $iconContent.val();
        if (iconContent != "") {
            $menuIcon.removeClass(iconContent);
        }
        $iconContent.val("");
    });
    
    window["selIcon"] = function (iconClass) {
        var $iconContent = $("input[name='iconContent']");
        var $menuIcon = $("#menuIcon");
        var iconContent = $iconContent.val();
        if (iconContent != "") {
            $menuIcon.removeClass(iconContent);
        }
        $menuIcon.addClass(iconClass);
        $iconContent.val(iconClass);
        layui.layer.close(iconLayer);
    }
});