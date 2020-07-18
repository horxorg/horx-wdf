$(document).ready(function(){
    var form = layui.form;

    $.common.editPage({
        loadApi:$.root + "/api/sys/role/{0}",
        createApi:$.root + "/api/sys/role",
        modifyApi:$.root + "/api/sys/role/{0}",
        onLoad: init,
        beforeSubmit:beforeSubmit,
        onSaveSuccess:onSaveSuccess,
        showParentLayer:false
    });

    function init(roleData) {
        $("#orgId").treeSelect({
            multi:false,
            value:(roleData == null) ? null : roleData.orgId,
            load: {
                type: "POST",
                data: {"accessPermissionCode": "sys.role.query"},
                url: $.root + "/api/sys/org/queryForTree"
            }
        });
    }

    function beforeSubmit(data) {
        var orgId = $("#orgId").treeSelect().getValue();
        data.orgId = orgId;
    }

    $("#save-btn").on("click", function () {
        $("#edit-form-submit").trigger("click");
    });

    $("#cancel-btn").on("click", function () {
        parent.cancel();
    });

    function onSaveSuccess(createdId) {
        id = createdId;
        parent.saveSuccess(createdId);
    }
});