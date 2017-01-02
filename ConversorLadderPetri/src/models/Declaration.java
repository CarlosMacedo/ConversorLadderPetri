package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

public class Declaration {
	private ArrayList<String> inputs;
	private ArrayList<String> outputs;
	private ArrayList<String> functions;
	
	public Declaration(ArrayList<String> inputs, ArrayList<String> outputs, ArrayList<String> functions) {
		this.inputs = inputs;
		this.outputs = outputs;
		this.functions = functions;
	}
	
	/**
	 * Declara todo tipo de váriavel que será utilizada.
	 * @param path Caminho do arquivo a ser copiado
	 * @param buffWrite Buffer do arquivo para escrever
	 * @throws IOException
	 */
	public void writerDeclarations(String path, BufferedWriter buffWrite) throws IOException{
		BufferedReader buffDeclarations = new BufferedReader(new FileReader(path));
		String line = buffDeclarations.readLine();
		while (line != null) {
			 if (line.equals("<!-- function: insert n function? | STRING -->")){
				insertNFunction(buffWrite);
			 } else if (line.equals("<!-- choice: insert product ou alias | INPUT -->")) {
				insertProductOrAliasInput(buffWrite);
			 } else if (line.equals("<!-- id: how many BOOL in product | INPUT -->")){
				 insertBoolsInputs(buffWrite);
			 } else if (line.equals("<!-- choice: insert /product ou /alias | INPUT-->")) {
				 insertBarProductOrAliasInput(buffWrite);
			 } else if (line.equals("<!-- layout: product expression | INPUT -->")) {
				 insertProductExpressionInput(buffWrite);
			 } else if (line.equals("<!-- choice: insert product ou alias | OUTPUTS -->")) {
				 insertProductOrAliasOutput(buffWrite);
			 } else if (line.equals("<!-- id: how many BOOL in product | OUTPUTS -->")) {
				 insertBoolsOutput(buffWrite);
			 } else if (line.equals("<!-- choice: insert /product ou /alias | OUTPUTS-->")) {
				 insertBarProductOrAliasOutput(buffWrite);
			 } else if (line.equals("<!-- layout: product expression | OUTPUTS -->")) {
				 insertProductExpressionOutput(buffWrite);
			 } else  if (line.equals("<!-- id: how many var BOOL in product | OUTPUTS -->")) {
				 insertBoolsOutputVar(buffWrite);
			 } else  if (line.equals("<!-- layout: var expression | OUTPUTS -->")) {
				 insertProductExpressionOutputVar(buffWrite);
			 } else {
				 buffWrite.append(line+"\n");
			 }
		
			 line = buffDeclarations.readLine();		
		}				
		buffDeclarations.close();
	}
	
	private void insertNFunction(BufferedWriter buffWrite) throws IOException {
		for (int i = 0; i < functions.size(); i++) {
			Functions f = new Functions(functions.get(i));			
			URL resource = Arc.class.getResource("/templates/");
			String pathModels = resource.getFile();
			f.writerFunction(pathModels + "12-functions.cpn", buffWrite);
		}
	}

	private void insertBarProductOrAliasOutput(BufferedWriter buffWrite) throws IOException {
		if (outputs.size() > 1) {
			buffWrite.append("</product>\n");
		} else {
			buffWrite.append("</alias>\n");
		}
	}

	private void insertProductOrAliasOutput(BufferedWriter buffWrite) throws IOException {
		if (outputs.size() > 1) {
			buffWrite.append("<product>\n");
		} else {
			buffWrite.append("<alias>\n");
		}
	}

	private void insertBarProductOrAliasInput(BufferedWriter buffWrite) throws IOException {
		if (inputs.size() > 1) {
			buffWrite.append("</product>\n");
		} else {
			buffWrite.append("</alias>\n");
		}
	}

	private void insertProductOrAliasInput(BufferedWriter buffWrite) throws IOException {
		if (inputs.size() > 1) {
			buffWrite.append("<product>\n");
		} else {
			buffWrite.append("<alias>\n");
		}
	}

	/**
	 * Responsável pela tag: <layout>var out1, out2 : BOOL;</layout>.
	 * 
	 * @param buffWrite
	 * @throws IOException 
	 */
	private void insertProductExpressionOutputVar(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("<layout>var ");
		
		for (int i = 1; i <= outputs.size(); i++) {
			buffWrite.append("out"+i);
			
			if (i <= outputs.size()-1) buffWrite.append(", ");
		}
		buffWrite.append(" : BOOL;</layout>\n");
	}

	/**
	 * Responsável pela tag: <id>out1</id>.
	 * 
	 * @param buffWrite
	 * @throws IOException 
	 */
	private void insertBoolsOutputVar(BufferedWriter buffWrite) throws IOException {
		for (int i = 1; i <= outputs.size(); i++) {
			buffWrite.append("<id>out"+i+"</id>\n");
		}
	}

	/**
	 * Responsável pela tag: <layout>colset OUTPUTS = product BOOL*BOOL;</layout>.
	 * 
	 * @param buffWrite
	 * @throws IOException 
	 */
	private void insertProductExpressionOutput(BufferedWriter buffWrite) throws IOException {
		if (outputs.size() > 1){
			buffWrite.append("<layout>colset OUTPUTS = product ");
			
			for (int i = 0; i < outputs.size(); i++) {
				buffWrite.append("BOOL");
				
				if (i < outputs.size()-1) buffWrite.append("*");
			}
			buffWrite.append(";</layout>\n");
		} else {
			buffWrite.append("<layout>colset OUTPUTS = BOOL;</layout>\n");
		}
	}

	/**
	 * Responsável pela tag: <id>BOOL</id>.
	 * 
	 * @param buffWrite
	 * @throws IOException 
	 */
	private void insertBoolsOutput(BufferedWriter buffWrite) throws IOException {
		for (int i = 0; i < outputs.size(); i++) {
			buffWrite.append("<id>BOOL</id>\n");
		}
	}


	/**
	 * Responsável pela tag: <layout>colset INPUTS = product BOOL*BOOL*BOOL;</layout>.
	 * 
	 * @param buffWrite
	 * @throws IOException 
	 */
	private void insertProductExpressionInput(BufferedWriter buffWrite) throws IOException {
		if (inputs.size() > 1){
			buffWrite.append("<layout>colset INPUTS = product ");
			
			for (int i = 0; i < inputs.size(); i++) {
				buffWrite.append("BOOL");
				
				if (i < inputs.size()-1) buffWrite.append("*");
			}
			buffWrite.append(";</layout>\n");
		} else {
			buffWrite.append("<layout>colset INPUTS = BOOL;</layout>\n");
		}
	}


	/**
	 * Responsável pela tags: <id>BOOL</id>.
	 * 
	 * @param buffWrite
	 * @throws IOException 
	 */
	private void insertBoolsInputs(BufferedWriter buffWrite) throws IOException{
		for (int i = 0; i < inputs.size(); i++) {
			buffWrite.append("<id>BOOL</id>\n");
		}		
	}

	public ArrayList<String> getInputs() {
		return inputs;
	}

	public void setInputs(ArrayList<String> inputs) {
		this.inputs = inputs;
	}

	public ArrayList<String> getOutputs() {
		return outputs;
	}

	public void setOutputs(ArrayList<String> outputs) {
		this.outputs = outputs;
	}
	
	
}
