<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
  <head>
	<%@ include file="../header.jspf" %>
	<title>修改教师</title>
	
  </head>
  
  <body>
    	<div class="container-fluid">
    		<div class="row">
    			<%@ include file="../head.jsp" %>
    		</div>
    		<div class="row" style="margin-top: 70px">
    				<!--左侧的导航条 -->
					<div class="col-xs-2">
						<%@include file="../left.jsp"%>
					</div>
					<!--左侧的导航条 -->
					
					<!--右侧的内容 -->
					<div class="col-xs-10">
						<!-- 面板开始 -->
						<div class="panel panel-danger">
						  <div class="panel-heading">
						    <h1 class="panel-title" style="font-size: 25px">修改管理员  Edit Admin</h1>
						  </div>
						  <div class="panel-body">
						  	 <form action="${pageContext.request.contextPath }/admin/teacher/teacherAction!update" class="form-horizontal" role="form" id="form1" method="post">
									<div class="form-group">
										<label   class="col-xs-2 control-label">教师邮箱</label>
										<div class="col-xs-10">
											<input type="email" class="form-control " name="teacher.email" id="email" required value="${teacher.email }" >
											<input type="hidden" name="teacher.id" value="${teacher.id }">
										</div>
									</div>
									
									
									<div class="form-group">
										<label   class="col-xs-2 control-label">输入密码</label>
										<div class="col-xs-10">
											<input type="password" class="form-control  " name="teacher.password" id="password" required value="${teacher.password }">
										</div>
									</div>
									<div class="form-group">
										<label   class="col-xs-2 control-label">确认输入密码</label>
										<div class="col-xs-10">
											<input type="password" class="form-control  " name="password2" id="password2" required value="${teacher.password }">
										</div>
									</div>
									<div class="form-group">
										<label   class="col-xs-2 control-label">姓名</label>
										<div class="col-xs-10">
											<input type="text" class="form-control  " name="teacher.name" id="username"	required value="${teacher.name }">
										</div>
									</div>
									<div class="form-group">
										<label   class="col-xs-2 control-label">教职工号</label>
										<div class="col-xs-10">
											<input type="number" class="form-control  " name="teacher.teacherId" id="teacherId"	required value="${teacher.teacherId }">
											
										</div>
									</div>
									<div class="form-group" >
											<label  class="col-xs-2 control-label">推荐课程</label>
											<div class="col-xs-2">
												<s:radio list="%{#{'false':'不推荐','true':'推荐'} }" name="teacher.recommand"></s:radio>
											</div>
										</div>
									<div>
										<div class="form-group">    
											 <div class="col-xs-6 col-xs-offset-4">
												<button type="submit" class="btn btn-primary  btn-lg ">保存教师</button>																			 
											 </div>
										</div>
									</div>
							</form>
						  </div><!-- panel-body -->
						</div><!-- panel panel-default -->
						<!-- 面板结束 -->
					</div>
					<!--右侧的内容 -->
    		</div>
    		<%@ include file="../buttom.jsp" %>
    	</div>
    	<script src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/messages_zh.js"></script>
    	<script type="text/javascript">
    	$(function(){
			$("#teacher").css("margin-right","-30px").css("font-size"," 25px");
		});
		$(function() {  
			$("#form1").validate({
				rules:{
					'teacher.password': {
						required:true,
						minlength:5
					},
					password2: {
						required:true,
						minlength:5,
						equalTo:"#password"
					}
				}
    			
			});  
		});
    	</script>
    	
  </body>
</html>
