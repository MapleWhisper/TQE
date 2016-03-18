<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<style type="text/css">
.no1 {
	margin-top: 30px;
}
</style>
    <script
            src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/js/messages_zh.min.js"></script>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"/>
<title>管理员列表</title>
</head>

<body>
	<div class="container-fluid">
		<%@ include file="../head.jsp"%>
		<div class="row " style="margin-top: 70px">
			<div class="col-sm-2">
				<%@ include file="../left.jsp"%>
			</div>
			<div class="col-sm-10 ">

				<div class="panel panel-primary">
					<div class="panel-heading">管理员列表</div>

					<div class="panel-body">
						<table class="table table-hover table-striped table-bordered table-condensed">
							<thead>
								<tr class="info">
									<td>管理员账号</td>
									<td>管理员姓名</td>
									<td>管理员职位</td>
									<td>操作</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${adminList}" var="a">
									<tr>

										<td>${a.username }</td>
										<td>${a.name }</td>
										<td>${a.position }</td>

                                        <c:if test="${ sessionScope.admin.name=='admin' || sessionScope.admin.id==a.id }">
                                            <td><a admin-id="${a.id}"
                                                class="btn btn-info" data-toggle="modal" data-target="#edit-admin-modal">
                                                <span class=" glyphicon glyphicon-edit"></span>&nbsp;&nbsp;修改信息</a>
                                            </td>
                                        </c:if>
                                        <c:if test="${sessionScope.admin.name!='admin' && sessionScope.admin.id!=a.id}">
                                            <td>不可修改</td>
                                        </c:if>

									</tr>
								</c:forEach>

							</tbody>
						</table>
                        <c:if test="${sessionScope.admin.name=='admin'}">
                            <div class="row">
                                <div class="col-xs-6 col-xs-offset-5">
                                    <div class="no1">
                                        <a class="btn btn-primary" data-toggle="modal" data-target="#add-admin-modal">添加管理员</a>
                                    </div>

                                </div>
                            </div>
                        </c:if>


					</div>

				</div>
			</div>

		</div>

	</div>



    <!-- 添加管理员Modal -->
    <div class="modal fade" id="add-admin-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="add-admin-modal-title">添加管理员</h4>
                </div>
                <div class="modal-body">
                    <jsp:include page="addAdmin.jsp"/>
                </div>
            </div>
        </div>
    </div>

    <!-- 修改管理员Modal -->
    <div class="modal fade" id="edit-admin-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="edit-admin-modal-title">修改管理员</h4>
                </div>
                <div class="modal-body">
                    <jsp:include page="editAdmin.jsp"/>
                </div>
            </div>
        </div>
    </div>




	<%@ include file="../buttom.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.js"></script>


    <script type="text/javascript">
        $('#edit-admin-modal').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget);
            var adminId = button.attr('admin-id');
            var modal = $(this);
            $.get("/admin/admin/getInfo",{id:adminId},function(data){
                if(data.success){
                    var admin = data.item;
                    modal.find("#edit-username").html(admin.username);
                    modal.find("#edit-password").val("******");
                    modal.find("#edit-id").val(admin.id);
                    modal.find("#edit-name").val(admin.name);
                    modal.find("#edit-position").val(admin.position);
                }else{
                    alert(data.message);
                }
            });
        })
    </script>
</body>
</html>

