$(document).ready(function(){
    window["tag"] = "main";

    $("#side-menu-div").niceScroll();
    $("#side-menu-div").on("click", function () {
        $("#side-menu-div").getNiceScroll().resize();
    });

    $.ajax({
        type : "GET",
        url : $.root + "/public/api/msg",
        success : function(rst) {
            var checkRst = $.common.onAjaxSuccess(rst);
            if (!checkRst.success) {
                return;
            }
            window["msg"] = rst.data;
        }
    });

    var layer = layui.layer;

    var shrink = false;
    var mainDiv = $("#mainDiv");
    var flexibleBtn = $("#flexibleBtn");

    function debounce(func, wait, immediate) {
        var timeout;
        return function() {
            var context = this, args = arguments;
            var later = function() {
                timeout = null;
                if (!immediate) func.apply(context, args);
            };
            var callNow = immediate && !timeout;
            clearTimeout(timeout);
            timeout = setTimeout(later, wait);
            if (callNow) func.apply(context, args);
        };
    }

    function winChange() {
        debounce(function() {
            var width = $(window).width();
            mainDiv.removeClass("side-spread-sm");
            if (width < 992) {
                mainDiv.removeClass("side-shrink");
                flexibleBtn.removeClass("layui-icon-shrink-right");
                flexibleBtn.addClass("layui-icon-spread-left");
            } else {
                if (shrink) {
                    mainDiv.addClass("side-shrink");
                    flexibleBtn.removeClass("layui-icon-shrink-right");
                    flexibleBtn.addClass("layui-icon-spread-left");

                } else {
                    mainDiv.removeClass("side-shrink");
                    flexibleBtn.removeClass("layui-icon-spread-left");
                    flexibleBtn.addClass("layui-icon-shrink-right");

                }
            }
        }, 100)();
    }

    winChange();
    $(window).on("resize", function () {
        winChange();
    });

    flexibleBtn.on("click", function () {
        var width = $(window).width();
        if (width < 992) {
            if (flexibleBtn.hasClass("layui-icon-shrink-right")) {
                mainDiv.removeClass("side-spread-sm");
                flexibleBtn.removeClass("layui-icon-shrink-right");
                flexibleBtn.addClass("layui-icon-spread-left");
            } else {
                mainDiv.addClass("side-spread-sm");
                flexibleBtn.removeClass("layui-icon-spread-left");
                flexibleBtn.addClass("layui-icon-shrink-right");
            }
        } else {
            if (flexibleBtn.hasClass("layui-icon-shrink-right")) {
                flexibleBtn.removeClass("layui-icon-shrink-right");
                flexibleBtn.addClass("layui-icon-spread-left");
                mainDiv.addClass("side-shrink");
                shrink = true;
            } else {
                flexibleBtn.removeClass("layui-icon-spread-left");
                flexibleBtn.addClass("layui-icon-shrink-right");
                mainDiv.removeClass("side-shrink");
                shrink = false;
            }
        }

    });



    $("#fullScreenBtn").on("click", function () {
        var btn = $(this);
        if (btn.hasClass("layui-icon-screen-full")) {
            var ele =  document.documentElement, fs = ele.requestFullScreen||ele.webkitRequestFullScreen||ele.mozRequestFullScreen||ele.msRequestFullscreen;
            "undefined" != typeof fs && fs && fs.call(ele);
            btn.removeClass("layui-icon-screen-full");
            btn.addClass("layui-icon-screen-restore");
        } else {
            document.exitFullscreen?document.exitFullscreen():document.mozCancelFullScreen?document.mozCancelFullScreen():document.webkitCancelFullScreen?document.webkitCancelFullScreen():document.msExitFullscreen&&document.msExitFullscreen();
            btn.removeClass("layui-icon-screen-restore");
            btn.addClass("layui-icon-screen-full");
        }
    });

    function appendMenu(data) {
        var tree = $(".layui-nav-tree");
        for (var i = 0; i < data.length; i++) {
            var item = data[i];
            var icon = item["iconContent"];
            if (icon == null || icon == "") {
                icon = "layui-icon-app";
            }
            var dom = $('<li class="layui-nav-item' + (item.open ? " layui-nav-itemed" : "") + '"><a href="javascript:;" lay-direction="2"><i class="layui-icon ' + icon + '"></i><cite>'+item.name+'</cite></a></li>');
            tree.append(dom);
            genSubMenu(dom, item.children, 1);
        }
    }
    
    function genSubMenu(pdom, data, lvl) {
        if (data == null || data.length == 0) {
            return;
        }

        var dl = $('<dl class="layui-nav-child"></dl>');
        pdom.append(dl);
        for (var i = 0; i < data.length; i++) {
            var item = data[i];
            var html = "";
            var icon = item["iconContent"];
            var url = item["url"];

            var html = "<dd" + (item.open ? " class='layui-nav-itemed'" : "") + (item.current ? " class='layui-this'" : "") + "><a href='";
            if (url != null && url != "") {
                url = $.root + "/" + url + "?menuId=" + item["id"];
                html += url + "' target='content' "
            } else {
                html += "javascript:;' ";
            }
            html += "lay-direction='2' ";
            if (icon != null && icon != "") {
                html += "class='submenu-item'><i class='submenu-icon " + icon + "'></i>";
            } else {
                html += "class='submenu-no-icon'>";
            }
            html += "<cite>" + item["name"] + "</cite></a></dd>";

            /*if (item["iconContent"] != null) {
                html = '<dd><a href="javascript:;" lay-direction="2" class="submenu-item"><i class="submenu-icon ' + item["iconContent"] + '" ></i><cite>'+item.name+'</cite></a></dd>';
            } else {
                html = '<dd><a href="javascript:;" lay-direction="2"><cite>'+item.name+'</cite></a></dd>';
            }*/
            var dd = $(html);
            dl.append(dd);
            genSubMenu(dd, item.children, lvl + 1);
        }
    }

    $.ajax({
        type : "GET",
        url : $.root + "/api/sys/menu/queryForAuthorizedMenu",
        async:false,
        success : function(rst) {
            var checkRst = $.common.onAjaxSuccess(rst);
            if (!checkRst.success || !rst["data"]) {
                return;
            }

            function findFirstUrl(node) {
                if (node.url) {
                    node.current = true;
                    return node.url;
                }
                var children = node["children"];
                if (children == null || children.length == 0) {
                    return null;
                }
                for (var i = 0; i < children.length; i++) {
                    var curl = findFirstUrl(children[i]);
                    if (curl) {
                        node.open = true;
                        return curl;
                    }
                }
                return null;
            }

            var url = findFirstUrl(rst["data"][0]);
            appendMenu(rst["data"][0]["children"]);
            if (url) {
                $("#content").attr("src", $.root + "/" + url);
            }
            $(".layui-nav-bar").attr("class", "zorder:999;");
            layui.element.init();
        }
    });


    $(".layui-side-menu").find("a").on("click", function () {
        var width = $(window).width();
        if (width >= 992 && shrink) {
            flexibleBtn.trigger("click");
        }
    });

    $("#userInfo").on("click", function () {
        layui.layer.open({
            type: 2,
            title: $.msg("sys.user.info"),
            content: $.root + "/page/sys/user/currInfo",
            maxmin: true,
            area: ["700px", "400px"],
            btn: [$.msg("common.close")]
        });
    });

    $("#modifyPwd").on("click", function () {
        layui.layer.open({
            type: 2,
            title: $.msg("sys.user.info"),
            content: $.root + "/page/sys/user/modifyCurrPwd",
            maxmin: true,
            area: ["700px", "400px"],
            btn: [$.msg("common.save"), $.msg("common.cancel")],
            yes: function(index, layero){
                var submit = layero.find('iframe').contents().find("#edit-form-submit");
                submit.trigger("click");
            }
        });
    });
});