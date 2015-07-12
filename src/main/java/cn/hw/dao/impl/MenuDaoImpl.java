package cn.hw.dao.impl;

import org.springframework.stereotype.Repository;

import cn.hw.dao.MenuDao;
import cn.hw.domain.Tmenu;

@Repository(value="menuDao")
public class MenuDaoImpl  extends BaseDaoImpl<Tmenu> implements MenuDao{

}
