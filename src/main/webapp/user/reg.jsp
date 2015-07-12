<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(function() {//★增加回车提交功能
		//★★因为下面的"user_reg_regForm"没有action，则提交时默认刷新首页
		//所以提交表单之前必须将表单初始化，将普通表单变成easyui的表单，否则提交不了数据
		$('#user_reg_regForm').form({ 
			url : '${pageContext.request.contextPath}/userAction!reg.action',
			success : function(data) {
				var obj = jQuery.parseJSON(data);
				if (obj.success) {
					$('#user_reg_regDialog').dialog('close');
				}
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});

		$('#user_reg_regForm input').bind('keyup', function(event) {
			if (event.keyCode == '13') {
				$('#user_reg_regForm').submit();//这里才提交表单
			}
		});
	});
</script>
<!-- 以下这种方式（不在<javascript>中初始化数据）是easyUI1.3后的语法 -->
<div id="user_reg_regDialog" class="easyui-dialog" style="width:250px;" data-options="title:'注册',closed:true,modal:true,closable:false,buttons:[{
			text:'注册',
			iconCls:'icon-edit',
			handler:function(){ 
				/***如果不要实现按Enter执行注册，表单可以在这里初始化**********************
				$('#user_reg_regForm').form('submit', { //初始化表单和提交表单放在一起
					//如果不定义url,表单会提交到当前页面,这里引入根路径表达式，必须映入jstl相关的包
					url : '${pageContext.request.contextPath}/userAction!reg.action', 
					success : function(data) {//检查通过后后台向前台返回的字符串  
						var obj = jQuery.parseJSON(data);
						if(obj.success){
							$('#user_reg_regDialog').dialog('close');
						}
						$.messager.show({//★★弹出提示框，显示注册成功title:'提示',
							title:'提示',
							msg:obj.msg
						});
					}
				});
				**********************************************************************/
				$('#user_reg_regForm').submit();
			}
		}]">
	<form id="user_reg_regForm" method="post">
		<table>
			<tr>
				<th>用户名</th>        
				<td><input name="name" class="easyui-validatebox" data-options="required:true,missingMessage:'请输入用户名'"/>
				</td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="pwd" class="easyui-validatebox" type="password" data-options="required:true,missingMessage:'请输入密码'" />
				</td>
			</tr>
			<tr>
				<th>重复密码</th>
				<td><input name="rePwd" class="easyui-validatebox" type="password" data-options="required:true,missingMessage:'请输入确认密码',validType:'eqPwd[\'#user_reg_regForm input[name=pwd]\']'" />
				</td>
			</tr>
		</table>
	</form>
</div>