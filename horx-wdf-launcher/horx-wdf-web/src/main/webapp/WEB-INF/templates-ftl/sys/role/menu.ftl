<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
</head>
<body class="bottom-layer-body">
<input type="hidden" id="id" name="id" value="${id}">
<input type="hidden" id="usable" name="id" value="${usable!''}">
<input type="hidden" id="objType" name="objType" value="${objType}">
<input type="hidden" id="detail" name="detail" value="${detail!''}">
<div id="menuPermissions" style="padding:10px"></div>

<div class="bottom-div">
    <div class="layui-layer-btn">
        <a id="save-btn" class="layui-layer-btn0"><@spring.message "common.save"/></a>
        <a id="cancel-btn" class="layui-layer-btn1"><@spring.message "common.cancel"/></a>
    </div>
</div>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/role/menu.js?${staticVer}"></script>
</body>
</html>
