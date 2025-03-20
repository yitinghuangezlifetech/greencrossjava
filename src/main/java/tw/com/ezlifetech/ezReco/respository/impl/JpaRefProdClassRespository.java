package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.RefProdClass;
import tw.com.ezlifetech.ezReco.respository.RefProdClassRespository;

@Repository
public class JpaRefProdClassRespository  extends JpaGenericRepository<RefProdClass, String> implements RefProdClassRespository {

}
