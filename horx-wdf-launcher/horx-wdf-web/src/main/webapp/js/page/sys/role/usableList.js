$(document).ready(function(){
    $("#subUsable").listSelect({
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
    }

    function convertEnabled(d) {
        return (d.enabled) ? $.msg("common.yes") : $.msg("common.no");
    }
    function convertSubUsable(d) {
        return (d.subUsable) ? $.msg("common.yes") : $.msg("common.no");
    }

    var conf = {
        gridConf:{
            url:$.root + "/api/sys/role/usable/pagingQuery",
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
        detailPage: $.root + "/page/sys/role/detailTab/{0}?usable=1",
        beforeReload: beforeReload
    }
    $.common.listPage(conf);

});