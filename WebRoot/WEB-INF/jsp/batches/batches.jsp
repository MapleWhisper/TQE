<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>评教批次</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"/>
    <script
            src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/js/messages_zh.min.js"></script>
</head>

<body>
	<div class="container-fluid">
		<%@ include file="../head.jsp"%>
		<div class="row" style="margin-top: 70px">
			<div class="col-sm-2">
				<%@ include file="../left.jsp"%>
			</div>
			<div class="col-sm-10">



				<div class="panel panel-default">
					<div class="panel-heading">
						批次列表
					</div>
					<div class="panel-body">

                        <div class="bs-callout bs-callout-info">
                            <a class="btn btn-info btn-lg" data-toggle="modal" icon="plus" data-target="#add-batch-modal">生成新批次</a>
                        </div>

						<table class="table table-hover table-striped table-bordered table-condensed">
							<thead>
								<tr class="info">
                                    <td>批次号</td>
									<td>批次名</td>
									<td>学期</td>
                                    <td>状态</td>
									<td>开始/结束时间</td>
                                    <td>操作</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${batchesList }" var="b">
									<tr>
                                        <td>${b.id}</td>
										<td><a 
											href="${pageContext.request.contextPath}/admin/batches/show/${b.id}">${b.name}</a></td>
										<td>${b.season}</td>
                                        <td><span class="batch-status">${b.batchStatus}</span></td>
                                        <td><fm:formatDate value="${b.beginDate}"  dateStyle="medium"/> ~
                                            <fm:formatDate value="${b.endDate}"  dateStyle="medium"/></td>
                                        <td><a href="${pageContext.request.contextPath}/admin/batches/show/${b.id}" icon="zoom-in" class="btn btn-primary">查看批次</a></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>



        <!-- 添加批次Modal -->
        <div class="modal fade" id="add-batch-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                        <h4 class="modal-title" id="add-admin-modal-title">添加新批次</h4>
                    </div>
                    <div class="modal-body">
                        <jsp:include page="addBatch.jsp"/>
                    </div>
                </div>
            </div>
        </div>



		<script type="text/javascript">
    			$(function(){
    				$(".delete").click(function(){
    					var mes;
         				$.post("../../problem/delete/"+$(this).attr("name"),{"id":$(this).attr("name")},function(date){
         					mes= date.mes;
         				});
         				if(mes == 'success'){
         					alert("删除成功");
         					$(this).parent().parent("tr").fadeOut(2000);
         				}else{
         					alert("删除失败！有  用户的申请 使用了改试卷，不可删除");
         				}
         				
        			});
    			});
    		</script>
	</div>
	<%@ include file="../buttom.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.js"></script>
	<script language="javascript" type="text/javascript"
		src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
    <script type="text/javascript">
        $(function(){
            batchStatusRender();
            autoAddIcon();

        });
    </script>
</body>
</html>
