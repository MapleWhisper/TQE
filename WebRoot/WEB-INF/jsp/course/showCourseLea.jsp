<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="row">
    <c:forEach items="${courseModel.batchesList}" var="b">
        <div role="tabpanel" class="tab-pane" style="padding: 10px">
            <div class="bs-callout bs-callout-danger">
                <h4 class="panel-heading">
                    <a
                            href="${pageContext.request.contextPath}/admin/batches/show/${b.batches.id}"
                            target="_blank">${b.batches.name}</a>
                    <a style="padding-left: 50px;"
                       href="${pageContext.request.contextPath}/admin/evalTable/show/${b.batches.stuEvalId}"
                       target="_blank">点我查看评教表</a>
                    <a style="padding-left: 30px;">
                        评教时间: <fm:formatDate value="${b.batches.beginDate}"  dateStyle="medium"/> ~ <fm:formatDate value="${b.batches.endDate}"  dateStyle="medium"/>
                    </a>
                </h4>
                <h5>

                </h5>
                <div class="row">
                    <table
                            class="table table-hover table-striped  table-condensed">
                        <thead>
                        <tr>
                            <td>领导名</td>
                            <td>评分</td>
                            <td>等级</td>
                            <td>评教结果</td>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${b.leaTableList}" var="leaTable">
                            <tr>
                                <td>${leaTable.leader.name}</td>
                                <td>${leaTable.score}</td>
                                <td>${leaTable.level}</td>
                                <td><a
                                        href="${pageContext.request.contextPath}/admin/eval/show/leader/${leaTable.id}" target="_blank"
                                        class="btn btn-warning">查看该评价</a></td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </c:forEach>
</div>