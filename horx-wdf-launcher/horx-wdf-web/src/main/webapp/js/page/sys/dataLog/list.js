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

    var conf = {
        gridConf:{
            url: $.root + "/api/sys/dataLog/paginationQuery",
            initSort: {field:"startTime", type:"desc"},
            where: {startTime: $("#startTime").val(), endTime: $("#endTime").val()},
            cols: [[
                {field: "id", type: "checkbox", width:40, align:"center"},
                {field: "userName", title: $.msg("sys.user.name"), width:160, minWidth:120, align:"center", sort: true},
                {field: "orgName", title: $.msg("sys.user.org"), width:160, minWidth:120, align:"center", sort: true},
                {field: "traceId", title: "Trace Id", width:200, align:"center", sort: true},
                {field: "bizType", title: $.msg("sys.dataLog.bizType"), width:160, align:"center", sort: true},
                {field: "bizName", title: $.msg("sys.dataLog.bizName"), width:160, align:"center", sort: true},
                {field: "operationType", title: $.msg("sys.dataLog.operationType"), align:"center", width:160, sort: true},
                {field: "description", title: $.msg("sys.dataLog.description"), width:150, sort: true},
                {field: "className", title: $.msg("sys.accessLog.className"), width:200, sort: true},
                {field: "methodName", title: $.msg("sys.accessLog.methodName"), width:150, sort: true},
                {field: "serverIp", title: $.msg("sys.accessLog.serverIp"), width:150, sort: true},
                {field: "startTime", title: $.msg("common.startTime"), align:"center", width:200, sort: true},
                {field: "endTime", title: $.msg("common.endTime"), align:"center", width:200, sort: true},
                {field: "duration", title: $.msg("sys.accessLog.duration"), align:"center", width:150, sort: true}
            ]]
        },
        detailPage: $.root + "/page/sys/dataLog/detail/{0}"
    }
    $.common.listPage(conf);

});