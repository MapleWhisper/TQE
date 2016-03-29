<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
    <%@ include file="../header.jspf"%>
    <style type="text/css">
        .no1 {
            margin-top: 30px;
        }
        .eval-level-cnts{
            height: 400px;
        }
    </style>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"/>

    <title>课程列表</title>
</head>

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
            <input type="hidden" id="cid" name="cid" value="${courseModel.course.cid}"/>
            <input type="hidden" id="cno" name="cno" value="${courseModel.course.cno}"/>
            <input type="hidden" id="bid" name="bid" value="${batch.id}"/>
            <table
                    class="table table-hover table-striped " style="text-align: center">
                <tr class="info">
                    <td>课程名</td>
                    <td>教师</td>
                    <td>学院</td>
                    <td>学期</td>
                    <td>课程号</td>
                    <td>课序号</td>
                    <td>合班</td>
                <tr>
                    <td>${courseModel.course.name }</td>
                    <td>${courseModel.course.teacher.name	 }</td>
                    <td>${courseModel.course.department }</td>
                    <td>${courseModel.course.season }</td>
                    <td>${courseModel.course.cid }</td>
                    <td>${courseModel.course.cno }</td>
                    <td>${courseModel.course.combine }</td>


                </tr>
            </table>
            <div class="bs-callout bs-callout-info">
                <h4>该课程的所有批次:</h4>
                <div class="row">
                    <table
                            class="table table-hover table-striped  table-condensed">
                        <thead>
                        <tr class="info">
                            <td>序号</td>
                            <td>批次名</td>
                            <td>学期</td>
                            <td>开始/结束时间</td>
                        </tr>
                        </thead>
                        <tbody>
                             <c:forEach items="${courseModel.batchesList}" var="b" varStatus="s">
                                 <tr>
                                     <td>${s.count}</td>
                                     <td><a
                                             href="${pageContext.request.contextPath}/admin/batches/show/${b.batches.id}">${b.batches.name}</a></td>
                                     <td>${b.batches.season}</td>
                                     <td><fm:formatDate value="${b.batches.beginDate}"  dateStyle="medium"/> ~
                                         <fm:formatDate value="${b.batches.endDate}"  dateStyle="medium"/></td>

                                 </tr>
                              </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <div class="bs-callout bs-callout-info">
                <h4 class="panel-heading">
                    <a
                            href="${pageContext.request.contextPath}/admin/batches/show/${batch.id}"
                            target="_blank">${batch.name}</a>
                    <a style="padding-left: 50px;"
                       href="${pageContext.request.contextPath}/admin/evalTable/show/${batch.stuEvalId}"
                       target="_blank">点我查看评教表</a>
                    <a style="padding-left: 30px;">
                        评教时间: <fm:formatDate value="${batch.beginDate}"  dateStyle="medium"/> ~ <fm:formatDate value="${b.batches.endDate}"  dateStyle="medium"/>
                    </a>
                </h4>
                <hr>
                <h4 class="">学生评教进程: 已评 <code>${courseBatch.stuEvalCnt}</code> 人/ 共 <code>${courseBatch.stuEvalTotal}</code> 人</h4>
                <div class="progress">
                    <div
                            class="progress-bar progress-bar-info progress-bar-striped active"
                            role="progressbar" aria-valuenow="10" aria-valuemin="0"
                            aria-valuemax="100" style="min-width:15%;" id="level">

                    </div>
                </div>
                <hr>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-4">
                        <h4>学生评教平均得分：<code>${courseBatch.stuEvalAvgScore}</code>分</h4>
                        <div id="stu-eval-level-cnts" class="eval-level-cnts" ></div>
                    </div>
                    <div class="col-sm-4">
                        <h4>教师评教平均得分：<code>${courseBatch.teaEvalAvgScore}</code>分</h4>
                        <div id="tea-eval-level-cnts" class="eval-level-cnts" ></div>
                    </div>
                    <div class="col-sm-4">
                        <h4>领导评教平均得分：<code>${courseBatch.leaEvalAvgScore}</code>分</h4>
                        <div id="lea-eval-level-cnts" class="eval-level-cnts" ></div>
                    </div>
                </div>
            </div>

        </div>

    </div>

</div>
<%@ include file="../buttom.jsp"%>


<script>

    function fillCourseBatchInfo(courseBatch){
        var stuEvalTotal = courseBatch.stuEvalTotal;
        var stuEvalCnt = courseBatch.stuEvalCnt;
        var stuEvalPercent = parseInt((stuEvalCnt/stuEvalTotal)*100);
        $(".progress .progress-bar:eq(0)").css("width",stuEvalPercent);
        $(".progress .progress-bar:eq(0)").html("评教已进行:"+stuEvalPercent+"%");

        var stuEvalLevelCntsChart = echarts.init(document.getElementById('stu-eval-level-cnts'));

        var stuEvalLevelCnts = courseBatch.stuEvalLevelCnts;
        var teaEvalLevelCnts = courseBatch.teaEvalLevelCnts;
        var leaEvalLevelCnts = courseBatch.leaEvalLevelCnts;

        // 指定图表的配置项和数据
        var option = {
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b}个: {c} ({d}%)"
            },
            legend: {
                orient: 'horizontal',
                x: 'top',
                data:['优秀','良好','一般','差']
            },
            series: [
                {
                    name:'学生评教等级',
                    type:'pie',
                    radius: ['30%', '50%'],
                    avoidLabelOverlap: false,
                    label: {
                        normal: {
                            show: true,
                            formatter: "{b}{c}个\n({d}%)"
                        }
                    },
                    labelLine: {
                        normal: {
                            show: false
                        }
                    },

                    data:[
                        {value:stuEvalLevelCnts[0], name:'优秀',select:true},
                        {value:stuEvalLevelCnts[1], name:'良好'},
                        {value:stuEvalLevelCnts[2], name:'一般'},
                        {value:stuEvalLevelCnts[3], name:'差'}
                    ]
                }
            ]
        };


        stuEvalLevelCntsChart.setOption(option);

        //教师评教等级饼图绘制
        option.series[0].data = [
            {value: teaEvalLevelCnts[0], name: '优秀', select: true},
            {value: teaEvalLevelCnts[1], name: '良好'},
            {value: teaEvalLevelCnts[2], name: '一般'},
            {value: teaEvalLevelCnts[3], name: '差'}
        ];
        option.series[0].name = "教师评教等级";
        var teaEvalLevelCntsChart = echarts.init(document.getElementById('tea-eval-level-cnts'));
        teaEvalLevelCntsChart.setOption(option);

        //领导评教等级饼图绘制
        option.series[0].data = [
            {value: leaEvalLevelCnts[0], name: '优秀', select: true},
            {value: leaEvalLevelCnts[1], name: '良好'},
            {value: leaEvalLevelCnts[2], name: '一般'},
            {value: leaEvalLevelCnts[3], name: '差'}
        ];
        option.series[0].name = "领导评教等级";
        var leaEvalLevelCntsChart = echarts.init(document.getElementById('lea-eval-level-cnts'));
        leaEvalLevelCntsChart.setOption(option);
    }

    $(function(){

        var cid= $("#cid").val();
        var cno= $("#cno").val();
        var bid= $("#bid").val();

        $.get("/admin/course-batch/info",{cid:cid,cno:cno,bid:bid},function(data){
            if(data.success){
                var courseBatch = data.item;
                fillCourseBatchInfo(courseBatch);

            }else{
                showGlobalNotification(data.msg);
            }
        });
    });
</script>
<script src="${pageContext.request.contextPath}/js/echarts.common.min.js"></script>

</body>
</html>

