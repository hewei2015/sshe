package cn.hw.service;

import java.util.List;

import cn.hw.pageModel.Menu;

public interface MenuService {
	public List<Menu> getTree(String id);
	
	public List<Menu> getAllNode4Tree();
}
