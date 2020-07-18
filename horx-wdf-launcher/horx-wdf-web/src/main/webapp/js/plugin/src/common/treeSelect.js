(function($) {
    var treeSelect = function(target, customConf) {
        var _this = this;
        _this.target = target;
        _this.parent = target.parent();

        var conf = {
            multi: false,
            value: null,
            load: null,
            width: null,
            idProp: "id",
            textProp: "name",
            treeConf: {
                showCheckbox: false,
                click: function(obj){
                    return false;
                },
                iconClick:function () {
                    return false;
                }
            },
            convertItem: null,
            onChange: null
        }
        $.extend(true, conf, customConf);
        if (conf.multi) {
            conf.treeConf.showCheckbox = true;
        }

        _this.conf = conf;

        _this.init();
    }

    treeSelect.prototype = {
        init: function () {
            var _this = this;
            var target = _this.target;
            var dropdownEle;
            if (!target.parent().hasClass("layui-select-title")) {
                var html = "<div class=\"dropdown-container layui-unselect layui-form-select\">" +
                    "<div class=\"layui-select-title\">" +
                    "<i class=\"layui-edge\"></i>" +
                    "</div>" +
                    "<dl class=\"layui-anim layui-anim-upbit\">" +
                    "<div class='dropdown-div dropdown-div-nosearch'></div>" +
                    "<div class='layui-laydate-footer' style='height:20px'><div class='laydate-footer-btns'><span class='dropdown-btns-clear'>清空</span><span class='dropdown-btns-close'>关闭</span></div></div>" +
                    "</dl></div>";
                dropdownEle = $(html);
                _this.parent.append(dropdownEle);
                target.remove();
                dropdownEle.find(".layui-edge").before(target);
            } else {
                dropdownEle = target.parent().parent();
            }

            _this.dropdownEle = dropdownEle;
            _this.dlEle = dropdownEle.children("dl");
            if (_this.conf.width) {
                _this.dlEle.width(_this.conf.width);
            }

            target.prop("readonly", "readonly");
            target.bind("click", function () {
                _this.toggle();
            });

            dropdownEle.find(".dropdown-btns-clear").on("click", function () {
                _this.setValue(null, true);
            });

            dropdownEle.find(".dropdown-btns-close").on("click", function () {
                _this.close();
            });


            var doc = $(document);
            doc.on("keyup", function (event) {
                var keyCode = event.which || event.keyCode;
                if (keyCode == 27) {
                    _this.close();
                }
            });

            doc.on("click", function (e) {
                var tgt = $(e.target).parents(".dropdown-container");
                if (tgt.length > 0 && tgt[0] == dropdownEle[0]) {
                    return false;
                } else {
                    if (dropdownEle.hasClass("layui-form-selected")) {
                        dropdownEle.removeClass("layui-form-selected layui-form-selectup");
                    }
                }

            });

            var treeConf = _this.conf.treeConf;
            treeConf.elem = dropdownEle.find(".dropdown-div")[0];

            if (_this.conf.multi) {
                treeConf.oncheck = function (obj) {
                    _this.setChecked(_this.notCheckCallback == null || !_this.notCheckCallback);
                    _this.notCheckCallback = null;
                }
            } else {
                treeConf.click = function (obj) {
                    _this.setSelected(obj, true);
                    _this.close();
                    return false;
                }
            }

            if (treeConf.data == null && _this.conf.load != null) {
                var loadConf = {type:"GET"};
                $.extend(true, loadConf, _this.conf.load);
                loadConf.success = function(rst) {
                    var data = null;
                    if (loadConf.dataReader != null) {
                        data = loadConf.dataReader(rst);
                    } else if (rst != null) {
                        data = _this.convertData(rst.data);
                    }

                    treeConf.data = data;
                    _this.initTree(treeConf);
                    if (_this.conf.value != null) {
                        _this.setValue(_this.conf.value, false);
                    }
                }
                $.ajax(loadConf);
            } else {
                treeConf.data =  _this.convertData(_this.conf.data);
                _this.initTree(treeConf);
                if (_this.conf.value != null) {
                    _this.setValue(_this.conf.value, false);
                }
            }
        },
        convertData: function (data) {
            if (data == null) {
                return null;
            }
            var _conf = this.conf;
            var convertItem = _conf.convertItem;
            for (var i =0; i < data.length; i++) {
                var item = data[i];

                if (convertItem != null) {
                    item = convertItem(item);
                } else {
                    if (item.title == null && "title" != _conf.textProp) {
                        item.title = item[_conf.textProp];
                    }
                    item.title = $.common.escapeHTML(item.title);
                    if ("id" != _conf.idProp) {
                        item.id = item[_conf.idProp];
                    }
                }

                if (item.children != null) {
                    item.children = this.convertData( item.children);
                }
            }

            return data;
        },
        initTree: function(treeConf) {
            this.dropdownEle.find(".dropdown-div").empty();
            var tree = layui.tree;
            this.treeObj = tree.render(treeConf);

        },
        toggle: function () {
            if (this.target.hasClass("layui-disabled")) {
                return;
            }

            var dropdownEle = this.dropdownEle;
            if (dropdownEle.hasClass("layui-form-selected")) {
                dropdownEle.removeClass("layui-form-selected layui-form-selectup");
            } else {
                var win = $(window);
                var top = dropdownEle.offset().top + dropdownEle.outerHeight() + 5 - win.scrollTop()
                    ,dlHeight = this.dlEle.outerHeight();

                var cls = "layui-form-selected";
                if(top + dlHeight > win.height() && top >= dlHeight){
                    cls += " layui-form-selectup";
                }
                dropdownEle.addClass(cls);
            }
        },
        close: function () {
            this.dropdownEle.removeClass("layui-form-selected layui-form-selectup");
        },
        setSelected: function (obj, userTrigger) {
            if (obj == null && this.selectedObj == null || obj != null && this.selectedObj != null && this.selectedObj.data.id == obj.data.id) {
                return;
            }
            if (this.selectedObj != null) {
                this.selectedObj.elem.find(".layui-tree-txt:first").removeClass("dropdown-this");
            }

            var prevValue = this.value;
            if (obj != null) {
                this.target.val(obj.data.title);
                obj.elem.find(".layui-tree-txt:first").addClass("dropdown-this");
                this.value = obj.data.id;
            } else {
                this.target.val("");
                this.value = null;
            }


            this.selectedObj = obj;

            if (userTrigger) {
                var onChange = this.conf.onChange;
                if (onChange != null) {
                    onChange(this.value);
                }
            }
        },
        setChecked: function (userTrigger) {
            /*var list = this.dropdownEle.find(".layui-form-checked");
            for (var i = 0; i < list.length; i++) {
                var item = $(list[i]);
                var pitem = item.parent(".layui-tree-set");
                var id = pitem.attr("data-id");
            }*/
            var ids = [];
            var list = this.treeObj.getChecked();
            var str = "";

            function text(list) {
                if (list.length == 0) {
                    return;
                }

                for (var i = 0; i < list.length; i++) {
                    var item = list[i];
                    if (!item.unchecked) {
                        if (str.length > 0) {
                            str += ",";
                        }
                        str += item.title;
                        ids.push(item.id);
                    }

                    var children = item.children;
                    if (children != null && children.length > 0) {
                        text(children);
                    }
                }

            }

            text(list);
            this.target.val(str);
            var prevValue = this.value;
            this.value = ids;

            if (userTrigger && !(ids.length == 0 && (prevValue == null || prevValue.length == 0))) {
                var onChange = this.conf.onChange;
                if (onChange != null) {
                    onChange(ids);
                }
            }
        },
        setValue: function (id, userTrigger) {
            if (this.conf.multi) {
                if (id == null) {
                    this.dropdownEle.find(".layui-form-checked").removeClass("layui-form-checked");
                } else {
                    this.notCheckCallback = true;
                    this.treeObj.setChecked(id);
                }

                this.setChecked(userTrigger);
            } else {
                var data = this.getDataById(id);
                if (data == null) {
                    this.setSelected(null, userTrigger);
                    return;
                }
                var elem = this.dropdownEle.find(".layui-tree-set[data-id=" + id + "]");
                this.setSelected({data:data, elem:elem}, userTrigger);
                var parents = elem.parents(".layui-tree-set");
                for (var i = 0; i < parents.length; i++) {
                    var item = $(parents[i]);
                    item.find(".layui-tree-iconClick:first").trigger("click");
                }
            }
        },
        getValue: function () {
            return this.value;
        },
        getDataById: function (id) {
            if (id == null) {
                return null;
            }
            var data = this.conf.treeConf.data;
            if (data == null) {
                return null;
            }

            function get(list, id) {
                for (var i = 0; i < list.length; i++) {
                    var item = list[i];
                    if (item.id == id) {
                        return item;
                    }
                    var children = item.children;
                    if (children != null && children.length > 0) {
                        var result = get(children, id);
                        if (result != null) {
                            return result;
                        }
                    }
                }
                return null;
            }

            return get(data, id);
        },
        setData: function (data, value) {
            this.treeObj = null;
            this.value = null;
            this.selectedObj = null;
            this.target.val("");
            var treeConf = this.conf.treeConf;
            treeConf.data = data;
            this.initTree(treeConf);

            if (value != null) {
                this.setValue(value, false);
            }
        }
    }


    $.fn.treeSelect = function (customConf) {
        var $this = $(this);
        if ($this.length == 0) {
            return;
        }
        if ($(this).length > 1) {
            $this = $($this[0]);
        }
        var treeSelectObj = $this[0]["treeSelectObj"];
        if (treeSelectObj == null) {
            var obj = new treeSelect($this, customConf);
            treeSelectObj = {
                getValue:function (){return obj.getValue()},
                setValue:function (id){obj.setValue(id, false)},
                setData:function (data, value) {obj.setData(data, value)}
            }
            $this[0]["treeSelectObj"] = treeSelectObj;
        }
        return treeSelectObj;
    }

})(jQuery);