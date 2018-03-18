/**
 * 
 */

package hello;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import hello.loginRepository;


@Controller    // This means that this class is a Controller
@RequestMapping(path="/login") // This means URL's start with /demo (after Application path)
public class loginController
{
	@Autowired // This means to get the bean called userRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	
	public loginRepository LoginRepository;

	
	@GetMapping(path="/add") // Map ONLY GET Requests
	public @ResponseBody String addNewUser (@RequestParam String username, @RequestParam String password) 
	{
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		
		login n = new login();
		n.setuserName(username);
		n.setPassword(password);
		LoginRepository.save(n);
		return "User added successfully !!!";
	}
	
	@GetMapping(path="/displayall")
	public @ResponseBody Iterable<login> getAllUsers() 
	{
			return LoginRepository.findAll();// This returns a JSON or XML with the users
	}
	
	@GetMapping(path="/checklogin")
	public @ResponseBody String getByEmail(@RequestParam String Username,@RequestParam String Password) 
	{
	    String userID;
	    String password;
	    try 
	    {
	      login user = LoginRepository.findByUsername(Username);
	      login user1 = LoginRepository.findByPassword(Password);
	      userID = String.valueOf(user.getuserName());
	      password=String.valueOf(user1.getPassword());
	    }
	    catch (Exception ex)
	    {
	      return "Invalid Username or Password !!!";
	    }
	    return "Login Successful with  " +" username : " + userID + "  and  Password  : " + password;
	}
	

}







/*//String GET_URL = "https://www.quandl.com/api/v3/datasets/NSE/GS?start_date=2018-03-01&end_date=2018-03-13&api_key=b--k_vTGmHWc782UMm5E";
//String GET_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=AAPL&apikey=L3RLE915L2DHGIXQ";
*/