<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<style type="text/css">
.no1 {
	margin-top: 30px;
}
</style>

<title>学生评教中心</title>
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
					<div class="panel-heading">我的课程 | ${batches.name}</div>

					<div class="panel-body">
                            <table class="table table-hover table-striped table-condensed">
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

                        <div class="form-group error-message-container">
                            <div class="col-sm-8 col-sm-offset-2">
                                <div class="alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert">
                                        <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                                    </button>
                                    <span class="error-message">${message}</span>
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
	<script>
		$(function(){
			showErrorMessage();
		});
	</script>
</body>
</html>

