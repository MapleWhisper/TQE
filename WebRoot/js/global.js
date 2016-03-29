/**
 * Created by Maple on 2015/12/2.
 */
/**
 * 关闭当前浏览器TAB页
 */
function closeWindow(){
    window.opener = null;
    window.open('', '_self');
    window.close();
}

function showErrorMessage(className){
    $(".error-message").map(function(){
       if($(this).text().trim().length>=1){
           $(this).parents(".error-message-container").show();
           //$(this).remove();
       }
    });
    if(className){
        className = className.trim();
        $("."+className).map(function(){
            if($(this).text().trim().length>=1){
                $(this).parents(".error-message-container").show();
                $(this).show();
            }
        });
    }
}

/**
 * 记住season的选择
 */
function autoSeasonSelect(){
        var seasonVal = $("#condition-season").val();
        if(seasonVal){
            $("select#season").find("option[value='"+seasonVal+"']").attr("selected","true");
        }
}

function log(_object){
    console.log(_object);
}

/**
 * select 可以自动根据 select中的 key 选中对应value的option
 */
function autoSelect(){
    $(".auto-select").each(function(){
        var $select = $(this);
        var key = $select.attr("key");
        log(key);
        $select.find("option[value='"+key+"']").attr("selected","true");
    })
}


function showGlobalNotification(msg){

    if(msg){
        var globalNotification = $("#global-notification");
        globalNotification.find("#global-notification-text").html(msg);
        globalNotification.fadeIn("fast");
        //globalNotification.fadeOut(3000);
    }

}