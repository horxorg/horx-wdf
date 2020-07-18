layui.define(['layer', 'table'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var table = layui.table;

    var treetable = {
        // 渲染树形表格
        render: function (param) {
            // 检查参数
            if (!treetable.checkParam(param)) {
                return;
            }

            // 获取数据
            if (param.data) {
                treetable.init(param, param.data);
            } else {
                var where = param.where;
                if (where == null) {
                    where = {};
                }
                if (param.initSort && param.initSort.field) {
                    where[$.common.getConfigValue("sortFieldName")] = param.initSort.field;
                    where[$.common.getConfigValue("sortOrderName")] = param.initSort.type;
                }
                $.ajax({
                    type: param.method,
                    url: param.url,
                    data: where,
                    success: function (rst) {
                        var checkRst = $.common.onAjaxSuccess(rst);
                        if (!checkRst.success) {
                            layer.msg(checkRst.msg);
                            return;
                        }
                        treetable.init(param, rst.data);
                    },
                    error: function() {
                        layer.msg($.msg("common.load.fail"));
                    }
                });
            }
        },
        // 渲染表格
        init: function (param, data) {
            var mData = [];
            var doneCallback = param.done;
            var tNodes = data;

            function fillData(data) {
                if (!data.id) {
                    if (!param.treeIdName) {
                        layer.msg('参数treeIdName不能为空', {icon: 5});
                        return;
                    }
                    data.id = data[param.treeIdName];
                }
                if (!data.pid) {
                    if (!param.treePidName) {
                        layer.msg('参数treePidName不能为空', {icon: 5});
                        return;
                    }
                    data.pid = data[param.treePidName];
                }
            }

            if (param.treeDataType == "tree") {
                function processNodes(nodes, level) {
                    if (nodes == null || nodes.length == 0) {
                        return;
                    }

                    for (var i = 0; i < nodes.length; i++) {
                        var node = nodes[i];
                        fillData(node);
                        mData.push(node);

                        var children = node.children;
                        if (children != null && children.length > 0) {
                            node.isParent = true;
                        }
                        processNodes(children, level + 1);
                    }
                }

                if (tNodes.length > 0) {
                    processNodes(tNodes, 1);
                }
            } else {
                // 补上id和pid字段
                for (var i = 0; i < tNodes.length; i++) {
                    var tt = tNodes[i];
                    fillData(tt);
                }

                // 对数据进行排序
                var sort = function (s_pid, data) {
                    for (var i = 0; i < data.length; i++) {
                        if (data[i].pid == s_pid) {
                            var len = mData.length;
                            if (len > 0 && mData[len - 1].id == s_pid) {
                                mData[len - 1].isParent = true;
                            }
                            mData.push(data[i]);
                            sort(data[i].id, data);
                        }
                    }
                };
                sort(param.treeSpid, tNodes);
            }

            // 重写参数
            param.url = undefined;
            param.data = mData;
            /*param.page = {
                count: param.data.length,
                limit: param.data.length
            };*/
            param.page = false; // 20190915修改，不分页
            param.limit = 1000000;
            param.autoSort = false;

            var templet = param.cols[0][param.treeColIndex].templet;
            param.cols[0][param.treeColIndex].templet = function (d) {
                var text = (templet == null) ? d[param.cols[0][param.treeColIndex].field] : templet(d);
                var mId = d.id;
                var mPid = d.pid;
                var isDir = d.isParent;
                var emptyNum = treetable.getEmptyNum(mPid, mData);
                var iconHtml = '';
                for (var i = 0; i < emptyNum; i++) {
                    iconHtml += '<span class="treeTable-empty"></span>';
                }
                if (isDir) {
                    iconHtml += '<i class="layui-icon layui-icon-triangle-d"></i> <i class="layui-icon layui-icon-layer"></i>';
                } else {
                    iconHtml += '<i class="layui-icon layui-icon-file"></i>';
                }
                iconHtml += '&nbsp;&nbsp;';
                var ttype = isDir ? 'dir' : 'file';
                var vg = '<span class="treeTable-icon open" lay-tid="' + mId + '" lay-tpid="' + mPid + '" lay-ttype="' + ttype + '">';
                return vg + iconHtml + text + '</span>'
            };

            param.done = function (res, curr, count) {
                $(param.elem).next().addClass('treeTable');
                $('.treeTable .layui-table-page').css('display', 'none');
                $(param.elem).next().attr('treeLinkage', param.treeLinkage);
                // 绑定事件换成对body绑定
                /*$('.treeTable .treeTable-icon').click(function () {
                    treetable.toggleRows($(this), param.treeLinkage);
                });*/
                if (param.treeDefaultClose) {
                    treetable.foldAll(param.elem);
                }
                if (doneCallback) {
                    doneCallback(res, curr, count);
                }
            };

            // 渲染表格
            return table.render(param);
        },
        // 计算缩进的数量
        getEmptyNum: function (pid, data) {
            var num = 0;
            if (!pid) {
                return num;
            }
            var tPid;
            for (var i = 0; i < data.length; i++) {
                if (pid == data[i].id) {
                    num += 1;
                    tPid = data[i].pid;
                    break;
                }
            }
            return num + treetable.getEmptyNum(tPid, data);
        },
        // 展开/折叠行
        toggleRows: function ($dom, linkage) {
            var type = $dom.attr('lay-ttype');
            if ('file' == type) {
                return;
            }
            var mId = $dom.attr('lay-tid');
            var isOpen = $dom.hasClass('open');
            if (isOpen) {
                $dom.removeClass('open');
            } else {
                $dom.addClass('open');
            }
            $dom.closest('tbody').find('tr').each(function () {
                var $ti = $(this).find('.treeTable-icon');
                var pid = $ti.attr('lay-tpid');
                var ttype = $ti.attr('lay-ttype');
                var tOpen = $ti.hasClass('open');
                if (mId == pid) {
                    if (isOpen) {
                        $(this).hide();
                        if ('dir' == ttype && tOpen == isOpen) {
                            $ti.trigger('click');
                        }
                    } else {
                        $(this).show();
                        if (linkage && 'dir' == ttype && tOpen == isOpen) {
                            $ti.trigger('click');
                        }
                    }
                }
            });
        },
        // 检查参数
        checkParam: function (param) {
            if (!param.treeSpid && param.treeSpid != 0) {
                layer.msg('参数treeSpid不能为空', {icon: 5});
                return false;
            }

            if (!param.treeColIndex && param.treeColIndex != 0) {
                layer.msg('参数treeColIndex不能为空', {icon: 5});
                return false;
            }
            return true;
        },
        // 展开所有
        expandAll: function (dom) {
            $(dom).next('.treeTable').find('.layui-table-body tbody tr').each(function () {
                var $ti = $(this).find('.treeTable-icon');
                var ttype = $ti.attr('lay-ttype');
                var tOpen = $ti.hasClass('open');
                if ('dir' == ttype && !tOpen) {
                    $ti.trigger('click');
                }
            });
        },
        // 折叠所有
        foldAll: function (dom) {
            $(dom).next('.treeTable').find('.layui-table-body tbody tr').each(function () {
                var $ti = $(this).find('.treeTable-icon');
                var ttype = $ti.attr('lay-ttype');
                var tOpen = $ti.hasClass('open');
                if ('dir' == ttype && tOpen) {
                    $ti.trigger('click');
                }
            });
        },
        openNode: function ($dom, id, onlyParents) {
            var table = $dom.next('.treeTable');
            var nodes = [];
            function findNode(dataId) {
                var node = table.find(".treeTable-icon[lay-tid='" + dataId + "']");
                if (node.length > 0) {
                    nodes.push(node);

                    var pId = node.attr("lay-tpid");
                    findNode(pId);
                }
            }

            findNode(id);
            var start = (onlyParents) ? 1 : 0;
            if (nodes.length > start) {
                for (var i = nodes.length - 1; i >= start; i--) {
                    nodes[i].trigger('click');
                }
            }
        }
    };

    //layui.link(layui.cache.base + 'treetable-lay/treetable.css');

    // 给图标列绑定事件
    $('body').on('click', '.treeTable .treeTable-icon', function () {
        var treeLinkage = $(this).parents('.treeTable').attr('treeLinkage');
        if ('true' == treeLinkage) {
            treetable.toggleRows($(this), true);
        } else {
            treetable.toggleRows($(this), false);
        }

    });

    exports('treetable', treetable);
});
