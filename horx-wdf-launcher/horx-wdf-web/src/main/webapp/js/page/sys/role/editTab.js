$(document).ready(function(){
    var currTab = $("#currTab").val();
    var id = $("#id").val();

    var tabPage = $.common.tabPage({
        dataId: id,
        tabConf: {
            "baseInfo": {
                "title": $.msg("common.baseInfo"),
                "url": $.root + "/page/sys/role/modify/{0}",
                "urlForNullId": $.root + "/page/sys/role/create"
            },
            "menu": {
                "title": $.msg("sys.menu.permissions"),
                "url": $.root + "/page/sys/role/menu/{0}?objType=role"
            },
            "data": {
                "title": $.msg("sys.dataPermission.dataAuth"),
                "url": $.root + "/page/sys/dataAuthority/role/list?roleId={0}&roleType=role"
            },
            "adminRoleMenu": {
                "title": $.msg("sys.role.adminMenu"),
                "url": $.root + "/page/sys/role/menu/{0}?objType=adminRole"
            },
            "adminRoleData": {
                "title": $.msg("sys.role.adminData"),
                "url": $.root + "/page/sys/dataAuthority/role/list?roleId={0}&roleType=adminRole"
            }
        }
    });

    function adminRole(toTab) {
        $.ajax({
            url: $.root + "/api/sys/role/permission/adminRole/" + id,
            success: function (rst) {
                var data = rst.data;
                if (data["sys.dataAuthority.role"]) {
                    if (!tabPage.hasTab("data")) {
                        tabPage.createTab("data");
                    }
                } else if (tabPage.hasTab("data")) {
                    tabPage.removeTab("data");
                }

                if (data["sys.role.edit"]) {
                    if (!tabPage.hasTab("adminRoleMenu")) {
                        tabPage.createTab("adminRoleMenu");
                    }

                    if (data["sys.dataAuthority.role"]) {
                        if (!tabPage.hasTab("adminRoleData")) {
                            tabPage.createTab("adminRoleData");
                        }
                    }
                } else {
                    if (tabPage.hasTab("adminRoleMenu")) {
                        tabPage.removeTab("adminRoleMenu");
                    }
                    if (tabPage.hasTab("adminRoleData")) {
                        tabPage.removeTab("adminRoleData");
                    }
                }

                if (toTab) {
                    tabPage.changeTab(toTab);
                }
            }
        });
    }

    function init() {
        if (currTab == "") {
            currTab = "baseInfo";
        }


        tabPage.createTab("baseInfo");
        if (id != "") {
            tabPage.createTab("menu");
            var toTab = (currTab == "baseInfo" || currTab == "menu") ? null : currTab;
            adminRole(toTab);
        }
        if (currTab == "baseInfo" || currTab == "menu") {
            tabPage.changeTab(currTab);
        }

    }

    window.saveSuccess = function (createdId) {
        if (id == "") {
            id = createdId;
            tabPage.setDataId(id);
            tabPage.createTab("menu");
            adminRole();
        }
        tabPage.changeTab("menu");
        parent.layui.table.reload("grid");
    }

    window.saveMenuSuccess = function () {
        adminRole();
    }

    window.cancel = function () {
        var parentLayer = parent.layer;
        var index = parentLayer.getFrameIndex(window.name);
        parentLayer.close(index);
    }

    init();
});