package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.BasicSysparamDl;
import tw.com.ezlifetech.ezReco.respository.BasicSysparamDlRespository;

@Repository
public class JpaBasicSysparamDlRespository  extends JpaGenericRepository<BasicSysparamDl, String> implements BasicSysparamDlRespository{

	@Override
	public Long getNextSysparamDlSeq() {
		return convert2Long(getBySQL("select nextval('sysparam_dl_seq')"));
	}

}
