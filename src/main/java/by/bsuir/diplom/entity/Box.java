package by.bsuir.diplom.entity;


public class Box {

	private int boxId;
	private String startSerialNumber;
	private String endSerialNumber;
	private Worker worker;



	public int getBoxId(){
		return boxId;
	}

	public void setBoxId(int newVal){
		boxId = newVal;
	}

	public Worker getWorker(){
		return worker;
	}

	public void setWorker(Worker newVal){
		worker = newVal;
	}

	public String getEndSerialNumber(){
		return endSerialNumber;
	}

	public void setEndSerialNumber(String newVal){
		endSerialNumber = newVal;
	}

	public String getStartSerialNumber(){
		return startSerialNumber;
	}

	public void setStartSerialNumber(String newVal){
		startSerialNumber = newVal;
	}

}