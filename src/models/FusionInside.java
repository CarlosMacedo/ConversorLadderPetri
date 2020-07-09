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

import main.WriterPetri;

/**
 * Responsible for writing FusionInside in CPNTools
 * 
 * @author Carlos Macedo
 */

public class FusionInside {
	private String name;
	private float x;
	private float y;
	
	/**
	 * @param name Name of the place where the merger belongs
	 * @param x Coordinate of the Cartesian plane, which should remain.
	 * @param y Coordinate of the Cartesian plane, which should remain.
	 */
	public FusionInside(String name, float x, float y) {
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @param path Absolute path of the .cpn file.
	 * @param buffWrite It is used to write to the file.
	 * @throws IOException Error reading file.
	 */
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
