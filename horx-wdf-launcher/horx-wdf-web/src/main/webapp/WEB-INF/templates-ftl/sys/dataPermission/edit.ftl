<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
    <script type="text/javascript">
        var dict=${dict!'{}'};
    </script>
</head>
<body>
<input type="hidden" id="id" name="id" value="${id!''}">
<div id="form" class="layui-form layui-container edit-container" lay-filter="edit-form">
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dataPermission.name"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="name" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dataPermission.code"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="code" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dataPermission.objType"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <#if !(id??)>
                <input type="text" id="objType" name="objType" lay-verify="required" autocomplete="off" class="layui-input hx-dropdown">
            </#if>
            <#if (id??)>
              <div id="objType-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
           </#if>
        </div>
    </div>
    <div id="objIdDiv" class="layui-row edit-row layui-hide">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dataPermission.obj"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
        <#if !(id??)>
            <input type="text" id="objId" name="objId" lay-verify="objId" autocomplete="off" class="layui-input hx-dropdown">
        </#if>
        <#if !(id??)>
            <div id="objId-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </#if>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.enabled"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="checkbox" checked="" id="enabled" name="enabled" lay-skin="switch" lay-filter="enabled" switch-value="true|false" lay-text="<@spring.message "common.yes"/>|<@spring.message "common.no"/>">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.displaySeq"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="displaySeq" lay-verify="number" autocomplete="off" class="layui-input">
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


</div>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/dataPermission/edit.js?${staticVer}"></script>
</body>
</html>
