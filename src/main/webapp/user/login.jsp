<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<script type="text/javascript">
	$(function() {//★增加回车提交功能
		//★★因为下面的"user_reg_regForm"没有action，则提交时默认刷新首页
		//所以提交表单之前必须将表单初始化，将普通表单变成easyui的表单，否则提交不了数据
		$('#user_login_loginForm').form({ 
			url : '${pageContext.request.contextPath}/userAction!login.action',
			success : function(data) {
				var obj = jQuery.parseJSON(data);
				if (obj.success) {
					$('#user_login_logDialog').dialog('close');
				}
				$.messager.show({
					title : '提示',
					msg : obj.msg
				});
			}
		});

		$('#user_login_loginForm input').bind('keyup', function(event) {
			if (event.keyCode == '13') {
				$('#user_login_loginForm').submit();//这里才提交表单
			}
		});
		
		//★★解决IE刷新后，输入框直接获焦和直接输入问题
		window.setTimeout(function(){
			$('#user_login_loginForm input[name=name]').focus();
		},0);
	});
</script>
<div id="user_login_logDialog" class="easyui-dialog" data-options="title:'登录',modal:true,closable:false,buttons:[{
		 text:'注册',
            iconCls:'icon-edit',
            handler:function(){
            //$('#user_reg_regForm').form('load',{name:'',pwd:'',repPwd:''});//作用：在第二次注册的时候信息框中的内容清空
                $('#user_reg_regDialog').dialog('open');
            }
        },{
            text:'登录',
            iconCls:'icon-help',
            handler:function(){
            	$('#user_login_loginForm').submit();
            }
        }]">
	<form id="user_login_loginForm" method="post">
		<table>	
			<tr>
				<th>登录名</th>		 
				<td><input name="name" value="admin"　class="easyui-validatebox" data-options="required:true,missingMessage:'请输入用户名'"/>
				
				</td>
			</tr>
			<tr>
				<th>密码</th>
				<td><input name="pwd" value="admin" type="password" class="easyui-validatebox" data-options="required:true,missingMessage:'请输入密码'"/>
				</td>
			</tr>
		</table>
	</form>
</div>
