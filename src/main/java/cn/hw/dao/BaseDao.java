package cn.hw.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public interface BaseDao<T> {
	public Serializable save(T o);

	public void delete(T o);

	public T get(Serializable id,Class<T> clazz);
	
	public T get(String hql);

	public T get(String hql, Object[] params);

	public T get(String hql, Map<String, Object> params);

	public void update(T o);

	public void saveOrUpdate(T o);

	public List<T> getAll(String hql);

	public List<T> getAll(String hql, Map<String, Object> params);

	/**分页查询
	 * @param hql
	 * @param params
	 * @param page	当前页数
	 * @param row	每页显示的条数
	 * @return
	 */
	public List<T> getPage(String hql, Map<String, Object> params, int page, int rows);
	
	/**
	 * 分页但是不传参
	 */
	public List<T> getPage(String hql,int page,int rows);

	/**
	 * 查询记录的总条数，分页时显示总条数
	 */
	public Long count(String hql);
	
	/**
	 * 有参数的情况
	 */
	public Long count(String hql,Map<String, Object> params);
	
	/**
	 * 直接执行hql语句
	 */
	public int executeHql(String hql);
}
