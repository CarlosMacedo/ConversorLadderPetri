package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

import main.WriterPetri;

public class Functions {
	String function;
	
	public Functions(String function) {
		this.function = function;
	}
	
	public void writerFunction(String path, BufferedWriter buffWrite) throws IOException{
		BufferedReader buffFuction = new BufferedReader(new FileReader(path));
		String line = buffFuction.readLine();
		while (line != null) {
			 if (line.equals("<!-- ml : ml id | STRING -->")){
				 insertFunctionId(buffWrite);
			 } else if (line.equals("<!-- fun : fun formula(a,b,c) = a orelse b orelse c; | STRING -->")) {
				 insertFunction(buffWrite);
			 } else {
				 buffWrite.append(line+"\n");
			 }
		
			 line = buffFuction.readLine();		
		}				
		buffFuction.close();
	}

	private void insertFunction(BufferedWriter buffWrite) throws IOException {
		buffWrite.append(function+"\n");
	}

	private void insertFunctionId(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+ WriterPetri.increaseId() +"\"\n");
	}
}
