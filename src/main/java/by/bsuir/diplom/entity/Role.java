package by.bsuir.diplom.entity;


import java.util.List;

public class Role {

	private int roleId;
	private String roleName;
	private List<Worker> workers;


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