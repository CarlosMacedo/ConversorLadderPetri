package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

import main.WriterPetri;

/**
 * Responsible for writing Functions in CPNTools
 * 
 * @author Carlos Macedo
 */
public class Functions {
	String function;
	
	/**
	 * @param function What function to write
	 */
	public Functions(String function) {
		this.function = function;
	}
	
	/**
	 * @param path Absolute path of the .cpn file.
	 * @param buffWrite It is used to write to the file.
	 * @throws IOException Error reading file.
	 */
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
