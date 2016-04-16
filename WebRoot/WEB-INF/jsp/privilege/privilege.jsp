<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<style type="text/css">
.no1 {
	margin-top: 30px;
}

</style>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"/>
<title>权限管理</title>
</head>

<body>
	<div class="container-fluid">
        <div class="row">
		<%@ include file="../head.jsp"%>
            </div>
		<div class="row " >
			<div class="col-sm-2">
				<%@ include file="../left.jsp"%>
			</div>
			<div class="col-sm-10 ">
				<div class="panel panel-default">
					<div class="panel-heading">权限查看 与 管理</div>
                    <div class="alert alert-warning">如果您对该页面内容不了解 请谨慎修改！</div>
					<div class="panel-body">
						<table class="table table-hover table-striped table-bordered table-condensed">
							<thead>
								<tr class="info">
									<td>权限Url</td>
									<td>权限名称</td>
									<td>学生</td>
									<td>教师</td>
									<td>领导</td>
									<td>管理员</td>
									<td style="width: 15%">操作</td>
								</tr>
							</thead>
							<tbody>

								<c:forEach items="${privilegeList}" var="p">
                                    <c:if test="${p.editable==0 || sessionScope.admin.name!='admin'}">
                                        <tr>

                                            <td>${p.url}</td>
                                            <td>${p.name}</td>
                                            <td >${p.stu}</td>
                                            <td >${p.tea}</td>
                                            <td >${p.adm}</td>
                                            <td >${p.lea}</td>
                                            <td>不可修改</td>
                                        </tr>
                                    </c:if>
                                    <c:if test="${p.editable == 1 && sessionScope.admin.name=='admin'}">
                                        <tr>
                                            <td>${p.url}</td>
                                            <td>${p.name}</td>
                                            <td ><input class="${p.id} form-control input-sm stu" disabled="disabled" value="${p.stu}"></td>
                                            <td ><input class="${p.id} form-control input-sm tea" disabled="disabled" value="${p.tea}"></td>
                                            <td ><input class="${p.id} form-control input-sm adm" disabled="disabled" value="${p.adm}"></td>
                                            <td ><input class="${p.id} form-control input-sm lea" disabled="disabled" value="${p.lea}"></td>
                                            <td>
                                                <button value="${p.id}"  class="btn btn-info edit">修改</button>
                                                <button value="${p.id}"  style="display: none" class="btn btn-info cancel" >取消修改</button>
                                                <button value="${p.id}"  style="display: none" class="btn btn-warning update" >保存修改</button>
                                            </td>
                                        </tr>
                                    </c:if>

								</c:forEach>

							</tbody>
						</table>
						<!--
						<div class="row">
							<div class="col-xs-6 col-xs-offset-5">
								<div class="no1">
									<a class="btn btn-primary " href="admin/add"></a>
								</div>

							</div>

						</div>
						 -->


					</div>

                    <div class="well">
                        权限说明:<br>
                        权限Url 代表角色是否有权限访问该Url，如果角色没有权限访问该Url，那么值为0，否则值为1;
                    </div>

				</div>
			</div>

		</div>

	</div>
	<%@ include file="../buttom.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.nodefault.js"></script>

	<script type="text/javascript">
		$(function(){
			$(".edit").click(function(){
				var id = $(this).val();
				$("input."+id).removeAttr("disabled");
				$(this).hide();
				$(this).siblings().show();
			});
			$(".update").click(function(){
				var id = $(this).val();
				var stu = $("input."+id+".stu").val();
				var tea = $("input."+id+".tea").val();
				var adm = $("input."+id+".adm").val();
				var lea = $("input."+id+".lea").val();
				$.post("privilege/update",{"id":id,"stu":stu,"tea":tea,"lea":lea,"adm":adm},function(data){
					if(data=="success"){
						alert("修改成功");
					}else{
						alert("修改失败！"+data);
					}
				});
				$(this).hide();
				$(this).siblings().toggle();
				$("input."+id).attr("disabled","disabled");
			});
            $(".cancel").click(function(){
                var id = $(this).val();
                $(this).hide();
                $(this).siblings().toggle();
                $("input."+id).attr("disabled","disabled");
            });


		});
		$(function(){
			$('table').has("thead").dataTable({
				 "language": {
			            "lengthMenu": "每页显示 _MENU_ 条纪录 ",
			            "zeroRecords": "抱歉,没有找到数据",
			            "info": "当前第  _PAGE_/_PAGES_ 页",
			            "infoEmpty": "",
			            "infoFiltered": "(过滤自  _MAX_ 条纪录)",
			            "search":"搜索:",
			            "searchPlaceholder":"请输入关键字",
                         "paginate": {
                             "first":      "首页",
                             "last":       "末页",
                             "next":       "下一页",
                             "previous":   "上一页"
                         }
			        },
					"lengthMenu": [[-1], [  "所有记录"]],
                    "dom":"<'float_left'f>r<'float_right'l>t<'float_left'i><'float_right'p>"
			});
		});
	</script>
</body>
</html>

