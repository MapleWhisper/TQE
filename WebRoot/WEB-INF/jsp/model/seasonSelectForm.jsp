<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<form class="form-inline" id="teacher-course-form" method="post" action="${pageContext.request.contextPath}/admin/${userType}/show">

    <div class="form-group">
        <input type="hidden" id="tid" name="tid" value="${teacher.id}"/>
        <input type="hidden" id="sid" name="sid" value="${student.sid}"/>
        <label for="season" >选择学期查看课程:</label>
        <select name="season" class="form-control auto-select" id="season" key="${condition.season}">
            <option value="">当前学期:${applicationScope.curSeason}</option>
            <c:forEach begin="2015" end="${applicationScope.curYear+1}" step="1" var="s">
                <option  value="${s}春">${s}春</option>
                <option  value="${s}秋">${s}秋</option>
            </c:forEach>
        </select>
    </div>

</form>

<script type="text/javascript">
    $(function(){
        autoSelect();
        $("#season").change(function(){
            $("#teacher-course-form").submit();
        });
    });
</script>