<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
</head>
<body>
<input type="hidden" id="dictId" name="dictId" value="${dictId}">
<input type="hidden" id="id" name="id" value="${id!''}">
<input type="hidden" id="parentId" value="${parentId!''}">
<input type="hidden" id="treeData" value="${treeData!''}">
<div id="form" class="layui-form layui-container edit-container" lay-filter="edit-form">
    <div id="parent-row" class="layui-row edit-row layui-hide">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.dict.item.parent"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
           <div id="parent-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dict.item.name"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" id="name" name="name" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dict.item.code"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="code" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.dict.item.fullCode"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" id="fullCode" name="fullCode" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.dict.item.simpleCode"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" id="simpleCode" name="simpleCode" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.enabled"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="checkbox" id="enabled" name="enabled" checked="checked" lay-skin="switch" switch-value="true|false" lay-text="<@spring.message "common.yes"/>|<@spring.message "common.no"/>">
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
<script src="${rc.contextPath}/js/Convert_Pinyin.js?${staticVer}"></script>
<script src="${rc.contextPath}/js/page/sys/dict/itemEdit.js?${staticVer}"></script>
</body>
</html>
