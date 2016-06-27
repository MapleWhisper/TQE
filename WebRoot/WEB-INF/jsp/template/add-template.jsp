<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<div class="container-fluid">
    <div class="row">
        <form action="${pageContext.request.contextPath }/admin/template/save" class="form-horizontal" role="form"
              id="add-template-form" method="post">

            <input name="type" type="hidden" value="${template.type}"/>
            <c:forEach items="${template.columns}" var="column">
                <div class="form-group">
                    <label  class="col-xs-2 control-label">${column}</label>
                    <div class="col-xs-9">
                        <textarea rows="3" type="text" class="form-control"
                                  name="values" required="required"></textarea>
                    </div>
                </div>
            </c:forEach>


            <div class="form-group">
                <div class="col-xs-4 col-xs-offset-3">
                    <a type="button" class="btn btn-default btn-lg model-close" data-dismiss="modal" icon="remove">关闭</a>
                </div>

                <div class="col-xs-4 ">
                    <button type="submit" class="btn btn-primary  btn-lg " icon="saved">保存${template.name}</button>
                </div>

            </div>
        </form>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        $("#add-template-form").validate({
            submitHandler:function(){
                ajaxSubmitForm($("#add-template-form"),{
                    reload:true
                });
            }
        });
    });
</script>


