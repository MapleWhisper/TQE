<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<table class="table table-hover ">
    <tr class="info thead">
        <td>教师名</td>
        <td>教师号</td>
        <td>学院</td>
        <td>职称</td>
        <td>操作</td>
    </tr>
    <tr>

        <td>${teacher.name }</td>
        <td>${teacher.id }</td>
        <td>${teacher.department }</td>
        <td>${teacher.title }</td>
        <td><a class="btn btn-danger" icon="zoom-in" target="_blank"
               href="${pageContext.request.contextPath}/admin/statistics/teacher?tid=${teacher.id}">查看教师统计</a></td>
    </tr>
</table>