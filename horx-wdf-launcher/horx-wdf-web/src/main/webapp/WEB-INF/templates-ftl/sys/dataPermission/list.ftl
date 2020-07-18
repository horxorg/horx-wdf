<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
    <script type="text/javascript">
        var dict=${dict!'{}'};
    </script>
</head>
<body>
<div class="page-content">
    <div class="layui-card">
        <div class="layui-form layui-card-header page-card-header-auto">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label"><@spring.message "sys.dataPermission.name"/></label>
                    <div class="layui-input-inline">
                        <input id="name" type="text" name="name" placeholder="" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label"><@spring.message "sys.dataPermission.code"/></label>
                    <div class="layui-input-inline">
                        <input id="code" type="text" name="code" placeholder="" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label"><@spring.message "sys.dataPermission.objType"/></label>
                    <div class="layui-input-inline">
                        <input id="objType" type="text" name="objType" placeholder="" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label"><@spring.message "common.enabled"/></label>
                    <div class="layui-input-inline">
                        <input id="enabled" type="text" name="enabled" placeholder="" autocomplete="off"
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
        <@wdf.permissionAllowed code="sys.dataPermission.create"><button class="layui-btn" lay-event="create"><i class="layui-icon layui-icon-add-circle"></i><span><@spring.message "common.create"/></span></button></@wdf.permissionAllowed>
        <@wdf.permissionAllowed code="sys.dataPermission.modify"><button class="layui-btn" lay-event="modify"><i class="layui-icon layui-icon-edit"></i><span><@spring.message "common.modify"/></span></button></@wdf.permissionAllowed>
        <@wdf.permissionAllowed code="sys.dataPermission.remove"><button class="layui-btn" lay-event="remove"><i class="layui-icon layui-icon-delete"></i><span><@spring.message "common.remove"/></span></button></@wdf.permissionAllowed>
        <@wdf.permissionAllowed code="sys.dataAuthority.default"><button class="layui-btn" lay-event="defaultAuth"><i class="layui-icon layui-icon-vercode"></i><span><@spring.message "sys.dataPermission.defaultAuth"/></span></button></@wdf.permissionAllowed>
    </div>
</script>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/dataPermission/list.js?${staticVer}"></script>
</body>
</html>
