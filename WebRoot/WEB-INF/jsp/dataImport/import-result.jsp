<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<html>
<head>
    <%@ include file="../header.jspf" %>
    <title >数据导入结果</title>
</head>

<body>
<div class="container-fluid">
    <div class="row">
        <%@ include file="../head.jsp" %>
    </div>
    <div class="row" >
        <div class="col-sm-2">
            <%@ include file="../left.jsp" %>
        </div>
        <div class="col-sm-8">

            <div class="row">

                <div class="error-message-container row">
                    <div class="col-sm-8 col-sm-offset-2">
                        <div class="alert alert-danger alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert">
                                <span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
                            </button>
                            <span class="error-message">${error}${importResult.message}</span>
                        </div>
                    </div>
                </div>


                <div class="bs-callout bs-callout-info">

                    <a href="${pageContext.request.contextPath}/admin/dataImport" class="btn btn-primary btn-lg">  《《 点我返回数据导入首页  </a>
                    <br>
                    <h4 style="margin-top: 40px;">文件导入结果</h4>
                    <table class="table" style="text-align: left">
                        <tr>
                            <td>导入的数据类型:</td>
                            <td>${importResult.importType} 信息导入</td>
                        </tr>
                        <tr>
                            <td>解析到要导入的记录个数:</td>
                            <td>${importResult.size}</td>
                        </tr>
                        <tr>
                            <td>导入成功数:</td>
                            <td>${importResult.successCnt}</td>
                        </tr>
                        <tr>
                            <td>数据库已经存在个数:</td>
                            <td>${importResult.existCnt}</td>
                        </tr>
                        <tr>
                            <td>导入失败数:</td>
                            <td>${importResult.failCnt}</td>
                        </tr>
                    </table>
                    <div class="well">
                        <h4>导入失败原因:</h4>
                        <ul>
                        <c:forEach var="meg"  items="${importResult.failMegs}">
                            <li>${meg}</li>
                        </c:forEach>
                        </ul>
                    </div>


                    <a href="${pageContext.request.contextPath}/admin/dataImport" class="btn btn-primary btn-lg">  《《 点我返回数据导入首页  </a>
                </div>


            </div>
        </div>

    </div>

    <%@ include file="../buttom.jsp" %>


</div>
<script type="text/javascript">
    $(function () {
        showErrorMessage();
    });
</script>
</body>
</html>
