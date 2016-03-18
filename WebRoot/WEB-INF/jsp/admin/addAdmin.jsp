<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<div class="container-fluid">
    <div class="row">
        <form action="${pageContext.request.contextPath }/admin/admin/save" class="form-horizontal" role="form"
              id="add-admin-form" method="post">
            <div class="form-group">

                <label for="username" class="col-xs-3 control-label">管理员账号</label>

                <div class="col-xs-8">
                    <input type="text" class="form-control inputxt" name="username" id="username" required>
                </div>
            </div>

            <div class="form-group">
                <label for="password" class="col-xs-3 control-label">输入密码</label>

                <div class="col-xs-8">
                    <input type="text" class="form-control inputxt" name="password" id="password" required>
                </div>
            </div>
            <div class="form-group">
                <label for="rePassword" class="col-xs-3 control-label">确认输入密码</label>

                <div class="col-xs-8">
                    <input type="text" class="form-control inputxt" id="rePassword" equalTo="#password" required>
                </div>
            </div>
            <div class="form-group">
                <label for="name" class="col-xs-3 control-label">姓名</label>

                <div class="col-xs-8">
                    <input type="text" class="form-control inputxt" name="name" id="name" required>
                </div>
            </div>
            <div class="form-group">
                <label for="position" class="col-xs-3 control-label">职位</label>

                <div class="col-xs-8">
                    <input type="text" class="form-control inputxt" name="position" id="position" required>
                </div>
            </div>


            <div class="form-group">
                <div class="col-xs-4 col-xs-offset-3">
                    <button type="button" class="btn btn-default btn-lg" data-dismiss="modal">关闭</button>
                </div>

                <div class="col-xs-4 ">
                    <button type="submit" class="btn btn-primary  btn-lg ">添加管理员</button>
                </div>

            </div>
        </form>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        $("#add-admin-form").validate({
            rules:{
                password:"required",
                rePassword:{
                    required:true,
                    equalTo:"#password"
                }
            }
        });
    });
</script>
						

