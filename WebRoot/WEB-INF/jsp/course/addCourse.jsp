
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>添加管理员</title>
</head>

<body>
	<div class="container">
		<%@ include file="../head.jsp" %>
		<div class="row " >
			<div class="col-xs-3">
				<%@ include file="../left.jsp"%>
			</div>
			<div class="col-xs-9 ">
				<div class="panel panel-primary">
					<div class="panel-heading">管理员信息填写</div>

					<div class="panel-body">
					<div class="row">
						<div class="col-xs-8 col-xs-offset-2">
							<form action="${pageContext.request.contextPath }/admin/admin/save" class="form-horizontal" role="form" id="form" method="post">
								<div class="form-group">
									
									<label for="inputEmail3" class="col-xs-3 control-label">管理员账号</label>
									<div class="col-xs-9">
										<input type="text" class="form-control inputxt" name="username" id="username" >
									</div>
								</div>
								
								<div class="form-group">
									<label for="inputEmail3" class="col-xs-3 control-label">输入密码</label>
									<div class="col-xs-9">
										<input type="text" class="form-control inputxt" name="password" id="username">
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-xs-3 control-label">确认输入密码</label>
									<div class="col-xs-9">
										<input type="text" class="form-control inputxt" id="username">
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-xs-3 control-label">姓名</label>
									<div class="col-xs-9">
										<input type="text" class="form-control inputxt" name="name" id="username"	>
									</div>
								</div>
								<div class="form-group">
									<label for="inputEmail3" class="col-xs-3 control-label">职位</label>
									<div class="col-xs-9">
										<input type="text" class="form-control inputxt" name="position" id="username"	>
									</div>
								</div>
								<div class="form-group" data-toggle="tooltip" data-placement="top" title="可多选" onmouseover="$(this).tooltip('show')">
									<label for="inputEmail3" class="col-xs-3 control-label">权限</label>
									<div class="col-xs-9">
										<c:forEach items="${privilegeList}" var="p">
											<label class="checkbox-inline">
											  <input type="checkbox"  name="privilegeIds" value="${p.id}"/> ${p.name}
											</label>
										</c:forEach>
									</div>
								</div>
								<div>
									
									<div class="form-group">    
										 <div class="col-xs-6 col-xs-offset-4">
											<button type="submit" class="btn btn-primary  btn-lg ">提交管理员</button>																			 
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

