package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.RefFunctionRole;
import tw.com.ezlifetech.ezReco.respository.RefFunctionRoleRepository;



@Repository
public class JpaRefFunctionRoleRepository extends JpaGenericRepository<RefFunctionRole, String> implements RefFunctionRoleRepository {

}
