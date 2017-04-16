package by.bsuir.diplom.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table
public class Shift implements Serializable {
	@Id
	@GeneratedValue
	private int shiftId;
    @ManyToOne
	private Worker worker;
	@Temporal(TemporalType.DATE)
	private Date startTime;
    @Temporal(TemporalType.DATE)
	private Date endTime;

    public Shift(Worker worker, Date startTime, Date endTime) {
        this.worker = worker;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getShiftId(){
		return shiftId;
	}

	public void setShiftId(int newVal){
		shiftId = newVal;
	}

	public Worker getWorker(){
		return worker;
	}

	public void setWorker(Worker newVal){
		worker = newVal;
	}

	public Date getEndTime(){
		return endTime;
	}

	public void setEndTime(Date newVal){
		endTime = newVal;
	}

	public Date getStartTime(){
		return startTime;
	}

	public void setStartTime(Date newVal){
		startTime = newVal;
	}

}