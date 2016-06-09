<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fm" uri="http://java.sun.com/jsp/jstl/fmt" %>

            <c:forEach items="${courseModel.batchesList}" var="b">
                <%--<div role="tabpanel" class="tab-pane" style="padding: 10px">--%>
                    <div class="bs-callout bs-callout-info">
                        <h4 class="panel-heading">
                            <a
                                    href="${pageContext.request.contextPath}/admin/batches/show/${b.batches.id}"
                                    target="_blank">${b.batches.name}</a>

                            <a style="padding-left: 30px;">
                                <span class="glyphicon glyphicon-time"></span>
                                评教时间: <fm:formatDate value="${b.batches.beginDate}"  dateStyle="medium"/> ~ <fm:formatDate value="${b.batches.endDate}"  dateStyle="medium"/>
                                （${b.batches.batchStatus}）
                            </a>
                            <a style="" class="btn btn-info"
                                   href="${pageContext.request.contextPath}/admin/evalTable/show/${b.batches.stuEvalId}"
                                   target="_blank"><span class="glyphicon glyphicon-file"></span> 点我查看评教表</a>
                            <a style="" class="btn btn-danger" href="${pageContext.request.contextPath}/admin/statistics/course?cid=${course.cid}&cno=${course.cno}&bid=${b.batches.id}"
                               target="_blank"><span class="glyphicon glyphicon-stats"></span> 点我查看课程统计</a>
                        </h4>
                        <div class="row">
                            <table
                                    class="table table-hover table-striped  table-condensed">
                                <thead>
                                <tr>
                                    <td>学号</td>
                                    <td>学生姓名</td>
                                    <td>评分</td>
                                    <td>等级</td>
                                    <td>评教结果</td>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${b.stuTableList}" var="st">
                                    <tr>
                                        <td>${st.student.sid}</td>
                                        <td><a target="_blank" href="${pageContext.request.contextPath}/admin/student/show?sid=${st.student.sid}">
                                                ${st.student.name}
                                            </a>
                                        </td>

                                        <td>${st.score}</td>
                                        <td>${st.level}</td>
                                        <td><a
                                                href="${pageContext.request.contextPath}/admin/eval/show/student/${st.id}" target="_blank"
                                                class="btn btn-warning">查看该评价</a></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </div>
                <%--</div>--%>
            </c:forEach>
<!-- panel panel-primary -->