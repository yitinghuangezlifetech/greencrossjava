package tw.com.ezlifetech.ezReco.respository.impl;

import org.springframework.stereotype.Repository;

import tw.com.ezlifetech.ezReco.common.respository.impl.JpaGenericRepository;
import tw.com.ezlifetech.ezReco.model.ProfFunction;
import tw.com.ezlifetech.ezReco.respository.ProfFunctionRepository;



@Repository
public class JpaProfFunctionRepository extends JpaGenericRepository<ProfFunction, String> implements ProfFunctionRepository {

}
