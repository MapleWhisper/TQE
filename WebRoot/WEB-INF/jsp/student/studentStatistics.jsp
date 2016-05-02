<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
    <%@ include file="../header.jspf"%>

    <title>学生统计详情</title>
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

            <input type="hidden" name="sid" id="sid" value="${student.sid}">
            <div class="bs-callout bs-callout-danger">
                <ol class="breadcrumb">
                    <li><a href="${pageContext.request.contextPath}/admin/student">学生列表</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/student/show?sid=${student.sid}">学生详情</a></li>
                    <li class="active">学生统计信息</li>
                </ol>
                <div class="row">
                    <jsp:include page="student-info-table.jsp"/>
                </div>
            </div>


            <div class="bs-callout bs-callout-info">
                <row>
                    <div id="stu-score-trend-chart" style="height: 400px">

                    </div>
                </row>
            </div>
        </div>
    </div>

</div>
<%@ include file="../buttom.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/tqe/course/eval-process-bar.js"></script>
<script type="text/javascript">



    function fillStuScoreTrendChart(teaVO) {
        var batchScoreList = teaVO.batchScoreList;
        if(batchScoreList){
            var stuScoreTrendChart = echarts.init(document.getElementById('stu-score-trend-chart'),echartTheme);
            var batchNames = [];
            var teaStuScores = [];
            var teaStuAvg = [];    //全局教师平均得分
            var bs ;
            $.each(batchScoreList,function(i){
                batchNames.push(this.batchName);
                teaStuScores.push(this.teaStuAvgScore);
                teaStuAvg.push(this.teaStuAvg);
            });

            /*debug
             batchNames=['2015','2016','2017','2018'];
             stuScores = [65,50,90,86];
             teaScores = [78,95,83,78];
             */
            var option = {
                title: {
                    text: '学生评分趋势图',
                    left: 'center'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['学生评分','学生平均'],
                    left: 'right'
                },
                grid: {
                    left: '3%',
                    right: '4%',
                    bottom: '3%',
                    containLabel: true
                },
                xAxis : [
                    {
                        type : 'category',
                        boundaryGap : false,
                        data : batchNames
                    }
                ],
                yAxis : [
                    {
                        type : 'value',
                        min : 'dataMin',
                        max : 100
                    }
                ],
                series : [
                    {
                        name:'学生评分',
                        type:'line',
                        label: {
                            normal: {
                                show: true,
                                position: 'top'
                            }
                        },
                        lineStyle:{
                            normal:{
                                color:"red",
                                width:3,
                            }
                        },
                        data:teaStuScores
                    },
                    {
                        name:'学生平均',
                        type:'line',
                        lineStyle:{
                            normal:{
                                color:"red",
                                width:1,
                                type:"dashed"
                            }
                        },
                        data:teaStuAvg
                    }
                ]
            };
            stuScoreTrendChart.setOption(option);

        }else{
            showGlobalNotification("没有信息需要展示");
        }
    }


    $(function(){

        var sid= $("#sid").val();

        $.get("../student/vo-info",{sid:sid},function(data){
            if(data.success){
                var stuVO = data.item;
                fillStuScoreTrendChart(stuVO);

            }else{
                showGlobalNotification(data.msg);
            }
        });
        $('[data-toggle="tooltip"]').tooltip();
    });
</script>
<script src="${pageContext.request.contextPath}/js/echarts/echarts.common.min.js"></script>
<script src="${pageContext.request.contextPath}/js/echarts/macarons.js"></script>

</body>
</html>

