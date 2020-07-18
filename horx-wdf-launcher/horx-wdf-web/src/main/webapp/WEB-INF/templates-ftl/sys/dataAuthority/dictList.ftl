<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
    <script type="text/javascript">
        var dict=${dict!'{}'};
        var data=${data!'{}'};
        var authorityObjType = "${authorityObjType!''}";
        var authorityObjId = ${authorityObjId!'null'};
        var objId = ${(dataPermission.objId)!'null'};
    </script>
</head>
<body <#if (authorityObjType!='default')>class="bottom-layer-body"</#if>>
<input type="hidden" id="detail" name="detail" value="${detail!''}">
<div id="form" class="layui-form layui-container edit-container" lay-filter="edit-form">
    <input type="hidden" id="dataPermissionId" name="dataPermissionId" value="${(dataPermission.id)!''}">
    <input type="hidden" id="roleType" name="roleType" value="${roleType!''}">
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dataAuth.authorityType"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" id="authorityType" name="authorityType" lay-verify="required" autocomplete="off" class="layui-input hx-dropdown">
        </div>
    </div>
    <div id="authorityDetailRow" class="layui-row edit-row layui-hide">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.dataAuth.assigned"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" id="authorityDetail" autocomplete="off" class="layui-input hx-dropdown">
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
            <input type="text" id="remarks" name="remarks" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="edit-form-submit" id="edit-form-submit">
    </div>
</div>

<#if (authorityObjType!='default')>
<div class="bottom-div">
    <div class="layui-layer-btn">
        <a id="save-btn" class="layui-layer-btn0"><@spring.message "common.save"/></a>
    </div>
</div>
</#if>

<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/dataAuthority/dictList.js?${staticVer}"></script>
</body>
</html>
