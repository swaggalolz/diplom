package by.bsuir.diplom.entity;


import java.util.Date;

public class Seal {

	private int sealId;
	private String serialNumber;
	private Date creationDate;
	private Worker worker;


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