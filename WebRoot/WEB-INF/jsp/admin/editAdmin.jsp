<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<div class="container-fluid">
    <div class="row">
        <form action="${pageContext.request.contextPath }/admin/admin/update" class="form-horizontal" role="form"
              id="edit-admin-form" method="post">
            <div class="form-group">

                <label for="edit-username" class="col-xs-3 control-label">管理员账号</label>

                <div class="col-xs-8">
                    <p class="form-control-static" id="edit-username"></p>
                    <input type="hidden" id="edit-id" name="id" />
                </div>
            </div>


            <div class="form-group">
                <label for="edit-name" class="col-xs-3 control-label">姓名</label>

                <div class="col-xs-8">
                    <input type="text" class="form-control inputxt" name="name" id="edit-name" required>
                </div>
            </div>
            <div class="form-group">
                <label for="edit-position" class="col-xs-3 control-label">职位</label>

                <div class="col-xs-8">
                    <input type="text" class="form-control inputxt" name="position" id="edit-position" required>
                </div>
            </div>

            <div class="form-group">
                <label for="edit-password" class="col-xs-3 control-label">修改密码</label>
                <div class="col-xs-8">
                    <input type="text" class="form-control inputxt" name="password" id="edit-password" value="******" required>
                </div>
            </div>

            <div class="form-group">
                <div class="col-xs-4 col-xs-offset-3">
                    <a type="button" class="btn btn-default btn-lg" data-dismiss="modal" icon="remove">关闭</a>
                </div>

                <div class="col-xs-4 ">
                    <button type="submit" class="btn btn-primary  btn-lg " icon="saved">修改管理员</button>
                </div>

            </div>
        </form>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        $("#edit-admin-form").validate();
    });
</script>


