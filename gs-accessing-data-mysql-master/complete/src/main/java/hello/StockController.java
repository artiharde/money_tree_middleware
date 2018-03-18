
package hello;
import hello.company;
import hello.companyRepository;
import hello.stock_data;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
//import java.util.Calendar;  
import java.text.SimpleDateFormat;  
import java.util.Date;  
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller    // This means that this class is a Controller
@RequestMapping(path="/stock") // This means URL's start with /demo (after Application path)
public class StockController
{
	
	@Autowired 
	public companyRepository CompanyRepository;
	 
	//end_date
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");  
    Date date = new Date();  
	String end_date = formatter.format(date);

	//start_date
	//Calendar cal = Calendar.getInstance();
    //cal.add(Calender.WEEK_OF_DAY,-2);
	String start_date = "2018-03-08";
    
	stock_data[] st = new stock_data[500];
	demo[] large = new demo[100];
	demo[] small = new demo[100];
	demo[] mid = new demo[100];
	
	@GetMapping(path="/get-stock-data")
	public @ResponseBody String getStockdata() throws IOException
	{
		int cid=0;
		int j=0;
		String code;
		StringBuffer response = new StringBuffer();
		for(cid=20;cid<41;cid++) 
		{
			try 
			{
				company temp = CompanyRepository.findByCid(cid);// This returns a JSON or XML with the users
				code = String.valueOf(temp.getCompanyCode());
				String GET_URL = "https://www.quandl.com/api/v3/datasets/EOD/"+ code +
						         ".csv?start_date="+ start_date +"&end_date="+ end_date +"&api_key=b--k_vTGmHWc782UMm5E" ;
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
							st[j].company_name=code;
							st[j].date=tempStr[0];
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
			    System.out.println("\n t : " + t + " COMPANY : "+st[t].company_name +"  Date:"+st[t].date+"  "
			    + " Open:"+st[t].open+"   High:"+st[t].high+"   Low:"+st[t].low + "   Close:"+st[t].close+"  Volume:"+st[t].volume);
		}

		return response.toString();
	}

	@GetMapping(path="/view-large-cap")
	public @ResponseBody String  showLargeCap() 
	{
		int i=0;
		int j=0;
		demo temp = new demo();
		//sort large cap
		for(i=0;i<(large.length-1);i++)
		{
			for(j=0;j<(large.length-1-i);j++)
			{
				if(large[j].change > large[j+1].change)
				{
					temp=large[j];
					large[j]=large[j+1];
					large[j+1]=temp;
				}
			}
		}
		for(i=large.length-1;i>5;i--)
		{
			System.out.println("Company name:"+large[i].company_name+"Change:"+large[i].change);
		}
		return large.toString();
	}
	
	@GetMapping(path="/view-small-cap")
	public @ResponseBody String  showSmallCap() 
	{
		int i=0;
		int j=0;
		demo temp = new demo();
		//sort small cap
		for(i=0;i<(small.length-1);i++)
		{
			for(j=0;j<(small.length-1-i);j++)
			{
				if(small[j].change>small[j+1].change)
				{
					temp=small[j];
					small[j]=small[j+1];
					small[j+1]=temp;
				}
			}
		}
		return small.toString();
	}
	
	@GetMapping(path="/view-mid-cap")
	public @ResponseBody String  showMidCap() 
	{
		int i=0;
		int j=0;
		demo temp = new demo();

		//sort mid cap
		for(i=0;i<(mid.length-1);i++)
		{
			for(j=0;j<(mid.length-1-i);j++)
			{
				if(mid[j].change>mid[j+1].change)
				{
					temp=mid[j];
					mid[j]=mid[j+1];
					mid[j+1]=temp;
				}
			}
		}
		return mid.toString();
	}
	
	@GetMapping(path="/store-change")
	public @ResponseBody String capitalization() 
	{
		int lg=0;
		int sm=0;
		int md=0;
		String str ;
		String company_name;
		String code;
		String cap;
		double open1 = 0;
		double open = 0;
		double high = 0;
		double low = 0;
		double close = 0 ;
		double volume = 0;
		
		for(int i=20;i<31;i++)
		{
			company temp = CompanyRepository.findByCid(i);// This returns a JSON or XML with the users
			code = String.valueOf(temp.getCompanyCode());
			company_name= String.valueOf(temp.getcompanyName());
			cap  = String.valueOf(temp.getCap());
			double change = 0;

			for(int j=0;st[j]!=null;j++)
			{
				if(code.equals(st[j].company_name) && start_date.equals(st[j].date))
				{
					str=st[j].open;
					open1 = Double.parseDouble(str);
					
				}
				if(code.equals(st[j].company_name) && end_date.equals(st[j].date))
				{
					str=st[j].open;
					open=Double.parseDouble(str);
					str=st[j].high;
					high=Double.parseDouble(str);
					str=st[j].low;
					low=Double.parseDouble(str);
					str=st[j].close;
					close=Double.parseDouble(str);
					str=st[j].volume;
					volume=Double.parseDouble(str);
				}
			}
			change=((close-open1)/open1)*100;
			if(cap.equals("LARGE"))
			{
				large[lg] = new demo();
				large[lg].cid=i;
				large[lg].company_code=code;
				large[lg].company_name=company_name;
				large[lg].change=change;
				large[lg].cap=cap;
				large[lg].open=open;
				large[lg].high=high;
				large[lg].close=close;
				large[lg].low=low;
				large[lg].volume=volume;
				lg++;
			}
			if(cap.equals("SMALL"))
			{
				small[sm] = new demo();
				small[sm].cid=i;
				small[sm].company_code=code;
				small[sm].company_name=company_name;
				small[sm].change=change;
				small[sm].cap=cap;
				small[sm].open=open;
				small[sm].high=high;
				small[sm].close=close;
				small[sm].low=low;
				small[sm].volume=volume;
				sm++;
			}
			if(cap.equals("MID"))
			{
				mid[md] = new demo();
				mid[md].cid=i;
				mid[md].company_code=code;
				mid[md].company_name=company_name;
				mid[md].change=change;
				mid[md].cap=cap;
				mid[md].open=open;
				mid[md].high=high;
				mid[md].close=close;
				mid[md].low=low;
				mid[md].volume=volume;
				md++;
			}

		}
		System.out.println("\n\n");
		int i=0;
		for(i=0;i<lg;i++)
		{
			System.out.println("\n Company name:"+large[i].company_name+"Change:"+large[i].change);
		}
		return "Done";
	}
	
}
