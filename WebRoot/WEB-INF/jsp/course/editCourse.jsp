
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>

<title>修改管理员</title>
</head>

<body>
	<div class="container">
		<%@ include file="../head.jsp" %>
		<div class="row " style="margin-top: 70px">
			<div class="col-xs-3">
				<%@ include file="../left.jsp"%>
			</div>
			<div class="col-xs-9 ">
				<div class="panel panel-primary">
					<div class="panel-heading">修改管理员信息</div>

					<div class="panel-body">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<form action="${pageContext.request.contextPath}/admin/admin/update" class="form-horizontal" role="form" id="form" method="post">
								<div class="form-group">
									
									<label for="inputEmail3" class="col-xs-3 control-label">管理员账号</label>
									<div class="col-xs-9">
										<input type="text" class="form-control inputxt" id="username" name="username" value="${admin.username }">
										<input type="hidden" name="id" value="${admin.id }">
									</div>
								</div>
								
								<div class="form-group">
									<label for="inputEmail3" class="col-xs-3 control-label">输入新密码</label>
									<div class="col-xs-9">
										<input type="password" class="form-control inputxt" id="password" name="password" placeholder="password" value="${admin.password }">
									</div>
								</div>
								
								<div class="form-group">
									<label for="inputEmail3" class="col-xs-3 control-label">姓名</label>
									<div class="col-xs-9">
										<input type="text" class="form-control inputxt" id="name" name="name" value="${admin.name }">
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-xs-3 control-label">职位</label>
									<div class="col-xs-9">
										<input type="text" class="form-control inputxt" name="position"  id="position"value="${admin.position }">
									</div>
								</div>
								<div class="form-group" data-toggle="tooltip" data-placement="top" title="可多选" onmouseover="$(this).tooltip('show')">
									<label for="inputEmail3" class="col-xs-3 control-label">权限</label>
									<div class="col-xs-9">
										<c:forEach items="${privilegeList}" var="p">
											<label class="checkbox-inline">
											  <f:checkbox  path="admin.privilegeIds" name="privilegeIds" value="${p.id}"/> ${p.name}
											</label>
										</c:forEach>
									</div>
								</div>
								<div>
									
									<div class="form-group">    
										 <div class="col-xs-6 col-xs-offset-4">				
											<button type="submit" class="btn btn-primary  btn-lg "><span class="glyphicon glyphicon-floppy-saved"></span>&nbsp;&nbsp;确认修改管理员信息</button>																			 
										 </div>
									</div>
								</div>
						</form>
						</div>
					
					</div>
						
					</div>
				</div>
			</div>
		</div>

	</div>
<!-- 	container -->
	<%@ include file="../buttom.jsp" %>
</body>
</html>

