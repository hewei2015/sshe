package cn.hw.action;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hw.pageModel.Menu;
import cn.hw.service.MenuService;

import com.opensymphony.xwork2.ModelDriven;

@Action(value="menuAction")
public class MenuAction extends BaseAction implements ModelDriven<Menu>{
	private Menu menu = new Menu();
	private MenuService menuService;
	public MenuService getMenuService() {
		return menuService;
	}
	@Autowired
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	@Override
	public Menu getModel() {
		return this.menu;
	}
	/**
	 * 异步获取树节点
	 */
	public void getTree(){
		super.writeJson(menuService.getTree(menu.getId()));
	}
	/**
	 * 获得树全部节点
	 */
	public void getAllNode4Tree(){
		super.writeJson(menuService.getAllNode4Tree());
	}
	
	
}
