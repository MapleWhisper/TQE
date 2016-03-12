<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>创建新批次</title>
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
</head>

<body>
	<div class="container-fluid">
		<%@ include file="../head.jsp"%>
		<div class="row" style="margin-top: 70px">
			<div class="col-sm-2">
				<%@ include file="../left.jsp"%>
			</div>
			<div class="col-sm-10">
				<div class="panel panel-primary">
					<div class="panel-heading">添加评教批次</div>

					<div class="panel-body">
						<form
							action="${pageContext.request.contextPath }/admin/batches/save"
							class="form-horizontal" role="form" id="form" method="post">
							<div class="form-group">
								<label class="col-sm-1 control-label">学期</label>
								<div class="col-sm-2">
								<select name="season" class="form-control" id="season">
									<c:forEach begin="2015" end="2050" step="1" var="s">
										<option  value="${s}春">${s}春</option>
										<option  value="${s}秋">${s}秋</option>
									</c:forEach>
								</select>
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-1 control-label">批次名</label>
								<div class="col-sm-6" id="item">
									<input type="text" class="form-control inputxt" name="name" id="name">
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
					<!-- panel body end -->
				</div>
				<!-- panel end -->
			</div>
			<!-- col-sm-10 -->

		</div>

	</div>
	<script type="text/javascript">
	$(function(){
		$("#season").change(function(){
			$("#name").val($("option:selected").val());
		});
	});
		
	</script>
	<%@ include file="../buttom.jsp"%>
</body>
</html>
