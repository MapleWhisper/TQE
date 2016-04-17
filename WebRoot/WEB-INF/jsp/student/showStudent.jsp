<%@page import="java.text.DateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>学生详情</title>
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
		<div class="row">
			<!--左侧的导航条 -->
			<div class="col-xs-2">
				<%@include file="../left.jsp"%>
			</div>
			<!--左侧的导航条 -->

			<!--右侧的内容 -->
			<!-- 学生详情 -->
			<div class="col-xs-10">
				<div class="panel panel-default">
					<div class="panel-heading">
						学生详情
					</div>
					<div class="panel-body ">
						<table class="table table-hover table-striped ">
							<tr class="info">
								<td>学生名</td>
								<td>学生号</td>
								<td>性别</td>
								<td>院系</td>
								<td>专业</td>
								<td>班级</td>
							<tr>
								<td>${student.name }</td>
								<td>${student.sid	 }</td>
								<td>${student.sex }</td>
								<td>${student.department}</td>
								<td>${student.major }</td>
								<td>${student.grade }</td>

							</tr>
						</table>
                        <div class="bs-callout bs-callout-info">
                            <c:set scope="request" var="userType" value="student"/>
                            <jsp:include page="../model/seasonSelectForm.jsp"/>

                        </div>
						<c:forEach items="${courseModel.batchesList}" var="b">
							<div class="bs-callout bs-callout-danger" style="margin-top: 30px;">
									<h4>
										<a
											href="${pageContext.request.contextPath}/admin/batches/show/${b.batches.id}"
											target="_blank">${b.batches.name}</a>
									</h4>
										<table
											class="table table-hover table-striped  table-condensed" style="margin-top: 20px;">
                                            <thead>
											<tr>
												<td>评价教师</td>
												<td>评价课程</td>
												<td>评分</td>
												<td>等级</td>
												<td>评教结果</td>
											</tr>
                                            </thead>
                                            <tbody>
                                            <c:forEach items="${b.teaStuTableList}" var="ts">
                                                <tr>
                                                    <td>${ts.tname}</td>
                                                    <td>${ts.course.name}</td>

                                                    <td>${ts.score}</td>
                                                    <td>${ts.level}</td>
                                                    <td><a target="_blank"
                                                           href="${pageContext.request.contextPath}/admin/eval/show/teaStu/${ts.id}"
                                                           class="btn btn-warning">查看该评价</a></td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>

										</table>
								</div>
						</c:forEach>
					</div>
				</div>

			</div>
			<!--右侧的内容 -->


		</div>
	</div>

	<%@ include file="../buttom.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.js"></script>

	<script type="text/javascript">

    	</script>

</body>
</html>
