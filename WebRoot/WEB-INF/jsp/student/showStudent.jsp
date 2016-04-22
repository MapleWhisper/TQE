<%@page import="java.text.DateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>
<title>学生详情</title>
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"></link>


<style type="text/css">
.table>thead>tr>th,.table>tbody>tr>th,.table>tfoot>tr>th,.table>thead>tr>td,.table>tbody>tr>td,.table>tfoot>tr>td
	{
	border-top: 0px solid #ddd;
}
</style>

</head>

	<div class="container-fluid">
		<div class="row">
			<%@ include file="../head.jsp"%>
		</div>
		<div class="row">
			<!--左侧的导航条 -->
			<div class="col-xs-2">
				<%@include file="../left.jsp"%>
			</div>
			<!--左侧的导航条 -->

			<!--右侧的内容 -->
			<!-- 学生详情 -->
			<div class="col-xs-10">
				<div class="panel panel-default">
					<div class="panel-heading">
						学生详情
					</div>
					<div class="panel-body ">
                        <input type="hidden" name="sid" value="${student.sid}">

						<table class="table table-hover table-striped ">
							<tr class="info">
								<td>学生名</td>
								<td>学生号</td>
								<td>性别</td>
								<td>院系</td>
								<td>专业</td>
								<td>班级</td>
								<td>平均得分</td>
							<tr>
								<td>${student.name }</td>
								<td>${student.sid}</td>
								<td>${student.sex }</td>
								<td>${student.department}</td>
								<td>${student.major }</td>
                                <td>${student.clazz }</td>
								<td><code>${student.avgScore } 分</code></td>

							</tr>
						</table>
                        <div class="bs-callout bs-callout-info">
                            <c:set scope="request" var="userType" value="student"/>
                            <jsp:include page="../model/seasonSelectForm.jsp"/>
                            <a href="${pageContext.request.contextPath}/admin/student-season/info" class="btn btn-primary show-stu-statistic"> 查看学生统计信息 </a>

                        </div>

                        <div id="student-season-statistic" class="bs-callout bs-callout-info" style="display: none">
                            <h4 id="student-season"></h4>
                            <hr>
                            <h4>学生平均得分:<code id="stu-avgScore"></code>分 &nbsp;&nbsp;  评教人数:<code id="stu-table-num"></code>人  &nbsp;&nbsp; 各批次得分: <code class="stu-avgScore-list"></code></h4>
                            <br>
                            <span >得分统计:</span><div  class="eval-process-bar progress avgScore-process-bar" value="" ></div>

                            <hr>
                            <div class="row table-item-container">

                            </div>

                            <div class="col-sm-6 table-item" style="display: none">
                                <span class="table-item-key"></span> (平均得分:<code class="table-item-avgScore"></code>分)
                                <div class="eval-process-bar progress table-item-level" value="" ></div>
                            </div>

                        </div>


						<c:forEach items="${courseModel.batchesList}" var="b">
							<div class="bs-callout bs-callout-danger" style="margin-top: 30px;">
									<h4>
										<a
											href="${pageContext.request.contextPath}/admin/batches/show/${b.batches.id}"
											target="_blank">${b.batches.name}</a>
									</h4>
										<table
											class="table table-hover table-striped  table-condensed" style="margin-top: 20px;">
                                            <thead>
                                                <tr>
                                                    <td>评价教师</td>
                                                    <td>评价课程</td>
                                                    <td>评分</td>
                                                    <td>等级</td>
                                                    <td>评教结果</td>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach items="${b.teaStuTableList}" var="ts">
                                                    <tr>
                                                        <td>${ts.tname}</td>
                                                        <td>${ts.course.name}</td>

                                                        <td>${ts.score}</td>
                                                        <td>${ts.level}</td>
                                                        <td><a target="_blank"
                                                               href="${pageContext.request.contextPath}/admin/eval/show/teaStu/${ts.id}"
                                                               class="btn btn-warning">查看该评价</a></td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>

										</table>
								</div>
						</c:forEach>
					</div>
				</div>

			</div>
			<!--右侧的内容 -->


		</div>
	</div>

	<%@ include file="../buttom.jsp"%>
	<script
		src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
	<script
		src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/tqe/course/eval-process-bar.js"></script>

	<script type="text/javascript">
        $(function(){
           $(".show-stu-statistic").click(function(e){
               e.preventDefault();
               var season = $("#season").val();
               if(season){
                    var sid = $("input[name='sid']").val();
                   if(sid){
                       var $this = $(this);
                       $.post($this.attr("href"),{sid:sid,season:season},function(data){
                           if(data.success){
                                log(data.item);
                               var container = $("#student-season-statistic");
                               var ss = data.item;
                               $("#student-season").html(ss.season);
                               $("#stu-avgScore").html(ss.avgScore);
                               $(".avgScore-process-bar").attr("value","["+ss.levelCnts+"]");
                               $(".stu-avgScore-list").html(ss.avgScoreList);
                               $("#stu-table-num").html(ss.resultTableNum);
                               var tableItemList = $.parseJSON(ss.resultTableJsonString).tableItemList;
                               var $item = $(".table-item:first");
                               log($item);
                               var $itemContainer = $(".table-item-container");
                               $.each(tableItemList,function(){
                                   var item = $item.clone().css("display","");

                                   item.find(".table-item-key").html(this.context);
                                   item.find(".table-item-avgScore").html(this.avgScore);
                                   item.find(".table-item-level").attr("value","["+this.scoreLevelCnts+"]");
                                   $itemContainer.append(item);
                               });
                               renderProcessBar();
                               container.show();
                           } else{
                               showGlobalNotification(data.message);
                           }
                       });
                   }else{
                       showGlobalNotification("未知的sid："+sid);
                   }
               }else{
                   showGlobalNotification("未知的season:"+season)
               }
           });
        });
    </script>

</body>
</html>
