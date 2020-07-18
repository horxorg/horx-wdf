<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
</head>
<body>
<input type="hidden" id="id" value="${id!''}">
<input type="hidden" id="parentId" value="${parentId!''}">
<div id="form" class="layui-form layui-container edit-container" lay-filter="edit-form">
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.menu.parent"/></label>
        <div id="parentName" class="layui-col-xs8 layui-col-md6 edit-div">
            ${parentName!''}
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.menu.name"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="name" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.menu.type"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <select name="type" xm-select-show-count="1" lay-verify="required">
                <option value=""><@spring.message "common.pleaseSelect"/></option>
                <option value="01"><@spring.message "sys.menu.type.01"/></option>
                <option value="02"><@spring.message "sys.menu.type.02"/></option>
            </select>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.menu.icon"/></label>
        <div class="layui-col-xs8 layui-col-md6" style="margin-top:5px">
            <div class="layui-inline" style="width:30px"><i id="menuIcon" class="layui-icon"></i></div>&nbsp;&nbsp;&nbsp;&nbsp;<button class="layui-btn layui-btn-sm" id="btnSelect"><@spring.message "common.select"/></button>&nbsp;&nbsp;<button class="layui-btn layui-btn-sm" id="btnCancel"><@spring.message "common.cancel"/></button>
            <input type="hidden" name="iconContent">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.menu.code"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="code" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.menu.url"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="url" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.menu.permissionCode"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <textarea name="permissionCode" class="layui-textarea"></textarea>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.menu.visible"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="checkbox" checked="" name="visible" lay-skin="switch" switch-value="true|false" lay-text="<@spring.message "common.yes"/>|<@spring.message "common.no"/>">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.enabled"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="checkbox" checked="" name="enabled" lay-skin="switch" switch-value="true|false" lay-text="<@spring.message "common.yes"/>|<@spring.message "common.no"/>">
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
<script src="${rc.contextPath}/js/page/sys/menu/edit.js?${staticVer}"></script>
</body>
</html>
