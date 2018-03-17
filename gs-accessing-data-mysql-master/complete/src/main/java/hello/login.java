package hello;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name = "login")
public class login {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer uid;

    private String username;

    private String password;

	public Integer getId() {
		return uid;
	}

	public void setId(Integer id) {
		this.uid = id;
	}

	public String getuserName() {
		return username;
	}

	public void setuserName(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
    
    
}

