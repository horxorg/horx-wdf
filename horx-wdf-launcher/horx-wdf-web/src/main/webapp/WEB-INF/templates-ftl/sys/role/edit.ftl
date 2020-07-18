<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
</head>
<body class="bottom-layer-body">
<input type="hidden" id="id" name="id" value="${id!''}">
<div id="form" class="layui-form layui-container edit-container" lay-filter="edit-form">
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.role.name"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="name" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.role.code"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="code" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><#if (orgId??)><span class="edit-required">*</span></#if><@spring.message "sys.role.org"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" id="orgId" name="orgId" <#if (orgId??)>lay-verify="required"</#if> autocomplete="off" class="layui-input hx-dropdown">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.role.subUsable"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="checkbox" id="subUsable" name="subUsable" lay-skin="switch" lay-filter="subUsable" switch-value="true|false" lay-text="<@spring.message "common.yes"/>|<@spring.message "common.no"/>">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.enabled"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="checkbox" checked="" id="enabled" name="enabled" lay-skin="switch" lay-filter="enabled" switch-value="true|false" lay-text="<@spring.message "common.yes"/>|<@spring.message "common.no"/>">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.remarks"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="remarks" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="edit-form-submit" id="edit-form-submit">
    </div>

    <div class="bottom-div">
        <div class="layui-layer-btn">
            <a id="save-btn" class="layui-layer-btn0"><@spring.message "common.save"/></a>
            <a id="cancel-btn" class="layui-layer-btn1"><@spring.message "common.cancel"/></a>
        </div>
    </div>
</div>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/role/edit.js?${staticVer}"></script>
</body>
</html>
