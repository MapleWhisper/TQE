<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<style type="text/css">
.no1 {
	margin-top: 30px;
}
</style>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"></link>
<title>权限管理</title>
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
					<div class="panel-heading">权限查看 与管理</div>

					<div class="panel-body">
						<table class="table table-hover table-striped table-bordered table-condensed">
							<thead>
								<tr class="info">
									<td>权限Url</td>
									<td>权限名称</td>
									<td>学生</td>
									<td>教师</td>
									<td>管理员</td>
									<td>操作</td>
								</tr>
							</thead>
							<tbody>
								
								<c:forEach items="${privilegeList}" var="p">
									<tr>

										<td>${p.url}</td>
										<td>${p.name}</td>
										<td><input class="${p.id} form-control stu" disabled="disabled" value="${p.stu}"></td>
										<td><input class="${p.id} form-control tea" disabled="disabled" value="${p.tea}"></td>
										<td><input class="${p.id} form-control adm" disabled="disabled" value="${p.adm}"></td>
										<td>
											<button value="${p.id}"  class="btn btn-info edit">修改</button>
											<button value="${p.id}"  style="display: none" class="btn btn-warning update" >保存修改</button>
										</td>
									</tr>
								</c:forEach>
								
							</tbody>
						</table>
						<!-- 
						<div class="row">
							<div class="col-xs-6 col-xs-offset-5">
								<div class="no1">
									<a class="btn btn-primary " href="admin/add"></a>
								</div>

							</div>

						</div>
						 -->


					</div>

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
		$(function(){
			$(".edit").click(function(){
				var id = $(this).val();
				$("input."+id).removeAttr("disabled");
				$(this).hide();
				$(this).next().show();
			});
			$(".update").click(function(){
				var id = $(this).val();
				var stu = $("input."+id+".stu").val();
				var tea = $("input."+id+".tea").val();
				var adm = $("input."+id+".adm").val();
				$.post("privilege/update",{"id":id,"stu":stu,"tea":tea,"adm":adm},function(data){
					if(data=="success"){
						alert("修改成功");
					}else{
						alert("修改失败！您没有修改权限！");
					}
				});
				$(this).hide();
				
				$(this).prev().show();
				$("input."+id).attr("disabled","disabled");
			});
		});
	</script>
</body>
</html>

