/**
 * Copyright 2017 Carlos Macêdo
 * 
 * This file is part of ConversorLadderPetri software.
 * 
 *  ConversorLadderPetri is free software: you can 
 *  redistribute it and/or modify it under the terms of the GNU General 
 *  Public License as published by the Free Software Foundation, either 
 *  version 3 of the License, or (at your option) any later version.
 *  
 *  ConversorLadderPetri is distributed in the hope that
 *  it will be useful, but WITHOUT ANY WARRANTY; without even the implied 
 *  warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See 
 *  the GNU General Public License for more details.
 *  You should have received a copy of the GNU General Public License
 *  along with ConversorLadderPetri. 
 *  If not, see <http://www.gnu.org/licenses/>.
 *  
 *  Contact: carlosmacedo025 at gmail dot com
 */
package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import main.WriterPetri;

/**
 * Responsible for writing FusionOutside in CPNTools
 * 
 * @author Carlos Macedo
 */

public class FusionOutside {
	private String name;
	private ArrayList<Integer> idref;

	/**
	 * @param name Name of the place where the merger belongs
	 * @param idref Ref of the place where the merger belongs
	 */
	public FusionOutside(String name, ArrayList<Integer> idref) {
		this.name = name;
		this.idref = idref;
	}
	
	/**
	 * @param path Absolute path of the .cpn file.
	 * @param buffWrite It is used to write to the file.
	 * @throws IOException Error reading file.
	 */
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
