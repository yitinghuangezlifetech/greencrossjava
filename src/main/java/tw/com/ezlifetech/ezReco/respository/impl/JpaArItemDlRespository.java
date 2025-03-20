package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.ArItemDl;
import tw.com.ezlifetech.ezReco.respository.ArItemDlRespository;

@Repository
public class JpaArItemDlRespository extends JpaGenericRepository<ArItemDl, String> implements ArItemDlRespository{

}
