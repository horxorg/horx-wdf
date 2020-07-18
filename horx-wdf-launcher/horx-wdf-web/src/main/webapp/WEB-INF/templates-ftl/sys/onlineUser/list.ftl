<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
</head>
<body>
<div class="page-content">
    <div class="layui-card">
        <div class="layui-form layui-card-header page-card-header-auto">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label"><@spring.message "sys.user.name"/></label>
                    <div class="layui-input-inline">
                        <input id="name" type="text" name="name" placeholder="" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label"><@spring.message "sys.user.username"/></label>
                    <div class="layui-input-inline">
                        <input id="username" type="text" name="username" placeholder="" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label"><@spring.message "sys.user.org"/></label>
                    <div class="layui-input-inline">
                        <input id="orgIds" type="text" name="orgIds" placeholder="" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline page-query" lay-submit lay-filter="formQuery">
                    <button class="layui-btn " id="btnQuery">
                        <i class="layui-icon layui-icon-search"></i><span><@spring.message "common.query"/></span>
                    </button>
                </div>
            </div>
        </div>

        <div class="page-grid">

            <table id="grid" lay-filter="grid"></table>

        </div>
    </div>
</div>

<script type="text/html" id="toolbar">
    <div class="layui-btn-group" >
        <@wdf.permissionAllowed code="sys.onlineUser.offline"><button class="layui-btn" lay-event="offline"><i class="layui-icon layui-icon-close"></i><span><@spring.message "sys.onlineUser.offline"/></span></button></@wdf.permissionAllowed>
    </div>
</script>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/onlineUser/list.js?${staticVer}"></script>
</body>
</html>
