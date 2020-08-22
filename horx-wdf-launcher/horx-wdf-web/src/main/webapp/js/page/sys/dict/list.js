$(document).ready(function(){
    $("#bizTypes").listSelect({
        multi:true,
        data: dict.bizType,
        idProp: "code",
        search: true,
        searchProps: ["code", "name", "fullCode", "simpleCode"]
    });

    $("#treeData").listSelect({
        multi:false,
        data: [{"code":"1", "name":$.msg("common.yes")}, {"code":"0", "name":$.msg("common.no")}],
        idProp: "code",
        search: false
    });

    function beforeReload(formData) {
        var bizTypes = $("#bizTypes").listSelect().getValue();
        formData.bizTypes = bizTypes;
        var treeData = $("#treeData").listSelect().getValue();
        formData.treeData = treeData;
    }

    function convertBizType(rowData) {
        return $.common.convertByList(dict.bizType, rowData.bizType, "", "code", "name");
    }

    function convertTreeData(rowData) {
        return (rowData.treeData) ? $.msg("common.yes") : $.msg("common.no");
    }

    var conf = {
        gridConf:{
            url:$.root + "/api/sys/dict/paginationQuery",
            initSort:{field:"code", type:"asc"},
            cols: [[
                {field: "id", type: "checkbox", width:40, align:"center"},
                {field: "name", title: $.msg("sys.dict.name"), width:160, minWidth:150, align:"center", sort: true},
                {field: "code", title: $.msg("sys.dict.code"), width:160, minWidth:150, align:"center", sort: true},
                {field: "bizType", title: $.msg("sys.dict.bizType"), width:150, align:"center", sort: true, templet:convertBizType},
                {field: "treeData", title: $.msg("sys.dict.treeData"), width:150, sort: true, align:"center", templet:convertTreeData},
                {field: "remarks", title: $.msg("common.remarks"), width:300, sort: false}
            ]]
        },
        createPage: $.root + "/page/sys/dict/tab?currTab=baseInfo",
        modifyPage: $.root + "/page/sys/dict/tab?currTab=baseInfo&dictId={0}",
        removeApi: $.root + "/api/sys/dict/{0}",
        showSaveBtn: false,
        onToolbarClick: onToolbarClick,
        beforeReload: beforeReload
    }
    $.common.listPage(conf);

    function onToolbarClick(obj) {
        if (obj.event == "item") {
            var checkStatus = layui.table.checkStatus("grid");
            if (checkStatus.data == null || checkStatus.data.length != 1) {
                layer.msg($.msg("common.oper.needOneRow"));
                return;
            }

            var win = $(window);
            var dlgConf = {
                type: 2,
                title: $.msg("sys.dict"),
                content: $.root + "/page/sys/dict/tab?currTab=item&dictId=" + checkStatus.data[0].id,
                maxmin: true,
                area: [(win.width() - 100) + "px", (win.height() - 80) + "px"]
            };
            layui.layer.open(dlgConf);
        }
    }

});