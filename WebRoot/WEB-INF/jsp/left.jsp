<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import="java.text.*"%>
<div class="tree well">
	<div style="margin-left: 15px">
		今天是:<br>
		<p>
			<code><%=DateFormat.getDateInstance(DateFormat.FULL).format(
					new Date())%><br>
			</code>
		</p>

	</div>
	<ul style="margin-left: -40px">
		<!-- 主菜单 -->
		<li><span class="glyphicon glyphicon-folder-open">主菜单</span>
			<ul>
				<li class="root"><span><i
						class="glyphicon glyphicon-minus-sign"></i>评教中心</span>
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
							style="display: ${sessionScope.pMap.leaderEval==true ? '' : 'none'}">
							<span><i class="glyphicon glyphicon-leaf"></i> <a
								href="${pageContext.request.contextPath}/admin/leaderEval">领导评教</a></span>
						</li>
					</ul></li>
				<!-- 				
							
							<c:if test="${p.name == '申请管理' }">
								<li><span><i class="glyphicon glyphicon-minus-sign"></i>申请管理</span>
									<ul>
										<li><span><i class="glyphicon glyphicon-leaf"></i>
										<a href="${pageContext.request.contextPath}/admin/apply">申请审核</a></span>
										</li>
									</ul>
								</li>
							</c:if>
							
						
				<li><span><i class="glyphicon glyphicon-minus-sign"></i>试卷试题</span>
					<ul>
							
								<li><span><i class="glyphicon glyphicon-leaf"></i><a href="${pageContext.request.contextPath}/admin/paper">试卷管理</a></span></li>
								<li><span><i class="glyphicon glyphicon-leaf"></i><a href="${pageContext.request.contextPath}/admin/problem">试题管理</a></span></li>
						
						
					</ul>
				</li>
				简历管理 -->




				<!-- 如果是系统管理员，才显示页面 -->

				<li class="root"><span><i
						class="glyphicon glyphicon-minus-sign"></i>数据查询</span>
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
				<li><span><i class="glyphicon glyphicon-minus-sign"></i>评教管理</span>
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
					</ul></li>
				<!-- 				系统管理 -->
				<li class="root"><span><i
						class="glyphicon glyphicon-minus-sign"></i>系统管理</span>
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
					</ul></li>

			</ul></li>
	</ul>
	<!-- 主菜单 -->

</div>

