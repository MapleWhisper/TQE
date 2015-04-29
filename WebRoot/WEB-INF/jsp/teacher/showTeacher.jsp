<%@page import="java.text.DateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>教师详情</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"></link>


<style type="text/css">
.table>thead>tr>th,.table>tbody>tr>th,.table>tfoot>tr>th,.table>thead>tr>td,.table>tbody>tr>td,.table>tfoot>tr>td
	{
	border-top: 0px solid #ddd;
}
</style>

</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="../head.jsp"%>
		</div>
		<div class="row" style="margin-top: 70px">
			<!--左侧的导航条 -->
			<div class="col-xs-2">
				<%@include file="../left.jsp"%>
			</div>
			<!--左侧的导航条 -->

			<!--右侧的内容 -->

			<!-- 班级详情 -->
			<div class="col-xs-10">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h1 class="panel-title" style="font-size: 25px">教师详情</h1>
					</div>
					<div class="panel-body ">
						<table
					class="table table-hover table-striped table-bordered table-condensed">
					<tr class="warning">
						<td>教师名</td>
						<td>教师号</td>
						<td>学院</td>
						<td>职称</td>
					<tr>
						<td>${teacher.name }</td>
						<td>${teacher.id }</td>
						<td>${teacher.department }</td>
						<td>${teacher.title }</td>

						<!-- 
						<td>${batches.name }</td>
						<td><fm:formatDate value="${batches.beginDate}"
								pattern="yyyy-MM-dd" /></td>
						<td><fm:formatDate value="${batches.endDate }"
								pattern="yyyy-MM-dd" /></td>
							 -->
					</tr>
				</table>
					</div>


				</div>
			</div>
			<!-- 班级详情 -->




			<div class="panel panel-info">
				<div class="panel-heading">
					<h1 class="panel-title" style="font-size: 25px">老师教的班级</h1>
				</div>
				<div class="panel-body">

					<!-- 班级内容 -->
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="list1">
							<table
								class="table table-hover table-striped table-bordered table-condensed"
								style="margin-top: 10px">
								<thead>
									<tr class="info">
										<td>课程名</td>
										<td>开课时间</td>
										<td>结束时间</td>
										<td>状态</td>
										<td>操作</td>
									</tr>
								</thead>
								<tbody>
									<c:forEach items="${teacher.classes}" var="c">
										<tr>
											<td>${c.name }</td>
											<td><fm:formatDate value="${c.beginDate }"
													pattern="yyyy-MM-dd" /></td>
											<td><fm:formatDate value="${c.endDate }"
													pattern="yyyy-MM-dd" /></td>
											<td><button class="btn label" disabled="disabled">${c.status}</button></td>

											<td><a
												href="${pageContext.request.contextPath}/admin/classes/classesAction!show?id=${c.id }"
												class="btn btn-info" target="_blank"><span
													class=" glyphicon glyphicon-zoom-in"></span>&nbsp;&nbsp;查看</a></td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
						</div>
					</div>
				</div>
				<!-- panel-body -->
			</div>



		</div>
		<!--右侧的内容 -->


	</div>

	<%@ include file="../buttom.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
	<script src="${pageContext.request.contextPath}/js/messages_zh.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.js"></script>

	<script type="text/javascript">
    	</script>

</body>
</html>
