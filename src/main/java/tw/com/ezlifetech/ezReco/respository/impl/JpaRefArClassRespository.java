package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.RefArClass;
import tw.com.ezlifetech.ezReco.respository.RefArClassRespository;

@Repository
public class JpaRefArClassRespository  extends JpaGenericRepository<RefArClass, String> implements RefArClassRespository {

}
