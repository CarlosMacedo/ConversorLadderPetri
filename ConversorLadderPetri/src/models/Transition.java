package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

import main.WriterPetri;

public class Transition {
	private int id;
	private float x;
	private float y;
	private String name;
	
	public Transition(int id, float x, float y, String name) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.name = name;
	}

	public void writerTrans(String path, BufferedWriter buffWrite) throws IOException{
		BufferedReader buffTrans = new BufferedReader(new FileReader(path));
		String line = buffTrans.readLine();
		while (line != null) {
			 if (line.equals("<!-- trans : trans id | STRING -->")){
				 insertIdTrans(buffWrite);
			 } else if (line.equals("<!-- posattr : trans position x | STRING -->")) {
				 insertTransPosx(buffWrite);
			 } else if (line.equals("<!-- posattr : trans position y | STRING -->")) {
				 insertTransPosy(buffWrite);
			 } else if (line.equals("<!-- text : name of the trans | STRING -->")) {
				 insertTransName(buffWrite);
			 } else  if (line.equals("<!-- cond : cond id | STRING -->")) {
				 insertCondId(buffWrite);
			 } else  if (line.equals("<!-- time : time id | STRING -->")) {
				 insertTimeId(buffWrite);
			 } else if (line.equals("<!-- code : code id | STRING -->")){
				 insertCodeId(buffWrite);
			 } else if (line.equals("<!-- priority : priority id | STRING -->")) {
				 insertPriorityId(buffWrite);
			 }  else {
				 buffWrite.append(line+"\n");
			 }
		
			 line = buffTrans.readLine();		
		}				
		buffTrans.close();
	}
	
	private void insertPriorityId(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+WriterPetri.increaseId()+"\"\n");
	}

	private void insertCodeId(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+WriterPetri.increaseId()+"\"\n");
		
	}

	private void insertTimeId(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+WriterPetri.increaseId()+"\"\n");
	}

	private void insertCondId(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+WriterPetri.increaseId()+"\"\n");
	}

	private void insertTransName(BufferedWriter buffWrite) throws IOException {
		buffWrite.append(this.name+"\n");
	}

	private void insertTransPosy(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\""+this.y+"\"\n");
	}

	private void insertTransPosx(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\""+this.x+"\"\n");
	}

	private void insertIdTrans(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+this.id+"\"\n");	
	}

	public int getId() {
		return id;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public String getName() {
		return name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setX(float x) {
		this.x = x;
	}

	public void setY(float y) {
		this.y = y;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
}
