$(document).ready(function(){
    $("#status").listSelect({
        multi:true,
        data: dict.userStatus,
        idProp: "code",
        search: false
    });

    $("#pwdErrLocked").listSelect({
        multi:false,
        data: [{"code":"1", "name":$.msg("common.yes")}, {"code":"0", "name":$.msg("common.no")}],
        idProp: "code",
        search: false
    });

    $("#orgIds").treeSelect({
        width: 300,
        multi:true,
        load: {
            type: "POST",
            url: $.root + "/api/sys/org/queryForTree?accessPermissionCode=sys.user.query"
        },
        treeConf: {
            checkParent: false
        }
    });

    function beforeReload(formData) {
        var orgIds = $("#orgIds").treeSelect().getValue();
        formData.orgIds = orgIds;
        var status = $("#status").listSelect().getValue();
        formData.status = status;
        var pwdErrLocked = $("#pwdErrLocked").listSelect().getValue();
        formData.pwdErrLocked = pwdErrLocked;
    }

    function convertStatus(rowData) {
        return $.common.convertByList(dict.userStatus, rowData.status, "", "code", "name");
    }

    var conf = {
        gridConf:{
            url:$.root + "/api/sys/user/pagingQuery",
            initSort:{field:"username", type:"asc"},
            cols: [[
                {field: "id", type: "checkbox", width:40, align:"center", fixed: "left"},
                {field: "name", title: $.msg("sys.user.name"), width:120, minWidth:120, align:"center", sort: true, fixed: "left"},
                {field: "username", title: $.msg("sys.user.username"), width:120, align:"center", sort: true},
                {field: "orgName", title: $.msg("sys.user.org"), width:160, align:"center", sort: true},
                {field: "mobile", title: $.msg("sys.user.mobile"), width:120, align:"center", sort: true},
                {field: "phone", title: $.msg("sys.user.phone"), width:120, align:"center", sort: true},
                {field: "email", title: $.msg("sys.user.email"), width:120, align:"center", sort: true},
                {field: "status", title: $.msg("sys.user.status"), width:120, align:"center", sort: true, templet:convertStatus},
                {field: "pwdErrLockTime", title: $.msg("sys.user.pwdErrLockTime"), width:180, align:"center", sort: true},
                {field: "lastLoginTime", title: $.msg("sys.user.lastLoginTime"), width:180, align:"center", sort: true},
                {field: "lastLoginIp", title: $.msg("sys.user.lastLoginIp"), width:160, align:"center", sort: true},
                {field: "remarks", title: $.msg("common.remarks"), width:200, sort: true}
            ]]
        },
        createPage: $.root + "/page/sys/user/create",
        modifyPage: $.root + "/page/sys/user/modify/{0}",
        removeApi: $.root + "/api/sys/user/{0}",
        beforeReload: beforeReload,
        onToolbarClick: onToolbarClick
    }
    $.common.listPage(conf);

    function onToolbarClick(obj) {
        if (obj.event == "userAuth") {
            userAuth();
        } else if (obj.event == "modifyPwd") {
            modifyPwd();
        } else if (obj.event == "unlock") {
            unlock();
        }
    }

    function userAuth() {
        var checkData = layui.table.checkStatus("grid").data;
        if (checkData == null || checkData.length != 1) {
            layer.msg($.msg("common.oper.needOneRow"));
            return;
        }

        var win = $(window);
        layui.layer.open({
            type: 2,
            title: $.msg("sys.dataPermission.dataAuth"),
            content: $.root + "/page/sys/dataAuthority/user/list?userId=" + checkData[0].id,
            maxmin: true,
            area: [(win.width() - 100) + "px", (win.height() - 80) + "px"]
        });
    }

    function modifyPwd() {
        var checkData = layui.table.checkStatus("grid").data;
        if (checkData == null || checkData.length != 1) {
            layer.msg($.msg("common.oper.needOneRow"));
            return;
        }

        var win = $(window);
        layui.layer.open({
            type: 2,
            title: $.msg("sys.user.password.modify"),
            content: $.root + "/page/sys/user/modifyPwd/" + checkData[0].id,
            maxmin: true,
            area: [(win.width() - 100) + "px", (win.height() - 80) + "px"],
            btn: [$.msg("common.save"), $.msg("common.cancel")],
            yes: function(index, layero){
                var submit = layero.find('iframe').contents().find("#edit-form-submit");
                submit.trigger("click");
            }
        });
    }

    function unlock() {
        var checkData = layui.table.checkStatus("grid").data;
        if (checkData == null || checkData.length <= 0) {
            layer.msg($.msg("common.oper.needRow"));
            return;
        }

        var exist = false;
        for (var i = 0; i < checkData.length; i++) {
            if (checkData[i].pwdErrLockTime != null) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            layer.msg($.msg("common.oper.needNt"));
            return;
        }

        var ids = [];
        for (var i = 0; i< checkData.length; i++) {
            ids.push(checkData[i]["id"]);
        }

        var loadIndex = layer.load();
        $.ajax({
            type: "POST",
            url: $.root + "/api/sys/user/unlock",
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
    }
});