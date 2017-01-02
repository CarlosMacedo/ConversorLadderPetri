package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

import main.WriterPetri;

public class FusionInside {
	private String name;
	private float x;
	private float y;
	
	public FusionInside(String name, float x, float y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	public void writerFusion(String path, BufferedWriter buffWrite) throws IOException{
		BufferedReader buffFusion = new BufferedReader(new FileReader(path));
		String line = buffFusion.readLine();
		while (line != null) {
			 if (line.equals("<!-- fusioninfo : fusioninfo id | STRING -->")){
				 insertIdFusion(buffWrite);
			 } else if (line.equals("<!-- fusioninfo : fusioninfo name | STRING -->")) {
				 insertNameFusion(buffWrite);
			 } else if (line.equals("<!-- posattr : fusion position x | STRING -->")) {
				 insertFusionPosx(buffWrite);
			 } else if (line.equals("<!-- posattr : fusion position y | STRING -->")) {
				 insertFusionPosy(buffWrite);
			 } else {
				 buffWrite.append(line+"\n");
			 }
		
			 line = buffFusion.readLine();		
		}				
		buffFusion.close();
	}

	private void insertFusionPosy(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\""+this.y+"\"\n");
	}

	private void insertFusionPosx(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\""+this.x+"\"\n");
	}

	private void insertNameFusion(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\""+this.name+"\"\n");
	}

	private void insertIdFusion(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+WriterPetri.increaseId()+"\"\n");
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
	
	
}