<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<script type="text/javascript">
	$(function() {
		$('#admin_userManag_datagrid').datagrid({
			url : '${pageContext.request.contextPath}/userAction!datagrid.action',
			fit : true, //随外面自动缩放
			fitColumns : true,//当列很少的时候，能让列自动填充所有区域
			border : false,
			pagination : true,
			idField : 'id',//★确定/获取所有选中项（跨页选中）
			rownumbers : true,//该属性会使得dataGrid渲染的时间会加长，不推荐使用（IE6），且显示区间不能扩大
			sortName : 'name',//要排序的字段，是Field的值，默认升序排序
			sortOrder : 'asc',//确定排序方式，不写默认升序
			pageSize : 10,
			pageList : [ 5, 10, 20, 30, 40, 50 ],//pageSize和pageList两个属性必须同时设置，且pageSize的值应该在pageList的数组里面
			//pagePosition:'both',//如果不写，则默认显示在下面
			//确定翻页用的toolbar缩放位置
			checkOnSelect : false,//点击行的时候，前面复选框是否选中
			selectOnCheck : false,//点击前面复选框的时候是否选中一行
			frozenColumns : [ [ {//★★冻结列
				field : 'id',
				title : '编号',
				width : 150,//如果不给宽度，则自动计算宽度，在渲染页面的时候将延长时间（很明显），建议所有列给定宽度
				//hidden : true,//隐藏列
				checkbox : true
			}, {
				field : 'name',
				title : '登录名',
				width : 150,
				sortable : true
			//★点击表列头，实现排序方式转换
			} ] ],
			columns : [ [ {//普通列
				field : 'pwd',
				title : '密码',
				width : 150,
				align : 'right',
				formatter : function(value, index, row) {//★鼠标放上面，内容看全
					return "**********";/*'<span title="'+value+'">' + value + '</span>'*/
				}
			}, {
				field : 'creatTime',
				title : '创建时间',
				width : 150,
				sortable : true
			//点击表列头，实现排序方式转换
			}, {
				field : 'endTime',
				title : '修改时间',
				width : 150,
				sortable : true
			//点击表列头，实现排序方式转换
			} ] ],
			/*toolbar : [ {//这是第一种方式，中括号里面每一项都是一个button
				text : '查询',
				iconCls : 'icon-search',
				handler : function() {//按钮点击触发事件

				}
			}, '-', {//'-'表示两个按钮之间的分隔形式
				text : '增加',
				iconCls : 'icon-search'
			} ]*/
			//toolbar : '#admin_userManag_toolbar'//第二种方式
			toolbar : [ {
				text : '增加',
				iconCls : 'icon-add',
				handler : function() {
					append();
				}
			}, '-', {
				text : '删除',
				iconCls : 'icon-remove',
				handler : function() {
					remove();
				}
			}, '-', {
				text : '编辑',
				iconCls : 'icon-edit',
				handler : function() {
					editFun();
				}
			}, '-' ]

		});

	});

	function editFun() {
		var rows = $('#admin_userManag_datagrid').datagrid('getChecked');
		//得到rows是数组
		if (rows.length == 1) {
			var d = $('<div/>').dialog({
				width : 280,
				height : 210,
				href : '${pageContext.request.contextPath}/admin/userManagEdit.jsp',//将目标页面载入进来
				modal : true,
				title : '编辑用户',
				buttons : [ {
					text : '编辑',
					handler : function() {//点击编辑是触发的事件
						// ★★用ajax方式和提交form表单的形式都是可以的
						$('#admin_userManagEdit_Form').form('submit', {
							url : '${pageContext.request.contextPath}/userAction!edit.action',
							success : function(data) {
								var obj = jQuery.parseJSON(data);
								if (obj.success) {
									//d.dialog('close');///▲▲▲为什么放到这里，后面的所有内容都不再执行
									//$('#admin_userManag_datagrid').datagrid('reload');
								 	$('#admin_userManag_datagrid').datagrid('updateRow', {
										index : $('#admin_userManag_datagrid').datagrid('getRowIndex', rows[0].id),
										row : obj.obj
									});
									d.dialog('close');//▲▲为什么关闭修改对话框必须放到之后，这里有问题。能成功，但是js报错
								}
								$.messager.show({
									title : '提示',
									msg : obj.msg
								});
							
							}
						});
					}
				} ],
				//★★动态加载dialog，则应该在最后写一个closed的事件，否则随着点击次数增加，dom结点变得越来越多【节点内存溢出】
				onClose : function() {
					$(this).dialog('destory');//点击关闭按钮删除相关结点
				},
				onLoad : function() {//★★在打开dialog的时候回显数据，这里不能用onOpen事件
					//最笨的方法：逐个实现回显
					/* $('#admin_userManagEdit_Form input[name=id]').val(rows[0].id);
					$('#admin_userManagEdit_Form input[name=name]').val(rows[0].name);
					$('#admin_userManagEdit_Form input[name=pwd]').val(rows[0].pwd);
					$('#admin_userManagEdit_Form input[name=creatTime]').val(rows[0].creatTime);
					$('#admin_userManagEdit_Form input[name=endTime]').val(rows[0].endTime); */
					$('#admin_userManagEdit_Form').form('load', rows[0]);//★等同于上面的多项
				}
			});
		} else {
			$.messager.alert('提示', '请选择一项数据');
		}
	}
	function searchFun() {
		/*
		$('#admin_userManag_datagrid').datagrid('load', {
			name : $('#admin_userManag_layout input[name=name]').val()
		});*/
		//这样处理多个条件的模糊查询：对应后台要扩展：addWhereCond()
		$('#admin_userManag_datagrid').datagrid('load', serializeObject($('#admin_userManag_searchForm')));
	}
	function clearFun() {
		$('#admin_userManag_layout input[name=name]').val('');
		$('#admin_userManag_datagrid').datagrid('load', {});//清空就是什么都不往后台传
	}
	function append() {
		$('#admin_userManag_addForm input').val('');//清空
		$('#admin_userManag_addDialog').dialog('open');
	}
	function remove() {
		//利用好getChecked和getSelect的意义
		var rows = $('#admin_userManag_datagrid').datagrid('getChecked');
		//var rows = $('#admin_yhgl_datagrid').datagrid('getSelected');
		//var rows = $('#admin_yhgl_datagrid').datagrid('getSelections');
		var ids = [];
		if (rows.length > 0) {
			$.messager.confirm('确认', '您是否要删除当前选中的项目？', function(r) {
				if (r) {
					for ( var i = 0; i < rows.length; i++) {
						ids.push(rows[i].id);
					}
					$.ajax({
						url : '${pageContext.request.contextPath}/userAction!remove.action',
						data : {
							ids : ids.join(',')
						},
						dataType : 'json',
						success : function(r) {
							//★load:使当前页变为1；reload：记住当前页。删除后不要执行reload
							$('#admin_userManag_datagrid').datagrid('load');
							$('#admin_userManag_datagrid').datagrid('unselectAll');
							$.messager.show({
								title : '提示',
								msg : r.msg
							});
						}
					});
				}
			});
		} else {
			$.messager.show({
				title : '提示',
				msg : '请勾选要删除的记录！'
			});
		}
	}
