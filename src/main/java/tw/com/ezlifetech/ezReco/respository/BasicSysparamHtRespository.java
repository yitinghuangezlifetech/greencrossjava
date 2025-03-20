package tw.com.ezlifetech.ezReco.respository;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.model.BasicSysparamHt;

public interface BasicSysparamHtRespository extends GenericRepository<BasicSysparamHt, String>{

	Long getNextSysparamHtSeq();

	BasicSysparamHt getEntityByCode(String paramCode);

}
