package tw.com.ezlifetech.ezReco.respository;

import tw.com.ezlifetech.ezReco.common.respository.GenericRepository;
import tw.com.ezlifetech.ezReco.model.Role;
public interface RoleRepository extends GenericRepository<Role, String> {

	public Long getNextCustRoleSeq();

}
