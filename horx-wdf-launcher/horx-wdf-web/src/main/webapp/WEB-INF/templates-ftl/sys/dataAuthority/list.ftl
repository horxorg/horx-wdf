<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
    <script type="text/javascript">
        var defList=${defList!'[]'};
    </script>
</head>
<body>
<input type="hidden" id="objType" name="objType" value="${objType!''}">
<input type="hidden" id="objId" name="objId" value="${objId!''}">
<input type="hidden" id="roleType" name="roleType" value="${roleType!''}">
<input type="hidden" id="detail" name="detail" value="${detail!''}">
<div class="layui-layout">
    <div class="layui-side" id="def-list" style="background-color: #D0D0D0">
    </div>
    <div class="layui-body">
        <iframe name="dataauth" id="dataauth" src="" frameborder="0" class="iframe"></iframe>
    </div>
</div>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/dataAuthority/list.js?${staticVer}"></script>
</body>
</html>
