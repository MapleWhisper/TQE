<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!--导航条 -->
<nav class="navbar navbar-default navbar-fixed-top ">

	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#"></a>
		</div>

		<!-- Collect the nav links, forms, and other content for toggling -->
		<div class="collapse navbar-collapse"
			id="bs-example-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<%--<li ><a--%>
					<%--href="${pageContext.request.contextPath}/admin/admin"><span--%>
						<%--class="glyphicon glyphicon-home"></span>&nbsp;&nbsp;后台管理</a></li>--%>
                <li><a ><span class="glyphicon glyphicon-home"></span> 教学质量评教</a></li>
			</ul>
			<form class="navbar-form navbar-left" role="search"></form>
			<ul class="nav navbar-nav navbar-right">
				<li class="active">
					<p class="navbar-text">
						您好，${sessionScope.teacher.name}
						${sessionScope.student.name}${sessionScope.admin.name}
						${sessionScope.leader.name}
					</p>
				</li>

				<%--<li><a--%>
					<%--href="${pageContext.request.contextPath}/admin/resetPwdUI" ><span--%>
                        <%--class="glyphicon glyphicon-edit"></span>  修改密码</a></li>--%>
                <li><a data-toggle="modal" data-target="#reset-pwd" href="#"
                       data-remote="${pageContext.request.contextPath}/admin/resetPwdUI">
                    <span class="glyphicon glyphicon-edit"></span>  修改密码</a></li>

				<li ><a
					href="${pageContext.request.contextPath}/logout"><span
						class="glyphicon glyphicon-log-in"></span>&nbsp;&nbsp;退出登陆</a></li>

			</ul>
		</div>
		<!-- /.navbar-collapse -->
	</div>
	<!-- /.container-fluid -->
</nav>


