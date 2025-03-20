package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.ProductClass;
import tw.com.ezlifetech.ezReco.respository.ProductClassRespository;

@Repository
public class JpaProductClassRespository extends JpaGenericRepository<ProductClass, String> implements ProductClassRespository{

}
