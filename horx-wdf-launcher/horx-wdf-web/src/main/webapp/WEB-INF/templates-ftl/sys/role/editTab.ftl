<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
</head>
<body>
<input type="hidden" id="currTab" value="${currTab!''}">
<input type="hidden" id="id" value="${id!''}">
<div class="layui-tab layui-tab-brief" lay-filter="tab">
    <ul class="layui-tab-title">
    </ul>
    <div class="layui-tab-content" style="padding:0px">
    </div>
</div>

<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/role/editTab.js?${staticVer}"></script>
</body>
</html>
