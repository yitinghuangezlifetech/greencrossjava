package tw.com.ezlifetech.ezReco.respository;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.model.BasicSysparamDl;

public interface BasicSysparamDlRespository extends GenericRepository<BasicSysparamDl, String>{

	Long getNextSysparamDlSeq();

}
