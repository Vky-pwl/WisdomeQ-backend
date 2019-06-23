package com.icat.quest.generic.dao.framework.impl;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.TypedQuery;

import org.hibernate.HibernateException;
import org.hibernate.MultiIdentifierLoadAccess;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.transaction.annotation.Transactional;

import com.icat.quest.generic.dao.framework.GenericDao;

@Transactional
public class GenericDaoHibernateImpl<T, PK extends Serializable> implements GenericDao<T, PK> {

	private final Class<T> type;

	private SessionFactory sessionFactory;

	public GenericDaoHibernateImpl(Class<T> type) {

		this.type = type;
	}

	@Override
	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	@SuppressWarnings("unchecked")
	@Override
	public PK create(T o) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		PK pk = (PK) session.save(o);
		session.flush();
		return pk;
	}

	@Override
	public T read(PK id) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
		T t = (T) session.get(type, id);
		session.flush();
		return t;
	}

	@Override
	public void update(T o) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		session.update(o);
		session.flush();
	}

	@Override
	public void updateBatch(List<T> transientObjectList) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		for (T o : transientObjectList) {
			session.update(o);
		}
		session.flush();
	}

	@Override
	public void saveOrUpdate(T o) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		session.evict(o);
		session.flush();
		session.saveOrUpdate(o);
	}

	@Override
	public void merge(T o) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		session.merge(o);
		session.flush();
	}

	@Override
	public void evict(T o) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		session.evict(o);
		session.flush();
	}

	@Override
	public void flush() {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		session.flush();
	}

	@Override
	public void delete(T o) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		session.delete(o);
		session.flush();
	}

	@Override
	public void deleteBatch(List<T> oList) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		for (T o : oList) {
			session.delete(o);
		}
		session.flush();
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<PK> createBatch(List<T> oList) {
		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		List<PK> pkList = new ArrayList<PK>();
		for (T o : oList) {
			pkList.add((PK) session.save(o));

		}
		session.flush();
		return pkList;
	}

	@Override
	public List<Object> convertToObjectList(List<Object[]> objArrayList, Object object, List<String> excludedFieldNames,
			List<String> includedFieldNames)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		List<Object> ObjectList = new ArrayList<Object>();

		for (Object[] obj : objArrayList) {
			Class<?> cls = object.getClass();
			object = cls.newInstance();
			Field[] fields = cls.getDeclaredFields();

			int i = 0;
			for (Field field : fields) {
					if ("serialVersionUID".equalsIgnoreCase(field.getName())
							|| excludedFieldNames != null && excludedFieldNames.contains(field.getName())) {
						continue;
					}
					field.setAccessible(true);
					 if (obj[i] instanceof Character) {
						field.set(object, obj[i] + "");
					} else if(obj[i] != null && !field.getType().equals(obj[i].getClass()))	{
						setValue(field, object, obj[i]);
					}else {
						field.set(object, obj[i]);
					}
					i++;
		}
			ObjectList.add(object);
		}
		return ObjectList;
	}
	
	private void setValue(Field field, Object obj,Object value ) {
		try {
		if(field.getType().equals(Integer.class)) {
			field.set(obj, Integer.parseInt(value.toString()));
		} else if(field.getType().equals(Long.class)) {
			field.set(obj, Long.parseLong(value.toString()));
		} else if(field.getType().equals(Float.class)) {
			field.set(obj, Float.parseFloat(value.toString()));
		} else if(field.getType().equals(Boolean.class)) {
			if(value.toString().equals("1")) {
			field.set(obj, true);
			} else {
				field.set(obj, false);	
			}
		} else if(field.getType().equals(BigInteger.class)) {
			field.set(obj, new BigInteger(value.toString()));
		} else if(field.getType().equals(BigDecimal.class)) {
			field.set(obj, new BigDecimal(value.toString()));
		} else if(field.getType().equals(String.class)) {
			field.set(obj, value.toString());
		} 
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
		
	@Override
	public Object convertToObject(Object[] objArray, Object object, List<String> excludedFieldNames,
			List<String> includedFieldNames)
			throws ClassNotFoundException, InstantiationException, IllegalAccessException {

		Class<?> cls = object.getClass();
		object = cls.newInstance();
		Field[] fields = cls.getDeclaredFields();

		int i = 0;
		for (Field field : fields)
			if (includedFieldNames != null) {
				if (includedFieldNames.contains(field.getName())) {
					field.setAccessible(true);
					field.set(object, objArray[i]);
					i++;
				}
			} else {
				if ("serialVersionUID".equalsIgnoreCase(field.getName())
						|| excludedFieldNames != null && excludedFieldNames.contains(field.getName()))
					continue;
				field.setAccessible(true);
				field.set(object, field.getType().cast(objArray[i]));
				i++;
			}

		return object;
	}

	@Override
	public List<T> listEntityByIdList(List<PK> idList) {
		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		MultiIdentifierLoadAccess<T> multi = session.byMultipleIds(type);
		List<T> entityList = multi.multiLoad(idList);
		session.flush();

		return entityList;
	}

	@Override
	public List<T> listEntityByParameter(String queryStatment, Map<String, Object> paramsKayAndValues, Integer start,
			Integer maxRows) {
		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		@SuppressWarnings("unchecked")
		TypedQuery<T> query = session.createQuery(queryStatment);
		if (start != null && maxRows != null) {
			query.setFirstResult(start);
			query.setMaxResults(maxRows);
		}
		if (paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for (String key : paramsKayAndValues.keySet()) {
				query.setParameter(key, paramsKayAndValues.get(key));
			}
		}
		session.flush();

		return query.getResultList();
	}

	@Override
	public T findEntityByParameter(String queryStatment, Map<String, Object> paramsKayAndValues) {
		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		@SuppressWarnings("unchecked")
		TypedQuery<T> query = session.createQuery(queryStatment);
		if (paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for (String key : paramsKayAndValues.keySet()) {
				query.setParameter(key, paramsKayAndValues.get(key));
			}
		}
		session.flush();

		return query.getResultList() != null && query.getResultList().size() > 0 ? query.getResultList().get(0) : null;
	}

	@Override
	public List<T> listEntity(String queryStatment) {
		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		@SuppressWarnings("unchecked")
		TypedQuery<T> query = session.createQuery(queryStatment);

		session.flush();

		return query.getResultList();
	}

	@Override
	public List<Object[]> listCompositeSqlQuery(String sqlQuery, Map<String, Object> paramsKayAndValues) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}
		
		@SuppressWarnings({ "rawtypes", "deprecation" })
		Query query = session.createSQLQuery(sqlQuery);
		if (paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for (String key : paramsKayAndValues.keySet()) {
				query.setParameter(key, paramsKayAndValues.get(key));
			}
		}
		@SuppressWarnings("unchecked")
		List<Object[]> result = query.getResultList();
		session.flush();

		return result;
	}

	@Override
	public List<?> listSingleRowResult(String sqlQuery, Map<String, Object> paramsKayAndValues) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		@SuppressWarnings({ "rawtypes", "deprecation" })
		Query query = session.createSQLQuery(sqlQuery);
		if (paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for (String key : paramsKayAndValues.keySet()) {
				query.setParameter(key, paramsKayAndValues.get(key));
			}
		}
		List<?> result = query.list();
		session.flush();

		return result;
	}

	@Override
	public void updateBySqlQuery(String sqlQuery, Map<String, Object> paramsKayAndValues) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		@SuppressWarnings({ "rawtypes", "deprecation" })
		Query query = session.createSQLQuery(sqlQuery);
		if (paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for (String key : paramsKayAndValues.keySet()) {
				query.setParameter(key, paramsKayAndValues.get(key));
			}
		}
		query.executeUpdate();
		session.flush();

	}

	
	@Override
	public Object singleRowResult(String sqlQuery, Map<String, Object> paramsKayAndValues) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		@SuppressWarnings({ "rawtypes", "deprecation" })
		Query query = session.createSQLQuery(sqlQuery);
		if (paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for (String key : paramsKayAndValues.keySet()) {
				query.setParameter(key, paramsKayAndValues.get(key));
			}
		}
		List<?> result = query.list();
		session.flush();

		return result != null && result.size() > 0 ? result.get(0) : null;
	}

	@Override
	public Object[] listSqlQuerySingleResult(String sqlQuery, Map<String, Object> paramsKayAndValues) {

		Session session;
		try {
			session = sessionFactory.getCurrentSession();
		} catch (HibernateException e) {
			session = sessionFactory.openSession();
		}

		@SuppressWarnings({ "rawtypes", "deprecation" })
		Query query = session.createSQLQuery(sqlQuery);

		if (paramsKayAndValues != null && !paramsKayAndValues.isEmpty()) {
			for (String key : paramsKayAndValues.keySet()) {
				query.setParameter(key, paramsKayAndValues.get(key));
			}
		}
		Object[] r = null;
		try {
		r =  (Object[]) query.getSingleResult();
		} catch(Exception e) {
			return null;
		}
		session.flush();

		return r;
	}

}
