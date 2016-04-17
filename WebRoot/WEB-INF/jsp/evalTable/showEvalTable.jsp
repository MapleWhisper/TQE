<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>评教指标显示|${evalTable.title}</title>
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

<body >
	<div class="container-fluid">
		<%@ include file="../head.jsp"%>
		<div class="row" style="margin-top: 70px">
			<div class="col-sm-2">
				<%@ include file="../left.jsp"%>
			</div>
			<div class="col-sm-10">
				<div style="text-align: center;margin-top: 100px">
					<h2>${evalTable.title }</h2>
				</div>
				<div class="row">

					<!--左侧的导航条 -->
					<div class="col-xs-12"  >
						
						<form
							action="${pageContext.request.contextPath}/admin/paper/answer"
							method="post">
							<input type="hidden" name="id" value="${evalTable.id}">

                            <div class=" bs-callout bs-callout-danger" id="part1">

                                <h4>评教须知:</h4>

                                <p>${evalTable.note }</p>
                            </div>

                            <div class=" bs-callout bs-callout-info" id="part2">
                                <h4>请如实填写表单信息:</h4>
                                <table class="table table-striped table-hover ">
                                    <c:forEach items="${evalTable.itemList}" var="item"
                                               varStatus="s">
                                        <tr>
                                            <td style="width: 10%;"><p class="">${item.context}：<p></td>
                                            <td ><input type="text" class="form-control"/></td>
                                        </tr>
                                    </c:forEach>
                                </table>
                            </div>




							<!-- 问答题 -->
							<div class="bs-callout bs-callout-info" id="part3">
									<h4 class="panel-title">打分表和评价</h4>
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
												<td>
													${item.level}
												
												</td>
												<td>
													<select type="number" class="form-control score" required="required">
														<c:forTokens items="${item.level}" delims=" " var="num">
															<option value="${num}">${num}</option>
														</c:forTokens>
													</select>	
												</td>
											</tr>

										</c:forEach>
										<tr class="warning">
											<td>评价级别</td>
											<td >
												<div class="progress">
													<div
														class="progress-bar progress-bar-danger progress-bar-striped active" 
														role="progressbar" aria-valuenow="20" aria-valuemin="0"
														aria-valuemax="100" style="min-width:10%;width: 0%" id="level">
														0分
													</div>
												</div>
											</td>
											<td>总分</td>
											<td id="sum">0分</td>
										</tr>
									</table>
									<c:forEach items="${evalTable.questionList}" var="que"
										varStatus="s">

										<div class="quest">
											<code>${s.count}.</code>
											${que.context }
										</div>
										<div class="questArea">
											<textarea class="form-control"></textarea>
										</div>
									</c:forEach>


							</div>

						</form>
					</div>
				</div>
				<!-- 第一行结束 -->
			</div>
		</div>

	</div>
	<script type="text/javascript">
		$(function() {


			$(".score").bind('change click ready',function() {
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
		});
	</script>
	<%@ include file="../buttom.jsp"%>
</body>
</html>
