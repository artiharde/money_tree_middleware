package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "company")
public class company {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer cid;

    private String company;

    private String company_code;
    
    private String cap;

	public Integer getId() {
		return cid;
	}

	public void setId(Integer id) {
		this.cid = id;
	}

	public String getcompanyName() {
		return company;
	}

	public void setuserName(String company) {
		this.company = company;
	}

	public String getCompanyCode() {
		return company_code;
	}

	public void setCompanyCode(String company_code) {
		this.company_code = company_code;
	}
	public String getCap() {
		return cap;
	}

	public void setCap(String cap) {
		this.cap = cap;
	}
    
}
