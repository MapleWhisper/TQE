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
	href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"/>
<title>课程列表</title>
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

				<div class="panel panel-primary">
					<div class="panel-heading">课程列表</div>

					<div class="panel-body">
						<blockquote>
							<form class="form-inline" method="post" action="${pageContext.request.contextPath}/admin/course">
								<div class="form-group">
									<label for="department" >学院:</label>
									<select  class="form-control" id="department" name="did" >
										<option value="">不限</option>
										<c:forEach items="${ departmentList}" var="dep" >
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
                                    <input type="hidden" id="condition-season" value="${condition.season}">
                                    <label for="season" >学期:</label>
                                    <select name="season" class="form-control" id="season" >
                                        <option value="">不限</option>
                                        <c:forEach begin="2015" end="${applicationScope.curYear+1}" step="1" var="s">
                                            <option  value="${s}春">${s}春</option>
                                            <option  value="${s}秋">${s}秋</option>
                                        </c:forEach>
                                    </select>
                                </div>
								<div class="form-group">
									<label for="cname" >课程名:</label> <input type="text"
										class="form-control" id="cname" name="cname">
								</div>
								<div class="form-group">
									<label for="cid" >课程号:</label> <input type="text"
										class="form-control" id="cid" name="cid">
								</div>
								<div class="form-group">
									<label for="tname" >教师名:</label> <input type="text"
										class="form-control" id="tname" name="tname">
								</div>

								<button type="submit" class="btn btn-primary btn-lg">
									<span class="glyphicon glyphicon-search" aria-hidden="true"></span>搜索
								</button>
							</form>


						</blockquote>
						<table class="table table-hover table-striped  table-condensed ">
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
									<td width="10%">合班</td>
									<!--
									<td>操作</td>
									 -->
									<td>操作</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${courseList}" var="c">
									<tr>

										<td><a href="${pageContext.request.contextPath}/admin/course/show/${c.cid}/${c.cno}">${c.name }</a></td>
										<td>${c.cid }</td>
										<td>${c.cno }</td>
										<td>${c.teacher.name }</td>
										<td>${c.department }</td>
										<td>${c.season }</td>
										<td>${c.stuNumber }</td>
										<td>${c.credit }</td>
										<td>${c.combine }</td>
										<!--
										<td><a href="admin/edit/${admin.id }"
											class="btn btn-info"><span
												class=" glyphicon glyphicon-edit"></span>&nbsp;&nbsp;修改</a></td>
										 -->
										<td><a href="${pageContext.request.contextPath}/admin/course/show/${c.cid}/${c.cno}"
											class="btn btn-danger" ><span
												class=" glyphicon glyphicon-zoom-in"></span>查看评教详情</a></td>
									</tr>
								</c:forEach>

							</tbody>
						</table>
						<!-- 
						<div class="row">
							<div class="col-xs-6 col-xs-offset-5">
								<div class="no1">
									<a class="btn btn-primary " href="admin/add">添加管理员</a>
								</div>

							</div>

						</div>
						 -->
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

    <script type="text/javascript">
        $(function(){
            autoSeasonSelect();
        });
    </script>
</body>
</html>

