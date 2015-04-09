
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>

<title>课程评教详情|${course.name}</title>
</head>

<body>
	<div class="container-fluid">
		<%@ include file="../head.jsp"%>
		<div class="row " style="margin-top: 70px">
			<div class="col-sm-2">
				<%@ include file="../left.jsp"%>
			</div>
			<div class="col-sm-10 ">
				<table
					class="table table-hover table-striped table-bordered table-condensed">
					<tr class="warning">
						<td>课程名</td>
						<td>教师</td>
						<td>学院</td>
						<td>学期</td>
						<td>课程号</td>
						<td>课序号</td>
					<tr>
						<td>${course.name }</td>
						<td>${course.teacher.name	 }</td>
						<td>${course.department }</td>
						<td>${course.season }</td>
						<td>${course.cid }</td>
						<td>${course.cno }</td>

						<!-- 
						<td>${batches.name }</td>
						<td><fm:formatDate value="${batches.beginDate}"
								pattern="yyyy-MM-dd" /></td>
						<td><fm:formatDate value="${batches.endDate }"
								pattern="yyyy-MM-dd" /></td>
							 -->
					</tr>
				</table>
				<div role="tabpanel">

					<!-- Nav tabs -->
					<ul class="nav nav-pills" role="tablist">
						<li role="presentation" class="active"><a href="#student"
							aria-controls="student" role="tab" data-toggle="tab">学生评教信息</a></li>
						<li role="presentation"><a href="#profile"
							aria-controls="profile" role="tab" data-toggle="tab">教师评教信息</a></li>
						<li role="presentation"><a href="#messages"
							aria-controls="messages" role="tab" data-toggle="tab">领导评教信息</a></li>
					</ul>

					<!-- Tab panes -->
					<div class="tab-content">
						<div role="tabpanel" class="tab-pane active" id="student">
							<div class="panel panel-primary">
								<div class="panel-heading">${course.season}</div>
								<div class="panel-body">
									<div class="row">
										<c:forEach items="${courseModel.batchesList}" var="b">
										<div role="tabpanel" class="tab-pane">
											<div class="panel panel-warning">
												<div class="panel-heading"><a href="${pageContext.request.contextPath}/admin/batches/show/${b.batches.id}" target="_blank">${b.batches.name}</a></div>
												<div class="panel-body">
													<table
														class="table table-hover table-striped table-bordered table-condensed">
														<tr>
															<td>学号</td>
															<td>学生姓名</td>
															<td>评教表</td>
															<td>评分</td>
															<td>等级</td>
															<td>评教结果</td>
														</tr>

														<c:forEach items="${b.stuTableList}" var="st">
															<tr>
																<td>${st.student.sid}</td>
																<td>${st.student.name}</td>
																<td><a href="${pageContext.request.contextPath}/admin/evalTable/show/${b.batches.stuEvalId}" target="_blank">点此查看该课程评教指标表</a></td>
																<td>${st.score}</td>
																<td>${st.level}</td>
																<td><a
																	href="${pageContext.request.contextPath}/admin/eval/show/student/${st.id}"
																	class="btn btn-warning">查看该评价</a></td>
															</tr>
														</c:forEach>
													</table>
												</div>
											</div>
										</div>
										</c:forEach>
									</div>
								</div>
							</div>
							<!-- panel panel-primary -->
						</div>
						<div role="tabpanel" class="tab-pane" id="profile">...</div>
						<div role="tabpanel" class="tab-pane" id="messages">...</div>
						<div role="tabpanel" class="tab-pane" id="settings">...</div>
					</div>

				</div>

			</div>
		</div>

	</div>
	<!-- 	container -->
	<%@ include file="../buttom.jsp"%>
</body>
</html>

