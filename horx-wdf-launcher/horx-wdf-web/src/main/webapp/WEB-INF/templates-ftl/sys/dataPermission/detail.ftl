<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
    <script type="text/javascript">
        var dict=${dict!'{}'};
    </script>
</head>
<body>
<input type="hidden" id="id" name="id" value="${id!''}">
<div id="form" class="layui-form layui-container edit-container" lay-filter="edit-form">
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dataPermission.name"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="name-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dataPermission.code"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="code-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dataPermission.objType"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="objType-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div id="objIdDiv" class="layui-row edit-row layui-hide">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dataPermission.obj"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="objId-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.enabled"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="enabled-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.displaySeq"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="displaySeq-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.remarks"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="remarks-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>


</div>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/dataPermission/detail.js?${staticVer}"></script>
</body>
</html>
