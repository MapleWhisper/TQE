<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>教师列表</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"></link>

</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="../head.jsp"%>
		</div>
		<div class="row" style="margin-top: 70px">
			<!--左侧的导航条 -->
			<div class="col-sm-2">
				<%@include file="../left.jsp"%>
			</div>
			<!--左侧的导航条 -->

			<!--右侧的内容 -->
			<div class="col-sm-10">
				<!-- 面板开始 -->
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h1 class="panel-title" style="font-size: 25px">教师列表 Teacher
							List</h1>
					</div>
					<div class="panel-body">
						<table
							class="table table-hover table-striped table-bordered table-condensed">
							
							<thead>
								<tr class="info">
									<td>name</td>
									<td>id</td>
									<td>教师邮箱</td>
									<td>证件号码</td>
									<td>性别</td>
									<td>院系</td>
									<td>职称</td>
									<td>操作</td>
									<td>操作</td>
								</tr>
							</thead>
							<!-- 
							<tbody>
								
								<c:forEach items="${teacherList}" var="tea">
									<tr>
										<td><a
											href="${pageContext.request.contextPath}/admin/teacher/teacherAction!show?id=${tea.id}">${tea.name}</a></td>
										<td>${tea.id}</td>
										<td>${tea.email }</td>
										<td>${tea.idNumber }</td>
										<td>${tea.sex }</td>
										<td>${tea.department }</td>
										<td>${tea.title }</td>


										<td><a href="teacherAction!edit?id=${tea.id }"
											class="btn btn-info"><span
												class=" glyphicon glyphicon-edit"></span>&nbsp;&nbsp;修改</a></td>
										<td><a href="teacherAction!delete?id=${tea.id}"
											class="btn btn-danger" onclick="return confirm('确认要删除吗？')"><span
												class=" glyphicon  glyphicon-trash"></span>&nbsp;&nbsp;删除</a></td>
									</tr>
								</c:forEach>
								
							</tbody>
							 -->

						</table>
						<div class="row">
							<div class="col-xs-6 col-xs-offset-5">
								<div class="no1">
									<a class="btn btn-primary " href="teacherAction!add">添加教师</a>
								</div>

							</div>
						</div>
					</div>
					<!-- panel-body -->
				</div>
				<!-- panel panel-default -->
				<!-- 面板结束 -->
			</div>
			<!--右侧的内容 -->
		</div>
		<%@ include file="../buttom.jsp"%>
	</div>
	<script
		src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.server.js"></script>
	<script type="text/javascript">
		$(function() {
			$('table').has("thead").dataTable({
				"language" : {
					"lengthMenu" : "每页显示 _MENU_ 条纪录 ",
					"zeroRecords" : "抱歉,没有找到数据",
					"info" : "当前第  _PAGE_/_PAGES_ 页",
					"infoEmpty" : "",
					"infoFiltered" : "(过滤自  _MAX_ 条纪录)",
					"search" : "搜索:",
					"searchPlaceholder" : "请输入关键字",
					"next" : "下一页",
					"previous" : "上一页"
				},
				"processing": true,
		        "serverSide": true,
		        "ajax": "teacher/json",
		        "columns": [
		                    { "data": "id" },
		                    { "data": "name" },
		                ]
			});
		});
	</script>
</body>
</html>
