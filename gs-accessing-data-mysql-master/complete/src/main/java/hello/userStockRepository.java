/**
 * 
 */
package hello;

import org.springframework.data.repository.CrudRepository;

/**
 * @author Arti Harde
 *
 */
public interface userStockRepository extends CrudRepository<user_stocks,Long>{
	
	public user_stocks findByUidAndCid(Integer uid,Integer cid);

}
