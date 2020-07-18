$(document).ready(function(){
    var id = $("#id").val();

    $.common.editPage({
        loadApi:$.root + "/api/sys/dict/{0}",
        createApi:$.root + "/api/sys/dict",
        modifyApi:$.root + "/api/sys/dict/{0}",
        onLoad: onLoad,
        beforeSubmit: beforeSubmit,
        onSaveSuccess:onSaveSuccess,
        showParentLayer:false
    });

    function onLoad(data) {
        $("#bizType").listSelect({
            multi:false,
            value:(data == null) ? null : data.bizType,
            data: dict.bizType,
            idProp: "code",
            search: true,
            searchProps: ["code", "name", "fullCode", "simpleCode"]
        });

        if (data != null) {
            $("#tree-data-div").text((data.treeData) ? $.msg("common.yes") : $.msg("common.no"));
        }
    }
    
    function beforeSubmit(formData) {
        var bizType = $("#bizType").listSelect().getValue();
        formData.bizType = bizType;
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