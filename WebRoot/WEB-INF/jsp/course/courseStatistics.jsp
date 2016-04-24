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
        .eval-table-item-cnts{
            height: 400px;
        }
    </style>

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
            <input type="hidden" id="bid" name="bid" value="${courseBatch.batch.id}"/>
            <div class="bs-callout bs-callout-danger">
                <div class="row">
                    <div class="col-sm-3">
                        <h4 >${course.teacher.name} -- ${course.name} (${course.season })</h4>
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
                                <td> <div class="eval-process-bar" value="${course.teaEvalLevelCnts}"></div> </td>
                            </tr>
                            <tr>
                                <td>领导评教</td>
                                <td> <code>${course.leaEvalAvgScore}</code>分</td>
                                <td> <code>${course.leaEvalScores}</code>分 </td>
                                <td> <div class="eval-process-bar" value="${course.leaEvalLevelCnts}"></div> </td>
                            </tr>
                        </table>
                    </div>
                </div>
            </div>
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
                                     <td><a class="btn btn-primary" data-toggle="tooltip" data-placement="right" title="点击查看课程统计信息"
                                             href="${pageContext.request.contextPath}/admin/statistics/course?cid=${courseModel.course.cid}&cno=${courseModel.course.cno}&bid=${b.batches.id}">
                                             ${b.batches.name}
                                        </a>
                                     </td>
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
                            href="${pageContext.request.contextPath}/admin/batches/show/${courseBatch.batch.id}"
                            target="_blank">${courseBatch.batch.name}</a>
                    <a style="padding-left: 50px;"
                       href="${pageContext.request.contextPath}/admin/evalTable/show/${courseBatch.batch.stuEvalId}"
                       target="_blank">点我查看评教表</a>
                    <a style="padding-left: 30px;">
                        评教时间: <fm:formatDate value="${courseBatch.batch.beginDate}"  dateStyle="medium"/> ~ <fm:formatDate value="${courseBatch.batch.endDate}"  dateStyle="medium"/>
                    </a>
                </h4>
                <hr>
                <h4 class="">学生评教进程: 已评 <code>${courseBatch.stuEvalCnt}</code> 人/ 共 <code>${courseBatch.stuEvalTotal}</code> 人</h4>
                <div class="progress stu-eval-progress" style="margin-top: 20px">
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

                <hr>
                <div class="row" style="margin-top: 20px;">
                    <div class="col-sm-6">
                        <h4>学生评教打分表统计</h4>
                        <div id="stu-eval-table-item-cnts" class="eval-table-item-cnts" ></div>
                    </div>
                    <div class="col-sm-6">
                        <h4>教师评教打分表统计</h4>
                        <div id="tea-eval-table-item-cnts" class="eval-table-item-cnts" ></div>
                    </div>
                    <div class="col-sm-6">
                        <h4>领导评教打分表统计</h4>
                        <div id="lea-eval-table-item-cnts" class="eval-table-item-cnts" ></div>
                    </div>

                </div>
                <hr>

                <div class="row" style="margin-top: 20px">
                    <div role="tabpanel">

                        <!-- Nav tabs -->
                        <ul class="nav nav-tabs" role="tablist">
                            <li role="presentation" class="active"><a href="#student"
                                                                      aria-controls="student" role="tab" data-toggle="tab"><h4>学生问题回答和建议</h4></a></li>
                            <li role="presentation"><a href="#teacher"
                                                       aria-controls="teacher" role="tab" data-toggle="tab"><h4>教师问题回答和建议</h4></a></li>
                            <li role="presentation"><a href="#leader"
                                                       aria-controls="leader" role="tab" data-toggle="tab"><h4>领导问题回答和建议</h4></a></li>
                        </ul>

                        <!-- Tab panes -->
                        <div role="tabpanel" class="tab-content" >
                            <!-- 学生评论信息 -->
                            <div role="tabpanel" class="tab-pane active"  id="student" >
                                <c:if test="${courseBatch.stuQuestionList == null}">
                                    <h3>暂无评教数据</h3>
                                </c:if>
                                <c:forEach var="quest" items="${courseBatch.stuQuestionList}" varStatus="v">

                                        <div class="panel panel-info ">
                                            <div class="panel-heading" style="text-align: center"><h4> <code>${v.count}</code> ${quest.left}</h4></div>
                                            <!-- Table -->
                                            <table class="table table-hover table-condensed">

                                                <tbody>
                                                <c:forEach varStatus="vs" var="q" items="${quest.right}">
                                                    <tr>
                                                        <td># ${vs.count}</td>
                                                        <td>${q}</td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>

                                </c:forEach>
                            </div>

                            <!-- 教师评论信息 -->
                            <div role="tabpanel" class="tab-pane " id="teacher">
                                <c:if test="${courseBatch.teaQuestionList == null}">
                                    <h3>暂无评教数据</h3>
                                </c:if>
                                <c:forEach var="quest" items="${courseBatch.teaQuestionList}" varStatus="v">
                                        <div class="panel panel-default">
                                            <div class="panel-heading" style="text-align: center"><h4> <code>${v.count}</code> ${quest.left}</h4></div>
                                            <!-- Table -->
                                            <table class="table table-hover table-condensed">


                                                <tbody>
                                                <c:forEach varStatus="vs" var="q" items="${quest.right}">
                                                    <tr>
                                                        <td># ${vs.count}</td>
                                                        <td>${q}</td>
                                                    </tr>
                                                </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                </c:forEach>
                            </div>



                            <!-- 领导评论信息 -->
                            <div role="tabpanel" class="tab-pane" id="leader">
                                <c:if test="${courseBatch.leaQuestionList == null}">
                                    <h3>暂无评教数据</h3>
                                </c:if>
                                <c:forEach var="quest" items="${courseBatch.leaQuestionList}" varStatus="v">
                                        <div class="panel panel-default">
                                            <div class="panel-heading" style="text-align: center"><h4> <code>${v.count}</code> ${quest.left}</h4></div>
                                            <!-- Table -->
                                            <table class="table table-hover table-condensed">

                                                <tbody>
                                                    <c:forEach varStatus="vs" var="q" items="${quest.right}">
                                                        <tr>
                                                            <td># ${vs.count}</td>
                                                            <td>${q}</td>
                                                        </tr>
                                                    </c:forEach>
                                                </tbody>
                                            </table>
                                        </div>
                                </c:forEach>
                            </div>

                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<%@ include file="../buttom.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/tqe/course/eval-process-bar.js"></script>
