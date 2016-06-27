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
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"/>
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
					<div class="panel-heading">修改评教指标</div>

					<div class="panel-body">
						<form
							action="${pageContext.request.contextPath }/admin/evalTable/update"
							class="form-horizontal" role="form" id="form" method="post">
							<input value="${evalTable.id }" type="hidden" name="eid">

                            <div class="form-group">
                                <label for="type" class="col-xs-2 control-label">评教指标类型</label>
                                <div class="col-xs-3">
                                    <select class="form-control auto-select" name="type" required="required"
                                            id="type" key="${evalTable.type}" >
                                        <option value="学生评教师">学生评教师</option>
                                        <option value="教师评教师">教师评教师</option>
                                        <option value="教师评学生">教师评学生</option>
                                        <option value="领导评教师">领导评教师</option>
                                    </select>
                                </div>
                            </div>

							<div class="form-group">

								<label class="col-xs-2 control-label">评教指标标题</label>
								<div class="col-xs-10">
									<input type="text" class="form-control" name="title"
										required="required" id="username" value="${evalTable.title}">
								</div>
							</div>

							<div class="form-group">
								<label class="col-xs-2 control-label">评教须知</label>
								<div class="col-xs-8">
									<textarea rows="5" cols="100%" name="note" class="form-control"
										required="required" id="note">${evalTable.note}</textarea>
								</div>
                                <div class="col-xs-2">

                                    <a class="btn btn-primary" id="note-template-btn">插入须知模板</a>
                                </div>
							</div>

							<div class="form-group">
								<label class="col-xs-2 control-label">输入表头项</label>
								<div class="col-xs-10" id="item">
									<c:forEach items="${evalTable.itemList}" var="item"
										varStatus="s">
										<div class="col-xs-4 item" style="margin-top: 5px;">
											<input type="text" class="form-control inputxt"
												required="required" name="itemList[${s.index }].context"
												value="${item.context}">
										</div>

									</c:forEach>

									<div class="col-xs-4">
										<button class="btn btn-primary" id="addItem">
											<span class="glyphicon glyphicon-plus-sign"
												aria-hidden="true">添加新表头项</span>
										</button>
									</div>
									<div class="col-xs-4">
										<button class="btn btn-warning" id="removeItem">
											<span class="glyphicon glyphicon-remove-sign"
												aria-hidden="true">删除最后一个</span>
										</button>

									</div>

								</div>

							</div>



							<div class="form-group">
								<label class="col-xs-2 control-label">评教表单项</label>
								<div class="col-xs-10" id="tableItem">
									<div class="col-xs-6">
										<div class="alert alert-info" role="alert">请在下面 ↓ ↓ ↓
											输入评教表格的信息</div>
									</div>
									<div class="col-xs-6">
										<div class="alert alert-success" role="alert">
											请在下面 ↓ ↓ ↓ 输入评教等级或评分<br>
											<code>不同等级评分之间使用 空格 逗号 隔开</code>
											<br>例如：
											<code>10 8 5 2</code>
											或者
											<code>10,8,5,2</code>
										</div>

									</div>
									<c:forEach items="${evalTable.tableItemList}" var="item"
										varStatus="s">
										<div class="tableItem row" style="padding-top: 20px;">


											<div class="col-xs-6 context">
												<input type="text" class="form-control tableitem-context " required="required"
													name="tableItemList[${s.index }].context"
													value="${item.context }">
											</div>
											<div class="col-xs-6 level">
												<input type="text" class="form-control tableitem-levle" required="required"
													name="tableItemList[${s.index }].level"
													value="${item.level }">
											</div>


										</div>
									</c:forEach>

									<div class="col-xs-3">
										<button class="btn btn-primary" id="addTableItem">
											<span class="glyphicon glyphicon-plus-sign"
												aria-hidden="true">添加表单新项</span>
										</button>

									</div>
                                    <div class="col-xs-2">
                                        <button class="btn btn-primary" id="tableitem-template-btn">
											<span class="glyphicon glyphicon-plus-sign"
                                                  aria-hidden="true">插入模板记录</span>
                                        </button>
                                    </div>
									<div class="col-xs-3">
										<button class="btn btn-warning" id="removeTableItem">
											<span class="glyphicon glyphicon-remove-sign"
												aria-hidden="true">删除最后一个</span>
										</button>

									</div>


								</div>
							</div>

							<div class="form-group">
								<label class="col-xs-2 control-label">问题和建议</label>
								<div class="col-xs-10" id="question">
									<c:forEach items="${evalTable.questionList}" var="item"
										varStatus="s">
										<div class="col-xs-12 question" style="margin-top: 5px;">

											<input type="text" class="form-control inputxt"
												required="required" name="questionList[${s.index}].context"
												value="${ item.context}">

										</div>
									</c:forEach>
									<div class="col-xs-3">
										<button class="btn btn-primary" id="addQuestion">
											<span class="glyphicon glyphicon-plus-sign"
												aria-hidden="true"> 添加问题和建议项</span>

										</button>
										
									</div>

									<div class="col-xs-3">
										<button class="btn btn-warning" id="removeQuestion">
											<span class="glyphicon glyphicon-remove-sign"
												aria-hidden="true">删除最后一个</span>
										</button>
									</div>
								</div>
							</div>


                            <div class="form-group">
                                <label class="col-sm-2 control-label">修改说明</label>
                                <div class="col-sm-10" >
                                    <p class="form-control-static text-danger " style="font-size: 18px;">
                                        强烈不建议您对评教指标进行大幅度的修改，否则将会影响已经评教的数据的统计！！！<br>
                                        您只能在该指标表没有使用的时候才可以修改成功</p>
                                </div>
                            </div>

							<div>
								<div class="form-group">
									<div class="col-xs-6 col-xs-offset-4">
										<button type="submit" class="btn btn-primary  btn-lg ">确认修改指标</button>
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
    <script
            src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tqe/template.js"></script>

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/tqe/evaltable/edit-evaltable.js"></script>
	<script type="text/javascript">
		$(function() {
            initEditEvalTableBtnEvent();

            autoSelect();
            $("#edit-evalTable-form").validate();
		});
	</script>
	<%@ include file="../buttom.jsp"%>
</body>
</html>
