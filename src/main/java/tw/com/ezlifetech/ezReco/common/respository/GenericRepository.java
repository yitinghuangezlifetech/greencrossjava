package tw.com.ezlifetech.ezReco.common.respository;

import java.io.Serializable;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.type.StringType;

import tw.com.ezlifetech.ezReco.apcommon.PageResult;
import tw.com.ezlifetech.ezReco.common.model.GenericEntity;




/**
 * Title: GenericRespository<br>
 * Description: <br>
 * Company: Tradevan Co.<br>
 * 
 * @author Neil Chen
 * @version 1.6
 * @param <T>
 * @param <ID>
 */
public interface GenericRepository<T extends GenericEntity<ID>, ID extends Serializable> {
    
	void create(T entity);

	void detach(T entity);
	
	T update(T entity);
	
	T save(T entity);
	
	T checkThenSave(T entity);

	void saveInBatch(Collection<T> entities, int batchSize);

	int updateByJPQL(String jpql, Object... params);
	
	int updateByNamedQuery(String namedQuery, Object... params);
	
	int updateBySQL(String sql, Object... params);
	
	void delete(T entity);

	boolean delete(ID id);

	int deleteByProperty(String name, Object value);
	
	int deleteByProperties(String[] nameArray, Object[] valueArray);
	
	T getEntityById(ID id);
	
	T getEntityReferenceById(ID id);
	
	T getEntityByProperty(String name, Object value);
	
	T getEntityByProperties(String[] nameArray, Object[] valueArray);
	
	T getEntityByJPQL(String jpql, Object... params);
	
	T getEntityBySQL(String sql, Object... params);
	
	Object getByProperty(String[] fieldArray, String name, Object value);
	
	Object getByProperties(String[] fieldArray, String[] nameArray, Object[] valueArray);
	
	Object getByJPQL(String jpql, Object... params);
	
	Object getByNamedQuery(String namedQuery, Object... params);
	
	Object getBySQL(String sql, Object... params);
	
	Map<String, Object> getMapBySQL(String sql, Object... params);
	
	Map<String, Object> getMapBySQL_map(String sql, Map<String, Object> params);
	
	boolean exists(ID paramID);
	
	Long countByProperty(String name, Object value);
	
	Long countByProperties(String[] nameArray, Object[] valueArray);
	
	Long countByPropertyInValueList(String inName, List<?> valueList);
	
	Long countByPropertyAndInValueList(String name, Object value, String inName, List<?> valueList);
	
	Long countEntityByJPQL(String jpql, Object... params);
	
	Long countEntityByJPQL_map(String jpql, Map<String, Object> params);
	
	Long countEntityByNamedQuery(String namedQuery, Object... params);
	
	Long countEntityByNamedQuery_map(String namedQuery, Map<String, Object> params);
	
	Long countBySQL(String sql, Object... params);
	
	Long countBySQL_map(String sql, Map<String, Object> params);
	
	Long countAll();
	
	List findByProperty(String[] fieldArray, String name, Object value, String orderBy);
	
	List findByProperties(String[] fieldArray, String[] nameArray, Object[] valueArray, String[] orderByArray);
	
	List findByPropertyInValueList(String[] fieldArray, String name, List<?> valueList, String orderBy);
	
	List findByPropertyAndInValueList(String[] fieldArray, String name, Object value, String inName, List<?> valueList, String orderBy);
	
	List findByJPQL(String jpql, Object... params);
	
	List findByJPQLForPlaceholder(String jpql, Map<String, Object> params);
	
	List findByJPQL_map(String jpql, Map<String, Object> params);
	
	List findByNamedQuery(String sql, Object... params);
	
	List findByNamedQuery_map(String sql, Map<String, Object> params);
	
	List findBySQL(String sql, Object... params);
	
	List findBySQL_map(String sql, Map<String, Object> params);
	
	List<T> findAllEntityList();
	
	List<T> findAllEntityList(String orderBy, String[] joinProps, String[] leftJoinProps);
	
	List<T> findEntityListByProperty(String name, Object value, String orderBy);
	
	List<T> findEntityListByProperty(String name, Object value, String orderBy, String[] joinProps, String[] leftJoinProps);
	
	List<T> findEntityListByProperties(String[] nameArray, Object[] valueArray, String[] orderByArray, String[] joinProps, String[] leftJoinProps);
	
	List<T> findEntityListInValueList(String inName, List<?> valueList, String[] orderByArray, String[] joinProps, String[] leftJoinProps);
	
	List<T> findEntityListByPropertyAndInValueList(String name, Object value, String inName, List<?> valueList, String[] orderByArray, String[] joinProps, String[] leftJoinProps);
	
	List<T> findEntityListByJPQL(String jpql, Object... params);
	
	List<T> findEntityListByJPQL_map(String jpql, Map<String, Object> param);
	
	List<T> findEntityListByNamedQuery(String namedQuery, Object... params);
	
	List<T> findEntityListByNamedQuery_map(String namedQuery, Map<String, Object> param);
	
	List<T> findEntityListBySQL(String sql, Object... params);
	
	List<T> findEntityListBySQL_map(String sql, Map<String, Object> param);
	
	List<T> findPagedEntityListByJPQL(String jpql, Integer page, Integer pageSize, Object... params);
	
	List<T> findPagedEntityListByJPQL_map(String jpql, Integer page, Integer pageSize, Map<String, Object> param);
	
	List<T> findPagedEntityListByNamedQuery(String namedQuery, Integer page, Integer pageSize, Object... params);
	
	List<T> findPagedEntityListByNamedQuery_map(String namedQuery, Integer page, Integer pageSize, Map<String, Object> params);
	
	List<T> findPagedEntityListBySQL(String sql, Integer page, Integer pageSize, Object... params);
	
	List<T> findPagedEntityListBySQL_map(String sql, Integer page, Integer pageSize, Map<String, Object> params);
	
	PageResult findEntityPageResultByJPQL(String jpql, Integer page, Integer pageSize, Object... params);
	
	PageResult findEntityPageResultByJPQL_map(String jpql, Integer page, Integer pageSize, Map<String, Object> params);
	
	PageResult findEntityPageResultByNamedQuery(String namedQuery, Integer page, Integer pageSize, Object... params);

	PageResult findEntityPageResultByNamedQuery_map(String namedQuery, Integer page, Integer pageSize, Map<String, Object> params);
	
	PageResult findEntityPageResultBySQL(String sql, Integer page, Integer pageSize, Object... params);
	
	PageResult findEntityPageResultBySQL_map(String sql, Integer page, Integer pageSize, Map<String, Object> params);

	List<Map<String, Object>> findListMapBySQL(String sql, Object... params);
	
	List<Map<String, Object>> findListMapBySQL_map(String sql, Map<String, Object> params);
	
	List<Map<String, Object>> findPagedListMapBySQL(String sql, Integer page, Integer pageSize, Object... params);
	
	List<Map<String, Object>> findPagedListMapBySQL_map(String sql, Integer page, Integer pageSize, Map<String, Object> params);
	
	List<Map<String, Object>> findBySQLForPlaceholder(String sql, Map<String, StringType> scalarMap, Map<String, Object> params);
	
	PageResult findListMapPageResultBySQL(String sql, Integer page, Integer pageSize, Object... params);
	
	PageResult findListMapPageResultBySQL_map(String sql, Integer page, Integer pageSize, Map<String, Object> params);
	
	CallableStatement createCallableStatement(String sql) throws HibernateException, SQLException;
	
	void flush();

	void clear();
	
	void flushAndClear();
}
