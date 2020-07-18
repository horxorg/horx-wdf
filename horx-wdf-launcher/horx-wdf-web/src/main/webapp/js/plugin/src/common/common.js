(function($) {
    $.root = $("meta[name='contextPath']").attr("content");

    var common = {
        format : function(msg) {
            if (msg == null || msg == "") {
                return "";
            }

            var args = $.makeArray(arguments).slice(1);
            if(args.length == 0) {
                return msg;
            }

            for(var i=0; i<args.length; i++) {
                msg = msg.replace(new RegExp("\\{"+i+"\\}","g"), args[i]);
            }
            return msg;
        },
        convertByList : function(list, v, dft, vProp, toProp) {
            if (list == null) {
                return dft;
            }
            if (v == null) {
                return dft;
            }

            if (vProp == null) {
                vProp = "id";
            }
            if (toProp == null) {
                toProp = "name";
            }

            function search(v) {
                for (var i=0; i<list.length; i++) {
                    var obj = list[i];
                    if (v == obj[vProp]) {
                        return obj[toProp];
                    }
                }
                return dft;
            }

            v = v + "";
            var result = "";
            if (v.indexOf(",") >= 0) {
                var arr = v.split(",");
                for (var i=0; i<arr.length; i++) {
                    var converted = search(arr[i].trim());
                    if (converted) {
                        if (i > 0 && result != "") {
                            result += ",";
                        }
                        result += converted;
                    }
                }
            } else {
                result = search(v);
            }
            return result;
        },
        searchList : function(list, v, vProp) {
            if (list == null || v == null) return null;
            if (vProp == null) vProp = "id";
            for (var i=0; i<list.length; i++) {
                var obj = list[i];
                if (v == obj[vProp]) {
                    return obj;
                }
            }
            return null;
        },
        convertByTree : function(tree, v, dft, vProp, toProp, childrenProp) {
            if (tree == null) {
                return dft;
            }
            if (v == null) {
                return dft;
            }

            if (vProp == null) {
                vProp = "id";
            }
            if (toProp == null) {
                toProp = "name";
            }
            if (childrenProp == null) {
                childrenProp = "children";
            }

            function search(list, v) {
                for (var i=0; i<list.length; i++) {
                    var obj = list[i];
                    if (v == obj[vProp]) {
                        return obj;
                    }
                    var children = obj[childrenProp];
                    if (children) {
                        obj = search(children, v);
                        if (obj != null) {
                            return obj;
                        }
                    }
                }
                return null;
            }

            v = v + "";
            var result = "";
            if (v.indexOf(",") >= 0) {
                var arr = v.split(",");
                for (var i=0; i<arr.length; i++) {
                    var found = search(tree, arr[i].trim());
                    var converted = (found) ? found[toProp] : null;
                    if (converted) {
                        if (i > 0 && result != "") {
                            result += ",";
                        }
                        result += converted;
                    }
                }
            } else {
                var found = search(tree, v);
                result = (found) ? found[toProp] : null;
                if (!result) {
                    result = dft;
                }
            }
            return result;
        },
        escapeHTML : function(str, isConverNewLine) {
            if (str == null || str == "") return "";
            str = String(str).replace(/&/g, '&amp;').
            replace(/>/g, '&gt;').
            replace(/</g, '&lt;').
            replace(/"/g, '&quot;').
            replace(/ /g, '&nbsp;').
            replace(/\t/g, '&nbsp;&nbsp;&nbsp;&nbsp;');
            if (isConverNewLine) str = str.replace(/\n/g, '<br/>');
            return str;
        },
        unescapeHTML : function(str, isConverNewLine) {
            if (str == null || str == "") return "";
            str = String(str).replace(/&gt;/g, '>').
            replace(/&lt;/g, '<').
            replace(/&quot;/g, '"').
            replace(/&nbsp;/g, ' ').
            replace(/&amp;/g, '&');
            if (isConverNewLine) str = str.replace(/<br\/>/g, '\r\n').replace(/<br>/g, '\r\n').replace(/<BR\/>/g, '\r\n').replace(/<BR>/g, '\r\n');
            return str;
        },
        formEnterToClick : function(form, submitBtn) {
            form.find("input[type='text'],input[type='password']").bind("keydown", function(event) {
                if (event.keyCode == 13) {
                    submitBtn.trigger("click");
                }
            });
        },
        getMainWindow : function() {
            var win = window;
            while (win["tag"] != "main" && win.parent != win) {
                win = win.parent;
            }
            return win;
        },
        getConfigValue: function(configKey) {
            if ($.wdfPlugin != null && $.wdfPlugin.config != null) {
                var value = $.wdfPlugin.config[configKey];
                if (value != null) {
                    return value;
                }
            }
            var config = {
                statusKey: "success",
                successValue: true,
                totalKey: "total",
                currPageName: "currPage",
                pageSizeName: "pageSize",
                sortFieldName: "sortField",
                sortOrderName: "sortOrder"
            };
            return config[configKey];
        },
        onAjaxSuccess: function(rst) {
            if ($.wdfPlugin != null && $.wdfPlugin.onAjaxSuccess != null) {
                return $.wdfPlugin.onAjaxSuccess(rst);
            }
            if (rst == null) {
                return {success: false, msg: $.msg("common.oper.fail")};
            }
            if (rst[$.common.getConfigValue("statusKey")] != $.common.getConfigValue("successValue")) {
                return {success: false, msg: $.common.escapeHTML(rst["msg"], true)};
            }
            return {success: true, msg: $.common.escapeHTML(rst["msg"], true)};
        },
        listPage : function (customConf) {
            var form = layui.form;
            form.render();
            var table = layui.table;
            var layerType = null;
            var layerIndex = null;

            var conf = {
                idField: "id",
                showSaveBtn: true,
                dialogTitleField: null,
                createPage: null,
                modifyPage: null,
                removeApi: null,
                removeMethod: "DELETE",
                gridConf: {
                    elem: "#grid",
                    method:"POST",
                    height:"full",
                    page:true,
                    limits:[10,20,30,50,100,200],
                    toolbar:"#toolbar",
                    defaultToolbar:["filter"],
                    autoSort:false,
                    even:true,
                    checkOnClickRow: true,
                    request:{
                        pageName: $.common.getConfigValue("currPageName"),
                        limitName: $.common.getConfigValue("pageSizeName")
                    },
                    response: {
                        statusName: $.common.getConfigValue("statusKey"),
                        statusCode: $.common.getConfigValue("successValue"),
                        countName: $.common.getConfigValue("totalKey")
                    },
                    done:function () {
                        $("#btnQuery").removeClass("layui-btn-disabled").attr("disabled", false);
                    },
                    onToolbarClick:  null
                }
            }

            $.extend(true, conf, customConf);

            var gridConf = conf.gridConf;
            var gridWhere = gridConf.where;
            var gridSort = gridConf.initSort;

            if (gridSort != null) {
                if (gridWhere == null) {
                    gridWhere = {};
                    gridConf.where = gridWhere;
                }
                gridWhere[$.common.getConfigValue("sortFieldName")] = gridSort.field;
                gridWhere[$.common.getConfigValue("sortOrderName")] = gridSort.type;
            }

            var grid = table.render(conf.gridConf);

            table.on("sort(grid)", function(obj){
                gridWhere[$.common.getConfigValue("sortFieldName")] = obj.field;
                gridWhere[$.common.getConfigValue("sortOrderName")] = obj.type;
                gridSort = obj;
                grid.reload({
                    initSort: gridSort,
                    where: gridWhere
                });
            });

            form.on("submit(formQuery)", function(data){
                $("#btnQuery").addClass("layui-btn-disabled").attr("disabled", true);
                if (gridWhere[$.common.getConfigValue("sortFieldName")] != null) {
                    data.field[$.common.getConfigValue("sortFieldName")] = gridWhere[$.common.getConfigValue("sortFieldName")];
                }
                if (gridWhere[$.common.getConfigValue("sortOrderName")] != null) {
                    data.field[$.common.getConfigValue("sortOrderName")] = gridWhere[$.common.getConfigValue("sortOrderName")];
                }
                var beforeReload = conf.beforeReload;
                if (beforeReload != null) {
                    beforeReload(data.field);
                }
                gridWhere = data.field;
                grid.reload({where:gridWhere, initSort: gridSort, page:{curr:1}});
                return false;
            });

            function create() {
                var createPage = conf["createPage"];
                if (!createPage) {
                    return;
                }
                var win = $(window);
                var dlgConf = {
                    type: 2,
                    title: getDialogTitle("create"),
                    content: createPage,
                    maxmin: true,
                    area: [(win.width() - 100) + "px", (win.height() - 80) + "px"]
                };
                if (conf.showSaveBtn) {
                    dlgConf.btn = [$.msg("common.save"), $.msg("common.cancel")];
                    dlgConf.yes = function(index, layero){
                        var submit = layero.find('iframe').contents().find("#edit-form-submit");
                        submit.trigger("click");
                    }
                }
                layerIndex = layui.layer.open(dlgConf);
                layerType = "create";
            }

            function modify() {
                var modifyPage = conf["modifyPage"];
                if (!modifyPage) {
                    return;
                }

                var checkStatus = table.checkStatus("grid");
                if (checkStatus.data == null || checkStatus.data.length != 1) {
                    layer.msg($.msg("common.oper.needOneRow"));
                    return;
                }

                if (modifyPage != null && modifyPage.indexOf("{0}") >= 0) {
                    modifyPage = $.common.format(modifyPage, checkStatus.data[0][conf.idField]);
                }

                var win = $(window);
                var dlgConf = {
                    type: 2,
                    title: getDialogTitle("modify", checkStatus.data[0]),
                    content: modifyPage,
                    maxmin: true,
                    area: [(win.width() - 100) + "px", (win.height() - 80) + "px"]
                };
                if (conf.showSaveBtn) {
                    dlgConf.btn = [$.msg("common.save"), $.msg("common.cancel")];
                    dlgConf.yes = function(index, layero){
                        var submit = layero.find('iframe').contents().find("#edit-form-submit");
                        submit.trigger("click");
                    }
                }

                layerIndex = layui.layer.open(dlgConf);
                layerType = "modify";
            }

            function remove() {
                var removeApi = conf.removeApi;
                if (!removeApi) {
                    return;
                }

                var checkData = table.checkStatus("grid").data;
                if (checkData == null || checkData.length == 0) {
                    layer.msg($.msg("common.oper.needRow"));
                    return;
                }
                layer.confirm($.msg("common.removePrompt"), {title:$.msg("common.confirmCaption"), icon:3, btn: [$.msg("common.confirm"), $.msg("common.cancel")]}, function (index) {
                    layer.close(index);
                    var loadIndex = layer.load();
                    var arrayParam = true;

                    if (removeApi != null && removeApi.indexOf("{0}") >= 0) {
                        arrayParam = false;
                    }

                    var ids = (arrayParam) ? [] : "";
                    for (var i = 0; i< checkData.length; i++) {
                        if (arrayParam) {
                            ids.push(checkData[i]["id"]);
                        } else {
                            if (i > 0) {
                                ids += ",";
                            }
                            ids += checkData[i]["id"];
                        }
                    }

                    if (!arrayParam) {
                        removeApi = $.common.format(removeApi, ids);
                    }

                    var ajaxParam = {
                        type: conf.removeMethod,
                        url: removeApi,
                        success: function(rst) {
                            var checkRst = $.common.onAjaxSuccess(rst);
                            if (!checkRst.success) {
                                layer.msg(checkRst.msg);
                                return;
                            }

                            table.reload("grid");
                        },
                        error: function() {
                            layer.msg($.msg("common.oper.fail"));
                        },
                        complete: function () {
                            layer.close(loadIndex);
                        }
                    };
                    if (arrayParam) {
                        ajaxParam.data = {id:ids};
                    }
                    $.ajax(ajaxParam);
                });
            }

            function detail(data) {
                var detailPage = conf["detailPage"];
                if (!detailPage) {
                    return;
                }

                if (data == null) {
                    var checkStatus = table.checkStatus("grid");
                    if (checkStatus.data == null || checkStatus.data.length != 1) {
                        layer.msg($.msg("common.oper.needOneRow"));
                        return;
                    }
                    data = checkStatus.data[0];
                }

                if (detailPage != null && detailPage.indexOf("{0}") >= 0) {
                    detailPage = $.common.format(detailPage, data[conf.idField]);
                }
                var win = $(window);
                var dlgConf = {
                    type: 2,
                    title: getDialogTitle("detail", data),
                    content: detailPage,
                    maxmin: true,
                    area: [(win.width() - 100) + "px", (win.height() - 80) + "px"]
                };
                layerIndex = layui.layer.open(dlgConf);
                layerType = "modify";
            }

            table.on("toolbar(grid)", function(obj){
                switch(obj.event){
                    case "create":
                        create();
                        break;
                    case "modify":
                        modify();
                        break;
                    case "remove":
                        remove();
                        break;
                    case "detail":
                        detail();
                        break;
                    default:
                        if (conf.onToolbarClick != null) {
                            conf.onToolbarClick(obj);
                        }
                }
            });

            table.on("tool(grid)", function(obj){
                switch(obj.event){
                    case "detail":
                        detail(obj.data);
                        break;
                    default:
                        if (conf.onGridEvent != null) {
                            conf.onGridEvent(obj);
                        }
                }
            });

            //下拉菜单
            /*$(".downpanel").on("click",function(e){
                //$(".layui-form-select").not($(this).parents(".layui-form-select")).removeClass("layui-form-selected");
                $(this).toggleClass("layui-form-selected");
                e.stopPropagation();
            });*/

            var layerTypeTitle = {"create": $.msg("common.create"), "modify": $.msg("common.modify"), "detail": $.msg("common.detail")}
            function getDialogTitle(layerType, data) {
                var title = layerTypeTitle[layerType];
                if (conf.dialogTitleField && data && data[conf.dialogTitleField]) {
                    title += "【" + data[conf.dialogTitleField] + "】";
                }
                return title;
            }
            function resetDialogTitle(data) {
                if (conf.dialogTitleField && data && data[conf.dialogTitleField]) {
                    layer.title(getDialogTitle(layerType, data), layerIndex);
                }
            }
            window.resetDialogTitle = resetDialogTitle;

            return {table: grid, resetDialogTitle: resetDialogTitle}
        },
        treePage : function (customConf) {
            var form = layui.form;
            form.render();
            var table = layui.table;
            var treetable = layui.treetable;
            var layerType = null;
            var layerIndex = null;

            var conf = {
                idField: "id",
                dialogTitleField: null,
                parentIdRequired: false,
                showSaveBtn: true,
                createPage: null,
                modifyPage: null,
                removeApi: null,
                removeMethod: "DELETE",
                treeGridConf: {
                    treeColIndex: 1,
                    treeSpid: -1,
                    treeIdName: "id",
                    treePidName: "parentId",
                    treeDefaultClose: true,
                    treeLinkage: false,
                    treeDataType: "tree",
                    elem: "#grid",
                    method:"POST",
                    height:"full",
                    page:false,
                    toolbar:"#toolbar",
                    defaultToolbar:["filter"],
                    checkOnClickRow: true,
                    response: {
                        statusName: $.common.getConfigValue("statusKey"),
                        statusCode: $.common.getConfigValue("successValue"),
                    },
                    done:function () {
                        $("#btnQuery").removeClass("layui-btn-disabled").attr("disabled", false);
                        if (modifyId != null) {
                            treetable.openNode($("#grid"), modifyId, true);
                            modifyId = null;
                        } else if (createParentId != null) {
                            treetable.openNode($("#grid"), createParentId);
                            createParentId = null;
                        }
                    },
                    onToolbarClick:  null
                }
            }

            $.extend(true, conf, customConf);

            var treeGridConf = conf.treeGridConf;
            var gridWhere = treeGridConf.where;

            var modifyId = null, createParentId = null;

            function renderTreeTable() {
                var gridConf = {};
                $.extend(true, gridConf, treeGridConf);
                gridConf.where = gridWhere;
                var grid = treetable.render(gridConf);

                table.on("sort(grid)", function(obj){
                    treeGridConf.initSort = obj;
                    renderTreeTable();
                });

                return grid;
            }
            renderTreeTable();

            form.on("submit(formQuery)", function(data){
                $("#btnQuery").addClass("layui-btn-disabled").attr("disabled", true);
                var beforeReload = conf.beforeReload;
                if (beforeReload != null) {
                    beforeReload(data.field);
                }
                gridWhere = data.field;
                renderTreeTable();
                return false;
            });

            function create() {
                var createPage = conf["createPage"];
                if (!createPage) {
                    return;
                }

                var checkStatus = table.checkStatus("grid");
                var parentId = "";
                if (checkStatus.data == null || checkStatus.data.length != 1) {
                    if (conf.parentIdRequired) {
                        layer.msg($.msg("common.oper.needOneRow"));
                        return;
                    }
                } else {
                    parentId = checkStatus.data[0][conf.idField];
                    createParentId = parentId;
                }

                if (createPage != null && createPage.indexOf("{0}") >= 0) {
                    createPage = $.common.format(createPage, parentId);
                }

                var win = $(window);
                var dlgConf = {
                    type: 2,
                    title: getDialogTitle("create"),
                    content: createPage,
                    maxmin: true,
                    area: [(win.width() - 100) + "px", (win.height() - 80) + "px"]
                };
                if (conf.showSaveBtn) {
                    dlgConf.btn = [$.msg("common.save"), $.msg("common.cancel")];
                    dlgConf.yes = function(index, layero){
                        var submit = layero.find('iframe').contents().find("#edit-form-submit");
                        submit.trigger("click");
                    }
                }
                layerIndex = layui.layer.open(dlgConf);
                layerType = "create";
            }

            function modify() {
                var modifyPage = conf["modifyPage"];
                if (!modifyPage) {
                    return;
                }

                var checkStatus = table.checkStatus("grid");
                if (checkStatus.data == null || checkStatus.data.length != 1) {
                    layer.msg($.msg("common.oper.needOneRow"));
                    return;
                }

                modifyId = checkStatus.data[0][conf.idField];
                if (modifyPage != null && modifyPage.indexOf("{0}") >= 0) {
                    modifyPage = $.common.format(modifyPage, modifyId);
                }
                var win = $(window);
                var dlgConf = {
                    type: 2,
                    title: getDialogTitle("modify", checkStatus.data[0]),
                    content: modifyPage,
                    maxmin: true,
                    area: [(win.width() - 100) + "px", (win.height() - 80) + "px"]
                };
                if (conf.showSaveBtn) {
                    dlgConf.btn = [$.msg("common.save"), $.msg("common.cancel")];
                    dlgConf.yes = function(index, layero){
                        var submit = layero.find('iframe').contents().find("#edit-form-submit");
                        submit.trigger("click");
                    }
                }

                layerIndex = layui.layer.open(dlgConf);
                layerType = "modify";
            }

            function remove() {
                var removeApi = conf.removeApi;
                if (!removeApi) {
                    return;
                }

                var checkData = table.checkStatus("grid").data;
                if (checkData == null || checkData.length == 0) {
                    layer.msg($.msg("common.oper.needRow"));
                    return;
                }
                layer.confirm($.msg("common.removePrompt"), {title:$.msg("common.confirmCaption"), icon:3, btn: [$.msg("common.confirm"), $.msg("common.cancel")]}, function (index) {
                    layer.close(index);
                    var loadIndex = layer.load();
                    var arrayParam = true;

                    if (removeApi != null && removeApi.indexOf("{0}") >= 0) {
                        arrayParam = false;
                    }

                    var ids = (arrayParam) ? [] : "";
                    for (var i = 0; i< checkData.length; i++) {
                        if (arrayParam) {
                            ids.push(checkData[i]["id"]);
                        } else {
                            if (i > 0) {
                                ids += ",";
                            }
                            ids += checkData[i]["id"];
                        }
                    }

                    if (!arrayParam) {
                        removeApi = $.common.format(removeApi, ids);
                    }

                    var ajaxParam = {
                        type: conf.removeMethod,
                        url: removeApi,
                        success: function(rst) {
                            var checkRst = $.common.onAjaxSuccess(rst);
                            if (!checkRst.success) {
                                layer.msg(checkRst.msg);
                                return;
                            }

                            renderTreeTable();
                        },
                        error: function() {
                            layer.msg($.msg("common.oper.fail"));
                        },
                        complete: function () {
                            layer.close(loadIndex);
                        }
                    };
                    if (arrayParam) {
                        ajaxParam.data = {id:ids};
                    }
                    $.ajax(ajaxParam);
                });
            }

            function detail(data) {
                var detailPage = conf["detailPage"];
                if (!detailPage) {
                    return;
                }

                if (data == null) {
                    var checkStatus = table.checkStatus("grid");
                    if (checkStatus.data == null || checkStatus.data.length != 1) {
                        layer.msg($.msg("common.oper.needOneRow"));
                        return;
                    }
                    data = checkStatus.data[0];
                }


                if (detailPage != null && detailPage.indexOf("{0}") >= 0) {
                    detailPage = $.common.format(detailPage, data[conf.idField]);
                }
                var win = $(window);
                var dlgConf = {
                    type: 2,
                    title: getDialogTitle("detail", data),
                    content: detailPage,
                    maxmin: true,
                    area: [(win.width() - 100) + "px", (win.height() - 80) + "px"]
                };
                layerIndex = layui.layer.open(dlgConf);
                layerType = "modify";
            }

            table.on("toolbar(grid)", function(obj){
                switch(obj.event){
                    case "create":
                        create();
                        break;
                    case "modify":
                        modify();
                        break;
                    case "remove":
                        remove();
                        break;
                    case "detail":
                        detail();
                        break;
                    default:
                        if (conf.onToolbarClick != null) {
                            conf.onToolbarClick(obj);
                        }
                }
            });

            table.on("tool(grid)", function(obj){
                switch(obj.event){
                    case "detail":
                        detail(obj.data);
                        break;
                    default:
                        if (conf.onGridEvent != null) {
                            conf.onGridEvent(obj);
                        }
                }
            });

            var layerTypeTitle = {"create": $.msg("common.create"), "modify": $.msg("common.modify"), "detail": $.msg("common.detail")}
            function getDialogTitle(layerType, data) {
                var title = layerTypeTitle[layerType];
                if (conf.dialogTitleField && data && data[conf.dialogTitleField]) {
                    title += "【" + data[conf.dialogTitleField] + "】";
                }
                return title;
            }
            function resetDialogTitle(data) {
                if (conf.dialogTitleField && data && data[conf.dialogTitleField]) {
                    layer.title(getDialogTitle(layerType, data), layerIndex);
                }
            }
            window.resetDialogTitle = resetDialogTitle;

            return {renderTreeTable: renderTreeTable, resetDialogTitle: resetDialogTitle}
        },
        editPage : function(customConf) {
            var conf = {
                idField: "id",
                showParentLayer: true
            }
            $.extend(true, conf, customConf);

            var form = layui.form;
            form.render();

            if (conf.verify != null) {
                form.verify(conf.verify);
            }

            var id = null;
            var onLoad = conf.onLoad;
            var loadApi = conf.loadApi;
            var appendId = false;
            if (loadApi != null) {
                id = $("#" + conf.idField).val();
                if (id != "") {
                    var loadApi = conf.loadApi;
                    if (loadApi != null && loadApi.indexOf("{0}") >= 0) {
                        loadApi = $.common.format(loadApi, id);
                    }
                    $.ajax({
                        type : "GET",
                        url : loadApi,
                        success : function(rst) {
                            var checkRst = $.common.onAjaxSuccess(rst);
                            if (!checkRst.success) {
                                layer.msg(checkRst.msg);
                                return;
                            }

                            form.val("edit-form", rst["data"]);

                            if (onLoad != null) {
                                onLoad(rst["data"]);
                            }
                        },
                        error : function() {
                            if (onLoad != null) {
                                onLoad();
                            }
                            layer.msg($.msg("common.load.fail"));
                        }
                    });
                } else {
                    if (onLoad != null) {
                        onLoad();
                    }
                }
            } else {
                var data = conf.data;
                if (data != null) {
                    id = data[conf.idField];
                    appendId = true;
                }
                if (onLoad != null) {
                    onLoad(data);
                }
            }


            var url = null;
            var ajaxMethod = null;
            if (!id) {
                url = conf["createApi"];
                ajaxMethod = (conf["createMethod"] == null) ? "POST" : conf["createMethod"];
            } else {
                url = conf["modifyApi"];
                if (url != null && url.indexOf("{0}") >= 0) {
                    url = $.common.format(url, id);
                }
                ajaxMethod = (conf["modifyMethod"] == null) ? "PUT" : conf["modifyMethod"];
            }

            form.on("submit(edit-form-submit)", function(data){
                var field = data.field;
                $("#form").form().appendData(field);
                if (appendId) {
                    field[conf.idField] = id;
                }
                var beforeSubmit = conf["beforeSubmit"] ;
                if (beforeSubmit != null) {
                    var isSubmit = beforeSubmit(field);
                    if (isSubmit == false) {
                        return;
                    }
                }

                var parentLayer = null;
                var index = null;
                var loadIndex = null;
                if (conf.showParentLayer) {
                    parentLayer = parent.layer;
                    index = parentLayer.getFrameIndex(window.name);
                    loadIndex = parentLayer.load();
                } else {
                    loadIndex = layer.load();
                }

                var ajaxResult = null;
                $.ajax({
                    type: ajaxMethod,
                    url: url,
                    data: field,
                    success: function(rst) {
                        ajaxResult = $.common.onAjaxSuccess(rst);
                        if (!ajaxResult.success) {
                            layer.msg(ajaxResult.msg);
                            return;
                        }

                        var onSaveSuccess = conf["onSaveSuccess"];
                        if (onSaveSuccess == null) {
                            parent.layui.table.reload("grid");
                        } else {
                            onSaveSuccess(rst["data"], field);
                        }

                        if (conf.showParentLayer) {
                            parentLayer.close(index);
                            parentLayer.msg($.msg("common.oper.ok"));
                        } else {
                            layer.msg($.msg("common.oper.ok"));
                        }

                    },
                    error: function(jqXHR, textStatus, errorThrown) {
                        /*alert(jqXHR.responseText);
                        alert(jqXHR.status);
                        alert(jqXHR.readyState);
                        alert(jqXHR.statusText);
                        alert(textStatus);
                        alert(errorThrown);*/
                        layer.msg($.msg("common.oper.fail"));
                    },
                    complete: function () {
                        if (conf.showParentLayer) {
                            parentLayer.close(loadIndex);
                        } else {
                            layer.close(loadIndex);
                        }
                    }
                });
            });
        },
        tabPage : function(customConf) {
            var conf = {
                dataId: null,
                tabField: "tab",
                tabConf: null
            }
            $.extend(true, conf, customConf);

            var dataId = conf.dataId;
            var tabContents = {};
            var tabIds = [];
            currTab = null;

            var element = layui.element;

            function createTab(tabId) {
                if (tabContents[tabId] != null) {
                    return;
                }
                element.tabAdd(conf.tabField, {
                    title: conf.tabConf[tabId].title,
                    content: "<iframe id=\"" + tabId + "\" class=\"layui-col-xs12\" src=\"\" frameborder=\"0\"></iframe>",
                    id: tabId
                });

                tabContents[tabId] = $("#" + tabId);
                tabIds.push(tabId);
            }

            function showTab(tabId) {
                if (tabContents[tabId] == null) {
                    alert(tabId);
                    return;
                }
                var src = tabContents[tabId].attr("src");
                if (src != "") {
                    return;
                }

                var src = null;
                if (!dataId) {
                    src = conf.tabConf[tabId].urlForNullId;
                }
                if (!src) {
                    src = conf.tabConf[tabId].url;
                }

                if (src && dataId && src.indexOf("{0}") >= 0) {
                    src = $.common.format(src, dataId);
                }

                tabContents[tabId].attr("src", src);
                currTab = tabId;
                tabContents[tabId].height($(window).height() - $(".layui-tab-title").height() - 13);
            }

            $(window).resize(function () {
                tabContents[currTab].height($(window).height() - $(".layui-tab-title").height() - 13);
            });

            element.on("tab(" + conf.tabField + ")", function(data) {
                showTab(tabIds[data.index]);
            });

            function changeTab(tabId) {
                currTab = tabId;
                element.tabChange(conf.tabField, currTab);
                showTab(currTab);
            }

            function hasTab(tabId) {
                return (tabContents[tabId] != null);
            }

            function removeTab(tabId) {
                element.tabDelete(conf.tabField, tabId);
                delete tabContents[tabId];
            }

            function  setDataId(id) {
                dataId = id;
            }

            return {createTab: createTab, changeTab: changeTab, hasTab:hasTab, removeTab:removeTab, setDataId: setDataId};
        }
    }

    var bundleMsg = null;
    function msg(key) {
        var result = null;
        if (bundleMsg != null) {
            result = bundleMsg[key];
        }
        if (result == null) {
            var mainWin = common.getMainWindow();
            var msg = mainWin["msg"];
            if (msg != null) {
                bundleMsg = msg;
                result = msg[key];
            }
        }

        if (result == null) {
            $.ajax({
                type : "GET",
                url : $.root + "/api/public/msg",
                async:false,
                success : function(rst) {
                    var checkRst = $.common.onAjaxSuccess(rst);
                    if (!checkRst.success) {
                        return;
                    }
                    bundleMsg = rst.data;
                    if (bundleMsg != null) {
                        result = bundleMsg[key];
                    }
                }
            });
        }

        if (arguments.length <= 1) {
            return result;
        }
        arguments[0] = result;

        return common.format.apply(null, arguments);
    }


    $.msg = msg;

    $.common = common;

    function ajaxProxy() {
        var ajaxFunc = $.ajax;
        $.ajax = function (config) {
            if ($.wdfPlugin != null && $.wdfPlugin.beforeAjaxRequest != null) {
                $.wdfPlugin.beforeAjaxRequest(config);
            }
            ajaxFunc(config);
        }

        function getConfig(args) {
            var config = {url: args[0]};
            if (args.length >= 2) {
                if ($.isFunction(args[1])) {
                    config.success = args[1];
                } else {
                    config.data = args[1];
                }
            }

            if (args.length >= 3) {
                config.success = args[2];
            }
            return config;
        }

        var getFunc = $.get;
        $.get = function () {
            if ($.wdfPlugin != null && $.wdfPlugin.beforeAjaxRequest != null) {
                if (arguments == null || arguments.length == 0) {
                    return;
                }

                var config = getConfig();
                $.wdfPlugin.beforeAjaxRequest(config);
            }
            getFunc.apply(null, arguments);
        }

        var postFunc = $.post;
        $.post = function () {
            if ($.wdfPlugin != null && $.wdfPlugin.beforeAjaxRequest != null) {
                if (arguments == null || arguments.length == 0) {
                    return;
                }
                var config = getConfig();

                $.wdfPlugin.beforeAjaxRequest(config);
            }
            postFunc.apply(null, arguments);
        }
    }
    ajaxProxy();
    ///////////////////////////////////////////

    var form = function(target) {
        var _this = this;
        _this["target"] = $(target);
    }

    form.prototype.val = function(data) {
        var _this = this;
        alert($this.html())
    }

    form.prototype.appendData = function(data) {
        var target = this.target;
        var checkboxes = target.find("input[type='checkbox']");
        checkboxes.each(function() {
            var checkbox = $(this);
            var name = checkbox.attr("name");
            if (name == null || name == "") {
                return;
            }
            var value = checkbox.attr("switch-value");
            if (value != null && value.indexOf("|") > 0) {
                var arr = value.split("|");
                data[name] = (checkbox.is(":checked")) ? arr[0] : arr[1];
            }

        });

    }

    $.fn.form = function () {
        var $this = $(this);
        if ($this.length == 0) {
            return;
        }
        if ($(this).length > 1) {
            $this = $($this[0]);
        }
        var formObj = $this[0]["formObj"];
        if (formObj == null) {
            var obj = new form($this);
            formObj = {
                val:function (data){obj.val(data)},
                appendData:function (data){obj.appendData(data)}
            }
            $this[0]["formObj"] = formObj;
        }
        return formObj;
    }

    ///////////////////////////////////////////
})(jQuery);