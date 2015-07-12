package cn.hw.service.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.hw.dao.UserDao;
import cn.hw.domain.Tuser;
import cn.hw.pageModel.DataGrid;
import cn.hw.pageModel.User;
import cn.hw.service.UserService;
import cn.hw.utils.MD5Util;

@Service(value = "userService")
@Transactional
// 注解方式：配置事务，注意是在service层配置，不是在dao层配置（★★缺点，这个类中的所有方法都配置了事务）
public class UserServiceImpl implements UserService {
	private static final Logger logger = Logger.getLogger(UserServiceImpl.class);
	private UserDao userDao;

	public UserDao getUserDao() {
		return userDao;
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Override
	public void test() {
		// System.out.println("I am testing");
		logger.info("I am testing");
	}

	public Serializable save(Tuser t) {
		return userDao.save(t);
	}

	@Override
	public void add(String name, String pwd) {
		Tuser t = new Tuser();
		t.setId(UUID.randomUUID().toString());
		t.setPwd(pwd);
		t.setCreatTime(new Date());
		t.setName("hyc");
		userDao.save(t);
	}

	@Override
	public User save(User user) {
		// ▲注意点：如果向add()方法中一样，用set方法，则如果对象有100个属性，则这里要set*Xxx()100次
		// ★解决方案：使用BeanUtils类中的copyProperties()方法
		Tuser t = new Tuser();
		// 扩展：copyProperties()方法还可以使用第三个参数，刨除属性组成的数组
		BeanUtils.copyProperties(user, t, new String[] { "pwd" });
		t.setId(UUID.randomUUID().toString());
		t.setCreatTime(new Date());
		t.setPwd(MD5Util.encode(user.getPwd()));
		userDao.save(t);
		BeanUtils.copyProperties(t, user);
		return user;
	}

	@Override
	public User login(User user) {
		// 拼接字符串的方式不好
		// Tuser t =
		// userDao.get("from Tuser t where t.name='"+user.getName()+"' and pwd='"+MD5Util.encode(user.getPwd())+"'");
		// ★下面是改进方法（BaseDao中有相应的改变）
		// Tuser t = userDao.get("from Tuser t where t.name=? and t.pwd =?", new
		// Object[]{user.getName(),MD5Util.encode(user.getPwd())});
		// 第三种方法
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("name", user.getName());
		params.put("pwd", MD5Util.encode(user.getPwd()));
		;
		Tuser t = userDao.get("from Tuser t where t.name= :name and t.pwd = :pwd", params);
		if (t != null)
			return user;
		return null;
	}

	/**
	 * 带有查询功能
	 */

	@Override
	/***/
	public DataGrid datagrid(User user) {
		DataGrid dg = new DataGrid();
		String hql = "from Tuser t ";
		// 添加sql语句的where条件
		Map<String, Object> params = new HashMap<String, Object>();
		hql = addWhereCond(user, hql, params);
		// 用于分页的sql语句
		String totalHql = "select count(*) " + hql;
		// 前台有排序方式传过来才组织sql语句
		if (user.getSort() != null) {
			hql += " order by " + user.getSort() + " " + user.getOrder();// 确定页面数据排序方式
		}
		// 通过hql语句查询model
		List<Tuser> tuserL = userDao.getPage(hql, params, user.getPage(), user.getRows());
		List<User> userL = new ArrayList<User>();

		tuserL2userL(tuserL, userL);
		dg.setRows(userL);
		dg.setTotal(userDao.count(totalHql, params));
		return dg;
	}

	/**
	 * 完成Tuser->User的模型转换
	 */
	private void tuserL2userL(List<Tuser> tuserL, List<User> userL) {
		if (tuserL != null && tuserL.size() > 0) {
			for (Tuser t : tuserL) {
				User u = new User();
				BeanUtils.copyProperties(t, u);
				userL.add(u);
			}
		}
	}

	/**
	 * 依据查询条件，动态sql语句的where部分
	 */
	private String addWhereCond(User user, String hql, Map<String, Object> params) {
		// 依据name条件查询
		if (user.getName() != null && !user.getName().trim().equals("")) {
			hql += " where t.name like :name";
			params.put("name", "%%" + user.getName().trim() + "%%");// ★%%才表示模糊匹配
		}
		// ★依据Id条件查询：这样相加什么条件就加什么条件
		if (user.getId() != null && !user.getId().trim().equals("")) {
			hql += " where t.id like :id";
			params.put("name", "%%" + user.getId().trim() + "%%");
		}
		return hql;
	}

	@Override
	public void remove(String ids) {
		/*
		 * for(String id:ids.split(",")){ Tuser u = userDao.get(id, Tuser.class);
		 * if(u!=null){//这样一条条的删除，效率非常低 userDao.delete(u); } }
		 */
		//拼接hql语句，一次删除所有数据
		String[] nids = ids.split(",");
		String hql = "delete Tuser t where t.id in(";
		for (int i = 0; i < nids.length; i++) {
			if (i > 0) {
				hql += ",";
			}
			hql += "'" + nids[i] + "'";
		}
		hql += ")";
		userDao.executeHql(hql);
	}

	@Override
	public User edit(User user) {
		Tuser t = userDao.get(user.getId(), Tuser.class);
		//第三个参数时刨除的意思
		BeanUtils.copyProperties(user, t,new String[]{"id","pwd"});
		//在事务结束的时候hibernate自动提交事务，即完成update方法
		return user;
	}
}
