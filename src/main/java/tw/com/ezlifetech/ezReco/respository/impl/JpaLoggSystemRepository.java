package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.LoggSystem;
import tw.com.ezlifetech.ezReco.respository.LoggSystemRepository;


@Repository
public class JpaLoggSystemRepository extends JpaGenericRepository<LoggSystem, String> implements LoggSystemRepository {

}
