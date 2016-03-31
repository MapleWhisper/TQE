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

						<table class="table table-hover ">
                                <tr class="info thead">
                                    <td>教师名</td>
                                    <td>教师号</td>
                                    <td>学院</td>
                                    <td>职称</td>
                                </tr>
                                <tr>

                                    <td>${teacher.name }</td>
                                    <td>${teacher.id }</td>
                                    <td>${teacher.department }</td>
                                    <td>${teacher.title }</td>

                                    <!--
                                        <td>${batches.name }</td>
                                        <td><fm:formatDate value="${batches.beginDate}"
                                                pattern="yyyy-MM-dd" /></td>
                                        <td><fm:formatDate value="${batches.endDate }"
                                                pattern="yyyy-MM-dd" /></td>
                                             -->
                                </tr>
                        </table>



                <div class="panel panel-primary">
                    <div class="panel-heading">
                       教师所教班级
                    </div>
                    <div class="panel-body">
                        <div class="bs-callout bs-callout-info">
                            <form class="form-inline" id="teacher-course-form" method="post" action="${pageContext.request.contextPath}/admin/teacher/show">

                                <div class="form-group">
                                    <input type="hidden" id="tid" name="tid" value="${teacher.id}">
                                    <label for="season" >选择学期查看课程:</label>
                                    <select name="season" class="form-control" id="season" key="${condition.season}">
                                        <option value="">当前学期:${applicationScope.curSeason}</option>
                                        <c:forEach begin="2015" end="${applicationScope.curYear+1}" step="1" var="s">
                                            <option  value="${s}春">${s}春</option>
                                            <option  value="${s}秋">${s}秋</option>
                                        </c:forEach>
                                    </select>
                                </div>

                            </form>
                        </div>
                        <table class="table table-hover table-striped  table-condensed ">
                            <thead>
                            <tr class="info">
                                <td>课程名</td>
                                <td>课程号</td>
                                <td>课序号</td>
                                <td>教师名</td>
                                <td>学院</td>
                                <td>学期</td>
                                <td>学生数</td>
                                <td>学分</td>
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
                                    <td>${c.cid }</td>
                                    <td>${c.cno }</td>
                                    <td>${c.teacher.name }</td>
                                    <td>${c.department }</td>
                                    <td>${c.season }</td>
                                    <td>${c.stuNumber }</td>
                                    <td>${c.credit }</td>
                                    <td>${c.combine }</td>
                                    <!--
										<td><a href="admin/edit/${admin.id }"
											class="btn btn-info"><span
												class=" glyphicon glyphicon-edit"></span>&nbsp;&nbsp;修改</a></td>
										 -->
                                    <td><a href="${pageContext.request.contextPath}/admin/course/show/${c.cid}/${c.cno}"
                                           class="btn btn-danger" ><span
                                            class=" glyphicon glyphicon-zoom-in"></span>查看评教详情</a></td>
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
