package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.Role;
import tw.com.ezlifetech.ezReco.respository.RoleRepository;



@Repository
public class JpaRoleRepository extends JpaGenericRepository<Role, String> implements RoleRepository {

	@Override
	public Long getNextCustRoleSeq() {
		return convert2Long(getBySQL("select nextval('cust_role_seq')"));
	}

}
