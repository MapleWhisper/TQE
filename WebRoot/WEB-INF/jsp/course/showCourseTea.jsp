<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="panel panel-primary">
	<div class="panel-heading">${course.season}</div>
	<div class="panel-body">
		<div class="row">
			<c:forEach items="${courseModel.batchesList}" var="b">
				<div role="tabpanel" class="tab-pane">
					<div class="panel panel-warning">
						<div class="panel-heading">
							<a
								href="${pageContext.request.contextPath}/admin/batches/show/${b.batches.id}"
								target="_blank">${b.batches.name}</a>
						</div>
						<div class="panel-body">
							<table
								class="table table-hover table-striped table-bordered table-condensed">
								<tr>
									<td>教师号号</td>
									<td>教师姓名</td>
									<td>评教表</td>
									<td>评分</td>
									<td>等级</td>
									<td>评教结果</td>
								</tr>

								<c:forEach items="${b.teaTableList}" var="tea">
									<tr>
										<td>${tea.teacher.id}</td>
										<td>${tea.teacher.name}</td>
										<td><a
											href="${pageContext.request.contextPath}/admin/evalTable/show/${b.batches.teaEvalId}"
											target="_blank">点此查看该课程评教指标表</a></td>
										<td>${tea.score}</td>
										<td>${tea.level}</td>
										<td><a
											href="${pageContext.request.contextPath}/admin/eval/show/teacher/${tea.id}" target="_blank"
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