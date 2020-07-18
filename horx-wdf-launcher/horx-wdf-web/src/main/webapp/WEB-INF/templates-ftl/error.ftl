<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title><@spring.message "sys.meta.caption"/></title>
    <meta name="renderer" content="webkit">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="user-scalable=yes">
    <meta name="contextPath" content="${rc.contextPath}">
    <link rel="stylesheet" href="${rc.contextPath}/js/layui/css/layui.css?${staticVer}" media="all">
    <link rel="stylesheet" href="${rc.contextPath}/css/main.css?${staticVer}" media="all">
    <#include "inc/js.ftl">
    <script type="text/javascript">
        $(document).ready(function(){
            $("#relogin").bind("click", function() {
                var w = window;
                while (w.parent != w) {
                    w = w.parent;
                }
                w.location = "${rc.contextPath}/login";
            });
        });
    </script>
</head>
<body>
<div class="layui-fluid">
    <div style="margin-top:30px;text-align:center">
        <i class="layui-icon" style="display:inline-block;font-size:100px;color:#787878">&#xe664;</i>
        <div class="layui-text" style="width:500px;margin:30px auto;padding-top:20px;border-top:5px solid #009688;font-size:20px;line-height:30px">
            ${(result.msg)!'出错了，请稍后重试'}
            <#if (result.code=='A0230')><br><a href="javascript:void(0)" id="relogin"><@spring.message "sys.login"/></a></#if>
        </div>

    </div>
</div>
</body>
</html>
