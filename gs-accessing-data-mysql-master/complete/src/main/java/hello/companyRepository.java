/**

 * 
 */
package hello;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import hello.company;


@Transactional
public interface companyRepository extends CrudRepository<company,Long>{

	  public company findByCid(Integer Cid);
	  
}