package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Read and extract information from the .ld file.
 * 
 * @author Carlos Macedo
 */

public class ManipulatorLadder {	
	/**
	 * Read the ladder file and extract all information.
	 * @param path .ld File location.
	 * @param url .cpn File location.
	 * @throws IOException Error reading file.
	 */
	public void ladderToPetri(String path, String url) throws IOException {
		BufferedReader buffRead = new BufferedReader(new FileReader(path));
		String[] words; //String line
		ArrayList<String> inputs = new ArrayList<String>();
		ArrayList<String> outputs = new ArrayList<String>();
		ArrayList<String> functions = new ArrayList<String>();
		String funParallel = "";
		String formula = "";
		//String function = "";
		final int IOLIST = 0, RUNG = 1, PARALLEL = 2;	
		boolean[] whichPart = {false, false, false};//control variables
		
		
		String line = buffRead.readLine();
		while (line != null) {	
			/*Control*/
			if (line.equals("IO LIST")) {	
				whichPart[IOLIST] = true;
			} else if (line.equals("END") && whichPart[IOLIST]) {
				whichPart[IOLIST] = false;
			} else if (line.equals("    PARALLEL")) {
				whichPart[PARALLEL] = true;
			} else if (line.equals("    END") && whichPart[PARALLEL]) {
				whichPart[PARALLEL] = false;
				funParallel += ")";//close (				
				formula = insertFormula(formula, funParallel);				
				funParallel = "";
			} else if (line.equals("RUNG")) {
				whichPart[RUNG] = true;
			} else if (line.equals("END") && whichPart[RUNG]) {
				whichPart[RUNG] = false;
				functions.add(creatAndAddFunction(functions,inputs, formula));
				System.out.println(formula);
				formula = "";
			} else {
				/*Extract all*/
				if (whichPart[IOLIST]){ //inside list
					final int WORD = 4, KEYIO = 1;
					words = line.split(" ");
					char keyio = words[WORD].charAt(KEYIO);
					if (keyio == 'I'){
						inputs.add(words[WORD]);
					} else if (keyio == 'O') {
						outputs.add(words[WORD]);
					} else {
						System.out.println("Insert I\\O!");
					}
				/*Extract boolean formula*/	
				} else if (whichPart[RUNG] && whichPart[PARALLEL]) { //inside parallel
					final int CONTACTS = 8, XN = 9, NEG = 10;
					words = line.split(" ");
					if (words[CONTACTS].equals("CONTACTS")){
						if (funParallel.equals("")){
							funParallel += "(";
							if (words[NEG].equals("1")) {
								funParallel += "not("+ words[XN] +")";
							} else {
								funParallel += words[XN];
							}
						} else {
							funParallel += " orelse ";
							if (words[NEG].equals("1")) {
								funParallel += "not("+ words[XN] +")";
							} else {
								funParallel += words[XN];
							} 
						}
					}
				} else if (whichPart[RUNG]) { //inside rung
					final int CONTACTS = 4, XN = 5, NEGIN = 6;
					final int COIL = 4, NEGOUT = 6;
					//final int YOUT = 5;
					words = line.split(" ");
					if (words[CONTACTS].equals("CONTACTS")){
						if (formula.equals("")){
							if (words[NEGIN].equals("1")) {
								formula += "not("+ words[XN] +")";
							} else {
								formula += words[XN];
							}
						} else {
							formula += " andalso ";
							if (words[NEGIN].equals("1")) {
								formula += "not("+ words[XN] +")";
							} else {
								formula += words[XN];
							}
						}
					} else if (words[COIL].equals("COIL") &&
								words[NEGOUT].equals("1")) {
						formula = "not("+ formula +")";
					}
				}
			}			
			line = buffRead.readLine();
		}
		
		buffRead.close();		
		System.out.println("Leitura do ladder conclu�da.");
		
		if (inputs.isEmpty() || outputs.isEmpty()){
			System.out.println("A entrada n�o pode ser vazia.");
		} else {
			WriterPetri wp = new WriterPetri(inputs, outputs, functions);
			wp.writePetri(url);
		}			
	}
	/**
	 * Creates the function of each step.
	 * 
	 * @param functions functions from each step. 
	 * @param inputs Each step contact
	 * @param formula The Boolean equation of the step
	 * @return the function 
	 */
	private String creatAndAddFunction(ArrayList<String> functions, ArrayList<String> inputs, String formula) {
		String result = "fun formula" + (functions.size()+1) +"(";
		for (int i = 0; i < inputs.size(); i++) {
			result += inputs.get(i);
			
			if(i < inputs.size()-1) result += ",";
		}
		result += ") = " + formula +";";
		return result;
	}

	/**
	 * Create a Boolean equation of the step
	 * @param formula The Boolean equation current
	 * @param funParallel What to add the formula
	 * @return equation
	 */
	private String insertFormula(String formula, String funParallel) {
		String result;
		
		if (formula.equals("")){
			result = formula + funParallel;
		} else {
			result = formula + " andalso " + funParallel;
		}		
		return result;
	}
}
