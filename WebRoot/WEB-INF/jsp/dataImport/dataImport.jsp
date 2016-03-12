<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>

<html>
<head>
    <%@ include file="../header.jspf" %>
</head>

<body>
<div class="container-fluid">
    <%@ include file="../head.jsp" %>
    <div class="row" style="margin-top: 70px">
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
                            <span class="error-message">${error}</span>
                        </div>
                    </div>
                </div>



                <div class="bs-callout bs-callout-info">
                    <h4>课程信息上传</h4>
                    <form class="form-horizontal" name="import-teacher-form"
                          action="${pageContext.request.contextPath}/admin/dataImport/course"
                          enctype="multipart/form-data" method="post">
                        <div class="form-group">
                            <label class="col-sm-2 control-label">学期</label>
                            <div class="col-sm-2">
                                <select name="season" class="form-control" id="season">
                                    <c:forEach begin="2015" end="2050" step="1" var="s">
                                        <option  value="${s}春">${s}春</option>
                                        <option  value="${s}秋">${s}秋</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="course-file" class="col-sm-2 control-label">要上传的课程文件</label>
                            <div class="col-sm-5">
                                <input type="file" class="form-control"  name="courseFile" id="course-file" >
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-5">
                                <button type="submit" class="btn btn-info">导入课程信息</button>
                            </div>
                        </div>
                    </form>
                </div>


                <div class="bs-callout bs-callout-warning">
                    <h4>学生选课信息上传</h4>
                    <form class="form-horizontal" name="import-teacher-form"
                          action="${pageContext.request.contextPath}/admin/dataImport/sc"
                          enctype="multipart/form-data" method="post">
                        <div class="form-group">
                            <label for="sc-file" class="col-sm-2 control-label">要上传的学生选课文件</label>
                            <div class="col-sm-5">
                                <input type="file" class="form-control"  name="scFile" id="sc-file" >
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-5">
                                <button type="submit" class="btn btn-warning">导入学生选课信息</button>
                            </div>
                        </div>
                    </form>
                </div>




                <div class="bs-callout bs-callout-info">
                    <h4>教师信息上传</h4>

                    <form class="form-horizontal" name="import-teacher-form"
                          action="${pageContext.request.contextPath}/admin/dataImport/teacher"
                          enctype="multipart/form-data" method="post">
                        <div class="form-group">
                            <label for="teacher-file" class="col-sm-2 control-label">要上传的教师文件</label>
                            <div class="col-sm-5">
                                <input type="file" class="form-control"  name="teacherFile" id="teacher-file" >
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-5">
                                <button type="submit" class="btn btn-info">导入教师信息</button>
                            </div>
                        </div>
                    </form>
                </div>





                <div class="bs-callout bs-callout-warning">
                    <h4>学生信息上传</h4>

                    <form class="form-horizontal" name="import-teacher-form"
                          action="${pageContext.request.contextPath}/admin/dataImport/teacher"
                          enctype="multipart/form-data" method="post">
                        <div class="form-group">
                            <label for="student-file" class="col-sm-2 control-label">要上传的学生文件</label>
                            <div class="col-sm-5">
                                <input type="file" class="form-control"  name="studentFile" id="student-file" required="required">
                            </div>
                        </div>

                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-5">
                                <button type="submit" class="btn btn-warning">导入学生信息</button>
                            </div>
                        </div>
                    </form>
                </div>







                <div class="well text-danger">
                    文件导入说明:
                    <ul>
                        <li>上传文件必须为EXCEL文件，文件以.xls后缀</li>
                        <li>上传文件大小不能大于20KB</li>

                    </ul>
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
