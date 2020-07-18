$(document).ready(function(){
    function convertType(d) {
        if (d.type == "01") {
            return $.msg("sys.menu.type.01");
        } else if (d.type == "02") {
            return $.msg("sys.menu.type.02");
        }
        return "";
    }

    function convertVisible(d) {
        return (d.visible) ? $.msg("common.yes") : $.msg("common.no");
    }

    function convertEnabled(d) {
        return (d.enabled) ? $.msg("common.yes") : $.msg("common.no");
    }

    function convertIcon(d) {
        if (d.iconContent != null && d.iconContent != "") {
            return "<i class='layui-icon " + d.iconContent + "'></i>";
        }
        return "";
    }

    var conf = {
        parentIdRequired: true,
        treeGridConf: {
            treeDataType: "list",
            url: $.root + "/api/sys/menu/query",
            initSort:{field:"displaySeq", type:"asc"},
            cols: [[
                {field: "id", type: "checkbox", width:40, align:"center"},
                {field: "name", title: $.msg("sys.menu.name"), width:260, sort: true},
                {field: "type", title: $.msg("sys.menu.type"), width:100, align:"center", sort: true, templet:convertType},
                {field: "iconContent", title: $.msg("sys.menu.icon"), width:80, align:"center", sort: false, templet:convertIcon},
                {field: "code", title: $.msg("sys.menu.code"), width:200, align:"center", sort: true},
                {field: "url", title: $.msg("sys.menu.url"), width:200, sort: true},
                {field: "visible", title: $.msg("sys.menu.visible"), width:120, align:"center", sort: true, templet:convertVisible},
                {field: "enabled", title: $.msg("common.enabled"), width:120, align:"center", sort: true, templet:convertEnabled},
                {field: "displaySeq", title: $.msg("common.displaySeq"), width:120, align:"center", sort: true},
                {field: "remarks", title: $.msg("common.remarks"), width:200, sort: false}
            ]]
        },
        createPage: $.root + "/page/sys/menu/create?parentId={0}",
        modifyPage: $.root + "/page/sys/menu/modify/{0}",
        removeApi: $.root + "/api/sys/menu/{0}"
    };

    var treePage = $.common.treePage(conf);
    window["reloadTreeTable"] = treePage.renderTreeTable;

});