<script type="text/javascript">

    function fillCourseBatchInfo(courseBatch){
        var stuEvalTotal = courseBatch.stuEvalTotal;
        var stuEvalCnt = courseBatch.stuEvalCnt;
        var stuEvalPercent = parseInt((stuEvalCnt/stuEvalTotal)*100);
        $(".stu-eval-progress").find(".progress-bar").css("width",stuEvalPercent);
        $(".stu-eval-progress").find(".progress-bar").html("评教已进行:"+stuEvalPercent+"%");

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



        fillTableItemLevelInfo(courseBatch,'stu');
        fillTableItemLevelInfo(courseBatch,'tea');
        fillTableItemLevelInfo(courseBatch,'lea');
        //TODO
    }

    /**
     *  打分表的 表项的统计图的绘制
     */
    function fillTableItemLevelInfo(courseBatch,type) {
        var id = type+'-eval-table-item-cnts';
        var evalTableItemChart = echarts.init(document.getElementById(id));

        var evalTable = $.parseJSON(courseBatch.stuEvalTableJsonString);
        if(type=='tea'){
            evalTable = $.parseJSON(courseBatch.teaEvalTableJsonString);
        }
        if(type=='lea'){
            evalTable = $.parseJSON(courseBatch.leaEvalTableJsonString);
        }
        if(!evalTable){
            $("#"+id).html("<h3 style='text-align: center;margin-top: 30px;'>暂无评教数据</h3>");
            return ;
        }
        var tableItemList = evalTable.tableItemList;
        var itemList = [];

        var bestCnt=[];
        var goodCnt=[];
        var avgCnt=[];
        var badCnt=[];
        $.each(tableItemList,function(){
            var itemName = this.context;
            var item = {
                value:itemName + "\n \n 平均得分: "+this.avgScore+" 分/共"+this.maxLevel+"分("+this.percent+"%)",
                textStyle:{
                    fontSize:15
                }
            };
            itemList.push(item);
            bestCnt.push(this.scoreLevelCnts[0]);
            goodCnt.push(this.scoreLevelCnts[1]);
            avgCnt.push(this.scoreLevelCnts[2]);
            badCnt.push(this.scoreLevelCnts[3]);
        });


        var option = {
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            legend: {
                data: ['优','良','中','差']
            },
            grid: {
                left: '5%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis:  {
                type: 'value'
            },
            yAxis: {
                type: 'category',
                data: itemList
            },
            series: [
                {
                    name: '优',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'inside',
                            formatter: "{a}{c}个"
                        }
                    },
                    data: bestCnt
                },
                {
                    name: '良',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'inside',
                            formatter: "{a}{c}个"
                        }
                    },
                    data: goodCnt
                },
                {
                    name: '中',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'inside',
                            formatter: "{a}{c}个"
                        }
                    },
                    data: avgCnt
                },
                {
                    name: '差',
                    type: 'bar',
                    stack: '总量',
                    label: {
                        normal: {
                            show: true,
                            position: 'inside',
                            formatter: "{a}{c}个"
                        }
                    },
                    data: badCnt
                }
            ]
        };
        evalTableItemChart.setOption(option);
    }



    $(function(){

        var cid= $("#cid").val();
        var cno= $("#cno").val();
        var bid= $("#bid").val();

        $.get("../course-batch/info",{cid:cid,cno:cno,bid:bid},function(data){
            if(data.success){
                var courseBatch = data.item;
                fillCourseBatchInfo(courseBatch);

            }else{
                showGlobalNotification(data.msg);
            }
        });
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>
<script src="${pageContext.request.contextPath}/js/echarts.common.min.js"></script>

</body>
</html>

