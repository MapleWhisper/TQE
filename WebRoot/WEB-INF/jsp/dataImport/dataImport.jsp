<%@ page language="java" pageEncoding="UTF-8" %>

<html>
<head>
    <%@ include file="../header.jspf" %>
    <title>数据导入首页</title>
</head>

<body>
<div class="container-fluid">
    <div class="row">
        <%@ include file="../head.jsp" %>
    </div>
    <div class="row">
        <div class="col-sm-2">
            <%@ include file="../left.jsp" %>
        </div>
        <div class="col-sm-10">
            <div class="panel panel-default">
                <div class="panel-heading">数据导入</div>
                <div class="panel-body">
                    <div class="row">
                        <div class="alert alert-warning">导入文件前请先查看右侧的文件上传说明！文件上传前必须进行
                            <a target="_blank"
                               href="${pageContext.request.contextPath}/image/tqe-excel-upload-convert.jpg">格式转换</a>！
                        </div>
                        <div class="col-sm-9">
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

                                        <div class="col-sm-3">
                                            <select name="season" class="form-control" id="season">
                                                <option value="${applicationScope.curSeason}">
                                                    当前学期:${applicationScope.curSeason}</option>
                                                <c:forEach begin="2015" end="${applicationScope.curYear+1}" step="1"
                                                           var="s">
                                                    <option value="${s}春">${s}春</option>
                                                    <option value="${s}秋">${s}秋</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label for="course-file" class="col-sm-2 control-label">要上传的课程文件</label>

                                        <div class="col-sm-5">
                                            <input type="file" class="form-control" name="courseFile" id="course-file">
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
                                            <input type="file" class="form-control" name="scFile" id="sc-file">
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
                                            <input type="file" class="form-control" name="teacherFile"
                                                   id="teacher-file">
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
                                      action="${pageContext.request.contextPath}/admin/dataImport/student"
                                      enctype="multipart/form-data" method="post">
                                    <div class="form-group">
                                        <label for="student-file" class="col-sm-2 control-label">要上传的学生文件</label>

                                        <div class="col-sm-5">
                                            <input type="file" class="form-control" name="studentFile" id="student-file"
                                                   required="required">
                                        </div>
                                    </div>

                                    <div class="form-group">
                                        <div class="col-sm-offset-2 col-sm-5">
                                            <button type="submit" class="btn btn-warning">导入学生信息</button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </div>

                        <div class="col-sm-3">
                            <div class="well text-danger">
                                文件导入说明:
                                <ul>
                                    <li>上传文件必须为EXCEL文件，文件以.xls后缀</li>
                                    <li>上传文件大小不能大于10MB</li>
                                    <li>从教务系统下载的EXCEL必须要转换成兼容的Excel 2003 文件</li>
                                    <li><a target="_blank"
                                           href="${pageContext.request.contextPath}/image/tqe-excel-upload-convert.jpg"
                                           class="btn btn-danger btn-sm">点我查看上传格式转换说明</a></li>
                                </ul>

                                常见错误说明
                            </div>
                        </div>


                    </div>
                </div>


            </div>


        </div>


    </div>

    <%@ include file="../buttom.jsp" %>


</div>
<script type="text/javascript">
    $(function () {
        showErrorMessage();
        $("form").submit(function () {
            var $this = $(this);
            var season = $this.find("#season").val();
            var fileName = $this.find("input[type='file']").val();
            var meg = "您确定要提交表单吗?\n请您仔细核对，文件上传后不可取消!\n文件名:\t" + fileName;
            if (season) {
                meg += "\n学期:\t" + season + "!!\n";
            }
            if (!confirm(meg)) {
                return false;
            }
            return true;
        });
    });
</script>
</body>
</html>
