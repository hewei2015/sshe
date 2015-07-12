<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=UTF-8"%>

<form id="admin_userManagEdit_Form" method="post" >
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
			<td><input name="creatTime"/>
		</tr>
		<tr>
			<th>修改时间</th>
			<td><input name="endTime" />
		</tr>
	</table>
</form>
