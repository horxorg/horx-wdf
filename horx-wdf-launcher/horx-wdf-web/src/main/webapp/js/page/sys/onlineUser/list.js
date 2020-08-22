$(document).ready(function(){
    $("#orgIds").treeSelect({
        width: 300,
        multi:true,
        load: {
            type: "POST",
            url: $.root + "/api/sys/org/queryForTree?accessPermissionCode=sys.onlineUser.query"
        },
        treeConf: {
            checkParent: false
        }
    });

    function beforeReload(formData) {
        var orgIds = $("#orgIds").treeSelect().getValue();
        formData.orgIds = orgIds;
    }

    var conf = {
        gridConf:{
            url:$.root + "/api/sys/onlineUser/paginationQuery",
            initSort:{field:"createTime", type:"desc"},
            cols: [[
                {field: "id", type: "checkbox", width:40, align:"center", fixed: "left"},
                {field: "name", title: $.msg("sys.user.name"), width:120, minWidth:120, align:"center", sort: true},
                {field: "username", title: $.msg("sys.user.username"), width:120, align:"center", sort: true},
                {field: "orgName", title: $.msg("sys.user.org"), width:160, align:"center", sort: true},
                {field: "createTime", title: $.msg("sys.onlineUser.loginTime"), width:180, align:"center", sort: true},
                {field: "clientIp", title: $.msg("sys.onlineUser.loginIp"), width:160, align:"center", sort: true},
                {field: "lastAccessTime", title: $.msg("sys.onlineUser.lastAccessTime"), width:180, align:"center", sort: true}
            ]]
        },
        beforeReload: beforeReload,
        onToolbarClick: onToolbarClick
    }
    $.common.listPage(conf);

    function onToolbarClick(obj) {
        if (obj.event == "offline") {
            offline();
        }
    }

    function offline() {
        var checkData = layui.table.checkStatus("grid").data;
        if (checkData == null || checkData.length <= 0) {
            layer.msg($.msg("common.oper.needRow"));
            return;
        }

        var ids = [];
        for (var i = 0; i< checkData.length; i++) {
            ids.push(checkData[i]["id"]);
        }

        layer.confirm($.msg("common.operPrompt"), {title:$.msg("common.confirmCaption"), icon:3, btn: [$.msg("common.confirm"), $.msg("common.cancel")]}, function (index) {
            layer.close(index);

            var loadIndex = layer.load();
            $.ajax({
                type: "POST",
                url: $.root + "/api/sys/onlineUser/offline",
                data: {id:ids},
                success: function(rst) {
                    var checkRst = $.common.onAjaxSuccess(rst);
                    if (!checkRst.success) {
                        layer.msg(checkRst.msg);
                        return;
                    }

                    layui.table.reload("grid");
                },
                error: function() {
                    layer.msg($.msg("common.oper.fail"));
                },
                complete: function () {
                    layer.close(loadIndex);
                }
            });
        });
    }
});