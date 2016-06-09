<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
  <head>
	<%@ include file="header.jspf" %>
      <link rel="stylesheet"
            href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"/>
  </head>
  
  <body>
    	<div class="container">
    		<%@ include file="head.jsp" %>
    		<div class="row" style="margin-top: 100px">
                <table id="table1" class="table table-hover table-striped table-bordered table-condensed">

                </table>
    		</div>

    	</div>
    	<%@ include file="buttom.jsp" %>
        <script
                src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
        <script
                src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.js"></script>

        <script type="text/javascript">
            $(function(){
                $.get("${pageContext.request.contextPath}/admin/template/getInfo",
                        {type:"evaltable-note"},
                        function(data){
                            if(data.success){
                                renderTable(data.item);
                            }else{
                                alert('error');
                            }
                        });
            });

            function renderTable(template){
                var columns = template.columns;

                var items = template.items;

                var dataArr = [];
                var columnArr = [];
                for(var i=0;i<items.length;i++){
                    if(items[i] && items[i].values ){
                        var data = items[i].values;
                        data.push(null);
                        dataArr.push(data);
                    }
                }

                for(var i=0;i<columns.length;i++){
                    var d = {"title":""};
                    d.title = columns[i];
                    columnArr.push(d);
                }
                columnArr.push({title:'操作'});

                var options = $.extend(true,{},dataTableDefaultOptions,{
                    columns: columnArr,
                    data:dataArr,
                    columnDefs: [ {
                        "targets": -1,
                        "data": null,
                        "defaultContent": "<button class='btn btn-primary'>插入</button>"
                    } ]
                });
                log(options);

                $("#table1").dataTable(options);
            }
        </script>
  </body>
</html>
