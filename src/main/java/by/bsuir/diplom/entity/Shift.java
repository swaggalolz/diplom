package by.bsuir.diplom.entity;


import java.util.Date;

public class Shift {

	private int shiftId;
	private Worker worker;
	private Date startTime;
	private Date endTime;


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