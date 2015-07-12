package cn.hw.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hw.dao.MenuDao;
import cn.hw.domain.Tmenu;
import cn.hw.pageModel.Menu;
import cn.hw.service.MenuService;

@Service(value = "menuService")
public class MenuServiceImpl implements MenuService {
	private MenuDao menuDao;

	public MenuDao getMenuDao() {
		return menuDao;
	}

	@Autowired
	// 如果将@Autowired写在"private MenuDao menuDao;"上面，则会将MenuDao变成公开属性
	public void setMenuDao(MenuDao menuDao) {
		this.menuDao = menuDao;
	}

	@Override
	/**
	 * 由于OpenSessionView的作用，这里虽然只查询根节点，但是结果将会查询出来所有节点（Hibernate的级联查询）
	 */
	public List<Menu> getTree(String id) {
		List<Menu> menuL = new ArrayList<Menu>();
		String hql = "";
		Map<String, Object> params = new HashMap<String, Object>();
		if (id == null || id.equals("")) {
			// 查询所有根节点
			hql = "from Tmenu t where t.tmenu is null";
		} else {
			// 异步加载当前id下的子节点
			hql = "from Tmenu t where t.tmenu.id =:id";
		}
		List<Tmenu> tmenuL = menuDao.getAll(hql, params);
		if (tmenuL != null && tmenuL.size() > 0) {
			for (Tmenu t : tmenuL) {
				Menu m = new Menu();
				BeanUtils.copyProperties(t, m);
				// ★在pageModel中添加相关属性，这就是有个中间转换用的Model的好处
				// 首先判断是否有子节点
				// if (t.getTmenus() != null && !t.getTmenus().isEmpty()) {//get两次不是很好，用下面的方式
				Set<Tmenu> set = t.getTmenus();
				if (set != null && !set.isEmpty()) {
					m.setState("closed");// 节点显示为文件夹形式
				} else {
					m.setState("open");
				}
				menuL.add(m);
			}
		}
		return menuL;
	}

	@Override
	public List<Menu> getAllNode4Tree() {
		List<Menu> menuL = new ArrayList<Menu>();
		String hql = "from Tmenu t";
		List<Tmenu> tmenuL = menuDao.getAll(hql);
		if (tmenuL != null && tmenuL.size() > 0) {
			for (Tmenu t : tmenuL) {
				Menu m = new Menu();
				BeanUtils.copyProperties(t, m);
				Map<String,Object> attributes = new HashMap<String, Object>();
				attributes.put("url",t.getUrl());//
				m.setAttributes(attributes);
				Tmenu tm = t.getTmenu();// 获得当前节点的父节点对象
				if (tm != null) {// 有父节点
					m.setPid(t.getTmenu().getId());
				}
				menuL.add(m);
			}
		}
		return menuL;
	}

}
