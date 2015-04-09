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
<title>我的课程</title>
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
					<div class="panel-heading">我的课程|${batches.name}</div>

					<div class="panel-body">
						<table class="table table-hover table-striped table-bordered table-condensed">
							<thead>
								<tr class="info">
									<td>课程名</td>
									<td>教师名</td>
									<td>学院</td>
									<td>学期</td>
									<td>学生数</td>
									<td>学分</td>
									<td>操作</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${courseList}" var="c">
									<tr>

										<td>${c.name }</td>
										<td>${c.teacher.name }</td>
										<td>${c.department }</td>
										<td>${c.season }</td>
										<td>${c.stuNumber }</td>
										<td>${c.credit }</td>
										<c:if test="${c.isEvaled=='false'}">
											<td><a href="${pageContext.request.contextPath}/admin/stuEval/eval/${c.cid}/${c.cno}"
											class="btn btn-danger"><span
												class=" glyphicon glyphicon-edit"></span>&nbsp;&nbsp;评价</a></td>
										</c:if>
										<c:if test="${c.isEvaled=='true'}">
											<td><button  class="btn btn-default" disabled="disabled"><span
												class=" glyphicon glyphicon-ok"></span>已评价</button></td>
										</c:if>
									</tr>
								</c:forEach>

							</tbody>
						</table>
						<div style="text-align: center">
							<h1 class="well text-danger">${msg}</h1>
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

