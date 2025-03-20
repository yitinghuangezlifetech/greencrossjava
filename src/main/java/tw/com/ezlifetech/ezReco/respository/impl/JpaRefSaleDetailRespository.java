package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.RefSaleDetail;
import tw.com.ezlifetech.ezReco.respository.RefSaleDetailRespository;

@Repository
public class JpaRefSaleDetailRespository extends JpaGenericRepository<RefSaleDetail, String> implements RefSaleDetailRespository{

}
