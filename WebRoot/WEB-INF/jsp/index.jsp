<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="header.jspf"%>
<script
	src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/messages_zh.min.js"></script>
<title>教师质量评估系统</title>
</head>

<body
	style="background: url('${pageContext.request.contextPath}/image/adm-login-bg.jpg');">
	<div class="container">
		<div>
			<h1 class="" style="color: white;font-weight: bold;">
				教师质量评估系统<small style="color: white;">Teaching Quality
					Evaluation System</small>
			</h1>

		</div>
		<!--登陆表单 -->
		<div class="row" style="margin-top: 180px; ">
			<div class="row">
				<div class="col-sm-4 col-sm-offset-4">
					<div class="panel panel-primary" style="background:white"
						data-toggle="tooltip" data-placement="top">
						<div class="panel-heading">
							<h2 class="panel-title">系统登录</h2>
						</div>
						<div class="panel-body">
							<!--表单 -->
							<form action="${pageContext.request.contextPath}/login"
								class="form-horizontal" role="form" id="form1" name="form1"
								style="margin-top: 20px" method="post">
								<div class="form-group" align="center">
									<label for="inputEmail3" class="col-sm-3 control-label">用户名:</label>
									<div class="col-sm-9">
										<input type="text" class="form-control" id="username" required="required"
											name="username" placeholder="请输入学号/教师号/Id">
									</div>
								</div>
								<div class="form-group" align="center">
									<label for="inputPassword3" class="col-sm-3 control-label">密码:</label>
									<div class="col-sm-9">
										<input type="password" class="form-control" name="password" required="required"
											id="password">
									</div>
								</div>
								<div class="form-group" align="center">
									<label for="" class="col-sm-3 control-label">验证码</label>
									<div class="col-sm-5">
										<input type="text" class="form-control" name="valifCode"
											id="valifCode" required="required">
									</div>
									<div class="col-sm-3">
										<img alt="验证码" class="control-label"
											src="${pageContext.request.contextPath}/valifImage">
									</div>
								</div>

								<div class="form-group">
									<div class="col-sm-4 col-sm-offset-2">
										<div class="radio">
											<label> <input type="radio" name="type" required="required"
												id="" value="student" checked="checked"><span>学生登录</span>
											</label>
										</div>
										<div class="radio">
											<label> <input type="radio" name="type"
												id="" value="teacher" ><span>教师登录</span>
											</label>
										</div>
										<div class="radio">
											<label> <input type="radio" name="type"
												id="" value="admin"><span>管理员登录</span>
											</label>
										</div>
										
									</div>
									<div class="col-sm-6">
										<button type="submit" class="btn btn-warning" id="login-btn">学生登录</button>
									</div>
								</div>

								<div class="form-group" id="e2">
									<div class="col-sm-8 col-sm-offset-2">
										<div class="alert alert-danger alert-dismissible" role="alert">
											<button type="button" class="close" data-dismiss="alert">
												<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
											</button>
											<span id="e1">${error}</span>
										</div>
									</div>
								</div>
							</form>
							<!--登陆表单 -->
						</div>
					</div>

				</div>
			</div>
		</div>

	</div>
	<!--     	container -->
	<script type="text/javascript">
		$(function() {
			$("#form1").validate({
				rules : {
					username : {
						required : true
					},
					password : {
						required : true,
						minlength : 3
					}

				},
				messages : {
					username : "请输入用户名",
					password : {
						required : "请输入密码",
						minlength : "长度需要大于3"
					}
				}
			});
		});
		$(function() {
			var p = $("#e1").text();
			if (p.length == 0) {
				$("#e2").remove();
			}
		});
		$(function() {
			$("input:radio").click(function(){
				$("#login-btn").html($("input:radio:checked").next().text());
			})
		});
	</script>

</body>
</html>