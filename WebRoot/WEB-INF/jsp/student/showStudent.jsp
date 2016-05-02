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

                        <jsp:include page="student-info-table.jsp"/>

                        <div class="bs-callout bs-callout-info">
                            <c:set scope="request" var="userType" value="student"/>
                            <jsp:include page="../model/seasonSelectForm.jsp"/>
                            <a href="${pageContext.request.contextPath}/admin/student-season/info"
                               class="btn btn-danger show-stu-statistic" > 查看学生统计信息 </a>

                        </div>

                        <div id="student-season-statistic" class="bs-callout bs-callout-info" style="display: none">
                            <h4 id="student-season"></h4>
                            <hr>
                                <h4>学期平均得分:<code id="stu-avgScore"></code> &nbsp;&nbsp;  评教人数:<code id="stu-table-num"></code>  &nbsp;&nbsp;
                                    各批次平均得分: <code class="stu-avgScore-list"></code>
                                </h4>
                            <br>
                            <span >得分统计:</span><div  class="eval-process-bar progress avgScore-process-bar" value="" ></div>

                            <hr>
                            <div class="row table-item-container">

                            </div>

                            <div class="col-sm-6 table-item" style="display: none">
                                <span class="table-item-key"></span>
                                (平均得分:<code class="table-item-avgScore"></code>/<code class="table-item-maxLevel"></code>分 <code class="table-item-percent"></code>)
                                <div class="eval-process-bar progress table-item-level" value="" ></div>
                            </div>

                            <hr>
                            <h4>学生收到的评价：</h4>
                            <div class="row question-container">

                            </div>

                            <div class="col-sm-6 question-item"  style="display: none">
                                <ul class="list-group">
                                </ul>
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
        function fillTableItem(stuSeason) {
            var tableItemList = $.parseJSON(stuSeason.resultTableJsonString).tableItemList;
            var $item = $(".table-item:first");
            var $itemContainer = $(".table-item-container");
            $itemContainer.html('');
            $.each(tableItemList, function () {
                var item = $item.clone().css("display", "");
                item.find(".table-item-maxLevel").html(this.maxLevel);
                item.find(".table-item-percent").html(this.percent + "%");
                item.find(".table-item-key").html(this.context);
                item.find(".table-item-avgScore").html(this.avgScore);
                item.find(".table-item-level").attr("value", "[" + this.scoreLevelCnts + "]");
                $itemContainer.append(item);
            });
        }
        function fillQuestionList(stuSeason){

            var questList = $.parseJSON(stuSeason.questionListString);
            var questContainer = $(".question-container");
            questContainer.html('');
            var questItem = $(".question-item");
            $.each(questList,function(){
                var itemContainer = questItem.clone().css("display","");
                var qItem = $("<li  class='list-group-item'></li>");
                itemContainer.find(".list-group").append(qItem.clone().addClass("active").html(this.left));        //问题题目
                $.each(this.right,function(index,ans){       //问题回答
                   itemContainer.find(".list-group").append(qItem.clone().html(ans));
                });
                questContainer.append(itemContainer);
            });
        }
        $(function(){
            $(".show-stu-statistic").click(function(e){
                e.preventDefault();
                var container = $("#student-season-statistic");

                if(!container.is(":hidden")){    //如果已经打开了 那么关闭了 并返回
                   container.hide();
                   $(this).html("查看学生评教统计信息");
                   return ;
               }
               container.show();
               $(this).html("隐藏学生评教统计信息");
               var season = $("#season").val();
               if(season){
                   var sid = $("input[name='sid']").val();
                   if(sid){
                       var $this = $(this);
                       $.post($this.attr("href"),{sid:sid,season:season},function(data){
                           if(data.success){
                                log(data.item);

                               var ss = data.item;
                               $("#student-season").html(ss.season);
                               $("#stu-avgScore").html(ss.avgScore+"分");

                               $(".avgScore-process-bar").attr("value","["+ss.levelCnts+"]");
                               $(".stu-avgScore-list").html(ss.avgScoreList);
                               $("#stu-table-num").html(ss.resultTableNum+"人");

                               fillTableItem(ss);
                               fillQuestionList(ss);
                               renderProcessBar();
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
