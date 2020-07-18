<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
    <script type="text/javascript">
        var dict=${dict};
    </script>
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
                    <label class="layui-form-label"><@spring.message "sys.user.mobile"/></label>
                    <div class="layui-input-inline">
                        <input id="mobile" type="text" name="mobile" placeholder="" autocomplete="off"
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
                <div class="layui-inline">
                    <label class="layui-form-label"><@spring.message "sys.user.status"/></label>
                    <div class="layui-input-inline">
                        <input id="status" type="text" name="status" placeholder="" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label"><@spring.message "sys.user.pwdErrLocked"/></label>
                    <div class="layui-input-inline">
                        <input id="pwdErrLocked" type="text" name="pwdErrLocked" placeholder="" autocomplete="off"
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
        <@wdf.permissionAllowed code="sys.user.create"><button class="layui-btn" lay-event="create"><i class="layui-icon layui-icon-add-circle"></i><span><@spring.message "common.create"/></span></button></@wdf.permissionAllowed>
        <@wdf.permissionAllowed code="sys.user.modify"><button class="layui-btn" lay-event="modify"><i class="layui-icon layui-icon-edit"></i><span><@spring.message "common.modify"/></span></button></@wdf.permissionAllowed>
        <@wdf.permissionAllowed code="sys.user.remove"><button class="layui-btn" lay-event="remove"><i class="layui-icon layui-icon-delete"></i><span><@spring.message "common.remove"/></span></button></@wdf.permissionAllowed>
        <@wdf.permissionAllowed code="sys.dataAuthority.user"><button class="layui-btn" lay-event="userAuth"><i class="layui-icon layui-icon-vercode"></i><span><@spring.message "sys.dataPermission.dataAuth"/></span></button></@wdf.permissionAllowed>
        <@wdf.permissionAllowed code="sys.user.modifyPwd"><button class="layui-btn" lay-event="modifyPwd"><i class="layui-icon layui-icon-auz"></i><span><@spring.message "sys.user.password.modify"/></span></button></@wdf.permissionAllowed>
        <@wdf.permissionAllowed code="sys.user.unlock"><button class="layui-btn" lay-event="unlock"><i class="layui-icon layui-icon-slider"></i><span><@spring.message "sys.user.unlock"/></span></button></@wdf.permissionAllowed>
        <!--<button type="button" class="layui-btn layui-unselect layui-form-select downpanel">
            <div class="layui-select-title">
                编辑&#12288;&#12288;
                <i class="layui-edge"></i>
            </div>
            <dl class="layui-anim layui-anim-upbit">
                <dd>
                    <i class=""></i>
                    修改信息1
                </dd>
                <dd>
                    <i class=""></i>
                    修改信息2
                </dd>
            </dl>
        </button>-->

    </div>
</script>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/user/list.js?${staticVer}"></script>
</body>
</html>
