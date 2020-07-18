<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
    <script type="text/javascript">
        var dict=${dict};
    </script>
</head>
<body>
<input type="hidden" id="id" name="id" value="${id!''}">
<div id="form" class="layui-form layui-container edit-container" lay-filter="edit-form">
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.user.name"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="name" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.user.username"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" id="username" name="username" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <#if !(id??)>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.user.password"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="password" id="password" name="password" lay-verify="required|minChar" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.user.repassword"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="password" name="repassword" lay-verify="required|repassword" autocomplete="off" class="layui-input">
        </div>
    </div>
    </#if>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><#if (orgId??)><span class="edit-required">*</span></#if><@spring.message "sys.user.org"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" id="org" name="org" <#if (orgId??)>lay-verify="required"</#if> autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.user.status"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" id="status" name="status" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.role"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" id="role" name="role" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.user.mobile"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="mobile" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.user.phone"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="phone" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.user.email"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="text" name="email" autocomplete="off" class="layui-input">
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
<script src="${rc.contextPath}/js/page/sys/user/edit.js?${staticVer}"></script>
</body>
</html>
