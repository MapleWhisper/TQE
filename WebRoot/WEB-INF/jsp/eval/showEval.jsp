<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>评教指标显示|${evalTable.title}</title>
<style type="text/css">
		.quest {font-size: medium;margin-top: 20px}
		.questArea {margin-top: 10px;padding-left: 50px}
		.nav-left {position: fixed;width: 60px;height: 60px}
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
				<div style="text-align: center;margin-top: 100px">
					<h2>${evalTable.title }</h2>
					<h3>${stuTable.course.name }</h3>
				</div>
				<div class="row">
					<!--左侧的导航条 -->
					<div class="col-xs-1">
						<ul class="nav nav-pills nav-stacked nav-left" role="tablist"
							id="nav">
							<li role="presentation" class="pre active"><a href="#part1">评教须知</a></li>
							<li role="presentation" class="pre"><a href="#part2">表单</a></li>
							<li role="presentation" class="pre"><a href="#part3">表项</a></li>
						</ul>
					</div>
					<!--左侧的导航条 -->
					
					<div class="col-xs-11">
					<hr>
						<table class="table table-hover table-striped table-bordered table-condensed">
								<tr class="warning">
									<td>学院</td>
									<td>学期</td>
									<td>批次</td>
									<td>开始日期</td>
									<td>截止日期</td>
								</tr>
									<tr>
										<td>${stuTable.course.department }</td>
										<td>${stuTable.course.season }</td>
										<td>${stuTable.batches.name }</td>
										<td><fm:formatDate value="${stuTable.batches.beginDate}" pattern="yyyy-MM-dd"/></td>
										<td><fm:formatDate value="${stuTable.batches.endDate }" pattern="yyyy-MM-dd"/></td>
									</tr>
						</table>
						<hr>
							<!--评教须知: -->
							<div class="panel panel-primary" id="part1">
								<div class="panel-heading">
									<h3 class="panel-title">评教须知:</h3>
								</div>
								<div class="panel-body">${evalTable.note}</div>
							</div>

							<div class="panel panel-primary" id="part2">
								<div class="panel-heading">
									<h3 class="panel-title">请如实填写表单信息</h3>
								</div>
								<div class="panel-body">
									<table class="table table-striped table-hover table-bordered">

										<c:forEach items="${evalTable.itemList}" var="item"
											varStatus="s" >
											<tr>
												<td style="width: 100px;">${item.context}：</td>
												<td><input type="text" class="form-control" name="itemList[${s.index}].ans" required="required" disabled="disabled"
												      value="${item.ans }"></td>
											</tr>

										</c:forEach>
									</table>
								</div>
							</div>


							<!-- 打分表和评价 -->
							<div class="panel panel-primary" id="part3">
								<div class="panel-heading">
									<h3 class="panel-title">打分表和评价</h3>
								</div>
								<div class="panel-body">
									<table class="table table-striped table-hover table-bordered">
										<tr>
											<td>序号</td>
											<td>评价项目</td>
											<td>评价等级及参考分数(A B C D)</td>
											<td>得分</td>
										</tr>
										<c:forEach items="${evalTable.tableItemList}" var="item"
											varStatus="s">
											<tr>
												<td>${s.count}</td>
												<td>${item.context}</td>
												<td>${item.level}</td>
												<td><input type="number" class="form-control score" disabled="disabled"
													required="required"  name="tableItemList[${s.index}].ans" min="0" max="100" value="${item.ans}"></td>
											</tr>

										</c:forEach>
										<tr class="warning">
											<td>评价级别</td>
											<td >
												<div class="progress">
													<div
														class="progress-bar progress-bar-danger progress-bar-striped active" 
														role="progressbar" aria-valuenow="20" aria-valuemin="0"
														aria-valuemax="100" style="min-width:10%;width:0%" id="level">
														${evalTable.score}分
													</div>
												</div>
											</td>
											<td>总分</td>
											<td id="sum">${evalTable.score }分  </td>
										</tr>
									</table>
									<c:forEach items="${evalTable.questionList}" var="que"
										varStatus="s">

										<div class="quest">
											<code>${s.count}.</code>
											${que.context }
										</div>
										<div class="questArea">
											<textarea class="form-control" name="questionList[${s.index}].ans" required="required" disabled="disabled">${que.ans}</textarea>
										</div>
									</c:forEach>
									
									<div style="text-align: center;margin-top: 100px;">
											<a class="btn btn-info btn-lg btn-block" onclick="history.go(-1);"
												type="submit" value="提交评价" > 《《返回</a>
										
									</div>
									
								</div>

							</div>

					</div>
				</div>
				<!-- 第一行结束 -->
			</div>
		</div>

	</div>
	<script
	src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
<script
	src="${pageContext.request.contextPath}/js/messages_zh.min.js"></script>
	<script type="text/javascript">
		$(function() {
				var sum = 0;
				$.each($(".score"), function() {
					sum += parseInt($(this).val());
				});
				var lev;
				if(sum<60){
					$("#level").removeClass("progress-bar-warning")
					.removeClass("progress-bar-info")
					.removeClass("progress-bar-success")
					.addClass("progress-bar-danger");
					lev = '差';
				}else if(sum<75){
					$("#level").removeClass("progress-bar-danger")
					.removeClass("progress-bar-info")
					.removeClass("progress-bar-success")
					.addClass("progress-bar-warning");
					lev = '一般';
					
				}else if(sum<90){
					$("#level").removeClass("progress-bar-danger")
					.removeClass("progress-bar-warning")
					.removeClass("progress-bar-success")
					.addClass("progress-bar-info");
					lev = '良好';
					
				}else{
					$("#level").removeClass("progress-bar-warning")
					.removeClass("progress-bar-info")
					.removeClass("progress-bar-danger")
					.addClass("progress-bar-success");
					lev = '优秀';
					
				}
				$("#level").css("width",sum+"%");
				$("#level").html(sum+"分   "+lev);
				$("#sum").html(sum+"分   "+lev);
		});
		
		$(function() {
			$("#form").validate();
		});
	</script>
	<%@ include file="../buttom.jsp"%>
</body>
</html>
