package by.bsuir.diplom.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table
public class Role {
	@Id
	@GeneratedValue
	private int roleId;
	private String roleName;

    public Role(String roleName) {
        this.roleName = roleName;
    }

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

}