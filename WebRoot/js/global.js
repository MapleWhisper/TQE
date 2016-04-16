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

/**
 * 如果错误提示窗里的消息不为空 就显示该错误消息
 * @param className
 */
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
        if(!key){
            key = $select.attr("value");
        }
        if(key){
            key = key.trim();
            $select.find("option[value='"+key+"']").attr("selected","true");
        }
    })
}

/**
 * 显示全局的消息通知
 * @param msg   要显示的消息
 */
function showGlobalNotification(msg){

    if(msg){
        var globalNotification = $("#global-notification");
        globalNotification.find("#global-notification-text").html(msg);
        globalNotification.fadeIn("fast");
        //globalNotification.fadeOut(3000);
    }

}

function batchStatusRender(){
    var batchStatus = $(".batch-status");

    $.each(batchStatus,function(){
        var $this = $(this);
        var text = $this.html().trim();
        if(text){
            if(text=='未开始'){
                $this.addClass("bg-primary");
            }
            if(text=='进行中'){
                $this.addClass("bg-danger");
            }
            if(text=='已结束'){
                $this.addClass("bg-info");
            }
        }
    })
}

/**
 * 自动添加 图标 如果元素上有 icon标签 自动对标签内容前添加 <span class='glyphicon glyphicon-'></span>
 */
function autoAddIcon(){
    $("a").each(function(){
        addIcon($(this));
    });
    $("button").each(function(){
        addIcon($(this));
    });
    function addIcon(element){
        var icon = element.attr("icon");
        log(icon);
        if(icon){
            log(icon);
            var iconSpan = "<span class='glyphicon glyphicon-@1'></span>&nbsp;&nbsp;";
            iconSpan = iconSpan.replace("@1",icon);
            element.prepend(iconSpan);
        }
    }

}
