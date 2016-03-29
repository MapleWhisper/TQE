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
<title>我可以评教的课程</title>
</head>

<body>
	<div class="container-fluid">
		<%@ include file="../head.jsp"%>
		<div class="row " >
			<div class="col-sm-2">
				<%@ include file="../left.jsp"%>
			</div>
			<div class="col-sm-10 ">
				<div class="panel panel-primary">
					<div class="panel-heading">请选择要评价的课程</div>

					<div class="panel-body">
						<blockquote>
							<form class="form-inline" method="post"
								action="${pageContext.request.contextPath}/admin/leaEval">
								<div class="form-group">
									<label>学院:</label> <select class="form-control"
										id="department" name="did">
										<c:forEach items="${ departmentList}" var="dep">
											<c:if test="${dep.id != condition.did}">
												<option value="${dep.id}">${dep.name }</option>
											</c:if>
											<c:if test="${dep.id == condition.did}">
												<option value="${dep.id}" selected="selected">${dep.name }</option>
											</c:if>
										</c:forEach>
									</select>

								</div>
								<div class="form-group">
									<label>课程名:</label> <input type="text" class="form-control"
										id="cname" name="cname">
								</div>
								<div class="form-group">
									<label>课程号:</label> <input type="text" class="form-control"
										id="cid" name="cid">
								</div>
								<div class="form-group">
									<label>教师名:</label> <input type="text" class="form-control"
										id="tname" name="tname">
								</div>

								<button type="submit" class="btn btn-primary btn-lg">
									<span class="glyphicon glyphicon-search" aria-hidden="true"></span>搜索
								</button>
							</form>


						</blockquote>
						<table
							class="table table-hover table-striped table-bordered table-condensed">
							<thead>
								<tr class="info">
									<td>课程名</td>
									<td>课程号</td>
									<td>课序号</td>
									<td>教师名</td>
									<td>学院</td>
									<td>学期</td>
									<td>学生数</td>
									<!-- 
									<td>操作</td>
									 -->
									<td>操作</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${courseList}" var="c">
									<tr>

										<td><a
											href="${pageContext.request.contextPath}/admin/course/show/${c.cid}/${c.cno}">${c.name }</a></td>
										<td>${c.cid }</td>
										<td>${c.cno }</td>
										<td>${c.teacher.name }</td>
										<td>${c.department }</td>
										<td>${c.season }</td>
										<td>${c.stuNumber }</td>
										<!-- 
										<td><a href="admin/edit/${admin.id }"
											class="btn btn-info"><span
												class=" glyphicon glyphicon-edit"></span>&nbsp;&nbsp;修改</a></td>
										 -->
										<td><a
											href="${pageContext.request.contextPath}/admin/leaEval/eval/${c.cid}/${c.cno}"
											class="btn btn-danger"><span
												class=" glyphicon glyphicon-zoom-in"></span>点击去评教！</a></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>


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

