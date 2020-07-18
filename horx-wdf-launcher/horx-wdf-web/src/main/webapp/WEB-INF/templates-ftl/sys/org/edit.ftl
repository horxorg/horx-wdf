<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
    <script type="text/javascript">
        var dict=${dict!'{}'};
    </script>
</head>
<body>
<input type="hidden" id="id" value="${id!''}">
<input type="hidden" id="parentId" value="${parentId!''}">
<div id="form" class="layui-form layui-container edit-container" lay-filter="edit-form">
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.org.parent"/></label>
        <div id="parentName" class="layui-col-xs8 layui-col-md6 edit-div">
            ${parentName!''}
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.org.name"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="name" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.org.code"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="code" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.org.type"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" id="type" name="type" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.org.establishDate"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" id="establishDate" name="establishDate" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.enabled"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="checkbox" checked="" id="enabled" name="enabled" lay-skin="switch" lay-filter="enabled" switch-value="true|false" lay-text="<@spring.message "common.yes"/>|<@spring.message "common.no"/>">
        </div>
    </div>
    <div id="cancelDateRow" class="layui-row edit-row layui-hide">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.org.cancelDate"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" id="cancelDate" name="cancelDate" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.displaySeq"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="displaySeq" autocomplete="off" lay-verify="number" class="layui-input">
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
<script src="${rc.contextPath}/js/page/sys/org/edit.js?${staticVer}"></script>
</body>
</html>
