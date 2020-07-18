$(document).ready(function(){
    function convertName(d) {
        return $.common.escapeHTML(d.name);
    }
    function convertCode(d) {
        return $.common.escapeHTML(d.code);
    }
    function convertType(rowData) {
        return $.common.convertByList(dict.orgType, rowData.type, "", "code", "name");
    }

    function convertEnabled(d) {
        return (d.enabled) ? $.msg("common.yes") : $.msg("common.no");
    }

    var conf = {
        parentIdRequired: parentIdRequired,
        treeGridConf: {
            treeDataType: "tree",
            url: $.root + "/api/sys/org/queryForTree?accessPermissionCode=sys.org.query",
            initSort:{field:"displaySeq", type:"asc"},
            cols: [[
                {field: "id", type: "checkbox", width:40, align:"center"},
                {field: "name", title: $.msg("sys.org.name"), width:260, sort: true,templet:convertName},
                {field: "code", title: $.msg("sys.org.code"), width:120, align:"center", sort: true,templet:convertCode},
                {field: "type", title: $.msg("sys.org.type"), width:100, align:"center", sort: true, templet:convertType},
                {field: "establishDate", title: $.msg("sys.org.establishDate"), width:120, align:"center", sort: true},
                {field: "enabled", title: $.msg("common.enabled"), width:120, align:"center", sort: true, templet:convertEnabled},
                {field: "cancelDate", title: $.msg("sys.org.cancelDate"), width:120, align:"center", sort: true},
                {field: "displaySeq", title: $.msg("common.displaySeq"), width:120, align:"center", sort: true},
                {field: "remarks", title: $.msg("common.remarks"), width:200, sort: false}
            ]]
        },
        createPage: $.root + "/page/sys/org/create?parentId={0}",
        modifyPage: $.root + "/page/sys/org/modify/{0}",
        removeApi: $.root + "/api/sys/org/{0}"
    };

    var treePage = $.common.treePage(conf);
    window["reloadTreeTable"] = treePage.renderTreeTable;

});