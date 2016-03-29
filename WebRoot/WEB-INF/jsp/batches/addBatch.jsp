<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<style type="text/css">
.quest {
	font-size: medium;
	margin-top: 50px
}

.questArea {
	margin-top: 10px;
	padding-left: 50px
}

.nav-left {
	position: fixed;
	width: 60px;
	height: 60px
}
</style>

	<div class="container-fluid">
		<div class="row">
						<form
							action="${pageContext.request.contextPath }/admin/batches/save"
							class="form-horizontal" role="form" id="add-batch-form" method="post">
							<div class="form-group">
								<label class="col-sm-2 control-label">学期</label>
								<div class="col-sm-3">
                                    <select name="season" class="form-control" id="season">
                                        <c:forEach begin="2015" end="${applicationScope.curYear+1}" step="1" var="s">
                                            <option  value="${s}春">${s}春</option>
                                            <option  value="${s}秋">${s}秋</option>
                                        </c:forEach>
                                    </select>
								</div>
							</div>

							<div class="form-group">
								<label for="name" class="col-sm-2 control-label">批次名</label>
								<div class="col-sm-10" id="item">
									<input type="text" class="form-control" name="name" id="name" required="required">
								</div>
							</div>

							
							<div>

								<div class="form-group">
									<div class="col-sm-4 col-sm-offset-2">
										<button type="submit" class="btn btn-primary  btn-lg ">提交新批次</button>
									</div>
								</div>
							</div>
						</form>

		</div>

	</div>

	<script type="text/javascript">
        $(function(){
            $("#season").change(function(){
                $("#name").val($(this).find("option:selected").val()+" ");
            });
            $("#add-batch-form").validate();
        });
	</script>
