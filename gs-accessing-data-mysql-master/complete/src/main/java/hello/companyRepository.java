/**
 * 
 */
package hello;

import java.io.Serializable;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import hello.company;
/**
 * @author Arti Harde
 *
 */
@SuppressWarnings("hiding")
@NoRepositoryBean
@Transactional
public interface companyRepository <compnay,Long extends Serializable > extends CrudRepository<company,Long>{

	  public company findByCid(Integer cid);
	  
}