$(document).ready(function(){
    var currTab = $("#currTab").val();
    var dictId = $("#dictId").val();

    var tabPage = $.common.tabPage({
        dataId: dictId,
        tabField: "dict-tab",
        tabConf: {
            "baseInfo": {
                "title": $.msg("sys.dict.baseInfo"),
                "url": ($("#canModify").val() == "1") ? $.root + "/page/sys/dict/modify/{0}" : $.root + "/page/sys/dict/detail/{0}",
                "urlForNullId": $.root + "/page/sys/dict/create"
            },
            "item": {
                "title": $.msg("sys.dict.item"),
                "url": $.root + "/page/sys/dict/{0}/item/list"
            }
        }
    });

    window.saveSuccess = function (createdId) {
        if (dictId == "") {
            dictId = createdId;
            tabPage.setDataId(dictId);
            tabPage.createTab("item");
        }
        tabPage.changeTab("item");
        parent.layui.table.reload("grid");
    }

    window.cancel = function () {
        var parentLayer = parent.layer;
        var index = parentLayer.getFrameIndex(window.name);
        parentLayer.close(index);
    }

    tabPage.createTab("baseInfo");
    if (dictId != "") {
        tabPage.createTab("item");
    }
    if (currTab == "") {
        currTab = "baseInfo";
    }
    tabPage.changeTab(currTab);
});