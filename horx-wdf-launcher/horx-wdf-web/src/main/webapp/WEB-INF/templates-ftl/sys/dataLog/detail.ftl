<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
</head>
<body>
<input type="hidden" id="id" name="id" value="${id}">
<div id="form" class="layui-form layui-container edit-container" lay-filter="edit-form">
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.user.name"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="userName" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.user.org"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="orgName" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label">Trace Id</label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="traceId" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.dataLog.bizType"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="bizType" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.dataLog.bizName"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="bizName" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.dataLog.operationType"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="operationType" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.dataLog.data"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="data" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.dataLog.description"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="description" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.accessLog.className"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="className" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.accessLog.methodName"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="methodName" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.accessLog.serverIp"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="serverIp" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.startTime"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="startTime" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.endTime"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="endTime" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.accessLog.duration"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="duration" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>

</div>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/dataLog/detail.js?${staticVer}"></script>
</body>
</html>
