package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.WriterPetri;

public class FusionOutside {
	private String name;
	private ArrayList<Integer> idref;

	public FusionOutside(String name, ArrayList<Integer> idref) {
		this.name = name;
		this.idref = idref;
	}
	
	public void writerArc(String path, BufferedWriter buffWrite) throws IOException{
		BufferedReader buffFusion = new BufferedReader(new FileReader(path));
		String line = buffFusion.readLine();
		while (line != null) {
			 if (line.equals("<!-- fusion : fusion id | STRING -->")){
				 insertFusionId(buffWrite);
			 } else if (line.equals("<!-- fusion : fusion name | STRING -->")) {
				 insertFusionName(buffWrite);
			 } else if (line.equals("<!-- fusion : how many ref of the fusion | STRING -->")) {
				 insertFusionQnt(buffWrite);
			 } else {
				 buffWrite.append(line+"\n");
			 }
		
			 line = buffFusion.readLine();		
		}				
		buffFusion.close();
	}

	private void insertFusionQnt(BufferedWriter buffWrite) throws IOException {
		for (int i = 0; i < idref.size(); i++) {
			buffWrite.append("<fusion_elm idref=\"IDLD"+idref.get(i)+"\"/>\n");
		}
	}

	private void insertFusionName(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\""+this.name+"\"\n");
	}

	private void insertFusionId(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+WriterPetri.increaseId()+"\"\n");
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<Integer> getIdref() {
		return idref;
	}

	public void setIdref(ArrayList<Integer> idref) {
		this.idref = idref;
	}
}