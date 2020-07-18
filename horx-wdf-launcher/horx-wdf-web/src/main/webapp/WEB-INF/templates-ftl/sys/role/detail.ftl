<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
</head>
<body>
<input type="hidden" id="id" name="id" value="${id}">
<input type="hidden" id="usable" name="usable" value="${usable!''}">
<div id="form" class="layui-form layui-container edit-container" lay-filter="edit-form">
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.role.name"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="name-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.role.code"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="code-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.role.org"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="orgId-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.role.subUsable"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="subUsable-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.enabled"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="enabled-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.remarks"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="remarks-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>

</div>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/role/detail.js?${staticVer}"></script>
</body>
</html>
