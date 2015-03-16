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
<title>课程列表</title>
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
					<div class="panel-heading">课程列表</div>

					<div class="panel-body">
						<table class="table table-hover table-striped table-bordered table-condensed">
							<thead>
								<tr class="info">
									<td>课程名</td>
									<td>课程号</td>
									<td>课序号</td>
									<td>教师名</td>
									<td>学院</td>
									<td>学期</td>
									<td>学生数</td>
									<td>学分</td>
									<td>操作</td>
									<td>操作</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${courseList}" var="c">
									<tr>

										<td><a href="">${c.name }</a></td>
										<td>${c.cid }</td>
										<td>${c.cno }</td>
										<td>${c.teacher.name }</td>
										<td>${c.department }</td>
										<td>${c.season }</td>
										<td>${c.stuNumber }</td>
										<td>${c.credit }</td>
										<td><a href="admin/edit/${admin.id }"
											class="btn btn-info"><span
												class=" glyphicon glyphicon-edit"></span>&nbsp;&nbsp;修改</a></td>
										<td><a href="admin/delete/${admin.id}"
											class="btn btn-danger" onclick="return confirm('确认要删除吗？')"><span
												class=" glyphicon  glyphicon-trash"></span>&nbsp;&nbsp;删除</a></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
						<div class="row">
							<div class="col-xs-6 col-xs-offset-5">
								<div class="no1">
									<a class="btn btn-primary " href="admin/add">添加管理员</a>
								</div>

							</div>

						</div>


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
</body>
</html>

