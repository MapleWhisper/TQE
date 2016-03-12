/**
 * Created by Maple on 2015/12/2.
 */
/**
 * 关闭当前窗口
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
