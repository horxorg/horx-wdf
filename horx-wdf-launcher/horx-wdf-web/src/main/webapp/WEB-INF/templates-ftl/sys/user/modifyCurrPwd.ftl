<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
</head>
<body>
<div id="form" class="layui-form layui-container edit-container" lay-filter="edit-form">
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.user.password.old"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <input type="password" id="oldPwd" name="oldPwd" lay-verify="required" autocomplete="off" class="layui-input">
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.user.password.new"/></label>
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
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="edit-form-submit" id="edit-form-submit">
    </div>
</div>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/user/modifyCurrPwd.js?${staticVer}"></script>
</body>
</html>
