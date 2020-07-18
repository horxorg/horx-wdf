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
                    <label class="layui-form-label"><@spring.message "sys.role.code"/></label>
                    <div class="layui-input-inline">
                        <input id="code" type="text" name="code" placeholder="" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div id="" class="layui-inline">
                    <label class="layui-form-label"><@spring.message "sys.role.name"/></label>
                    <div class="layui-input-inline">
                        <input id="name" type="text" name="name" placeholder="" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label"><@spring.message "sys.role.org"/></label>
                    <div class="layui-input-inline">
                        <input id="orgIds" type="text" name="orgIds" placeholder="" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label"><@spring.message "sys.role.subUsable"/></label>
                    <div class="layui-input-inline">
                        <input id="subUsable" type="text" name="subUsable" placeholder="" autocomplete="off"
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
        <@wdf.permissionAllowed code="sys.role.usable"><button class="layui-btn" lay-event="detail"><i class="layui-icon layui-icon-align-center"></i><span><@spring.message "common.detail"/></span></button></@wdf.permissionAllowed>
    </div>
</script>

<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/role/usableList.js?${staticVer}"></script>
</body>
</html>
