package by.bsuir.diplom.entity;


import java.util.Date;
import java.util.List;
import javax.persistence.*;

@Entity
@Table
public class Worker {
	@Id
	@GeneratedValue
	private int workerId;
	private String name;
	private String surname;
	@Temporal(TemporalType.DATE)
	private Date birthday;
	private float performance;
	@ManyToMany
	private List<Role> roles;

	public Worker(String name, String surname, Date birthday, float performance, List<Role> roles) {
		this.name = name;
		this.surname = surname;
		this.birthday = birthday;
		this.performance = performance;
		this.roles = roles;
	}

	public int getWorkerId(){
		return workerId;
	}

	public void setWorkerId(int newVal){
		workerId = newVal;
	}

	public String getName(){
		return name;
	}

	public void setName(String newVal){
		name = newVal;
	}

	public String getSurname(){
		return surname;
	}

	public void setSurname(String newVal){
		surname = newVal;
	}

	public Date getBirthday(){
		return birthday;
	}

	public void setBirthday(Date newVal){
		birthday = newVal;
	}

	public float getPerformance(){
		return performance;
	}

	public void setPerformance(float newVal){
		performance = newVal;
	}

	public List<Role> getRoles(){
		return roles;
	}

	public void setRoles(List<Role> newVal){
		roles = newVal;
	}

}