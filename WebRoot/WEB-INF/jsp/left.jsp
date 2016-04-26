<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.text.*"%>
<div class="tree well">
	<div style="margin-left: 15px;margin-top: 10px;">
		<p>
			<code><%=DateFormat.getDateInstance(DateFormat.FULL,Locale.SIMPLIFIED_CHINESE).format(
					new Date())%><br>
			</code>
		</p>

	</div>
	<ul style="margin-left: -40px">
		<!-- 主菜单 -->
		<li><span class="glyphicon glyphicon-folder-open"> 主菜单</span>
			<ul>
                <c:if test="${sessionScope.admin == null}">
                    <li class="root"><span><i
                             ></i>评教中心</span>
                        <ul>
                            <li
                                style="display: ${sessionScope.pMap.stuEval==true ? '' : 'none'}">
                                <span><i class="glyphicon glyphicon-leaf"></i> <a
                                    href="${pageContext.request.contextPath}/admin/stuEval">学生评教
                                </a></span>
                            </li>
                            <li
                                style="display: ${sessionScope.pMap.teaEval==true ? '' : 'none'}">
                                <span><i class="glyphicon glyphicon-leaf"></i> <a
                                    href="${pageContext.request.contextPath}/admin/teaEval">教师评教</a></span>
                            </li>
                            <li
                                style="display: ${sessionScope.pMap.teaStuEval==true ? '' : 'none'}">
                                <span><i class="glyphicon glyphicon-leaf"></i> <a
                                    href="${pageContext.request.contextPath}/admin/teaStuEval">评教学生</a></span>
                            </li>
                            <li
                                style="display: ${sessionScope.pMap.leaEval==true ? '' : 'none'}">
                                <span><i class="glyphicon glyphicon-leaf"></i> <a
                                    href="${pageContext.request.contextPath}/admin/leaEval">领导评教</a></span>
                            </li>
                        </ul>
                    </li>
                </c:if>


				<li class="root"><span><i
						 ></i>数据查询</span>
					<ul>
						<li
							style="display: ${sessionScope.pMap.course==true ? '' : 'none'}"><span><i
								class="glyphicon glyphicon-leaf"></i> <a
								href="${pageContext.request.contextPath}/admin/course">课程列表</a></span>
						</li>

						<li
							style="display: ${sessionScope.pMap.teacher==true ? '' : 'none'}"><span><i
								class="glyphicon glyphicon-leaf"></i> <a
								href="${pageContext.request.contextPath}/admin/teacher">教师列表</a></span>
						</li>

						<li
							style="display: ${sessionScope.pMap.student==true ? '' : 'none'}"><span><i
								class="glyphicon glyphicon-leaf"></i> <a
								href="${pageContext.request.contextPath}/admin/student">学生列表</a></span>
						</li>
					</ul></li>

                <!-- 如果是系统管理员，才显示页面 -->
                <c:if test="${sessionScope.admin != null}">
                    <li class="root"><span><i  ></i>评教管理</span>
                        <ul>
                            <li
                                    style="display: ${sessionScope.pMap.evalTable==true ? '' : 'none'}"><span><i
                                    class="glyphicon glyphicon-leaf"></i> <a
                                    href="${pageContext.request.contextPath}/admin/evalTable">评教指标</a></span>
                            </li>
                            <li
                                    style="display: ${sessionScope.pMap.batches==true ? '' : 'none'}"><span><i
                                    class="glyphicon glyphicon-leaf"></i> <a
                                    href="${pageContext.request.contextPath}/admin/batches">评教批次</a></span>
                            </li>
                        </ul>
                    </li>
                    <!--系统管理 -->
                    <li class="root"><span><i
                             ></i>系统管理</span>
                        <ul>
                            <li
                                style="display: ${sessionScope.pMap.admin==true ? '' : 'none'}"><span><i
                                    class="glyphicon glyphicon-leaf"></i> <a
                                    href="${pageContext.request.contextPath}/admin/admin">管理员表</a></span></li>
                            <li
                                style="display: ${sessionScope.pMap.privilege==true ? '' : 'none'}"><span><i
                                    class="glyphicon glyphicon-leaf"></i> <a
                                    href="${pageContext.request.contextPath}/admin/privilege">权限管理</a></span>
                            </li>
                            <li
                                    style="display: ${sessionScope.pMap.dataImport==true ? '' : 'none'}"><span><i
                                    class="glyphicon glyphicon-leaf"></i> <a
                                    href="${pageContext.request.contextPath}/admin/dataImport">数据导入</a></span>
                            </li>
                        </ul>
                    </li>
                </c:if>

			</ul></li>
	</ul>
	<!-- 主菜单 -->

</div>
<script>
	//如果二级目录没有子目录的话 那么就隐藏该级目录
	$(".tree li.root").map(function(){
		var $this = $(this);

		if($this.find("li:visible").length<1){
			$this.hide();
		}
	});
</script>
