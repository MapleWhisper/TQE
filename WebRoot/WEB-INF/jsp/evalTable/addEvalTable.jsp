<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>创建新指标</title>
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
    <script
            src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/js/messages_zh.min.js"></script>
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
					<div class="panel-heading">创建新的评教指标</div>

					<div class="panel-body">
						<form
							action="${pageContext.request.contextPath }/admin/evalTable/save"
							class="form-horizontal" role="form" id="add-evalTable-form" method="post">

                            <div class="form-group">
                                <label for="type" class="col-xs-2 control-label">评教指标类型</label>
                                <div class="col-xs-3">
                                    <select type="text" class="form-control" name="type" required="required"
                                           id="type">
                                        <option value="学生评教师">学生评教师</option>
                                        <option value="教师评教师">教师评教师</option>
                                        <option value="教师评学生">教师评学生</option>
                                        <option value="领导评教师">领导评教师</option>
                                    </select>
                                </div>
                            </div>

							<div class="form-group">

								<label for="title" class="col-xs-2 control-label">评教指标标题</label>
								<div class="col-xs-10">
									<input type="text" class="form-control inputxt" name="title" required="required"
										id="title">
								</div>
							</div>
							
							<div class="form-group">
								<label for="note" class="col-xs-2 control-label">评教须知</label>
								<div class="col-xs-10">
									<textarea rows="5" cols="100%" class="form-control" name="note" id="note" required="required"></textarea>
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-xs-2 control-label">输入表头项</label>
								<div class="col-xs-10" id="item">
									<div class="col-xs-4 item" style="margin-top: 5px;">
										<input type="text" class="form-control inputxt" required="required"
											name="itemList[0].context">
									</div>
									<div class="col-xs-4">
										<button class="btn btn-primary" id="addItem">
											<span class="glyphicon glyphicon-plus-sign"
												aria-hidden="true">添加新表头项</span>
										</button>
									</div>

								</div>

							</div>

							

							<div class="form-group">
								<label class="col-xs-2 control-label">评教表单项</label>
								<div class="col-xs-10" id="tableItem">
									<div class="col-xs-6">
										<div class="alert alert-info" role="alert">请在下面 ↓ ↓ ↓ 输入评教表格的信息</div>
									</div>
									<div class="col-xs-6">
										<div class="alert alert-success" role="alert">
											请在下面  ↓ ↓ ↓ 输入评教等级或评分<br>
											<code>不同等级评分之间使用 空格 或者 逗号 隔开</code>
											<br>例如：<code>10 8 5 2</code> 或者 <code>10,8,5,2</code>
										</div>

									</div>
									<div class="tableItem row" style="padding-top: 20px;">

										<div class="col-xs-6 context">
											<input type="text" class="form-control inputxt" required="required"
												name="tableItemList[0].context">
										</div>
										<div class="col-xs-6 level">
											<input type="text" class="form-control inputxt"	required="required"
												name="tableItemList[0].level">
										</div>
									</div>

									<div class="col-xs-3">
										<button class="btn btn-primary" id="addTableItem">
											<span class="glyphicon glyphicon-plus-sign"
												aria-hidden="true">添加表单新项</span>
										</button>
									</div>

								</div>
							</div>

							<div class="form-group">
								<label class="col-xs-2 control-label">问题和建议</label>
								<div class="col-xs-10" id="question">
									<div class="col-xs-12 question" style="margin-top: 5px;">
										<input type="text" class="form-control inputxt" required="required"
											name="questionList[0].context">
									</div>
									<div class="col-xs-3">
										<button class="btn btn-primary" id="addQuestion">
											<span class="glyphicon glyphicon-plus-sign"
												aria-hidden="true"> 添加问题和建议项</span>

										</button>
									</div>

								</div>

							</div>

							<div>

								<div class="form-group">
									<div class="col-xs-6 col-xs-offset-4">
										<button type="submit" class="btn btn-primary  btn-lg ">提交新指标</button>
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
		$("#addItem").click(function(){
            var $item = $("#item");
			var cnt = $item.find(".item").size();
			var contextName = "itemList["+cnt+"].context";
			var item = $item.find(".item:last").clone();
			$(item).children("input").attr("name",contextName);
            $item.find(".item:last").after(item);
			return false;
		});
		$("#addQuestion").click(function(){
            var $question = $("#question");
			var cnt = $question.find(".question").size();
			var contextName = "questionList["+cnt+"].context";
			var item = $question.find(".question:last").clone();
			$(item).children("input").attr("name",contextName);
            $question.find(".question:last").after(item);
			return false;
		});
		$("#addTableItem").click(function(){
            var $tableItem = $("#tableItem");
			var cnt = $tableItem.find(".tableItem").size();
			var contextName = "tableItemList["+cnt+"].context";
			var levelName = "tableItemList["+cnt+"].level";
			var item = $tableItem.find(".tableItem:last").clone();
			$(item).children(".context").children("input").attr("name",contextName);
			$(item).children(".level").children("input").attr("name",levelName);
            $tableItem.find(".tableItem:last").after(item);
			return false;
		});
        $("#add-evalTable-form").validate();
		
	});
		
	</script>
	<%@ include file="../buttom.jsp"%>
</body>
</html>
