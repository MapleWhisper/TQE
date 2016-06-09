<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


    <c:forEach items="${courseModel.batchesList}" var="b">
            <div class="bs-callout bs-callout-warning">
                <h4 class="panel-heading">
                    <a
                            href="${pageContext.request.contextPath}/admin/batches/show/${b.batches.id}"
                            target="_blank">${b.batches.name}</a>
                    <a style="padding-left: 30px;">
                        <span class="glyphicon glyphicon-time"></span> 评教时间: <fm:formatDate value="${b.batches.beginDate}"  dateStyle="medium"/> ~ <fm:formatDate value="${b.batches.endDate}"  dateStyle="medium"/>
                    </a>
                    <a style="" class="btn btn-info"
                       href="${pageContext.request.contextPath}/admin/evalTable/show/${b.batches.teaEvalId}"
                       target="_blank"><span class="glyphicon glyphicon-file"></span> 点我查看评教表</a>
                    <a style="" class="btn btn-danger" href="${pageContext.request.contextPath}/admin/statistics/course?cid=${course.cid}&cno=${course.cno}&bid=${b.batches.id}"
                       target="_blank"><span class="glyphicon glyphicon-stats"></span> 点我查看课程统计</a>
                </h4>
                <h5>

                </h5>
                <div class="row">
                    <table
                            class="table table-hover table-striped  table-condensed">
                        <thead>
                        <tr>
                            <td>教师号</td>
                            <td>教师姓名</td>
                            <td>评分</td>
                            <td>等级</td>
                            <td>评教结果</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${b.teaTableList}" var="tea">
                            <tr>
                                <td>${tea.teacher.id}</td>
                                <td>${tea.teacher.name}</td>

                                <td>${tea.score}</td>
                                <td>${tea.level}</td>
                                <td><a
                                        href="${pageContext.request.contextPath}/admin/eval/show/teacher/${tea.id}" target="_blank"
                                        class="btn btn-warning">查看该评价</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
    </c:forEach>
