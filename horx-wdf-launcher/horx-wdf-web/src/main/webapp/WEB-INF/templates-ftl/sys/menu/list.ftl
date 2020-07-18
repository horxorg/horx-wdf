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
                    <label class="layui-form-label"><@spring.message "sys.menu.name"/></label>
                    <div class="layui-input-inline">
                        <input id="treeQueryName" type="text" name="treeQueryName" placeholder="" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div id="" class="layui-inline">
                    <label class="layui-form-label"><@spring.message "sys.menu.code"/></label>
                    <div class="layui-input-inline">
                        <input id="treeQueryCode" type="text" name="treeQueryCode" placeholder="" autocomplete="off"
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
    <@wdf.permissionAllowed code="sys.menu.create"><button class="layui-btn" lay-event="create"><i class="layui-icon layui-icon-add-circle"></i><span><@spring.message "sys.menu.createSub"/></span></button></@wdf.permissionAllowed>
    <@wdf.permissionAllowed code="sys.menu.modify"><button class="layui-btn" lay-event="modify"><i class="layui-icon layui-icon-edit"></i><span><@spring.message "common.modify"/></span></button></@wdf.permissionAllowed>
    <@wdf.permissionAllowed code="sys.menu.remove"><button class="layui-btn" lay-event="remove"><i class="layui-icon layui-icon-delete"></i><span><@spring.message "common.remove"/></span></button></@wdf.permissionAllowed>

    </div>
</script>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/menu/list.js?${staticVer}"></script>
</body>
</html>
