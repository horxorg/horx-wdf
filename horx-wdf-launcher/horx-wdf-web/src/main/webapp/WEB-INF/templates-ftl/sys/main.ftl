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
</head>
<body class="layui-layout-body">
<div id="mainDiv">
    <div class="layui-layout layout-main">
        <div class="layui-header">
            <ul class="layui-nav layout-header-left">
                <li class="layui-nav-item" lay-unselect>
                    <a href="javascript:;" title="<@spring.message "sys.main.shrink"/>">
                        <i class="layui-icon layui-icon-shrink-right" id="flexibleBtn"></i>
                    </a>
                </li>
            </ul>
            <div class="layout-caption"><@spring.message "sys.meta.caption.main"/></div>
            <ul class="layui-nav layout-header-right" >
                <li class="layui-nav-item layui-hide-xs" lay-unselect>
                    <a href="javascript:;">
                        <i class="layui-icon layui-icon-screen-full" id="fullScreenBtn"></i>
                    </a>
                </li>
                <li class="layui-nav-item" lay-unselect style="margin-right: 30px">
                    <a href="javascript:;">
                        <cite>${name}</cite>
                    </a>
                    <dl class="layui-nav-child">
                        <dd><a id="userInfo" style="cursor:pointer"><@spring.message "sys.main.userInfo"/></a></dd>
                        <dd><a id="modifyPwd" style="cursor:pointer"><@spring.message "sys.user.password.modify"/></a></dd>
                        <hr>
                        <dd style="text-align: center;cursor:pointer"><a href="${rc.contextPath}/logout"><@spring.message "sys.main.logout"/></a></dd>
                    </dl>
                </li>
            </ul>
        </div>

        <!-- 侧边菜单 -->
        <div class="layui-side layui-side-menu">
            <div class="layui-side-scroll">
                <div id="side-menu-div" style="width:220px;height:100%"><!--增加overflow:auto显示滚动条-->
                    <ul class="layui-nav layui-nav-tree" lay-shrink="all" style="width:100%"></ul>
                </div>
            </div>
        </div>

        <!-- 主体内容 -->
        <div class="layui-body">
            <div class="layout-main-body layui-show">
                <iframe name="content" id="content" src="" frameborder="0" class="main-iframe"></iframe>
            </div>
        </div>

        <div class="layui-footer">
            <@spring.message "sys.meta.copyright"/>&nbsp;&nbsp;&nbsp;&nbsp;<@spring.message "sys.meta.version"/>
        </div>
    </div>
</div>

<script src="${rc.contextPath}/js/jquery-3.3.1.min.js?${staticVer}"></script>
<script src="${rc.contextPath}/js/jquery.nicescroll.min.js?${staticVer}"></script>
<script src="${rc.contextPath}/js/plugin/src/common/common.js?${staticVer}"></script>
<script src="${rc.contextPath}/js/layui/layui.all.js?${staticVer}"></script>
<script src="${rc.contextPath}/js/page/sys/main.js?${staticVer}"></script>
</body>
</html>
