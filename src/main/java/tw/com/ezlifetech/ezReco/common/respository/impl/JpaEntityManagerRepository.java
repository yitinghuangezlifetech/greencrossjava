package tw.com.ezlifetech.ezReco.common.respository.impl;

import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.type.StringType;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ezlifetech.ezReco.apcommon.PageResult;



/**
 * JPA {@link EntityManager} based Repository
 * <p>
 * This could be used when non one-entity-per-table case. it won't limit the
 * entity type this repository handles for. You can get entity manager directly and
 * have your own operation freely.
 * </p>
 */
@Repository
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public abstract class JpaEntityManagerRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public JpaEntityManagerRepository() {
		super();
	}
	
	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	protected EntityManager getEntityManager() {
		if (entityManager == null)
			throw new IllegalStateException("EntityManager has not been set on repository before usage");
		
		return entityManager;
	}

	/**
	 * Find by jpql.
	 *
	 * @param jpql the jpql
	 * @param params the params
	 * @return the list
	 */
	public List findByJPQL(String jpql, Object... params) {
		
		return this.query(jpql, params);  
	}
	
	/**
	 * Find by jpql.
	 *
	 * @param jpql the jpql
	 * @param params the params
	 * @return the list
	 */
	public List findByJPQLForPlaceholder(String jpql, Map<String, Object> params) {
		
		return this.queryForPlaceholder(jpql, params);  
	}
	
	/**
	 * Find by jpql.
	 *
	 * @param jpql the jpql
	 * @param params the params
	 * @return the list
	 */
	public List findByJPQLForPlaceholder(String jpql, Integer page, Integer pageSize, Map<String, Object> params) {
		
		return this.queryForPlaceholder(jpql, page, pageSize, params);  
	}
	
	/**
	 * Count by sql.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the count
	 */
	public Long countBySQL(String sql, Object... params) {
		Session session = getEntityManager().unwrap(Session.class);
		
		String countSql = ("select count(*) as totalCnt from (" + sql + ") a");

		SQLQuery countQuery = session.createSQLQuery(countSql);
		
		for (int i = 0; i < params.length; i++) {
			countQuery.setParameter(i, params[i]);
		}
		
		return ((BigDecimal) countQuery.uniqueResult()).longValue();
	}
	
	/**
	 * Count by sql.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the count
	 */
	public Long countBySQLForPlaceholder(String sql, Map<String, Object> params) {
		Session session = getEntityManager().unwrap(Session.class);
		
		String countSql = ("select count(*) as totalCnt from (" + sql + ") a");

		SQLQuery countQuery = session.createSQLQuery(countSql);

		Iterator it = params.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Object> parameter = (Map.Entry<String, Object>)it.next();
	        Object objValue = parameter.getValue();
	        if(objValue instanceof List) {
	        	countQuery.setParameterList(parameter.getKey(), (List)objValue);
	        } else {
	        	countQuery.setParameter(parameter.getKey(), objValue);
	        }
	    }
		return ((Integer) countQuery.uniqueResult()).longValue();
	}
	
	/**
	 * Find by sql.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the list
	 */
	public List<Map<String, Object>> findBySQL(String sql, Object... params) {
		Session session = getEntityManager().unwrap(Session.class);
		SQLQuery query = session.createSQLQuery(sql);
		
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return query.list();  
	}
	
	/**
	 * Find by sql.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the list
	 */
	public List<Map<String, Object>> findBySQLForPlaceholder(String sql, Map<String, StringType> scalarMap, Map<String, Object> params) {
		Session session = getEntityManager().unwrap(Session.class);
		SQLQuery query = session.createSQLQuery(sql);
		
		if(scalarMap != null){
			Iterator scalarIt = scalarMap.entrySet().iterator();
		    while (scalarIt.hasNext()) {
		        Map.Entry<String, StringType> parameter = (Map.Entry<String, StringType>)scalarIt.next();
		        StringType objValue = parameter.getValue();
		        query.addScalar(parameter.getKey(), objValue);
		    }
		}
		
		
		Iterator it = params.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Object> parameter = (Map.Entry<String, Object>)it.next();
	        Object objValue = parameter.getValue();
	        if(objValue instanceof List) {
	        	query.setParameterList(parameter.getKey(), (List)objValue);
	        } else {
	        	query.setParameter(parameter.getKey(), objValue);
	        }
	    }
		
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		return query.list();  
	}
	
	/**
	 * Find by sql.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the list
	 */
	public List<Map<String, Object>> findBySQL(String sql, Integer page, Integer pageSize, Object... params) {
		Session session = getEntityManager().unwrap(Session.class);
		SQLQuery query = session.createSQLQuery(sql);
		
		if ((pageSize != null) && (pageSize.intValue() > 0)) {
			
			if ((page != null) && (page.intValue() > 1)) {
				query.setFirstResult((page - 1) * pageSize);
			}
			
			query.setMaxResults(pageSize.intValue());
		}
		
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		
		query.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
		
		return query.list();  
	}
	
	/**
	 * Find PageResult by sql.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the pageResult
	 */
	public PageResult findPageResultBySQL(String sql, Integer page, Integer pageSize, Object... params) {
		PageResult pageResult = new PageResult();
		pageResult.setTotal(countBySQL(sql, params));
		pageResult.setPage(page);
		pageResult.setPageSize(pageSize);
		pageResult.setData(findBySQL(sql, page, pageSize, params));
		
		return pageResult;  
	}
	
	/**
	 * Get by sql.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the unique result
	 */
	public Object getBySQL(String sql, Object... params) {
		Session session = getEntityManager().unwrap(Session.class);
		SQLQuery query = session.createSQLQuery(sql);
		for (int i = 0; i < params.length; i++) {
			query.setParameter(i, params[i]);
		}
		return query.uniqueResult();  
	}
	
	/**
	 * Update by sql.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the count
	 */
	public int updateBySQL(String sql, Object... params) {
		Session session = getEntityManager().unwrap(Session.class);

		SQLQuery countQuery = session.createSQLQuery(sql);
		
		for (int i = 0; i < params.length; i++) {
			countQuery.setParameter(i, params[i]);
		}
		
		return countQuery.executeUpdate();
	}
	
	/**
	 * Update by sql.
	 *
	 * @param sql the sql
	 * @param params the params
	 * @return the count
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public int updateBySQLForPlaceholder(String sql, Map<String, Object> params) {
		Session session = getEntityManager().unwrap(Session.class);

		SQLQuery countQuery = session.createSQLQuery(sql);
		
		Iterator it = params.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Object> parameter = (Map.Entry<String, Object>)it.next();
	        Object objValue = parameter.getValue();
	        if(objValue instanceof List) {
	        	countQuery.setParameterList(parameter.getKey(), (List)objValue);
	        } else {
	        	countQuery.setParameter(parameter.getKey(), objValue);
	        }
	    }
		
		return countQuery.executeUpdate();
	}
	
	/**
	 * flush and clear entity manager
	 */
	public void flushAndClear() {
		getEntityManager().flush();
		getEntityManager().clear();
	}

	/**
	 * flush entity manager
	 */
	public void flush() {
		getEntityManager().flush();
	}

	/**
	 * clear entity manager
	 */
	public void clear() {
		getEntityManager().clear();
	}

	/**
	 * this is equivalent to
	 * <code>getEntityManager().createQuery(String jpql);</code>
	 * 
	 * @param jpql
	 * @return
	 */
	protected Query createQuery(String jpql) {
		return getEntityManager().createQuery(jpql);
	}

	/**
	 * create query by entity manager and set parameters accordingly
	 * 
	 * @param jpql
	 *            JPQL string, using '?' as parameter place holder
	 * @param params
	 * @return
	 */
	protected Query createQuery(String jpql, Object... params) {
		Query q = createQuery(jpql);
		for (int i = 0; i < params.length; i++) {
			q = q.setParameter(i + 1, params[i]);
		}
		return q;
	}
	
	protected Query createQueryForPlaceholder(String jpql, Map<String, Object> params) {
		Query q = createQuery(jpql);
		Iterator it = params.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Object> parameter = (Map.Entry<String, Object>)it.next();
	        q = q.setParameter(parameter.getKey(), parameter.getValue());
	    }
