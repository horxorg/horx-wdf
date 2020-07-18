<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
</head>
<body>
<div id="form" class="layui-form layui-container edit-container" lay-filter="edit-form">
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.user.name"/></label>
        <div id="name" class="layui-col-xs8 layui-col-md6 edit-div"></div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.user.username"/></label>
        <div id="username" class="layui-col-xs8 layui-col-md6 edit-div"></div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.user.org"/></label>
        <div id="org" class="layui-col-xs8 layui-col-md6 edit-div"></div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.user.mobile"/></label>
        <div id="mobile" class="layui-col-xs8 layui-col-md6 edit-div"></div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.user.phone"/></label>
        <div id="phone" class="layui-col-xs8 layui-col-md6 edit-div"></div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.user.email"/></label>
        <div id="email" class="layui-col-xs8 layui-col-md6 edit-div"></div>
    </div>

</div>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/user/currInfo.js?${staticVer}"></script>
</body>
</html>
