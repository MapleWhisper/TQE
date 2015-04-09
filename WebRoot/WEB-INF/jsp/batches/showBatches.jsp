<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>评教批次详情|${batches.name}</title>
<style type="text/css">

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
					<h2>${batches.name}</h2>
				</div>
				<div class="row">
							<div class="panel panel-primary" id="part1">
								<div class="panel-heading">
									<h3 class="panel-title">详细信息</h3>
								</div>
								<div class="panel-body">
									<input type="hidden" value="${batches.id}" id="id"/>
									<table class="table table-striped table-hover table-bordered">
										<tr>
											<td>评教批次名</td>
											<td>学期</td>
											<td>评教开始日期</td>
											<td>评教截止日期</td>
											<td>操作</td>
										</tr>
										<tr>
											
											<td>${batches.name}</td>
											<td>${batches.season}</td>
											
											<td class="new"><fm:formatDate value="${batches.beginDate}" pattern="yyyy-MM-dd"/></td>
											<td class="new"><fm:formatDate value="${batches.endDate}" pattern="yyyy-MM-dd"/></td>
											
											<td class="old"><input type="date" class="form-control" value="<fm:formatDate value="${batches.beginDate}" pattern="yyyy-MM-dd"/>" id="beginDate" onClick="WdatePicker()"></td>
											
											<td class="old"><input type="date" class="form-control" value="<fm:formatDate value="${batches.endDate}" pattern="yyyy-MM-dd"/>" id="endDate" onClick="WdatePicker()"></td>
											
											<td class="new"><button class="btn btn-warning" id="edit">修改</button></td>
											<td class="old"><button class="btn btn-warning" id="save">保存</button></td>
										</tr>
									</table>
									<table class="table table-striped table-hover table-bordered">
										<tr>
											<td>默认学生评估表</td>
											<td>默认教师评估表</td>
											<td>默认领导评估表</td>
										</tr>
										<tr>
											
											<td class="new"><a href="${pageContext.request.contextPath}/admin/evalTable/show/${batches.stuEvalId}">${batches.stuEval.title}</a></td>
											<td class="old"><a class="btn btn-info" href="${pageContext.request.contextPath}/admin/evalTable?action=stuEval&bid=${batches.id}">点此更换默认<Strong>学生</Strong>评教指标表</a></td>
											
											<td class="new"><a href="${pageContext.request.contextPath}/admin/evalTable/show/${batches.teaEvalId}">${batches.teaEval.title}</a></td>
											<td class="old"><a class="btn btn-info" href="${pageContext.request.contextPath}/admin/evalTable?action=teaEval&bid=${batches.id}">点此更换默认<Strong>教师</Strong>评教指标表</a></td>
											
											<td class="new"><a href="${pageContext.request.contextPath}/admin/evalTable/show/${batches.leadEvalId}">${batches.leadEval.title}</a></td>
											<td class="old"><a class="btn btn-info" href="${pageContext.request.contextPath}/admin/evalTable?action=leadEval&bid=${batches.id}">点此更换默认<Strong>领导</Strong>评教指标表</a></td>
										</tr>
									</table>
								</div>
							</div>
							
							<div class="panel panel-primary" id="part1">
								<div class="panel-heading">
									<h3 class="panel-title">学生评教</h3>
								</div>
								<div class="panel-body">${evalTable.note }</div>
							</div>

							<div class="panel panel-primary" id="part2">
								<div class="panel-heading">
									<h3 class="panel-title">教师评教</h3>
								</div>
								<div class="panel-body">
								</div>
							</div>
							
							<div class="panel panel-primary" id="part2">
								<div class="panel-heading">
									<h3 class="panel-title">领教评教</h3>
								</div>
								<div class="panel-body">
								</div>
							</div>


				</div>
				<!-- 第一行结束 -->
			</div>
		</div>

	</div>
	<script type="text/javascript">
		$(function(){
			$(".old").css("display","none");
			
			$("#edit").click(function(){
				$(".new").css("display","none");
				$(".old").css("display","");
			});
			$("#save").click(function(){
				$(".new").css("display","");
				$(".old").css("display","none");
				var beginDate = $("#beginDate").val();
				var endDate = $("#endDate").val();
				if(new Date(beginDate)>=new Date(endDate)){
					alert("起始日期必须大于截止日期");
					return false;
				}
				var id = $("#id").val();
				$.post("../update",{beginDate:beginDate,endDate:endDate,id:id},function(data){
						alert(data);
						window.location.reload();
				});
				
			});
		});
	</script>
	<%@ include file="../buttom.jsp"%>
	<script  type="text/javascript" src="${pageContext.request.contextPath}/js/My97DatePicker/WdatePicker.js"></script>
</body>
</html>
