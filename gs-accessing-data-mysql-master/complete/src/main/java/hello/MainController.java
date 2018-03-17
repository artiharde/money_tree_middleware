
package hello;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import hello.loginRepository;
import hello.companyRepository;

@Controller    // This means that this class is a Controller
@RequestMapping(path="/login") // This means URL's start with /demo (after Application path)
public class MainController
{
	@Autowired // This means to get the bean called userRepository
	           // Which is auto-generated by Spring, we will use it to handle the data
	
	private loginRepository LoginRepository;
	private companyRepository CompanyRepository;
	String[] com = {"GS","AAPL","MSFT","DIS"};
	stock_data[] st = new stock_data[100];
	demo[] cobj = new demo[100];
	
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
	
	@GetMapping(path="/cap1")
	public @ResponseBody String cap_selection() {
		int id=1;
		String com;
		try
		{
			company temp = CompanyRepository.findByCid(id);// This returns a JSON or XML with the users
			com = String.valueOf(temp.getcompanyName());
			return "Company name :"+com;
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			return "failed";
		}
		//return CompanyRepository.findAll();
		
	}
	
	@GetMapping(path="/get-stock-data")
	public @ResponseBody String getStockdata() throws IOException
	{
		int cid=0;
		int j=0;
		StringBuffer response = new StringBuffer();
		for(cid=0;cid<4;cid++) 
		{
			try 
			{
				String C =com[cid];
				String GET_URL = "https://www.quandl.com/api/v3/datasets/EOD/"+C+".csv?api_key=b--k_vTGmHWc782UMm5E&start_date=2018-03-08" ;
				//String GET_URL ="https://www.quandl.com/api/v3/datasets/XNSE/ADSL.csv?start_date=2018-03-08&end_date=2018-03-16&api_key=b--k_vTGmHWc782UMm5E" ;       
				System.out.println("\n URL :" + GET_URL);
				URL obj = new URL(GET_URL);
				HttpURLConnection con = (HttpURLConnection) obj.openConnection();
				con.setRequestMethod("GET");
				String USER_AGENT = "Chrome";
				con.setRequestProperty("User-Agent", USER_AGENT);
				int responseCode = con.getResponseCode();
				System.out.println("GET Response Code :: " + responseCode);
				if (responseCode == HttpURLConnection.HTTP_OK) 
				{ 
					BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
					String inputLine;
					while ((inputLine = in.readLine()) != null)
					{
						response.append(inputLine);
						System.out.println("\n J : "+ j + " -----> " + inputLine);	
						String tempStr[]= inputLine.split(",");
						if(tempStr[0].equals("Date")==false)
						{
							st[j] = new stock_data();
							st[j].company_name=com[cid];
							st[j].cur_date=tempStr[0];
							st[j].open=tempStr[1];
							st[j].high=tempStr[2];
							st[j].low=tempStr[3];
							st[j].close=tempStr[4];
							st[j].volume=tempStr[5];
							j++;
						}
					}		
					in.close();
				} 
				else
				{
					return "Unable to fetch data";
				}
			} 
			catch (MalformedURLException e) 
			{
				e.printStackTrace();
				return "Unable to fetch data";
			}
		}//end of for loop
		for(int t=0;t<j;t++)
		{
			    System.out.println("\n t : " + t + " COMPANY : "+st[t].company_name +"  Date:"+st[t].cur_date+"   Open:"+st[t].open+"   High:"+st[t].high+
				"   Low:"+st[t].low + "   Close:"+st[t].close+"  Volume:"+st[t].volume);
		}

		return response.toString();
	}


	@GetMapping(path="/cap")
	public @ResponseBody String capitalization() 
	{
		int k=0;
		String str ;

		for(int i=0;i<4;i++)
		{
			double change = 0;
			double avg_open = 0;
			int n=0;
			for(int j=0;st[j]!=null;j++)
			{
				if(com[i].equals(st[j].company_name))
				{
					str=st[j].open;
					double open = Double.parseDouble(str);
					str=st[j].close;
					double close = Double.parseDouble(str);
					change = (change + (close - open));
					avg_open = avg_open + open ;
					n++;
				}
			}
			
			change=change/n;
			avg_open=avg_open/n;
			cobj[k] = new demo();
			cobj[k].company_name=com[i];
			cobj[k].per_change=((change/avg_open)*100);
			k++;
		}
		System.out.println("\n\n");
		for(int i=0;i<k;i++)
		{
			System.out.println("\n company :" + cobj[i].company_name + " %change :" + cobj[i].per_change);
		}
		return "Done";
	}
	
}







/*//String GET_URL = "https://www.quandl.com/api/v3/datasets/NSE/GS?start_date=2018-03-01&end_date=2018-03-13&api_key=b--k_vTGmHWc782UMm5E";
//String GET_URL = "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&symbol=AAPL&apikey=L3RLE915L2DHGIXQ";
*/