//	    q.setMaxResults(30);
		return q;
	}
	
	protected Query createQueryForPlaceholder(String jpql, Integer page, Integer pageSize, Map<String, Object> params) {
		Query q = createQuery(jpql);
		Iterator it = params.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<String, Object> parameter = (Map.Entry<String, Object>)it.next();
	        q = q.setParameter(parameter.getKey(), parameter.getValue());
	    }
	    if ((pageSize != null) && (pageSize.intValue() > 0)) {
			
			if ((page != null) && (page.intValue() > 1)) {
				q.setFirstResult((page - 1) * pageSize);
			}
			
			q.setMaxResults(pageSize.intValue());
		}
		return q;
	}

	/**
	 * query for a list of result by specified query expression and partameters
	 * 
	 * @param jpql
	 *            JPQL string, using '?' as parameter place holder
	 * @param params
	 *            the parameters for specified jpql
	 * @return if no result, an empty list will be returned
	 */
	protected List query(String jpql, Object... params) {
		return createQuery(jpql, params).getResultList();
	}
	
	protected List queryForPlaceholder(String jpql, Map<String, Object> params) {
		return createQueryForPlaceholder(jpql, params).getResultList();
	}
	
	protected List queryForPlaceholder(String jpql, Integer page, Integer pageSize, Map<String, Object> params) {
		return createQueryForPlaceholder(jpql, page, pageSize, params).getResultList();
	}
	
	/**
	 * query and get the first element from query result. return null if result
	 * is empty.
	 * 
	 * @param jpql
	 *            JPQL string, using '?' as parameter place holder
	 * @param params
	 *            the parameters for specified jpql
	 * @return null if result is empty
	 */
	protected Object queryForFirst(String jpql, Object... params) {
		List resultList = createQuery(jpql, params).getResultList();
		return returnFirstOrNull(resultList);
	}

	/**
	 * execute update/delete query string with specified parameters.
	 * 
	 * @param jpql
	 *            JPQL string, using '?' as parameter place holder
	 * @param params
	 *            the parameters for specified jpql
	 * @return the number of objects changed by the call
	 */
	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	protected int executeUpdate(String jpql, Object... params) {
		return createQuery(jpql, params).executeUpdate();
	}
	/**
	 * return the first entity of the specified list. or return null if the list
	 * is null or is empty.
	 * 
	 * @param entities
	 * @return the first entity of the list, or null.
	 * @author Morel
	 */
	private Object returnFirstOrNull(List entities) {
		if (entities != null && entities.size() > 0) {
			return entities.get(0);
		}
		return null;
	}

}