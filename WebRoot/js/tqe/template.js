/**
 * Created by Maple on 2016/6/5.
 */

/**
 * 弹出模板窗口 并插入数据到Id 为 TargetIds的元素中去
 * @param type          对应于数据库中 template.type 字段 模板类型
 * @param targetIds     需要插入对象的Id数据
 * @param callback      回调函数 将会回传用户选择的数据
 */
function template(type,targetIds,callback){
    var templateModalId = type+"-template";
    var options = {
        templateModalId:type+"-template",
        type:type,
        container:{},
        targetIds:targetIds,
        callback:callback
    };
    createModal(options);


    //callback('ok');
}


function templateItemSelectedEvent(options){

}

function modalCloseEvent(options){
    options.container.on('hidden.bs.modal', function (e) {
        options.callback(null);
    });
}


function createModal(options){
    //var templateModalId = type+"-template";
    var templateModal = $("#"+options.templateModalId);
    if(templateModal.length==0){    //不存在Modal 那么创建一个
        var $templateModal = $(
            "<div class='modal fade' id='"+options.templateModalId+"' tabindex='-1' role='dialog' aria-labelledby='myModalLabel'>"+
                 "<div class='modal-dialog modal-lg' role='document'>"+
                     "<div class='modal-content'>"+
                       "<div class='modal-header'>"+
                           "<button type='button' class='close' data-dismiss='modal' aria-label='Close'><span aria-hidden='true'>&times;</span></button>"+
                           "<h4 class='modal-title' id='edit-admin-modal-title'>Test</h4>" +
                       "</div>"+
                       "<div class='modal-body' style='padding-bottom:45px'> "+
                       "</div>" +
                       "<div class='modal-footer'>"+
                            "<button type='button' class='btn btn-info btn-lg' data-dismiss='modal'>关闭</button>"+
                        "</div>"+
                    "</div>" +
                "</div>" +
            "</div>");
        options.container = $templateModal;
        renderModalBody(options);
        $("body").append($templateModal);

    }
    templateModal = $("#"+options.templateModalId);
    options.container = templateModal;
    templateModal.modal('show');
}

function renderModalBody(options){
    var modalContainer = options.container;
    var modalBody = modalContainer.find(".modal-body");
    var tableId = options.type+"-table";
    var $modalTable = $("<table id='"+tableId+"' class='table table-hover table-striped table-bordered table-condensed'>"+
                    "</table>");
    modalBody.append($modalTable);

    $(function(){
        $.get("/TQE/admin/template/getInfo",
            {type:options.type},
            function(data){
                if(data.success){
                    var modalTable = renderTable(data.item);
                    insertBtnClickEvent(modalTable,$modalTable,options);
                }else{
                    alert(data.message);
                }
            });
    });


}

/**
 * 插入按钮返回该行数据
 */
function insertBtnClickEvent(modalTable,$modalTable,options){
    $modalTable.find('tbody').on( 'click', 'button', function () {
        var data = modalTable.row( $(this).parents('tr') ).data();
        if(data){
            if(data[data.length-1]==null){
                data.pop();
            }
            if(options.callback){
                options.callback(data); //调用回调函数
            }
            if(options.targetIds){
                var ids = options.targetIds.split(',');
                if(ids){
                    for(var i=0;i<ids.length;i++){
                        if(ids[i] && data[i]){
                            $("#"+ids[i]).val(data[i]);
                        }
                    }
                }
            }
            options.container.modal('hide');
        }
    } );
}

/**
 * 渲染 模板 表格
 */
function renderTable(template){
    var columns = template.columns;

    var items = template.items;

    var dataArr = [];
    var columnArr = [];
    for(var i=0;i<items.length;i++){
        if(items[i] && items[i].values ){
            var data = items[i].values;
            data.push(null);    // null for operation
            dataArr.push(data);
        }
    }

    for(i=0;i<columns.length;i++){
        var d = {"title":""};
        d.title = columns[i];
        columnArr.push(d);
    }
    columnArr.push({title:'操作',width:"80px"});

    var options = $.extend(true,{},dataTableDefaultOptions,{
        columns: columnArr,
        data:dataArr,
        autoWidth: false,   //禁用自动调整列宽
        columnDefs: [ {
            "targets": -1,
            "data": null,
            "width":"80px",
            "defaultContent": "<button class='btn btn-primary'>插入</button>"
        } ]
    });

    return $("#"+template.type+"-table").DataTable(options);
}
