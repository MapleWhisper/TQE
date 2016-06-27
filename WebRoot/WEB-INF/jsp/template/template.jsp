<%@ page language="java" pageEncoding="UTF-8" %>

<html>
<head>
    <%@ include file="../header.jspf" %>
    <style type="text/css">
        .no1 {
            margin-top: 30px;
        }
    </style>
    <script
            src="${pageContext.request.contextPath}/js/jquery.validate.min.js"></script>
    <script
            src="${pageContext.request.contextPath}/js/messages_zh.min.js"></script>
    <link rel="stylesheet"
          href="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.css"/>
    <title>模板列表</title>
</head>

<body>
<div class="container-fluid">
    <div class="row">
        <%@ include file="../head.jsp" %>
    </div>
    <div class="row ">
        <div class="col-sm-2">
            <%@ include file="../left.jsp" %>
        </div>
        <div class="col-sm-10 ">

            <div class="panel panel-default">
                <div class="panel-heading">模板列表</div>
                <div class="panel-body">
                    <table class="table table-hover table-striped table-bordered table-condensed">
                        <thead>
                            <tr class="info">
                                <td>模板名</td>
                                <td>模板类型</td>
                                <td>模板列名</td>
                                <td>操作</td>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${templateList}" var="t">
                                <tr>
                                    <td><a href="${pageContext.request.contextPath}/admin/template?type=${t.type}">
                                        ${t.name }</a>
                                    </td>
                                    <td>${t.type }</td>
                                    <td>${t.columns }</td>
                                    <td><a href="${pageContext.request.contextPath}/admin/template?type=${t.type}"
                                           class="btn btn-info btn-sm"  icon="zoom-in">查看</a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>

            </div>

            <c:if test="${template != null}">
                <div class="panel panel-default">
                    <div class="panel-heading">${template.name} ( ${template.type} )</div>
                    <div class="panel-body">
                        <table class="table table-hover table-striped table-bordered table-condensed">
                            <thead>
                            <tr class="info">
                                <c:forEach items="${template.columns}" var="column">
                                    <td>${column}</td>
                                </c:forEach>
                                <td>操作</td>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${template.items}" var="item">
                                <tr item-id="${item.id}">
                                    <c:forEach items="${item.values}" var="value">
                                        <td class="item-value">${value}</td>
                                    </c:forEach>
                                    <td><a data-toggle="modal" data-target="#edit-template-item"
                                           class="btn btn-info btn-sm"  icon="edit">修改</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="row">
                            <div class="col-xs-6 col-xs-offset-5">
                                <div class="no1">
                                    <a class="btn btn-primary" icon="plus" data-toggle="modal"
                                       data-target="#add-template-item">添加${template.name}</a>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>
            </c:if>
        </div>
    </div>
</div>


<!-- 添加模板Modal -->
<div class="modal fade" id="add-template-item" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="add-admin-modal-title">添加${template.name}</h4>
            </div>
            <div class="modal-body">
                <jsp:include page="add-template.jsp"/>
            </div>
        </div>
    </div>
</div>

<!-- 修改模板Modal -->
<div class="modal fade" id="edit-template-item" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="edit-admin-modal-title">修改${template.name}</h4>
            </div>
            <div class="modal-body">
                <jsp:include page="edit-template.jsp"/>
            </div>
        </div>
    </div>
</div>


<%@ include file="../buttom.jsp" %>
<script
        src="${pageContext.request.contextPath}/js/datatables/js/jquery.dataTables.min.js"></script>
<script
        src="${pageContext.request.contextPath}/js/datatables/dataTables.bootstrap.js"></script>


<script type="text/javascript">
    $(function () {

        $('#edit-template-item').on('show.bs.modal', function (event) {
            var button = $(event.relatedTarget);
            var itemId = button.parents('tr').attr('item-id');
            if(itemId==null){
                alert('未知的ItemId:'+itemId);
            }
            $("#edit-item-id").val(itemId);
            var values=[];
            $.each(button.parents('tr').find('.item-value'),function(){
                values.push($(this).html());
            });
            if(values.length>0){
                $.each($("#edit-template-form").find('textarea'),function(index){
                    $(this).val(values[index]);
                });
            }else{
                alert('没有找到要修改的记录');
            }

        });
        autoAddIcon();

    });

</script>
</body>
</html>

