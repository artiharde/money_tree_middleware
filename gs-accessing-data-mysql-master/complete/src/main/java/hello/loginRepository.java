

package hello;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import hello.login;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

@Transactional
public interface loginRepository extends CrudRepository<login, Long> {

		  public login findByUsername(String Username);
		  public login findByPassword(String Password);
}
