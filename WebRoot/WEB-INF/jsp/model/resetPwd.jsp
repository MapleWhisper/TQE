<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<script
	src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
</head>

<body>
	<div class="container-fluid">
		<div class="row" >

				<div class="panel panel-default" >
					<div class="panel-heading">修改密码</div>
					<div class="panel-body">
						<!-- 					  	修改密码 -->
						<form action="${pageContext.request.contextPath}/admin/resetPwd"
							class="form-horizontal" role="form" id="form1" method="post">
							<div class="form-group">
								<label class="col-xs-3 control-label">原密码</label>
								<div class="col-xs-8">
									<input type="password" name="oldPwd" id="oldPwd"
										class="form-control"/>
									<input type="hidden" name="id"
										value="${sessionScope.admin.id}${sessionScope.teacher.id}${sessionScope.student.sid}"/>

								</div>
                                
							</div>
							<div class="form-group">
								<label class="col-xs-3 control-label">新密码</label>
								<div class="col-xs-8">
									<input type="text" name="pwd" id="pwd" class="form-control"/>
								</div>
							</div>
							<div class="form-group">
								<label class="col-xs-3 control-label">确认新密码</label>
								<div class="col-xs-8">
									<input type="text" name="pwd2" id="pwd2" class="form-control"/>
								</div>
							</div>

							<div class="form-group">
								<div class="col-xs-offset-2 col-xs-4">
									<button type="submit" class="btn btn-primary btn-lg">提交修改</button>
								</div>
                                <div class="col-xs-4 ">
                                    <a type="button"  class="btn btn-default btn-lg model-close" data-dismiss="modal" icon="remove">关闭</a>
                                </div>
							</div>


						</form>
						<!-- 						修改密码 -->

					</div>
				</div>


		</div>

	</div>
	<script type="text/javascript">
    	$(function() {  
			$("#form1").validate({
                submitHandler: function() {
                    var $form = $("#form1");
                    $.post($form.attr("action"),$form.serialize(),function(data){
                        alert(data.message);

                        if(data.success){
                            $(".model-close").click();
                        }

                    });
                    return false;
                },
				rules:{
					oldPwd: {
						required:true
					},
					pwd: {
						required:true,
						minlength:2
					},
					pwd2: {
						required:true,
						equalTo:"#pwd"
					}
					
				},
				messages:{
					oldPwd: {
						required:"请输入原密码"
					},
					pwd: {
						required:"请输入密码"
					},
					pwd2: {
						required:"请输入确认密码",
						equalTo:"两次密码必须相同"
					}
				
				}
			});  
		});

    	</script>
</body>
</html>
