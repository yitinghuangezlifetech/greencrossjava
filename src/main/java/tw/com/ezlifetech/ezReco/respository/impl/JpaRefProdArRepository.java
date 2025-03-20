package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.RefProdAr;
import tw.com.ezlifetech.ezReco.respository.RefProdArRepository;

@Repository
public class JpaRefProdArRepository extends JpaGenericRepository<RefProdAr, String> implements RefProdArRepository{

}
