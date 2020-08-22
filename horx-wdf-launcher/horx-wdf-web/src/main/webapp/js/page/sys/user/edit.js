$(document).ready(function(){
    var id = $("#id").val();
    if (id == "") {
        $("#username").val("");
        $("#password").val("");
    }
    $.common.editPage({
        verify:{
            repassword:function (value) {
                var pwd = $("input[name='password']").val();
                if (pwd != value) {
                    return $.msg("sys.user.pwdne");
                }
                return null;
            },
            minChar:function (value) {
                if (value == null || value.length < 6) {
                    return $.msg("common.minChar", $.msg("sys.user.password"), "6");
                }
                return null;
            }
        },
        loadApi:$.root + "/api/sys/user/{0}",
        createApi:$.root + "/api/sys/user",
        modifyApi:$.root + "/api/sys/user/{0}",
        onLoad: onLoad,
        beforeSubmit: beforeSubmit
    });

    function onLoad(data) {
        $("#status").listSelect({
            multi:false,
            value:(data == null) ? "01" : data.status,
            data: dict.userStatus,
            idProp: "code",
            search: false
        });

        $("#org").treeSelect({
            multi:false,
            value:(data == null) ? null : data.orgId,
            load: {
                type: "POST",
                url: $.root + "/api/sys/org/queryForTree?accessPermissionCode=sys.user.query"
            }
        });

        $("#role").listSelect({
            multi:true,
            value:(data == null) ? null : data.roleIds,
            load: {
                type: "POST",
                url: $.root + "/api/sys/role/usable/paginationQuery",
                data: {"currPage":1, "pageSize":-1, "sortField":"name"}
            },
            search:true
        });
    }
    
    function beforeSubmit(formData) {
        var orgId = $("#org").treeSelect().getValue();
        formData.orgId = orgId;
        var roleIds = $("#role").listSelect().getValue();
        formData.roleIds = roleIds;
        var status = $("#status").listSelect().getValue();
        formData.status = status;
    }
});