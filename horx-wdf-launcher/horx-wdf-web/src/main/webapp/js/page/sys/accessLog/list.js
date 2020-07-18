$(document).ready(function(){
    var laydate = layui.laydate;
    laydate.render({
        elem: '#startTime'
        ,type: 'datetime'
    });
    laydate.render({
        elem: '#endTime'
        ,type: 'datetime'
    });

    function convertSuccess(d) {
        return (d.success) ? $.msg("common.yes") : $.msg("common.no");
    }

    var conf = {
        gridConf:{
            url: $.root + "/api/sys/accessLog/pagingQuery",
            initSort: {field:"startTime", type:"desc"},
            where: {startTime: $("#startTime").val(), endTime: $("#endTime").val()},
            cols: [[
                {field: "id", type: "checkbox", width:40, align:"center"},
                {field: "userName", title: $.msg("sys.user.name"), width:160, minWidth:120, align:"center", sort: true},
                {field: "orgName", title: $.msg("sys.user.org"), width:160, minWidth:120, align:"center", sort: true},
                {field: "traceId", title: "Trace Id", width:200, align:"center", sort: true},
                {field: "environment", title: $.msg("sys.accessLog.environment"), width:120, align:"center", sort: true},
                {field: "url", title: "URL", width:200, align:"left", sort: true},
                {field: "httpMethod", title: "Http Method", width:160, align:"center", sort: true},
                {field: "permissionCode", title: $.msg("sys.menu.permissionCode"), align:"left", width:160, sort: true},
                {field: "clientIp", title: $.msg("sys.accessLog.clientIp"), width:150, sort: true},
                {field: "className", title: $.msg("sys.accessLog.className"), width:200, sort: true},
                {field: "methodName", title: $.msg("sys.accessLog.methodName"), width:150, sort: true},
                {field: "serverIp", title: $.msg("sys.accessLog.serverIp"), width:150, sort: true},
                {field: "startTime", title: $.msg("common.startTime"), align:"center", width:200, sort: true},
                {field: "endTime", title: $.msg("common.endTime"), align:"center", width:200, sort: true},
                {field: "duration", title: $.msg("sys.accessLog.duration"), align:"center", width:150, sort: true},
                {field: "success", title: $.msg("sys.accessLog.success"), align:"center", width:150, sort: true, templet:convertSuccess}
            ]]
        },
        detailPage: $.root + "/page/sys/accessLog/detail/{0}"
    }
    $.common.listPage(conf);

});