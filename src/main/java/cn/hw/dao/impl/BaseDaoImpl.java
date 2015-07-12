package cn.hw.dao.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.hw.dao.BaseDao;

@Repository("baseDao")
public class BaseDaoImpl<T> implements BaseDao<T> {
	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getMySession() {
		return this.sessionFactory.getCurrentSession();
	}

	@Override
	public Serializable save(T o) {
		return sessionFactory.getCurrentSession().save(o);
	}

	@Override
	public T get(String hql) {
		Query q = this.sessionFactory.getCurrentSession().createQuery(hql);
		@SuppressWarnings("unchecked")
		List<T> l = q.list();
		if (l != null && l.size() > 0)
			return l.get(0);
		return null;
	}

	@Override
	public T get(String hql, Object[] params) {
		Query q = this.sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && params.length > 0) {
			for (int i = 0; i < params.length; i++)
				q.setParameter(i, params[i]);
		}
		@SuppressWarnings("unchecked")
		List<T> l = q.list();
		if (l != null && l.size() > 0)
			return l.get(0);
		return null;
	}

	@Override
	public T get(String hql, Map<String, Object> params) {
		Query q = this.sessionFactory.getCurrentSession().createQuery(hql);
		// ★这里可以用map循环对应赋值，那样就不要知道具体的参数值（如name和pwd）
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		@SuppressWarnings("unchecked")
		List<T> l = q.list();
		if (l != null && l.size() > 0)
			return l.get(0);
		return null;
	}

	@Override
	public void delete(T o) {
		this.getMySession().delete(o);
	}

	@Override
	public void update(T o) {
		this.getMySession().update(o);
	}

	@Override
	public void saveOrUpdate(T o) {
		this.getMySession().saveOrUpdate(o);
	}

	@Override
	public List<T> getAll(String hql) {
		Query q = this.getMySession().createQuery(hql);
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(String hql, Map<String, Object> params) {
		Query q = this.sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return q.list();
	}

	@SuppressWarnings("unchecked")
	@Override
	/**分页查询
	 * @param hql
	 * @param params
	 * @param page	当前页数
	 * @param row	每页显示的条数
	 * @return
	 */
	public List<T> getPage(String hql, Map<String, Object> params, int page, int rows) {
		Query q = this.sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		// ★★分页查询功能：从第几条开始查询，查多少条
		// 公式：（当前页-1）*每页显示的记录数
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * 分页但是不传参
	 */
	public List<T> getPage(String hql, int page, int rows) {
		Query q = this.sessionFactory.getCurrentSession().createQuery(hql);
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	/**
	 * 查询记录的总条数，分页时显示总条数
	 */
	public Long count(String hql) {
		Query q = this.sessionFactory.getCurrentSession().createQuery(hql);
		// ★★查询到的结果始终是一个数的时候用
		return (Long) q.uniqueResult();
	}

	@Override
	/**
	 * 有参数的情况
	 */
	public Long count(String hql, Map<String, Object> params) {
		Query q = this.sessionFactory.getCurrentSession().createQuery(hql);
		if (params != null && !params.isEmpty()) {
			for (String key : params.keySet()) {
				q.setParameter(key, params.get(key));
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public T get(Serializable id, Class<T> clazz) {
		return (T) this.getMySession().get(clazz, id);
	}

	@Override
	/**
	 * 直接执行hql语句
	 */
	public int executeHql(String hql) {
		Query q = this.getMySession().createQuery(hql);// 注意不能用creatSQLQuery()
		return q.executeUpdate();
	}	
}