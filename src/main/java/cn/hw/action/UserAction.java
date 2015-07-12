package cn.hw.action;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.beans.factory.annotation.Autowired;

import cn.hw.pageModel.HwJson;
import cn.hw.pageModel.User;
import cn.hw.service.UserService;

import com.opensymphony.xwork2.ModelDriven;

//不把action配置在struts.xml中，直接在action类中写注解
//相当于在<package name="basePackage" extends="struts-default"></package>中配置UserAction
@Namespace("/")
// 直接在根目录下访问，如果UserAction在/demo下则是"/demo"
// Action的整个类标识，调用时使用动态方法进行调用（用叹号）。如果用方法标识，则直接配置在方法名上面,如下面注释
@Action(value = "userAction")
public class UserAction extends BaseAction implements ModelDriven<User> {// 注意这里不是Tuser
	private static final Logger logger = Logger.getLogger(UserAction.class);
	User user = new User();
	private UserService userService;

	public UserService getUserService() {
		return userService;
	}

	@Autowired
	// 表示自动注入
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	// @Action(value="userAction")
	// 相当于配置文件形式：<action name="userTestAction" method="test" class="cn.hw.UserAction"/>
	public void test() {
		logger.info("进入Action_test");
		userService.test();
	}

	// public void addUser() {
	// Tuser t = new Tuser();
	// t.setCreatTime(new Date());
	// t.setId(UUID.randomUUID().toString());
	// t.setName("xxx");
	// t.setPwd("1111222");
	// }

	/**
	 * 虽然是void，但是这个方法是有返回值的，只是在里面通过其它方法writeJson()将json数据返回到了前台
	 */
	public void reg() {
		// Map<String,Object> m = new HashMap<String,Object>();//1
		HwJson j = new HwJson();
		try {
			// ▲注意点：如果add()的参数有100项，则这种方式不好，则应该直接传对象，
			userService.save(user);
			// m.put("success", true);//1
			// m.put("msg", "注册成功");//1
			j.setSuccess(true);
			j.setMsg("注册成功");
		} catch (Exception e) {
			// m.put("success",false);//1
			// m.put("msg", e.getMessage());//1
			j.setMsg(e.getMessage());
		}
		super.writeJson(j);// 调用父类的方法
	}

	public User getModel() {
		return this.user;
	}

	public void login() {
		User u = userService.login(user);
		HwJson j = new HwJson();
		if (u != null) {
			j.setSuccess(true);
			j.setMsg("登录成功");
		} else {
			j.setMsg("登录失败,用户名或密码错误");
		}
		super.writeJson(j);
	}

	public void datagrid() {
		// 依据EasyUI框架需要的json数据内容定义的pageModel
		// 也可以用Map
		// Map m = new HashMap();
		// m.put("total",0);
		// m.put("rows",new ArrayList());
		super.writeJson(userService.datagrid(user));
	}

	/**
	 * 添加一条数据
	 */
	public void addUser() {
		HwJson j = new HwJson();
		try {
			userService.save(user);
			j.setSuccess(true);
			j.setMsg("添加成功");
			j.setObj(user);
		} catch (Exception e) {
			j.setMsg(e.getMessage());
		}
		super.writeJson(j);
	}

	public void remove() {
		userService.remove(user.getIds());
	}

	public void edit() {
		HwJson j = new HwJson();
		try{
			User u = userService.edit(user);
			j.setSuccess(true);
			j.setMsg("编辑成功");
			j.setObj(u);
		}catch(Exception e){
			j.setMsg(e.getMessage());
		}
		super.writeJson(j);
	}
}