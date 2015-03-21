<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
  <head>
	<%@ include file="../header.jspf" %>
	<title>添加教师</title>
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
						    <h1 class="panel-title" style="font-size: 25px">添加教师  Add Teacher</h1>
						  </div>
						  <div class="panel-body">
						  	 <form action="${pageContext.request.contextPath }/admin/teacher/teacherAction!save" class="form-horizontal" role="form" id="form1" method="post">
									<div class="form-group">
										<label   class="col-xs-2 control-label">教师邮箱</label>
										<div class="col-xs-10">
											<input type="email" class="form-control " name="teacher.email" id="email" required  >
										</div>
									</div>
									
									
									<div class="form-group">
										<label   class="col-xs-2 control-label">输入密码</label>
										<div class="col-xs-10">
											<input type="password" class="form-control  " name="teacher.password" id="password" required>
										</div>
									</div>
									<div class="form-group">
										<label   class="col-xs-2 control-label">确认输入密码</label>
										<div class="col-xs-10">
											<input type="password" class="form-control  " name="password2" id="password2" required>
										</div>
									</div>
									<div class="form-group">
										<label   class="col-xs-2 control-label">姓名</label>
										<div class="col-xs-10">
											<input type="text" class="form-control  " name="teacher.name" id="username"	required>
										</div>
									</div>
									<div class="form-group">
										<label   class="col-xs-2 control-label">教职工号</label>
										<div class="col-xs-10">
											<input type="number" class="form-control  " name="teacher.teacherId" id="teacherId"	required>
											
										</div>
									</div>
									<div class="form-group" >
											<label  class="col-xs-2 control-label">推荐课程</label>
											<div class="col-xs-2">
												<input type="radio" name="teacher.recommand" value="false" checked="checked">不推荐<br>
												
												<input type="radio" name="teacher.recommand" value="true">推荐
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
    	<script src="${pageContext.request.contextPath}/js/jquery.metadata.js"></script>
    	
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
