$(document).ready(function(){

    $(".layui-icon").on("click", function () {

        parent["selIcon"]($(this).attr("class").substring(11));
    });
});