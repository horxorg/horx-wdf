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
    <link rel="stylesheet" href="${rc.contextPath}/css/login.css?${staticVer}" media="all">
</head>
<body>
<input type="hidden" id="returnUrl" value="${RequestParameters["returnUrl"]!""}">
<div class="login-container">
    <div class="login-main">
        <div class="login-box login-header">
            <h2><@spring.message "sys.meta.caption"/></h2>
            <p><@spring.message "sys.meta.caption.sub"/></p>
        </div>
        <div class="login-box login-body layui-form">
                <div class="layui-form-item">
                    <label class="login-icon layui-icon layui-icon-username" for="login-username"></label>
                    <input type="text" name="username" id="login-username" lay-verify="required" placeholder="<@spring.message "sys.user.username"/>"
                           class="layui-input">
                </div>
                <div class="layui-form-item">
                    <label class="login-icon layui-icon layui-icon-password" for="login-password"></label>
                    <input type="password" name="password" id="login-password" lay-verify="required" placeholder="<@spring.message "sys.user.password"/>"
                           class="layui-input">
                </div>
                <div id="vcode-row" class="layui-form-item layui-hide">
                    <div class="layui-row">
                        <div class="layui-col-xs7">
                            <label class="login-icon layui-icon layui-icon-vercode" for="login-vercode"></label>
                            <input type="text" name="vcode" id="login-vercode" lay-verify="" placeholder="<@spring.message "sys.vcode"/>" class="layui-input">
                        </div>
                        <div class="layui-col-xs5">
                            <div style="margin-left: 10px;">
                                <img id="vcode-img" src="" style="padding-top:2px;cursor:pointer">
                            </div>
                        </div>
                    </div>
                </div>
                <!--<div class="layui-form-item" style="margin-bottom: 20px;">
                    <input type="checkbox" name="remember" lay-skin="primary" title="记住密码">
                    <a href="forget.html" class="login-jump-change login-link" style="margin-top: 7px;">忘记密码？</a>
                </div>-->
                <div class="layui-form-item login-button">
                    <button type="submit" class="layui-btn layui-btn-fluid" lay-submit lay-filter="login-submit"><@spring.message "sys.login.submit"/>
                    </button>
                </div>
                <div class="login-waiting">
                    <@spring.message "sys.login.waiting"/>
                </div>
        </div>
    </div>

    <div class="layui-trans login-footer">
        <p><@spring.message "sys.meta.copyright"/></p>
    </div>
</div>

<#include "/inc/js.ftl">
<script src="${rc.contextPath}/js/page/sys/login.js?${staticVer}"></script>
</body>
</html>
