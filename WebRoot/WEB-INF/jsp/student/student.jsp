<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>学生列表</title>
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
							class="table table-hover table-striped table-bordered table-condensed compact">
							<thead>
								<tr class="info">
									<td>学生姓名</td>

									<td>学号</td>
									<td>证件号</td>
									<td>性别</td>
									<td>院系</td>
									<td>专业</td>
									<td>班级</td>
									<td>方向</td>
									<td>年级</td>
									<td>校区</td>
									<td>操作</td>
									<td>操作</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${studentList}" var="s">
									<tr>
										<td><a
											href="${pageContext.request.contextPath}/admin/studnet/show/${s.sid}">${s.name}</a></td>
										<td>${s.sid}</td>
										<td>${s.idNumber }</td>
										<td>${s.sex }</td>
										<td>${s.department }</td>
										<td>${s.major }</td>
										<td>${s.clazz }</td>
										<td>${s.field }</td>
										<td>${s.grade }</td>
										<td>${s.campus }</td>


										<td><a href="teacherAction!edit?id=${s.sid }"
											class="btn btn-info btn-sm"><span
												class=" glyphicon glyphicon-edit"></span>&nbsp;&nbsp;修改</a></td>
										<td><a href="teacherAction!delete?id=${s.sid}"
											class="btn btn-danger btn-sm"
											onclick="return confirm('确认要删除吗？')"><span
												class=" glyphicon  glyphicon-trash"></span>&nbsp;&nbsp;删除</a></td>
									</tr>
								</c:forEach>
							</tbody>

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
		src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.js"></script>

	
</body>
</html>