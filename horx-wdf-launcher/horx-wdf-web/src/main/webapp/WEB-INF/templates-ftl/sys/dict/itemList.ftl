<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
</head>
<body>
<input type="hidden" id="dictId" name="dictId" value="${dictId}">
<div class="page-content">
    <div class="layui-card">
        <div class="layui-form layui-card-header page-card-header-auto">
            <div class="layui-form-item">
                <div id="" class="layui-inline">
                    <label class="layui-form-label"><@spring.message "sys.dict.item.name"/></label>
                    <div class="layui-input-inline">
                        <input id="name" type="text" name="name" placeholder="" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label"><@spring.message "sys.dict.item.code"/></label>
                    <div class="layui-input-inline">
                        <input id="code" type="text" name="code" placeholder="" autocomplete="off"
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
        <@wdf.permissionAllowed code="sys.dict.item"><button class="layui-btn" lay-event="create"><i class="layui-icon layui-icon-add-circle"></i><span><@spring.message "common.create"/></span></button></@wdf.permissionAllowed>
        <@wdf.permissionAllowed code="sys.dict.item"><button class="layui-btn" lay-event="modify"><i class="layui-icon layui-icon-edit"></i><span><@spring.message "common.modify"/></span></button></@wdf.permissionAllowed>
        <@wdf.permissionAllowed code="sys.dict.item"><button class="layui-btn" lay-event="remove"><i class="layui-icon layui-icon-delete"></i><span><@spring.message "common.remove"/></span></button></@wdf.permissionAllowed>
    </div>
</script>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/dict/itemList.js?${staticVer}"></script>
</body>
</html>
