
function initEditEvalTableBtnEvent(){

    $("#addItem").click(function(){
        var $item = $("#item");
        var cnt = $item.find(".item").size();
        var contextName = "itemList["+cnt+"].context";
        var item = $item.find(".item:last").clone();
        $(item).children("input").attr("name",contextName);
        $item.find(".item:last").after(item);
        return false;
    });
    $("#addQuestion").click(function(){
        var $question = $("#question");
        var cnt = $question.find(".question").size();
        var contextName = "questionList["+cnt+"].context";
        var item = $question.find(".question:last").clone();
        $(item).children("input").attr("name",contextName);
        $question.find(".question:last").after(item);
        return false;
    });
    $("#addTableItem").click(function(){
        var $tableItem = $("#tableItem");
        var cnt = $tableItem.find(".tableItem").size();
        var contextName = "tableItemList["+cnt+"].context";
        var levelName = "tableItemList["+cnt+"].level";
        var item = $tableItem.find(".tableItem:last").clone();
        $(item).children(".context").children("input").attr("name",contextName);
        $(item).children(".level").children("input").attr("name",levelName);
        $tableItem.find(".tableItem:last").after(item);
        return false;
    });
    $("#removeItem").click(function() {
        $("#item .item:last").remove();
        return false;
    });

    $("#removeQuestion").click(function() {
        $("#question .question:last").remove();
        return false;
    });

    $("#removeTableItem").click(function() {
        $("#tableItem .tableItem:last").remove();
        return false;
    });
}