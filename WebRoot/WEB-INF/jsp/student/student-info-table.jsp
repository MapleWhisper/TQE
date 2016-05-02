<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<table class="table table-hover table-striped ">
    <tr class="info">
        <td>学生名</td>
        <td>学生号</td>
        <td>性别</td>
        <td>院系</td>
        <td>专业</td>
        <td>班级</td>
        <td>平均得分</td>
        <td>操作</td>
    <tr>
        <td>${student.name }</td>
        <td>${student.sid}</td>
        <td>${student.sex }</td>
        <td>${student.department}</td>
        <td>${student.major }</td>
        <td>${student.clazz }</td>
        <td><code>${student.avgScore } 分</code></td>
        <td>
            <a  target="_blank" class="btn btn-sm btn-primary"
                href="${pageContext.request.contextPath}/admin/statistics/student?sid=${student.sid}">统计信息</a>
        </td>

    </tr>
</table>
