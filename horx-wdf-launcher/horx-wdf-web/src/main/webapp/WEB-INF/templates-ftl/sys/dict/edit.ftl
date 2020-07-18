<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
    <script type="text/javascript">
        var dict=${dict};
    </script>
</head>
<body class="bottom-layer-body">
<input type="hidden" id="id" name="id" value="${id!''}">
<div id="form" class="layui-form layui-container edit-container" lay-filter="edit-form">
    <input type="hidden" id="parentId" name="parentId" value="${parentId!''}">
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dict.name"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="name" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dict.code"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="code" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dict.bizType"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" id="bizType" name="bizType" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.dict.treeData"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <#if !(id??)><input type="checkbox" id="treeData" name="treeData" lay-skin="switch" switch-value="true|false" lay-text="<@spring.message "common.yes"/>|<@spring.message "common.no"/>"></#if>
            <#if (id??)><div id="tree-data-div" class="layui-col-xs8 layui-col-md6 edit-div"></div></#if>
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



<div class="bottom-div">
    <div class="layui-layer-btn">
        <a id="save-btn" class="layui-layer-btn0"><@spring.message "common.save"/></a>
        <a id="cancel-btn" class="layui-layer-btn1"><@spring.message "common.cancel"/></a>
    </div>
</div>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/dict/edit.js?${staticVer}"></script>
</body>
</html>
