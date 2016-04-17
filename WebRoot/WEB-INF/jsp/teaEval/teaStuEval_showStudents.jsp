<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<h2 class="page-header">${course.name}
	<small>${course.combine}</small>
</h2>
<table
	class="table table-hover table-striped table-bordered table-condensed">
	<thead>
		<tr class="info">
			<td>学号</td>
			<td>学生姓名</td>
			<td>性别</td>
			<td>操作</td>
			<td>学号</td>
			<td>学生姓名</td>
			<td>性别</td>
			<td>操作</td>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${studentList}" var="stu" varStatus="s">
			<c:if test="${s.index%2==0 }">
				<tr>
			</c:if>
			<td>${stu.sid }</td>
			<td>${stu.name }</td>
			<td>${stu.sex }</td>
			<c:if test="${stu.isEvaled=='false'}">
				<td><a icon="edit" target="_blank"
				href="${pageContext.request.contextPath}/admin/teaStuEval/eval?cid=${course.cid}&cno=${course.cno}&sid=${stu.sid}"
				class="btn btn-danger">去评价</a></td>
			</c:if>
			<c:if test="${stu.isEvaled=='true'}">
				<td><button class="btn btn-default" disabled="disabled">
						<span class=" glyphicon glyphicon-ok"></span>已评价
					</button></td>
			</c:if>
			
			<!-- 
				<c:if test="${c.isEvaled=='true'}">
				disabled="disabled"-->

			<!-- 
				</c:if>
				-->
			<c:if test="${s.index%2!=0 }">
				</tr>
			</c:if>


		</c:forEach>



	</tbody>
</table>