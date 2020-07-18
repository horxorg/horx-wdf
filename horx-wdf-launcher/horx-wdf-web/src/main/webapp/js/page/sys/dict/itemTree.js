$(document).ready(function(){
    var dictId = $("#dictId").val();

    function convertEnabled(d) {
        return (d.enabled) ? $.msg("common.yes") : $.msg("common.no");
    }

    var conf = {
        treeGridConf: {
            treeDataType: "tree",
            url: $.root + "/api/sys/dict/" + dictId + "/item/queryForTree",
            initSort:{field:"displaySeq", type:"asc"},
            cols: [[
                {field: "id", type: "checkbox", width:40, align:"center"},
                {field: "name", title: $.msg("sys.dict.item.name"), width:160, minWidth:150, align:"left", sort: true},
                {field: "code", title: $.msg("sys.dict.item.code"), width:200, minWidth:150, align:"center", sort: true},
                {field: "fullCode", title: $.msg("sys.dict.item.fullCode"), width:250, align:"center", sort: true},
                {field: "simpleCode", title: $.msg("sys.dict.item.simpleCode"), width:150, align:"center", sort: true},
                {field: "enabled", title: $.msg("common.enabled"), width:150, align:"center", sort: true, templet:convertEnabled},
                {field: "displaySeq", title: $.msg("common.displaySeq"), width:120, align:"center", sort: true},
                {field: "remarks", title: $.msg("common.remarks"), width:300, sort: false}
            ]]
        },
        createPage: $.root + "/page/sys/dict/" + dictId + "/item/create?parentId={0}&treeData=1",
        modifyPage: $.root + "/page/sys/dict/" + dictId + "/item/modify/{0}?treeData=1",
        removeApi: $.root + "/api/sys/dict/" + dictId + "/item/{0}"
    };

    var treePage = $.common.treePage(conf);
    window["reloadTreeTable"] = treePage.renderTreeTable;

});