package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.BasicSysparamHt;
import tw.com.ezlifetech.ezReco.respository.BasicSysparamHtRespository;

@Repository
public class JpaBasicSysparamHtRespository extends JpaGenericRepository<BasicSysparamHt, String> implements BasicSysparamHtRespository{

	@Override
	public Long getNextSysparamHtSeq() {
		return convert2Long(getBySQL("select nextval('sysparam_ht_seq')"));
	}

	@Override
	public BasicSysparamHt getEntityByCode(String paramCode) {
		return getEntityByJPQL("SELECT h FROM BasicSysparamHt h WHERE h.paramCode=?", paramCode);
	}

}
