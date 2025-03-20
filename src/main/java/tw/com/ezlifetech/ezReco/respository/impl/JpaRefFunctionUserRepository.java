package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.RefFunctionUser;
import tw.com.ezlifetech.ezReco.respository.RefFunctionUserRepository;


@Repository
public class JpaRefFunctionUserRepository extends JpaGenericRepository<RefFunctionUser, String> implements RefFunctionUserRepository {

}
