package spring.model.dao;

import java.util.List;

import spring.model.User;

public interface GenericDao {
	<E> Boolean insert(E e);
	<E> E get(E e, int id);
	User getUserByEmail(String name);
	User getUserByMobile(String name);
	<E> E update(E e);
	<E> E get(E e, String id);
	@SuppressWarnings("rawtypes")
	List getAll(String className);
	@SuppressWarnings("rawtypes")
	List getById(String className, String columnName, int id);
}
