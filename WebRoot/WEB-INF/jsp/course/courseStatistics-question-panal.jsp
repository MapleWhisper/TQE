<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="panel panel-default">
    <div class="panel-heading"> <code>${v.count}</code> ${quest.left}</div>
    <!-- Table -->
    <table class="table table-hover table-condensed">
        <thead>
        <tr >
            <td>#</td>
            <td>内容</td>
        </tr>
        </thead>

        <tbody>
        <c:forEach varStatus="vs" var="q" items="${quest.right}">
            <tr>
                <td>${vs.count}</td>
                <td>${q}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>