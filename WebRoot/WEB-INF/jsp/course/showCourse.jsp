
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="../header.jspf"%>

<title>课程评教详情|${course.name}</title>

    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"></link>
</head>
<style>
	.tab-pane{
		padding-top: 5px;
	}
    .course-batch-bs-callout{
        padding-bottom: 50px;
    }
</style>

<body>
	<div class="container-fluid">
        <div class="row">
		    <%@ include file="../head.jsp"%>
        </div>
		<div class="row " >
			<div class="col-sm-2">
				<%@ include file="../left.jsp"%>
			</div>
			<div class="col-sm-10 ">
                <div class="bs-callout bs-callout-danger">
                    <ol class="breadcrumb">
                        <li><a href="${pageContext.request.contextPath}/admin/course">课程列表</a></li>
                        <li class="active">课程详情</li>
                    </ol>
                    <div class="row">
                        <div class="col-sm-3">
                            <h4>

                                <a icon="user" href="${pageContext.request.contextPath}/admin/teacher/show?tid=${course.teacher.id}">
                                    ${course.teacher.name}
                                </a> <br><span icon="book"> ${course.name}</span> (${course.season })
                            </h4>
                            <hr>
                            <h5>${course.department } </h5>
                            <h5>课程号-课序号 :${course.cid}-${course.cno}</h5>
                            <h5>合班:${course.combine }</h5>
                        </div>
                        <div class="col-sm-9">
                            <table class="table no-border" style="text-align: center">
                                <tr class="thead">
                                    <td>角色</td>
                                    <td>平均得分</td>
                                    <td>各批次平均得分</td>
                                    <td>得分统计</td>
                                </tr>
                               <tr>
                                   <td>学生评教</td>
                                   <td> <code>${course.stuEvalAvgScore}</code>分 </td>
                                   <td> <code>${course.stuEvalScores}</code>分 </td>
                                   <td> <div class="eval-process-bar progress" value="${course.stuEvalLevelCnts}">${course.stuEvalLevelCnts}</div> </td>
                               </tr>
                                <tr>
                                    <td>教师评教</td>
                                    <td><code>${course.teaEvalAvgScore}</code>分 </td>
                                    <td> <code>${course.teaEvalScores}</code>分 </td>
                                    <td> <div class="eval-process-bar progress" value="${course.teaEvalLevelCnts}"></div> </td>
                                </tr>
                                <tr>
                                    <td>领导评教</td>
                                    <td> <code>${course.leaEvalAvgScore}</code>分</td>
                                    <td> <code>${course.leaEvalScores}</code>分 </td>
                                    <td> <div class="eval-process-bar progress" value="${course.leaEvalLevelCnts}"></div> </td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>

				<div role="tabpanel">

					<!-- Nav tabs -->
					<ul class="nav nav-tabs" role="tablist">
						<li role="presentation" class="active"><a href="#student"
							aria-controls="student" role="tab" data-toggle="tab">学生评教信息</a></li>
						<li role="presentation"><a href="#teacher"
							aria-controls="profile" role="tab" data-toggle="tab">教师评教信息</a></li>
						<li role="presentation"><a href="#leader"
							aria-controls="messages" role="tab" data-toggle="tab">领导评教信息</a></li>
					</ul>

					<!-- Tab panes -->
					<div class="tab-content">
						<!-- 学生评教信息表 -->
						<div role="tabpanel" class="tab-pane active" style="padding-top: 10px;" id="student">
							<jsp:include page="showCourseStu.jsp"/>

						</div>

						<!-- 教师评教信息表 -->
						<div role="tabpanel" class="tab-pane" id="teacher">
							<%@ include file="showCourseTea.jsp"%>
						</div>
						
						<!-- 教师评教信息表 -->
						<div role="tabpanel" class="tab-pane" id="leader">
							<%@ include file="showCourseLea.jsp"%>
						</div>
					</div>

				</div>

			</div>
		</div>

	</div>
	<!-- 	container -->
	<%@ include file="../buttom.jsp"%>
    <script
            src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.js"></script>
    <script src="${pageContext.request.contextPath}/js/tqe/course/eval-process-bar.js"></script>

    <script  type="text/javascript">

        $(function(){
            autoAddIcon();
        });
    </script>
</body>
</html>

