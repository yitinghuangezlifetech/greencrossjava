package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.ArItemHt;
import tw.com.ezlifetech.ezReco.respository.ArItemHtRespository;


@Repository
public class JpaArItemHtRespository extends JpaGenericRepository<ArItemHt, String> implements ArItemHtRespository{

}
