package tw.com.ezlifetech.ezReco.common.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tw.com.ezlifetech.ezReco.common.service.VuforiaService;

@Service
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class VuforiaServiceImpl implements VuforiaService{

	
	
	
	
	
	
}
