<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<html>
<head>
    <%@ include file="../header.jspf"%>

    <title>教师统计详情</title>
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

            <input type="hidden" name="tid" id="tid" value="${teacher.id}">
            <div class="bs-callout bs-callout-danger">
                <ol class="breadcrumb">
                    <li><a href="${pageContext.request.contextPath}/admin/teacher">教师列表</a></li>
                    <li><a href="${pageContext.request.contextPath}/admin/teacher/show?tid=${teacher.id}">教师详情</a></li>
                    <li class="active">教师统计信息</li>
                </ol>
                    <jsp:include page="teacher-info-table.jsp"/>
            </div>


            <div class="bs-callout bs-callout-info">
                <row>
                    <div id="tea-score-trend-chart" style="height: 400px">

                    </div>
                </row>
             </div>
        </div>
    </div>

</div>
<%@ include file="../buttom.jsp"%>

<script type="text/javascript" src="${pageContext.request.contextPath}/js/tqe/course/eval-process-bar.js"></script>
<script type="text/javascript">



    function fillTeaScoreTrendChart(teaVO) {
        var batchScoreList = teaVO.batchScoreList;
        if(batchScoreList){
            var teaScoreTrendChart = echarts.init(document.getElementById('tea-score-trend-chart'),echartTheme);
            var batchNames = [];
            var stuScores = [];
            var teaScores = [];
            var stuAvg = [];    //全局学生平均得分
            var teaAvg = [];    //全局教师平均得分
            var bs ;
            $.each(batchScoreList,function(i){
                batchNames.push(this.batchName);
                stuScores.push(this.stuAvgScore);
                teaScores.push(this.teaAvgScore);
                stuAvg.push(this.stuAvg);
                teaAvg.push(this.teaAvg);
            });

            /*debug
             batchNames=['2015','2016','2017','2018'];
             stuScores = [65,50,90,86];
             teaScores = [78,95,83,78];
             */
            var option = {
                title: {
                    text: '教师评分趋势图',
                    left: 'center'
                },
                tooltip : {
                    trigger: 'axis'
                },
                legend: {
                    data:['学生评分','学生平均','教师评分','教师平均'],
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
                        data:stuScores
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
                        data:stuAvg
                    },
                    {
                        name:'教师评分',
                        type:'line',
                        label: {
                            normal: {
                                show: true,
                                position: 'top'
                            }
                        },
                        lineStyle:{
                            normal:{
                                color:"blue",
                                width:3,
                            }
                        },
                        data:teaScores
                    },
                    {
                        name:'教师平均',
                        type:'line',
                        lineStyle:{
                            normal:{
                                color:"blue",
                                type:"dashed",
                                width:1,
                            }
                        },
                        data:teaAvg
                    }
                ]
            };
            teaScoreTrendChart.setOption(option);

        }else{
            showGlobalNotification("没有信息需要展示");
        }
    }


    $(function(){

        var tid= $("#tid").val();

        $.get("../teacher/vo-info",{tid:tid},function(data){
            if(data.success){
                var teaVO = data.item;
                fillTeaScoreTrendChart(teaVO);

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

