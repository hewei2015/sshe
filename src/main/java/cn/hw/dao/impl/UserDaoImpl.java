package cn.hw.dao.impl;

import org.springframework.stereotype.Repository;

import cn.hw.dao.UserDao;
import cn.hw.domain.Tuser;

//如果是hibernate3则这里的类需要继承HibernateDaoTemplateSupport，这里通过注入SessionFactory达到目的（SessionFactory在spring-hibernate。xml中配置好了）
@Repository(value="userDao") //也可以换成@Component("userDao")
public class UserDaoImpl extends BaseDaoImpl<Tuser> implements UserDao {
}
