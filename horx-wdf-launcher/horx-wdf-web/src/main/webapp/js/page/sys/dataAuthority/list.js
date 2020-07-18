 $(document).ready(function(){
    var detail = $("#detail").val();

    var objType = $("#objType").val();
    var objId = $("#objId").val();
    var roleType = $("#roleType").val();
    var $dataauth = $("#dataauth");

    function getUrl(objType, objId, defId) {
        var url = $.root + "/page/sys/dataAuthority/" + objType + "/";
        if (detail == "1") {
            url += "detail/";
        }
        url += defId;
        url += "?objId=" + objId;
        if (objType == "role") {
            url += "&roleType=" + roleType;
        }
        return url;
    }

    if (defList.length > 0) {
        var $defList = $("#def-list");
        var html = "<dl class='list-container'>";
        for (var i = 0; i < defList.length; i++) {
            var def = defList[i];
            var currClass = "";
            if (i ==  0) {
                currClass = "layui-this";
            }
            html += "<dd class='list-item " + currClass + "' index='" + i + "'><a href='javascript:void(0)'>" + def.name + "</a></dd>";
        }
        html += "</dl>";
        var container = $(html);
        $defList.append(container);


        var url = getUrl(objType, objId, defList[0].id );
        $dataauth.attr("src", url);

        container.children("dd").on("click", function () {
            var $this = $(this);
            if ($this.hasClass("layui-this")) {
                return;
            }

            container.children(".layui-this").removeClass("layui-this");
            $this.addClass("layui-this");
            var data = defList[$this.attr("index")];

            var url = getUrl(objType, objId, data.id);
            $dataauth.attr("src", url);
        });
    }

});