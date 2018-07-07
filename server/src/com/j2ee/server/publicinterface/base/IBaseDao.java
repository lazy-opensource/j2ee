package com.j2ee.server.publicinterface.base;

import com.j2ee.server.core.PagingSortQuery;

import java.util.List;

@SuppressWarnings("all")
public interface IBaseDao<E> {

	/**
	 * 增
	 * @param orm
	 * @return String
	 */
	public String  insert(E orm);

	/**
	 * 改
	 * @param orm
	 * @return String
	 */
	public String  update(E orm);

	/**
	 * 删 支持批量
	 * @param ids
	 * @return String
	 */
	public String deleteInId(Class clazz, List<Long> ids);

	/**
	 * 查
	 * @return E
	 */
	public PagingSortQuery<E> findByPaging(E orm, PagingSortQuery<E> pagingSortQueryHelper);

	/**
	 * 查
	 * @return E
	 */
	public E findById(Class clazz, Long id);

}
