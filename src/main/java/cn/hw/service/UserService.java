package cn.hw.service;

import java.io.Serializable;

import cn.hw.domain.Tuser;
import cn.hw.pageModel.DataGrid;
import cn.hw.pageModel.User;


public interface UserService {
	public void test();
	
	public Serializable save(Tuser t);
	
	public void add(String name, String pwd);
	
	/**
	 * 前台添加一个用户后，要在不刷新页面的情况下在页面上显示，所以要返回该保存的User
	 */
	public User save(User user);

	public User login(User user);

	public DataGrid datagrid(User user);

	public void remove(String string);

	public User edit(User user);
}
