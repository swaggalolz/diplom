package by.bsuir.diplom.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table
public class Box implements Serializable {
	@Id
	@GeneratedValue
	private int boxId;
	private String startSerialNumber;
	private String endSerialNumber;
	@ManyToOne
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	private Worker worker;

    public Box() {
    }

    public Box(String startSerialNumber, String endSerialNumber, Worker worker) {
		this.startSerialNumber = startSerialNumber;
		this.endSerialNumber = endSerialNumber;
		this.worker = worker;
	}

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