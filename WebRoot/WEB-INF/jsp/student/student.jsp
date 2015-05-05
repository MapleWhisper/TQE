<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>学生列表</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"></link>
<style type="text/css">

</style>
</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="../head.jsp"%>
		</div>
		<div class="row" style="margin-top: 70px">
			<!--左侧的导航条 -->
			<div class="col-sm-2">
				<%@include file="../left.jsp"%>
			</div>
			<!--左侧的导航条 -->

			<!--右侧的内容 -->
			<div class="col-sm-10">
				<!-- 面板开始 -->
				<div class="panel panel-primary">
					<div class="panel-heading">
						<h1 class="panel-title" style="font-size: 25px">学生列表 Student
							List</h1>
					</div>
					<div class="panel-body">
						<blockquote>
							<form class="form-inline" method="post" action="${pageContext.request.contextPath}/admin/student">
								<div class="form-group">
									<label >学院:</label>
									<select  class="form-control" id="department" name="did" >
										<c:forEach items="${ departmentList}" var="dep" >
												<option value="${dep.id}">${dep.name }</option>
										</c:forEach>
									</select>
									
								</div>
								<div class="form-group">
									<label  >专业:</label> 
									  <select  class="form-control" id="major" name="mid">
									  <option value="0" selected="selected">不限</option>
									</select>
								</div>
								<div class="form-group">
									<label  >班级:</label>
										<select  class="form-control" id="clazz" name="cid">
									  <option value="0" selected="selected">不限</option>
									  </select>
								</div>
								<div class="form-group">
									<label  >姓名:</label> <input type="text"
										class="form-control" id="name" name="sname">
								</div>
								<div class="form-group">
									<label  >学号:</label> <input type="number"
										class="form-control" id="sid" name="sid">
								</div>

								<button type="submit" class="btn btn-primary btn-lg">
									<span class="glyphicon glyphicon-search" aria-hidden="true"></span>搜索
								</button>
							</form>


						</blockquote>
						<table
							class="table table-hover table-striped table-bordered table-condensed compact">
							<thead>
								<tr class="info">
									<td>学生姓名</td>
									<td>学号</td>
									<td>证件号</td>
									<td>性别</td>
									<td>院系</td>
									<td>专业</td>
									<td>班级</td>
									<!-- 
									<td>方向</td>
									 -->
									<td>年级</td>
									<td>校区</td>
									<td>操作</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${studentList}" var="s">
									<tr>
										<td><a
											href="${pageContext.request.contextPath}/admin/studnet/show/${s.sid}">${s.name}</a></td>
										<td>${s.sid}</td>
										<td>${s.idNumber }</td>
										<td>${s.sex }</td>
										<td>${s.department }</td>
										<td>${s.major }</td>
										<td>${s.clazz }</td>
										<!-- 
										<td>${s.field }</td>
										 -->
										<td>${s.grade }</td>
										<td>${s.campus }</td>


										<td><a href="${pageContext.request.contextPath}/admin/student/show/${s.sid}"
											class="btn btn-danger">查看学生</a></td>
									</tr>
								</c:forEach>
							</tbody>

						</table>
						<div class="row">
							<div class="col-xs-6 col-xs-offset-5">
								<div class="no1">
									<a class="btn btn-primary " href="teacherAction!add">添加教师</a>
								</div>

							</div>
						</div>
					</div>
					<!-- panel-body -->
				</div>
				<!-- panel panel-default -->
				<!-- 面板结束 -->
			</div>
			<!--右侧的内容 -->
		</div>
		<%@ include file="../buttom.jsp"%>
	</div>
	<script
		src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.js"></script>

	<script type="text/javascript">
		$(function(){
			
			//点击部门 动态加载专业列表
			$("#department").change(function(){
				var did = $("#department option:selected").val();
				$.post("../getMajor/"+did,function(data){
					//alert(data);
					if(data!=null){
						$("#major").html("<option value='0' selected='selected'>不限</option>");
						$(data).each(function(){
							//alert(this.id);
							//alert(this.name);
							var opt = $("<option value="+this.id+">"+this.name+"</option>")
							
							$("#major").append(opt);
						});
					}
					
				});
				$("#major option[value=0]:selected");
				$("#clazz option[value=0]:selected");
			});
			
			//点击专业，动态加载班级信息列表
			$("#major").change(function(){
				var did = $("#department option:selected").val();
				var mid = $("#major option:selected").val();
				$.post("../getClazz/"+did+"/"+mid,function(data){
					//alert(data);
					if(data!=null){
						$("#clazz").html("<option value='0' selected='selected'>不限</option>");
						$(data).each(function(){
							//alert(this.id);
							//alert(this.name);
							var opt = $("<option value="+this.id+">"+this.name+"</option>")
							
							$("#clazz").append(opt);
						});
					}
					
				});
				$("#major option[value=0]:selected");
				$("#clazz option[value=0]:selected");
			});
		});
	</script>
</body>
</html>
