(function($) {
    var listSelect = function(target, customConf) {
        var _this = this;
        _this.target = target;
        _this.parent = target.parent();

        var conf = {
            multi: false,
            value: null,
            load: null,
            data: null,
            idProp: "id",
            textProp: "name",
            search: true,
            searchProps: null,
            itemFilter: null,
            onChange: null
        };
        $.extend(true, conf, customConf);

        _this.conf = conf;
        _this.init();

    };

    listSelect.prototype = {
        init: function () {
            var _this = this;
            var conf = _this.conf;
            var target = _this.target;
            var dropdownEle;
            if (!target.parent().hasClass("layui-select-title")) {
                var html = "<div class=\"dropdown-container layui-unselect layui-form-select\">" +
                    "<div class=\"layui-select-title\">" +
                    "<i class=\"layui-edge\"></i>" +
                    "</div>" +
                    "<dl class=\"dropdown-canvas layui-anim layui-anim-upbit\">" +
                    ((conf.search) ? "<div class='dropdown-search-div'><input type='text' class='dropdown-search-input'><i class=\"layui-icon layui-icon-search dropdown-search-icon\"></i></div>" : "") +
                    "<div class='dropdown-div " + ((conf.search) ? "dropdown-div-search" : "dropdown-div-nosearch") + "'></div>" +
                    "<div class='layui-laydate-footer' style='height:20px'><div class='laydate-footer-btns'>" + ((conf.multi) ? "<span class='dropdown-btns-all'>全选</span>" : "") + "<span class='dropdown-btns-clear'>清空</span><span class='dropdown-btns-close'>关闭</span></div></div>" +
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
            _this.listEle = _this.dlEle.children(".dropdown-div");


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

            if (conf.multi) {
                dropdownEle.find(".dropdown-btns-all").on("click", function () {
                    _this.selectAll();
                });
            }

            if (conf.search) {
                _this.searchInput = dropdownEle.find(".dropdown-search-input");
                _this.searchInput.on("keyup", function (event) {
                    var keyCode = event.which || event.keyCode;
                    if (keyCode == 38 || keyCode == 40) {
                        _this.upDown(keyCode == 40);
                    } else if (keyCode == 27) {
                        _this.close();
                    } else {
                        _this.filter($(this).val());
                    }

                    if (keyCode == 13) {
                        var hot = _this.listEle.children(".dropdown-item-hot");
                        if (hot.is(":visible")) {
                            hot.trigger("click");
                        } else {
                            hot.removeClass("dropdown-item-hot");
                        }
                    }
                });

                dropdownEle.find(".dropdown-search-icon").on("click", function () {
                    _this.filter(_this.searchInput.val());
                });
            } else {
                _this.target.on("keyup", function (event) {
                    var keyCode = event.which || event.keyCode;
                    if (keyCode == 38 || keyCode == 40) {
                        _this.upDown(keyCode == 40);
                    } else if (keyCode == 27) {
                        _this.close();
                    } else if (keyCode == 13) {
                        var hot = _this.listEle.children(".dropdown-item-hot");
                        if (hot.is(":visible")) {
                            hot.trigger("click");
                        } else {
                            hot.removeClass("dropdown-item-hot");
                        }
                    }
                });
            }

            /*_this.target.on("focus", function () {
                _this.toggle();
            });
            _this.target.on("blur", function () {
                _this.close();
            });*/

            $(document).on("click", function (e) {
                var tgt = $(e.target).parents(".dropdown-container");
                if (tgt.length > 0 && tgt[0] == dropdownEle[0]) {
                    if (conf.search && _this.searchInput.length > 0) {
                        _this.searchInput[0].focus();
                    }
                    return false;
                } else {
                    if (dropdownEle.hasClass("layui-form-selected")) {
                        dropdownEle.removeClass("layui-form-selected layui-form-selectup");
                    }
                }

            });

            if (conf.data == null && conf.load != null) {
                var loadConf = {type:"GET"};
                $.extend(true, loadConf, conf.load);
                loadConf.success = function(rst) {
                    var data = null;
                    if (loadConf.dataReader != null) {
                        data = loadConf.dataReader(rst);
                    } else if (rst != null) {
                        data = rst.data;
                    }
                    _this.conf.data = data;
                    _this.initList();
                    if (conf.value != null) {
                        _this.setValue(conf.value, false);
                    }
                }
                $.ajax(loadConf);
            } else {
                _this.initList();
                if (conf.value != null) {
                    _this.setValue(conf.value, false);
                }
            }
        },
        initList: function () {
            var _this = this;
            var conf = _this.conf;
            var data = conf.data;
            var container = _this.listEle;
            container.empty();
            if (data == null) {
                return null;
            }


            for (var i =0; i < data.length; i++) {
                var item = data[i];
                var id = item[conf.idProp];
                var text = $.common.escapeHTML(item[conf.textProp]);
                var html = "<dd data-id='" + id + "'>";
                if (conf.multi) {
                    html += "<div class=\"layui-unselect layui-form-checkbox\" lay-skin=\"primary\"><i class=\"layui-icon layui-icon-ok\"></i></div>";
                }
                html += text + "</dd>";
                container.append($(html));
            }


            if (conf.multi) {
                container.children("dd").on("click", function () {
                    var ckbox = $(this).children(".layui-form-checkbox");
                    ckbox.toggleClass("layui-form-checked");
                    _this.setChecked(true);
                });
            } else {
                container.children("dd").on("click", function () {
                    _this.setSelected($(this), true);
                    _this.close();
                });
            }

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
                if(top + dlHeight > win.height() && top - dropdownEle.outerHeight() >= dlHeight){
                    cls += " layui-form-selectup";
                }
                dropdownEle.addClass(cls);

            }
        },
        close: function () {
            this.dropdownEle.removeClass("layui-form-selected layui-form-selectup");
        },
        filter: function(keyword) {
            if (this.keyword == keyword) {
                return;
            }
            var container = this.listEle;
            container.children(".dropdown-item-hot").removeClass("dropdown-item-hot");
            this.keyword = keyword;

            var items = container.children();
            if (keyword == null ) {
                items.show();
                return;
            }
            keyword = keyword.trim();
            if (keyword == "") {
                items.show();
                return;
            }

            function search(str, key) {
                if (str == null) {
                    return false;
                }
                str = str + "";
                str = str.toUpperCase();
                if (str.indexOf(key) >= 0) {
                    return true;
                }
                return false;
            }

            for (var i = 0; i < items.length; i++) {
                var item = $(items[i]);
                var itemD = this.conf.data[i];
                var hit = false;
                if (this.conf.itemFilter != null) {
                    hit = this.conf.itemFilter(itemD);
                } else {
                    var uKeyword = keyword.toUpperCase();
                    var searchProps = this.conf.searchProps;
                    if (searchProps == null || searchProps.length == 0) {
                        hit = search(itemD[this.conf.idProp], uKeyword);
                        if (!hit) {
                            hit = search(itemD[this.conf.textProp], uKeyword);
                        }
                    } else {
                        if (!hit && searchProps != null) {
                            for (var j = 0; j < searchProps.length; j++) {
                                hit = search(itemD[searchProps[j]], uKeyword);
                                if (hit) {
                                    break;
                                }
                            }
                        }
                    }
                }
                (hit) ? item.show() : item.hide();
            }
        },
        upDown: function(down) {
            var container = this.listEle;
            var vlist = container.children(":visible");
            if (vlist.length == 0) {
                return;
            }

            function setHot(row) {
                row.scrollIntoView(false);
                $(row).addClass("dropdown-item-hot");
            }

            var hot = container.children(".dropdown-item-hot");
            if (hot.length == 0) {
                setHot((down) ? vlist[0] : vlist[vlist.length - 1]);
            } else if (!hot.is(":visible")) {
                hot.removeClass("dropdown-item-hot");
                setHot((down) ? vlist[0] : vlist[vlist.length - 1]);
            } else if (vlist.length > 1) {
                hot.removeClass("dropdown-item-hot");
                var seq = -1;
                for (var i = 0; i < vlist.length; i++) {
                    if (vlist[i] == hot[0]) {
                        seq = i;
                        break;
                    }
                }
                if (down) {
                    setHot((seq < vlist.length - 1) ? vlist[seq + 1] : vlist[0]);
                } else {
                    setHot((seq > 0) ? vlist[seq - 1] : vlist[vlist.length - 1]);
                }

            }
        },
        setSelected: function (obj, userTrigger) {
            if (obj == null && this.selectedObj == null || obj != null && this.selectedObj != null && this.selectedObj[0] == obj[0]) {
                return;
            }
            if (this.selectedObj != null) {
                this.selectedObj.removeClass("layui-this");
            }
            var id = null;
            var itemData = null;
            if (obj != null) {
                id = obj.attr("data-id");
                itemData = this.getDataById(id);
                if (itemData != null) {
                    this.target.val(itemData[this.conf.textProp]);
                    obj.addClass("layui-this");
                }

            } else {
                this.target.val("");
            }

            this.value = id;
            this.selectedObj = obj;

            if (userTrigger) {
                var onChange = this.conf.onChange;
                if (onChange != null) {
                    onChange(id, itemData);
                }
            }

        },
        setChecked: function (userTrigger) {
            var container = this.listEle;
            var checkedList = container.find(".layui-form-checked");
            var text = "";
            var ids = [];
            var itemDatas = [];
            for (var i = 0; i < checkedList.length; i++) {
                var row = $(checkedList[i]).parents("dd");
                var id = row.attr("data-id");
                ids.push(id);
                var item = this.getDataById(id);
                if (item != null) {
                    if (text.length > 0) {
                        text += ",";
                    }
                    text += item[this.conf.textProp];
                }
                itemDatas.push(item);
            }

            var prevValue = this.value;
            this.value = ids;
            this.target.val(text);

            if (userTrigger && !(ids.length == 0 && (prevValue == null || prevValue.length == 0))) {
                var onChange = this.conf.onChange;
                if (onChange != null) {
                    onChange(ids, itemDatas);
                }
            }

        },
        selectAll: function() {
            var container = this.listEle;
            container.find(".layui-form-checkbox").addClass("layui-form-checked");
            this.setChecked(true);
        },
        setValue: function (id, userTrigger) {
            if (this.conf.multi) {
                var container = this.listEle;
                container.find(".layui-form-checked").removeClass("layui-form-checked");
                if (id != null) {
                    if (Object.prototype.toString.call(id) === '[object Array]') {
                        for (var i = 0; i < id.length; i++) {
                            container.children("dd[data-id='" + id[i] + "']").children(".layui-form-checkbox").addClass("layui-form-checked");
                        }
                    } else {
                        container.children("dd[data-id='" + id + "']").children(".layui-form-checkbox").addClass("layui-form-checked");
                    }
                }

                this.setChecked(userTrigger);
            } else {
                var data = this.getDataById(id);
                if (data == null) {
                    this.setSelected(null, userTrigger);
                    return;
                }
                var elem = this.listEle.children("dd[data-id='" + id + "']");
                this.setSelected(elem, userTrigger);
            }
        },
        getValue: function () {
            return this.value;
        },
        getDataById: function (id) {
            if (id == null) {
                return null;
            }
            var conf = this.conf;
            var data = conf.data;
            if (data == null) {
                return null;
            }

            for (var i = 0; i < data.length; i++) {
                var item = data[i];
                if (item[conf.idProp] == id) {
                    return item;
                }
            }

            return null;
        },
        setData: function (data, value) {
            this.keyword = null;
            this.value = null;
            this.selectedObj = null;
            this.target.val("");
            this.conf.data = data;
            this.initList();

            if (value != null) {
                this.setValue(value, false);
            }
        }
    }


    $.fn.listSelect = function (customConf) {
        var $this = $(this);
        if ($this.length == 0) {
            return;
        }
        if ($(this).length > 1) {
            $this = $($this[0]);
        }
        var listSelectObj = $this[0]["listSelectObj"];
        if (listSelectObj == null) {
            var obj = new listSelect($this, customConf);
            listSelectObj = {
                getValue:function (){return obj.getValue()},
                setValue:function (id){obj.setValue(id, false)},
                setData:function (data, value) {obj.setData(data, value)}
            }
            $this[0]["listSelectObj"] = listSelectObj;
        }
        return listSelectObj;
    }

})(jQuery);