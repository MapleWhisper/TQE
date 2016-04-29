<%@page import="java.text.DateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>教师详情</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"/>


<style type="text/css">
.table>thead>tr>th,.table>tbody>tr>th,.table>tfoot>tr>th,.table>thead>tr>td,.table>tbody>tr>td,.table>tfoot>tr>td
	{
	border-top: 0px solid #ddd;
}
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

			<div class="col-sm-10">


                <div class="panel panel-default">
                    <div class="panel-heading">
                       教师详情
                    </div>
                    <div class="panel-body">
                        <ol class="breadcrumb">
                            <li><a href="${pageContext.request.contextPath}/admin/teacher">教师列表</a></li>
                            <li class="active">教师详情</li>
                        </ol>
                        <jsp:include page="teacher-info-table.jsp"/>

                        <div class="bs-callout bs-callout-info">
                            <c:set scope="request" var="userType" value="teacher"/>
                            <jsp:include page="../model/seasonSelectForm.jsp"/>

                        </div>
                        <table class="table table-hover table-striped  table-condensed ">
                            <thead>
                            <tr class="info">
                                <td>课程名</td>
                                <td>课程号/课序号</td>
                                <td>学期</td>
                                <td>学生数</td>
                                <td>平均评分(学生/教师/领导)</td>
                                <td width="10%">合班</td>
                                <!--
                                <td>操作</td>
                                 -->
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${courseList}" var="c">
                                <tr>

                                    <td><a href="${pageContext.request.contextPath}/admin/course/show/${c.cid}/${c.cno}">${c.name }</a></td>
                                    <td>${c.cid }/${c.cno}</td>
                                    <td>${c.season }</td>
                                    <td>${c.stuNumber }</td>
                                    <td><code>${c.stuEvalAvgScore}分</code>---<code>${c.teaEvalAvgScore}分</code>---<code>${c.leaEvalAvgScore}分</code></td>
                                    <td>${c.combine }</td>
                                    <!--
										<td><a href="admin/edit/${admin.id }"
											class="btn btn-info"><span
												class=" glyphicon glyphicon-edit"></span>&nbsp;&nbsp;修改</a></td>
										 -->
                                    <td><a href="${pageContext.request.contextPath}/admin/course/show/${c.cid}/${c.cno}"
                                           class="btn btn-danger" ><span
                                            class=" glyphicon glyphicon-zoom-in"></span>查看课程详情</a></td>
                                </tr>
                            </c:forEach>

                            </tbody>
                        </table>
                    </div>
                    <!-- panel-body -->
                </div>
			</div>
			<!-- 班级详情 -->

		</div>
		<!--右侧的内容 -->


	</div>

	<%@ include file="../buttom.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.js"></script>


	<script type="text/javascript">
        $(function(){
            autoSelect();
            $("#season").change(function(){
                $("#teacher-course-form").submit();
            });
        });
    </script>

</body>
</html>
