$(document).ready(function(){
    $("#subUsable").listSelect({
        multi:false,
        data: [{"code":"1", "name":$.msg("common.yes")}, {"code":"0", "name":$.msg("common.no")}],
        idProp: "code",
        search: false
    });

    $("#enabled").listSelect({
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
            url: $.root + "/api/sys/org/queryForTree?accessPermissionCode=sys.role.query"
        },
        treeConf: {
            checkParent: false
        }
    });

    function beforeReload(formData) {
        var orgIds = $("#orgIds").treeSelect().getValue();
        formData.orgIds = orgIds;
        var subUsable = $("#subUsable").listSelect().getValue();
        formData.subUsable = subUsable;
        var enabled = $("#enabled").listSelect().getValue();
        formData.enabled = enabled;
    }

    function convertEnabled(d) {
        return (d.enabled) ? $.msg("common.yes") : $.msg("common.no");
    }
    function convertSubUsable(d) {
        return (d.subUsable) ? $.msg("common.yes") : $.msg("common.no");
    }

    var conf = {
        gridConf:{
            url:$.root + "/api/sys/role/paginationQuery",
            initSort:{field:"name", type:"asc"},
            cols: [[
                {field: "id", type: "checkbox", width:40, align:"center"},
                {field: "name", title: $.msg("sys.role.name"), width:160, minWidth:120, align:"center", sort: true},
                {field: "code", title: $.msg("sys.role.code"), width:160, minWidth:120, align:"center", sort: true},
                {field: "orgName", title: $.msg("sys.role.org"), width:200, align:"center", sort: true},
                {field: "subUsable", title: $.msg("sys.role.subUsable"), width:140, sort: true, align:"center", templet:convertSubUsable},
                {field: "enabled", title: $.msg("common.enabled"), width:120, sort: true, align:"center", templet:convertEnabled},
                {field: "remarks", title: $.msg("common.remarks"), width:300, sort: true}
            ]]
        },
        createPage: $.root + "/page/sys/role/editTab",
        modifyPage: $.root + "/page/sys/role/editTab?id={0}",
        detailPage: $.root + "/page/sys/role/detailTab/{0}",
        removeApi: $.root + "/api/sys/role/{0}",
        showSaveBtn: false,
        beforeReload: beforeReload,
        onToolbarClick: onToolbarClick
    }
    $.common.listPage(conf);

    function onToolbarClick(obj) {
        if (obj.event == "menuAuth") {
            menuAuth();
        } else if (obj.event == "roleAuth") {
            roleAuth();
        }
    }

    function menuAuth() {
        var checkData = layui.table.checkStatus("grid").data;
        if (checkData == null || checkData.length != 1) {
            layer.msg($.msg("common.oper.needOneRow"));
            return;
        }

        var win = $(window);
        layui.layer.open({
            type: 2,
            title: checkData[0].name,
            content: $.root + "/page/sys/role/editTab?id=" + checkData[0].id + "&currTab=menu",
            maxmin: true,
            area: [(win.width() - 100) + "px", (win.height() - 80) + "px"]
        });
    }

    function roleAuth() {
        var checkData = layui.table.checkStatus("grid").data;
        if (checkData == null || checkData.length != 1) {
            layer.msg($.msg("common.oper.needOneRow"));
            return;
        }

        var win = $(window);
        layui.layer.open({
            type: 2,
            title: checkData[0].name,
            content: $.root + "/page/sys/role/editTab?id=" + checkData[0].id + "&currTab=data",
            maxmin: true,
            area: [(win.width() - 100) + "px", (win.height() - 80) + "px"]
        });
    }
});