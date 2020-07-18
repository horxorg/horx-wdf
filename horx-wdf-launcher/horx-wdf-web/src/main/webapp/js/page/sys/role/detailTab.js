$(document).ready(function(){
    var id = $("#id").val();
    var usable = $("#usable").val();

    var tabPage = $.common.tabPage({
        dataId: id,
        tabConf: {
            "baseInfo": {
                "title": $.msg("common.baseInfo"),
                "url": $.root + "/page/sys/role/detail/{0}?usable=" + usable
            },
            "menu": {
                "title": $.msg("sys.menu.permissions"),
                "url": $.root + "/page/sys/role/detail/menu/{0}?objType=role&usable=" + usable
            },
            "data": {
                "title": $.msg("sys.dataPermission.dataAuth"),
                "url": $.root + "/page/sys/dataAuthority/role/list/detail?roleId={0}&roleType=role"
            },
            "adminRoleMenu": {
                "title": $.msg("sys.role.adminMenu"),
                "url": $.root + "/page/sys/role/detail/menu/{0}?objType=adminRole"
            },
            "adminRoleData": {
                "title": $.msg("sys.role.adminData"),
                "url": $.root + "/page/sys/dataAuthority/role/list/detail?roleId={0}&roleType=adminRole"
            }
        }
    });

    function adminRole(toTab) {
        $.ajax({
            url: $.root + "/api/sys/role/permission/adminRole/" + id,
            success: function (rst) {
                var data = rst.data;
                if (data == null) {
                    return;
                }
                if (data["sys.dataAuthority.role"]) {
                    tabPage.createTab("data");
                }

                if (data["sys.role.edit"]) {
                    tabPage.createTab("adminRoleMenu");

                    if (data["sys.dataAuthority.role"]) {
                        tabPage.createTab("adminRoleData");
                    }
                }
            }
        });
    }

    function init() {
        tabPage.createTab("baseInfo");
        tabPage.createTab("menu");
        adminRole();
        tabPage.changeTab("baseInfo");
    }

    init();
});