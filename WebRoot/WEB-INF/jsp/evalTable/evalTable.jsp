<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>评教指标</title>
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


				<div class="panel panel-default">
					<div class="panel-heading">
						指标列表
					</div>
					<div class="panel-body">
                        <div class="bs-callout bs-callout-info">
                            <a href="${pageContext.request.contextPath}/admin/evalTable/add"
                               class="btn btn-info btn-lg " icon="plus">生成新指标</a>

                            <form class="form-inline" method="post" action="${pageContext.request.contextPath}/admin/evalTable"
                                    style=" margin-top: 30px">
                                <div class="form-group">
                                    <label for="type">指标类型</label>
                                    <select type="text" class="form-control auto-select" name="type" id="type" key="${condition.type}">
                                        <option value="">不限</option>
                                        <option value="学生评教师">学生评教师</option>
                                        <option value="教师评教师">教师评教师</option>
                                        <option value="教师评学生">教师评学生</option>
                                        <option value="领导评教师">领导评教师</option>
                                    </select>
                                    <button type="submit" class="btn btn-primary ">
                                        <span class="glyphicon glyphicon-search" aria-hidden="true"></span>搜索
                                    </button>
                                </div>

                            </form>
                        </div>

						<table class="table table-hover table-striped table-bordered table-condensed">
							<thead>
								<tr class="info">

									<td>指标标题</td>
                                    <td>指标类型</td>
									<td>生成时间</td>
									<td>操作</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${evalTableList }" var="e">
									<tr>

										<td><a target="_blank"
											href="${pageContext.request.contextPath}/admin/evalTable/show/${e.id}">${e.title }</a></td>
                                        <td>${e.type}</td>
										<td><fm:formatDate value="${e.createDate}"
												pattern="yyyy-MM-dd" /></td>
										<td>
                                            <a href="${pageContext.request.contextPath}/admin/evalTable/edit/${e.id}" class="btn btn-sm btn-info update" icon="edit">修改</a>
                                            <a href="${pageContext.request.contextPath}/admin/evalTable/delete" id="${e.id}" icon="remove" class="btn btn-sm btn-danger delete">删除</a>
                                        </td>
										<!--	评教指标暂时不能删除 
										<td></td>
										 -->
										
										<c:if test="${param.action == 'stuEval'}">
											<td>
											<a
												href="${pageContext.request.contextPath}/admin/batches/setEval/student/${param.bid}/${e.id}"
												class="btn btn-lg btn-danger">点此选为默认学生评教指标表</a>
												</td>
										</c:if>
										
										<c:if test="${param.action == 'teaEval'}">
											<td>
											<a
												href="${pageContext.request.contextPath}/admin/batches/setEval/teacher/${param.bid}/${e.id}"
												class="btn btn-lg btn-danger">点此选为默认教师评教指标表</a>
												</td>
										</c:if>
										
										<c:if test="${param.action == 'leadEval'}">
											<td>
											<a
												href="${pageContext.request.contextPath}/admin/batches/setEval/lead/${param.bid}/${e.id}"
												class="btn btn-lg btn-danger">点此选为默认领导评教指标表</a>
												</td>
										</c:if>
										
										<c:if test="${param.action == 'teaStuEval'}">
											<td>
											<a
												href="${pageContext.request.contextPath}/admin/batches/setEval/teaStu/${param.bid}/${e.id}"
												class="btn btn-lg btn-danger">点此选为默认教师评价学生表</a>
												</td>
										</c:if>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>

	</div>
	<%@ include file="../buttom.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.js"></script>
    <script type="text/javascript">

        $(function() {
            $(".delete").click(function(e) {
                e.preventDefault();
                var eid = $(this).attr("id");
                if(eid){
                    if(confirm("您确认要删除该评教表吗？只有没有被使用的表才可以被删除")){
                        $.post($(this).attr("href"),{eid:eid},function(data){
                            if(data.success){
                                location.reload();
                            }else{
                                showGlobalNotification("删除评教表失败！该评教表已经被使用；");
                            }
                        });
                    }
                }else{
                    showGlobalNotification("未知的表Id: eid:"+eid);
                }

            });
            $("#type").change(function(){
                $(this).parents('form').submit();
            });
            autoAddIcon();
            autoSelect();
        });
    </script>
</body>
</html>
