<%@page import="java.text.DateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>教师详情</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"></link>


<style type="text/css">
.table > thead > tr > th,
.table > tbody > tr > th,
.table > tfoot > tr > th,
.table > thead > tr > td,
.table > tbody > tr > td,
.table > tfoot > tr > td {
  border-top: 0px solid #ddd;
}
</style>

</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="../head.jsp"%>
		</div>
		<div class="row" style="margin-top: 70px">
			<!--左侧的导航条 -->
			<div class="col-xs-2">
				<%@include file="../left.jsp"%>
			</div>
			<!--左侧的导航条 -->

			<!--右侧的内容 -->

			<!-- 班级详情 -->
			<div class="col-xs-10">
				<div class="panel panel-info">
					<div class="panel-heading">
						<h1 class="panel-title" style="font-size: 25px">教师详情</h1>
					</div>
					<div class="panel-body ">
						<div class="row">
							<div class="col-xs-2" style="text-align: center;" >
								
								<img  class="img-thumbnail" src="${pageContext.request.contextPath}/${teacher.pic}"
											height="150px" width="150px"></img>
										<br>
										<h3>${Teacher.name }</h3>
							</div>
							<div class="col-xs-4">
								
								<table class="table info">
									<tr>
										<td >学号:</td>
										<td >${teacher.teacherId }</td>
									</tr>
									<tr>
										<td>邮箱:</td>
										<td>${teacher.email }</td>
									</tr>
									<tr>
										<td>电话号码:</td>
										<td>${teacher }</td>
									</tr>
								</table>
							</div>
							<div class="col-xs-6">
								账户余额：
								<h2>${teacher.account.money }元</h2>
								 <button class="btn btn-warning" data-toggle="modal" data-target="#teacherPay">点此充值</button>
								
							</div>
							
						</div>
						
					
					</div>
				</div>
				<!-- 班级详情 -->
			
			
				<!--学生账户 -->
				<div class="panel panel-info">
					<div class="panel-heading">
						<h1 class="panel-title" style="font-size: 25px">交易记录</h1>
					</div>
					<div class="panel-body">
						<div style="text-align: center;">
							<span class="well">今天是<%=DateFormat.getDateInstance(DateFormat.FULL).format(new Date() ) %></span>
							<hr>
						</div>
						<table class="table table-hover table-striped table-bordered table-condensed">
							<thead>
								<tr class="info">
									<td>记录Id</td>
									<td>记录时间</td>
									<td>班级</td>
									<td>班级类型</td>
									<td>金额</td>
									<td>附加费</td>
									<td>类型</td>
									<td>备注</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${teacher.account.resumes}" var="r">
									<tr >
									<td>${r.id }</td>
									<td><fm:formatDate value="${r.tradeDate}"
												type="both" /></td>
									<td>${r.classes.name }</td>
									<td>${r.classes.classType}</td>
									<td class="money" id="${r.amount}">${r.amount}</td>
									<td>${r.additionAmount}</td>
									<td>${r.type}</td>
									<td>${r.note }</td>
									</tr>
								</c:forEach>
							</tbody>

						</table>
					</div>
				</div><!-- 学生账户 -->

			
				<div class="panel panel-info">
					<div class="panel-heading">
						<h1 class="panel-title" style="font-size: 25px">老师教的班级</h1>
					</div>
					<div class="panel-body">

						<!-- 班级内容 -->
						<div class="tab-content">
							<div role="tabpanel" class="tab-pane active" id="list1">
								<table
									class="table table-hover table-striped table-bordered table-condensed"
									style="margin-top: 10px">
									<thead>
										<tr class="info">
											<td>课程名</td>
											<td>开课时间</td>
											<td>结束时间</td>
											<td>状态</td>
											<td>操作</td>
										</tr>
									</thead>
									<tbody>
										<c:forEach items="${teacher.classes}" var="c">
											<tr>
												<td>${c.name }</td>
												<td><fm:formatDate value="${c.beginDate }"
														pattern="yyyy-MM-dd" /></td>
												<td><fm:formatDate value="${c.endDate }"
														pattern="yyyy-MM-dd" /></td>
												<td><button class="btn label" disabled="disabled">${c.status}</button></td>

												<td><a
													href="${pageContext.request.contextPath}/admin/classes/classesAction!show?id=${c.id }"
													class="btn btn-info" target="_blank"><span
														class=" glyphicon glyphicon-zoom-in"></span>&nbsp;&nbsp;查看</a></td>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
					</div>
					<!-- panel-body -->
				</div>



			</div>
			<!--右侧的内容 -->


		</div>
		</div>
		
		<%@ include file="../buttom.jsp"%>
		<script src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
		<script src="${pageContext.request.contextPath}/js/messages_zh.js"></script>
		<script src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
		<script	src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.js"></script>
		
	<script type="text/javascript">
    		$(function(){
    			$("#teacher").css("margin-right","-30px").css("font-size"," 25px");
    		});

    		$(function(){
    			$(".label").each(function(){
    				var status = $(this).html();
    				if(status == '未开始'){
    					$(this).addClass("btn-success");
    				}else if(status == '已结束'){
    					$(this).addClass("btn-default");
    				}else{
    					$(this).addClass("btn-danger");
    				}
    			});
    			
    			$(".money").each(function(){
    				var m = $(this).attr("id");
    				if(m<0){
    					$(this).addClass("danger");
    				}else{
    					$(this).addClass("success");
    				}
    			});
    			
    			$(".info tr td:even").css("text-align","right");
    			$(".info tr td:odd").css("text-align","left");
    			
    			$("#form2").validate();
    		});
    		
    	</script>

</body>
</html>
