package cn.hw.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.hw.dao.BaseDao;
import cn.hw.dao.UserDao;
import cn.hw.domain.Tmenu;
import cn.hw.domain.Tuser;
import cn.hw.service.RepairService;
import cn.hw.utils.MD5Util;

@Service(value="repairService")
public class RepairServiceImpl implements RepairService {
	private BaseDao<Tmenu> menuDao;
	private UserDao userDao;

	public BaseDao<Tmenu> getMenuDao() {
		return menuDao;
	}
	@Autowired
	public void setMenuDao(BaseDao<Tmenu> menuDao) {
		this.menuDao = menuDao;
	}
	public UserDao getUserDao() {
		return userDao;
	}
	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void repair() {
		repairUser();
		repairMenu();
	}
	/**
	 * ★★修复超级管理员
	 */
	private void repairUser() {
		Map<String,Object> m  = new HashMap<String, Object>();
		m.put("name","admin");
		Tuser t = userDao.get("from Tuser t where t.name=:name and t.id !='0'",m);
		if(t!=null){
			t.setName(UUID.randomUUID().toString());
		}
		//###必须包含上面的语句，否则可能不能恢复（三步攻克）-------------------------
		Tuser admin = new Tuser();//高级管理员初始化
		admin.setId("0");
		admin.setName("admin");
		admin.setPwd(MD5Util.encode("admin"));
		userDao.saveOrUpdate(admin);
	}
	/**
	 * 修复左边菜单
	 */
	private void repairMenu() {
		Tmenu root = new Tmenu();
		//修复时，Id不能用随机码，必须是固定的，否则action执行多次，会插入多条数据
		root.setId("0");
		root.setText("首页");
		root.setIconcls(null);
		root.setUrl(null);
		menuDao.saveOrUpdate(root);
		
		Tmenu sysManag = new Tmenu();
		sysManag.setId("xtgl");
		sysManag.setTmenu(root);
		sysManag.setText("系统管理");
		sysManag.setUrl("");
		menuDao.saveOrUpdate(sysManag);
		
		Tmenu userManag = new Tmenu();
		userManag.setId("yhgl");
		userManag.setTmenu(sysManag);
		userManag.setText("用户管理");
		userManag.setUrl("/admin/userManag.jsp");
		menuDao.saveOrUpdate(userManag);
		
		Tmenu roleManag = new Tmenu();
		roleManag.setId("jsgl");
		roleManag.setTmenu(sysManag);
		roleManag.setText("角色管理");
		roleManag.setUrl("");
		menuDao.saveOrUpdate(roleManag);

		Tmenu PermManag = new Tmenu();
		PermManag.setId("qxgl");
		PermManag.setTmenu(sysManag);
		PermManag.setText("权限管理");
		PermManag.setUrl("");
		menuDao.saveOrUpdate(PermManag);
		
		Tmenu menuManag = new Tmenu();
		menuManag.setId("cdgl");
		menuManag.setTmenu(sysManag);
		menuManag.setText("菜单管理");
		menuManag.setUrl("");
		menuDao.saveOrUpdate(menuManag);
		
		Tmenu bugManag = new Tmenu();
		bugManag.setId("buggl");
		bugManag.setTmenu(sysManag);
		bugManag.setText("用户管理管理");
		bugManag.setUrl("");
		menuDao.saveOrUpdate(bugManag);
		
		
		
	}
	
}
