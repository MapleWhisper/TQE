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
									<td>领导名</td>
									<td>评教表</td>
									<td>评分</td>
									<td>等级</td>
									<td>评教结果</td>
								</tr>

								<c:forEach items="${b.leaTableList}" var="leaTable">
									<tr>
										<td>${leaTable.leader.name}</td>
										<td><a
											href="${pageContext.request.contextPath}/admin/evalTable/show/${b.batches.leadEvalId}"
											target="_blank">点此查看该课程评教指标表</a></td>
										<td>${leaTable.score}</td>
										<td>${leaTable.level}</td>
										<td><a
											href="${pageContext.request.contextPath}/admin/eval/show/leader/${leaTable.id}"
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