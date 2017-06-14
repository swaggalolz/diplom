package by.bsuir.diplom.entity;


import org.hibernate.annotations.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Worker implements Serializable {
	@Id
	@GeneratedValue
	private int workerId;
	private String name;
	private String surname;
    private String password;
	@Temporal(TemporalType.DATE)
	private Date birthday;
	private float performance;
	@ManyToMany
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	private List<Role> roles;

    public Worker() {
    }

    public Worker(String name, String surname, String password, Date birthday, float performance, List<Role> roles) {
        this.name = name;
        this.surname = surname;
        this.password = password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}