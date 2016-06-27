<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>学生列表</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"/>
<style type="text/css">

</style>
</head>

<body>
	<div class="container-fluid">
		<div class="row">
			<%@ include file="../head.jsp"%>
		</div>
		<div class="row" >
			<!--左侧的导航条 -->
			<div class="col-sm-2">
				<%@include file="../left.jsp"%>
			</div>
			<!--左侧的导航条 -->

			<!--右侧的内容 -->
			<div class="col-sm-10">
				<!-- 面板开始 -->
				<div class="panel panel-default">
					<div class="panel-heading">
						学生列表
					</div>
					<div class="panel-body">
						<div class="bs-callout bs-callout-info">
							<form class="form-inline" method="post" action="${pageContext.request.contextPath}/admin/student">
								<div class="form-group">
									<label >学院:</label>
									<select  class="form-control auto-select" id="department" name="did" value="${condition.did}">
										<option value="" selected="selected">不限</option>
											<c:forEach items="${ departmentList}" var="dep" >
													<option value="${dep.id}">${dep.name }</option>
											</c:forEach>
									</select>
								</div>
								<div class="form-group">
									<label for="major" >专业:</label>
									  <select  class="form-control auto-select" id="major" name="mid" value="${condition.mid}">
									  <option value="" selected="selected">不限</option>
									</select>
								</div>
                                <div class="form-group">
                                    <label for="grade" >年级</label>
                                    <select  class="form-control auto-select" id="grade" name="grade" value="${condition.grade}">
                                        <option value="" selected="selected">不限</option>
                                        <c:forEach begin="2012" end="${applicationScope.curYear}"  var="g">
                                            <option  value="${g}级">${g}级</option>
                                        </c:forEach>
                                    </select>
                                </div>
								<div class="form-group">
									<label for="clazz" >班级:</label>
										<select  class="form-control auto-select" id="clazz" name="cid" value="${condition.cid}">
									  <option value="" selected="selected">不限</option>
									  </select>
								</div>
								<div class="form-group">
									<label for="sname" >姓名:</label> <input type="text"
										class="form-control" id="sname" name="sname">
								</div>
								<div class="form-group">
									<label for="sid" >学号:</label> <input type="number"
										class="form-control" id="sid" name="sid">
								</div>

								<button type="submit" class="btn btn-primary btn-lg">
									<span class="glyphicon glyphicon-search" aria-hidden="true"></span>搜索
								</button>
							</form>


						</div>
						<table
							class="table table-hover table-striped table-bordered table-condensed compact">
							<thead>
								<tr class="info">
									<td>学生姓名</td>
									<td>学号</td>
									<td>性别</td>
									<td>院系</td>
									<td>专业</td>
									<td>班级</td>
									<!-- 
									<td>方向</td>
									 -->
									<td>年级</td>
									<td>校区</td>
									<td>操作</td>
								</tr>
							</thead>
							<tbody>
								<c:forEach items="${studentList}" var="s">
									<tr>
										<td><a
											href="${pageContext.request.contextPath}/admin/student/show?sid=${s.sid}">${s.name}</a></td>
										<td>${s.sid}</td>
										<td>${s.sex }</td>
										<td>${s.department }</td>
										<td>${s.major }</td>
										<td>${s.clazz }</td>
										<!-- 
										<td>${s.field }</td>
										 -->
										<td>${s.grade }</td>
										<td>${s.campus }</td>


										<td><a href="${pageContext.request.contextPath}/admin/student/show?sid=${s.sid}"
											class="btn btn-primary" icon="zoom-in">查看学生</a></td>
									</tr>
								</c:forEach>
							</tbody>

						</table>

					</div>
					<!-- panel-body -->
				</div>
				<!-- panel panel-default -->
				<!-- 面板结束 -->
			</div>
			<!--右侧的内容 -->
		</div>
		<%@ include file="../buttom.jsp"%>
	</div>
	<script
		src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.nodefault.js"></script>

	<script type="text/javascript">
		$(function(){

            $('table').has("thead").dataTable($.extend(true,dataTableDefaultOptions,{
                language:{
                    zeroRecords:'<h2>请从上面搜索栏中选择要查看的数据</h2>'
                }
            }));

            autoSelect();


			//点击部门 动态加载专业列表
			$("#department").change(function(){
				fetchMajorList();
			});
			//点击专业，动态加载班级信息列表
			$("#major").change(function(){
				fetchClassList();
			});
            //年级变化时候 加载班级列表
            $("#grade").change(function(){
                fetchClassList();
            });

            
            fetchMajorList();
            fetchClassList();

            autoAddIcon();


		});

		function fetchMajorList(){
			var did = $("#department option:selected").val();
            console.log('did:'+did);
			if(did){
				$.post("../getMajor/"+did,function(data){
					//alert(data);
					if(data!=null){
						$("#major").html("<option value='' selected='selected'>不限</option>");
						$(data).each(function(){
							//alert(this.id);
							//alert(this.name);
							var opt = $("<option value="+this.id+">"+this.name+"</option>");

							$("#major").append(opt);
						});
                        autoSelect();
					}

				});
				$("#major option[value='']:selected");
				$("#clazz option[value='']:selected");

			}
		}

		function fetchClassList(departmentId ,majorId){
			var did = $("#department").find("option:selected").val();
			var mid = $("#major").find("option:selected").val();
            var grade = $("#grade").find("option:selected").val();
            log(grade);
			if(did && mid && grade){
				$.post("../getClazz",
                        {
                            did:did,
                            mid:mid,
                            grade:grade
                        },
                        function(data){
					//alert(data);
					if(data){
						$("#clazz").html("<option value='' selected='selected'>不限</option>");
						$(data).each(function(){
							var opt = $("<option value="+this.id+">"+this.name+"</option>")
							$("#clazz").append(opt);
						});
                        autoSelect();
					}

				});
				$("#major option[value='']:selected");
				$("#clazz option[value='']:selected");

			}
		}
	</script>
</body>
</html>
