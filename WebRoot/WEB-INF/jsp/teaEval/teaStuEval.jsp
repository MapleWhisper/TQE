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
<title>我的学生</title>
</head>

<body>
	<div class="container-fluid">
        <div class="row">
		    <%@ include file="../head.jsp"%>
        </div>
		<div class="row " >
			<div class="col-sm-2">
				<%@ include file="../left.jsp"%>
			</div>
			<div class="col-sm-10 ">

				<div class="panel panel-default">
					<div class="panel-heading">我的学生|${batches.name}</div>

					<div class="panel-body">
						<table
							class="table table-hover table-striped table-bordered table-condensed">
							<thead>
								<tr class="info">
									<td>课程名</td>
									<td>教师名</td>
									<td>学院</td>
									<td>学期</td>
									<td>学生数</td>
									<td>合班</td>
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
										<td>${c.combine }</td>
										<td><a
											href="${pageContext.request.contextPath}/admin/teaStuEval/show?cid=${c.cid}&cno=${c.cno}"
											class="btn btn-primary" icon="zoom-in">查看学生</a></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>

						<!-- 如果选择了课程 -->
						<c:if test="${course != null }">
							<%@include file="teaStuEval_showStudents.jsp" %>
						</c:if>

                        <div class="form-group error-message-container">
                            <div class="col-sm-8 col-sm-offset-2">
                                <div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert">
                                        <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                                    </button>
                                    <span class="error-message">${msg}</span>
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
		src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.nodefault.js"></script>
	<script>
		$(function(){
			showErrorMessage();
            autoAddIcon();
            var options = $.extend(true,{},dataTableDefaultOptions);
            options.order = {};
            $('table').has("thead").dataTable(options);
		});
	</script>
</body>
</html>

