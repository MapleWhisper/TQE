/**
 * Created by Maple on 2016/4/2.
 */


/**
 * 用于将页面 这种格式的统计数据[0,1,3,1] 渲染成进度条的形式
 */
$(function(){
    renderProcessBar();
});

function renderProcessBar(){
    $(".eval-process-bar").each(function(){
        var $this = $(this);
        var cntsString = $this.attr('value');
        if(cntsString){
            var evalLevelCnts = $.parseJSON(cntsString);
            buildProcessBar(evalLevelCnts,$this);
        }
    });
    function buildProcessBar(evalLevelCnts,processBarElement){
        var processBarColor = ['success','info','warning','danger'];
        var levels = ['优','良','中','差'];
        var sum = eval(evalLevelCnts.join("+"));
        var processPercents = [];
        if(sum>0){
            for(var i =0;i<evalLevelCnts.length;i++){
                processPercents.push( parseInt( (evalLevelCnts[i]/sum)*100 ) );
            }
        }else{
            processPercents = [25,25,25,25];
        }

        var evalProcessBar = "<div class='progress-bar progress-bar-@1' style='width: @2%'>"
            +"<span >@4 @3个( @2% )</span></div>";
        processBarElement.html('');
        for(var j=0;j<4;j++){
            var tmpBar = evalProcessBar;
            tmpBar = tmpBar.replace(/@1/g,processBarColor[j]);
            tmpBar = tmpBar.replace(/@2/g,processPercents[j]);
            tmpBar = tmpBar.replace(/@3/g,evalLevelCnts[j]);
            tmpBar = tmpBar.replace(/@4/g,levels[j]);
            processBarElement.append(tmpBar);
        }
    }
}