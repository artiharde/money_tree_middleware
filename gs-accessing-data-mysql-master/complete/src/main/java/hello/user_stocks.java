package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "user_stocks")
public class user_stocks {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer uid;
	
	private Integer cid;
	
	public void setuid(Integer uid)
	{
		this.uid=uid; 
	}

	public Integer getuid()
	{
		return this.uid;
	}
	
	public void setcid(Integer cid)
	{
		this.cid=cid; 
	}

	public Integer getcid()
	{
		return this.cid;
	}
	
}
