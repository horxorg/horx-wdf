$(document).ready(function(){
    $("#objType").listSelect({
        multi:false,
        data: dict.dataPermissionType,
        idProp: "code",
        search: false,
        searchProps: ["code", "name", "fullCode", "simpleCode"]
    });

    $("#enabled").listSelect({
        multi:false,
        data: [{"code":"1", "name":$.msg("common.yes")}, {"code":"0", "name":$.msg("common.no")}],
        idProp: "code",
        search: false
    });

    function beforeReload(formData) {
        var objType = $("#objType").listSelect().getValue();
        formData.objType = objType;
        var enabled = $("#enabled").listSelect().getValue();
        formData.enabled = enabled;
    }

    function convertObjType(rowData) {
        return $.common.convertByList(dict.dataPermissionType, rowData.objType, "", "code", "name");
    }

    function convertEnabled(rowData) {
        return (rowData.enabled) ? $.msg("common.yes") : $.msg("common.no");
    }

    var conf = {
        gridConf:{
            url:$.root + "/api/sys/dataPermission/pagingQuery",
            initSort:{field:"displaySeq", type:"asc"},
            cols: [[
                {field: "id", type: "checkbox", width:40, align:"center", fixed: "left"},
                {field: "name", title: $.msg("sys.dataPermission.name"), width:200, align:"center", sort: true},
                {field: "code", title: $.msg("sys.dataPermission.code"), width:200, align:"center", sort: true},
                {field: "objType", title: $.msg("sys.dataPermission.objType"), width:160, align:"center", sort: true, templet:convertObjType},
                {field: "objName", title: $.msg("sys.dataPermission.obj"), width:200, align:"center", sort: true},
                {field: "enabled", title: $.msg("common.enabled"), width:150, align:"center", sort: true, templet:convertEnabled},
                {field: "displaySeq", title: $.msg("common.displaySeq"), width:120, align:"center", sort: true},
                {field: "remarks", title: $.msg("common.remarks"), width:200, sort: true}
            ]]
        },
        createPage: $.root + "/page/sys/dataPermission/create",
        modifyPage: $.root + "/page/sys/dataPermission/modify/{0}",
        detailPage: $.root + "/page/sys/dataPermission/detail/{0}",
        removeApi: $.root + "/api/sys/dataPermission/{0}",
        beforeReload: beforeReload,
        onToolbarClick: onToolbarClick
    }
    $.common.listPage(conf);

    function onToolbarClick(obj) {
        if (obj.event == "defaultAuth") {
            defaultAuth();
        }
    }

    function defaultAuth() {
        var checkData = layui.table.checkStatus("grid").data;
        if (checkData == null || checkData.length != 1) {
            layer.msg($.msg("common.oper.needOneRow"));
            return;
        }

        var win = $(window);
        layui.layer.open({
            type: 2,
            title: $.msg("sys.dataPermission.defaultAuth"),
            content: $.root + "/page/sys/dataAuthority/default/" + checkData[0].id,
            maxmin: true,
            area: [(win.width() - 100) + "px", (win.height() - 80) + "px"],
            btn: [$.msg("common.save"), $.msg("common.cancel")],
            yes: function(index, layero){
                var submit = layero.find('iframe').contents().find("#edit-form-submit");
                submit.trigger("click");
            }
        });
    }

});