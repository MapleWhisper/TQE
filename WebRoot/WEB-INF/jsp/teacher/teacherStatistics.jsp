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
                <div class="row">
                    <jsp:include page="teacher-info-table.jsp"/>
                </div>
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
            var bs ;
            $.each(batchScoreList,function(i){
                batchNames.push(this.batchName);
                stuScores.push(this.stuAvgScore);
                teaScores.push(this.teaAvgScore);
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
                    data:['学生评分','教师评分'],
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
                        data:stuScores
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
                        data:teaScores
                    }
                ]
            };
            teaScoreTrendChart.setOption(option);

        }else{
            showGlobalNotification("没有信息需要展示");
        }
    }

    function fillCourseBatchInfo(courseBatch){


        var stuEvalLevelCntsChart = echarts.init(document.getElementById('stu-eval-level-cnts'),echartTheme);

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
        var teaEvalLevelCntsChart = echarts.init(document.getElementById('tea-eval-level-cnts'),echartTheme);
        teaEvalLevelCntsChart.setOption(option);

        //领导评教等级饼图绘制
        option.series[0].data = [
            {value: leaEvalLevelCnts[0], name: '优秀', select: true},
            {value: leaEvalLevelCnts[1], name: '良好'},
            {value: leaEvalLevelCnts[2], name: '一般'},
            {value: leaEvalLevelCnts[3], name: '差'}
        ];
        option.series[0].name = "领导评教等级";
        var leaEvalLevelCntsChart = echarts.init(document.getElementById('lea-eval-level-cnts'),echartTheme);
        leaEvalLevelCntsChart.setOption(option);



        fillTableItemLevelInfo(courseBatch,'stu');
        fillTableItemLevelInfo(courseBatch,'tea');
        fillTableItemLevelInfo(courseBatch,'lea');
        //TODO
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

