<!DOCTYPE html>
<html>
<head>
<#include "../../inc/head.ftl">
    <script type="text/javascript">
        var dict=${dict};
    </script>
</head>
<body>
<input type="hidden" id="id" name="id" value="${id}">
<div id="form" class="layui-form layui-container edit-container" lay-filter="edit-form">
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dict.name"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="name-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><@spring.message "sys.dict.code"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="code-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><span class="edit-required">*</span><spring:message code="sys.dict.bizType"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="biz-type-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "sys.dict.treeData"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="tree-data-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-row edit-row">
        <label class="layui-col-xs4 layui-col-md3 edit-label"><@spring.message "common.remarks"/></label>
        <div class="layui-col-xs8 layui-col-md6 edit-input">
            <div id="remarks-div" class="layui-col-xs8 layui-col-md6 edit-div"></div>
        </div>
    </div>
    <div class="layui-form-item layui-hide">
        <input type="button" lay-submit lay-filter="edit-form-submit" id="edit-form-submit">
    </div>

</div>
<#include "../../inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/dict/detail.js?${staticVer}"></script>
</body>
</html>
