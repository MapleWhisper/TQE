<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="header.jspf"%>
<script
	src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/messages_zh.min.js"></script>
	<script
			src="${pageContext.request.contextPath}/js/jquery.cookie.js"></script>
<title>教师质量评估系统</title>
</head>

<body
	style="background: url('${pageContext.request.contextPath}/image/adm-login-bg.jpg'); background-size: cover">
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
				<div class="col-sm-5 col-sm-offset-4">
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
									<label for="username" class="col-sm-3 control-label">用户名:</label>
									<div class="col-sm-6">
										<input type="text" class="form-control" id="username" required="required"
											name="username"  placeholder="请输入学号/教师号/Id">
									</div>
								</div>
								<div class="form-group" align="center">
									<label for="password" class="col-sm-3 control-label">密码:</label>
									<div class="col-sm-6">
										<input type="password" class="form-control" name="password" required="required"
											id="password">
									</div>
									<div class="col-sm-3">
											<%--<span style="top: 7px" class="glyphicon glyphicon-question-sign"--%>
												  <%--aria-hidden="true" data-toggle="tooltip" data-placement="top"--%>
												  <%--title="初始密码默认为身份证后8位" ></span>--%>
                                            <a href="#" id="init-pwd" class="btn btn-sm btn-default">初始密码？</a>
									</div>
								</div>
                                <c:if test="${ not sessionScope.skipVerify }">
                                    <div class="form-group" align="center">
                                        <label for="verificationCode" class="col-sm-3 control-label">验证码</label>
                                        <div class="col-sm-5">
                                            <input type="text" class="form-control" name="verificationCode"
                                                id="verificationCode" required="required">
                                        </div>
                                        <div class="col-sm-3">
                                            <img alt="验证码" class="control-label"
                                                src="${pageContext.request.contextPath}/valifImage">
                                        </div>
                                    </div>
                                </c:if>

								<div class="form-group">
									<div class="col-sm-4 col-sm-offset-2">
										<div class="radio">
											<label> <input type="radio" name="type" required="required"
												 value="student" checked="checked"><span>学生登录</span>
											</label>
										</div>
										<div class="radio">
											<label> <input type="radio" name="type"
												 value="teacher" ><span>教师登录</span>
											</label>
										</div>
										<div class="radio">
											<label> <input type="radio" name="type"
												 value="admin"><span>管理员登录</span>
											</label>
										</div>
										<div class="radio">
											<label> <input type="radio" name="type"
												 value="other"><span>其他</span>
											</label>
										</div>
										
									</div>
									<div class="col-sm-6">
										<button type="submit" class="btn btn-warning" id="login-btn">学生登录</button>
									</div>
								</div>

								<div class="form-group error-message-container">
									<div class="col-sm-8 col-sm-offset-2">
										<div class="alert alert-danger alert-dismissible" role="alert">
											<button type="button" class="close" data-dismiss="alert">
												<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
											</button>
											<span class="error-message">${error}</span>
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
			//移除空的错误提示框
			showErrorMessage();

			//根据登录方式显示不同的按钮文字
			$("input:radio").click(function(){
				$("#login-btn").html($("input:radio:checked").next().text());
			});

			//从cookie中获取用户的登录方式
			var loginType = $.cookie("loginType");
			if(loginType){
				$("input[value='"+loginType+"']").attr("checked","checked");
				$("#login-btn").html($("input:radio:checked").next().text());
			}

            $("#init-pwd").click(function(e){
                e.preventDefault();
                alert("初始密码默认为身份证后8位\n如果身份证信息不完整，默认为学号\n进入系统后请及时修改密码！");
            });

		});

		$(function () {
			$('[data-toggle="tooltip"]').tooltip();
		})

	</script>

</body>
</html>
