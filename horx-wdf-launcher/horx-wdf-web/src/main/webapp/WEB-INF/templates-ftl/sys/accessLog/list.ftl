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
                    <label class="layui-form-label"><@spring.message "common.keyWord"/></label>
                    <div class="layui-input-inline">
                        <input id="searchKey" type="text" name="searchKey" placeholder="" autocomplete="off"
                               class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label"><@spring.message "common.startTime"/></label>
                    <div class="layui-input-inline">
                        <input id="startTime" type="text" name="startTime" placeholder="" autocomplete="off"
                               class="layui-input" value="${startTime}">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label"><@spring.message "common.endTime"/></label>
                    <div class="layui-input-inline">
                        <input id="endTime" type="text" name="endTime" placeholder="" autocomplete="off"
                               class="layui-input" value="${endTime}">
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
        <button class="layui-btn" lay-event="detail"><i class="layui-icon layui-icon-align-center"></i><span><@spring.message "common.detail"/></span></button>
    </div>
</script>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/accessLog/list.js?${staticVer}"></script>
</body>
</html>
