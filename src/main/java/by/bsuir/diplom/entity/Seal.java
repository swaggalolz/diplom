package by.bsuir.diplom.entity;


import java.util.Date;
import javax.persistence.*;

@Entity
@Table
public class Seal {
	@Id
	@GeneratedValue
	private int sealId;
	private String serialNumber;
	@Temporal(TemporalType.DATE)
	private Date creationDate;
    @ManyToOne
    private Worker worker;

    public Seal(String serialNumber, Date creationDate, Worker worker) {
        this.serialNumber = serialNumber;
        this.creationDate = creationDate;
        this.worker = worker;
    }

    public int getSealId(){
		return sealId;
	}

	public void setSealId(int newVal){
		sealId = newVal;
	}

	public String getSerialNumber(){
		return serialNumber;
	}

	public void setSerialNumber(String newVal){
		serialNumber = newVal;
	}

	public Date getCreationDate(){
		return creationDate;
	}

	public void setCreationDate(Date newVal){
		creationDate = newVal;
	}

	public Worker getWorker(){
		return worker;
	}

	public void setWorker(Worker newVal){
		worker = newVal;
	}

}