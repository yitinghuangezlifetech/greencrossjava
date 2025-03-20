package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.GuardianPic;
import tw.com.ezlifetech.ezReco.respository.GuardianPicRepository;


@Repository
public class JpaGuardianPicRepository extends JpaGenericRepository<GuardianPic, String> implements GuardianPicRepository{

	@Override
	public Long getNextProductPicSeq() {
		return convert2Long(getBySQL("select nextval('product_pic_seq')"));
	}

}