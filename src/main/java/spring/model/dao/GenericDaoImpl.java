package spring.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import spring.model.User;

@Repository
public class GenericDaoImpl implements GenericDao {
	@PersistenceContext
	EntityManager em;

	@Transactional
	public <E> Boolean insert(E e) {
		try {
			em.persist(e);
			
		}catch(EntityExistsException ex) {
			return true;
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public <E> E get(E e, int id) {
		return (E)em.find(e.getClass(), id);
	}
	
	@Transactional
	public User getUserByEmail(String name) {
		Query query = em.createQuery("from User WHERE email=:name", User.class);
		query.setParameter("name", name);
		try {
			return (User)query.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}

	@Transactional
	public User getUserByMobile(String name) {
		Query query = em.createQuery("from User WHERE mobile_no=:name", User.class);
		query.setParameter("name", name);
		try {
			return (User)query.getSingleResult();
		} catch(NoResultException e) {
			return null;
		}
	}
	
	@Transactional
	public <E> E update(E e) {
		return em.merge(e);
	}
	
	@SuppressWarnings("unchecked")
	@Transactional
	public <E> E get(E e, String id) {
		return (E)em.find(e.getClass(), id);
	}
	
	@SuppressWarnings({ "rawtypes" })
	@Transactional
	public List getAll(String className) {
		Query query = em.createQuery("from " + className);
		try {
			return query.getResultList();
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	@Transactional
	public List getById(String className, String columnName, int id) {
		Query query = em.createQuery("from " + className + " where " + columnName +  "=" + id);
		
		return query.getResultList();
	}
}
