<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" pageEncoding="UTF-8" %>

<style type="text/css">

</style>

<div class="container-fluid">
    <div class="row">
        <form action="${pageContext.request.contextPath }/admin/batches/save"
              class="form-horizontal" role="form" id="add-batch-form" method="post">
            <div class="form-group">
                <label class="col-sm-2 control-label">学期</label>

                <div class="col-sm-5">
                    <select name="season" class="form-control" id="season">
                        <option value="${applicationScope.curSeason}">当前学期:${applicationScope.curSeason}</option>
                        <c:forEach begin="2015" end="${applicationScope.curYear+1}" step="1" var="s">
                            <option value="${s}春">${s}春</option>
                            <option value="${s}秋">${s}秋</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">批次名</label>

                <div class="col-sm-10" id="item">
                    <input type="text" class="form-control" name="name" id="name" required="required"
                           value="${applicationScope.curSeason} ">
                </div>
            </div>


            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">开始时间</label>
                <div class="col-sm-5" >
                    <input type="text" class="form-control" name="beginDate" id="beginDate" required="required"
                            onClick="WdatePicker()">
                </div>
            </div>

            <div class="form-group">
                <label for="name" class="col-sm-2 control-label">结束时间</label>
                <div class="col-sm-5" >
                    <input type="text" class="form-control" name="endDate" id="endDate" required="required"
                           onClick="WdatePicker()">
                </div>
            </div>

            <div class="form-group">
                <label class="col-sm-2 control-label">说明</label>
                <div class="col-sm-10">
                    <p class="form-control-static">批次一旦创建后 批次名不可修改 不可删除 请仔细核对信息</p>
                </div>
            </div>

            <div>
                <div class="form-group">

                    <div class="col-sm-4 col-sm-offset-2">
                        <button type="submit" class="btn btn-primary  btn-lg ">提交新批次</button>
                    </div>
                    <div class="col-sm-4">
                        <button type="button" class="btn btn-default btn-lg" data-dismiss="modal">关闭</button>
                    </div>
                </div>
            </div>
        </form>

    </div>

</div>

<script type="text/javascript">
    $(function () {
        $("#season").change(function () {
            $("#name").val($(this).find("option:selected").val() + " ");
        });
        $("#add-batch-form").validate();
    });
</script>