</script>

<div id="admin_userManag_layout" class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',title:'查询条件',border:false" style="height:65px;">
		<form id="admin_userManag_searchForm">
			检索用户名称（登录名）：<input name="name" /> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a> <a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="clearFun();">清空</a>
		</form>
	</div>

	<div data-options="region:'center',border:false">
		<table id="admin_userManag_datagrid"></table>
	</div>
	<!-- toolbar实现部分 
	<div id="admin_userManag_toolbar">
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-edit',plain:true" style="float:left;">编辑</a>
		<div class="datagrid-btn-separator"></div>
		<a href="#" class="easyui-linkbutton" data-options="iconCls:'icon-help',plain:true">帮助</a>
	</div>
	-->
</div>

<!-- 功能特点：
	1. 点击增加按钮的时候模式化弹出窗口
	2. 在填写信息后，点击"添加"后要提交表单
	3. 表单数据有些是不能填写的（设置为readonly），表单提交进行验证
	4. 添加完数据后要对datagrid刷新（reload）->easyUI的不刷新添加数据，否则toolbar区域的总数据条数不会发生变化
	5. 第二次添加时，数据框中没有数据
	6. 中文问题：所有的文件为UTF-8+★★[form]method="post"+struts字符拦截器+web.xml中的字符编码
	属性说明：
	closed:true：表示最初打开面板时该Dialog关闭，用到时才打开
	modal:true：表示模式化打开窗口
 -->
<div id="admin_userManag_addDialog" class="easyui-dialog" data-options="closed:true,modal:true,title:'添加用户',
	buttons:[{
		text : '增加',
		iconCls : 'icon-add',
		handler : function() {
			$('#admin_userManag_addForm').form('submit',{
				url:'${pageContext.request.contextPath}/userAction!addUser.action',
				success:function(data){
					var obj=jQuery.parseJSON(data);
					console.info(obj);
					if(obj.success){
						//★★刷新整个datagrid效率不高,用appendRow和insertRow代替
						//$('#admin_userManag_datagrid').datagrid('load');
						//加载datagrid的最后 一行
						//$('#admin_userManag_datagrid').datagrid('appendRow',obj.obj);
						//★推荐用这种方式
						$('#admin_userManag_datagrid').datagrid('insertRow',{
							index:0,
							row:obj.obj						
						});
						$('#admin_userManag_addDialog').dialog('close');				
					}
					$.messager.show({
						title:'提示',
						msg:obj.msg
					})
				}});
			}
		}]" style="width:280px;height:210px;" align="center">
	<form id="admin_userManag_addForm" method="post">
		<table>
			<tr>
				<th>编号</th>
				<td><input name="id" readonly="readonly" />
			</tr>
			<tr>
				<th>登录名</th>
				<td><input name="name" class="easyui-validatebox" data-options="requird:true" />
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="pwd" type="password" class="easyui-validatebox" data-options="requird:true" />
			</tr>
			<tr>
				<th>创建时间</th>
				<td><input name="creatTime" readonly="readonly" />
			</tr>
			<tr>
				<th>修改时间</th>
				<td><input name="endTime" readonly="readonly" />
			</tr>
		</table>
	</form>
</div>