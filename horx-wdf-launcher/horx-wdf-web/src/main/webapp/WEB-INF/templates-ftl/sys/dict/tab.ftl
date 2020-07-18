<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
</head>
<body>
<input type="hidden" id="currTab" value="${currTab!''}">
<input type="hidden" id="dictId" value="${dictId!''}">
<@wdf.permissionAllowed code="sys.dict.modify"><input type="hidden" id="canModify" value="1"></@wdf.permissionAllowed>
<div class="layui-tab layui-tab-brief" lay-filter="dict-tab">
    <ul class="layui-tab-title">
    </ul>
    <div class="layui-tab-content" style="padding:0px">
    </div>
</div>

<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/dict/tab.js?${staticVer}"></script>
</body>
</html>
