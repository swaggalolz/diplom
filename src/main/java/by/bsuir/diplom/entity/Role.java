package by.bsuir.diplom.entity;


import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table
public class Role {
	@Id
	@GeneratedValue
	private int roleId;
	private String roleName;
	@OneToMany(mappedBy = "workerId")
	private List<Worker> workers = new ArrayList<Worker>();


	public String getRoleName(){
		return roleName;
	}

	public void setRoleName(String newVal){
		roleName = newVal;
	}

	public int getRoleId(){
		return roleId;
	}

	public void setRoleId(int newVal){
		roleId = newVal;
	}

	public List<Worker> getWorkers(){
		return workers;
	}

	public void setWorkers(List<Worker> newVal){
		workers = newVal;
	}

}