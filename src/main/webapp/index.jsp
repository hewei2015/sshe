<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">


<html>
<head>
<title>首页</title>
<script type="text/javascript" src="jslib/jquery-easyui-1.3.1/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="jslib/jquery-easyui-1.3.1/jquery.easyui.min.js"></script>
<script type="text/javascript" src="jslib/jquery-easyui-1.3.1/locale/easyui-lang-zh_CN.js"></script>
<!-- 引入样式文件 -->
<link rel="stylesheet" href="jslib/jquery-easyui-1.3.1/themes/default/easyui.css" type="text/css"></link>
<!-- 引入相关的icon文件 -->
<link rel="stylesheet" href="jslib/jquery-easyui-1.3.1/themes/icon.css" type="text/css"></link>
<!-- 自己的js文件：一定要放在easyUI之后 -->
<script type="text/javascript" src="jslib/hwUtils.js"></script>
</head>

<body class="easyui-layout">
	<!-- center必须有，否则会出错，同时应该制定各个div的高度或宽度 -->
	<div data-options="region:'north'" style="height:60px;"></div>
	<div data-options="region:'south'" style="height:20px;"></div>
	<div data-options="region:'west'" style="width:200px;">
		<jsp:include page="layout/west.jsp"></jsp:include>
	</div>

	<!-- split:true表示不允许将功能区域块缩放 -->
	<div data-options="region:'east',title:'east',split:true" style="width:200px;"></div>

	<div data-options="region:'center',title:'Hw_SSHE学习示例'" style="overflow:hidden;">
		<jsp:include page="layout/center.jsp"></jsp:include>
	</div>

	<jsp:include page="user/login.jsp"></jsp:include>
	<jsp:include page="user/reg.jsp"></jsp:include>
</body>
</html>
