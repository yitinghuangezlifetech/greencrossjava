package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.User;
import tw.com.ezlifetech.ezReco.respository.UserRepository;

@Repository
public class JpaUserRepository extends JpaGenericRepository<User, String> implements UserRepository {

}
