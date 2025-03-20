package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.ArClass;
import tw.com.ezlifetech.ezReco.respository.ArClassRespository;

@Repository
public class JpaArClassRespository extends JpaGenericRepository<ArClass, String> implements ArClassRespository{

}
