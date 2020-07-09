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
import java.net.URL;

import main.WriterPetri;

/**
 * Responsible for writing Place in CPNTools
 * 
 * @author Carlos Macedo
 */

public class Place {
	private int id;
	private float x;
	private float y;
	private String name;   
	private String type;
	private String color;
	private String fusionName;
	

	/**
	 * @param id Place id
	 * @param positionx Coordinate of the Cartesian plane, which should remain.
	 * @param postiony Coordinate of the Cartesian plane, which should remain.
	 * @param name Place name
	 * @param type Place type
	 * @param color Place color type
	 * @param fusionName Contain or not fusion
	 */
	public Place(int id, float positionx, float postiony, String name,
			String type, String color, String fusionName) {
		this.id = id;
		this.x = positionx;
		this.y = postiony;
		this.name = name;
		this.type = type;
		this.color = color;
		this.fusionName = fusionName;
	}
	
	/**
	 * @param path Absolute path of the .cpn file.
	 * @param buffWrite It is used to write to the file.
	 * @throws IOException Error reading file.
	 */
	public void writerPlace(String path, BufferedWriter buffWrite) throws IOException{
		BufferedReader buffPlace = new BufferedReader(new FileReader(path));
		String line = buffPlace.readLine();
		while (line != null) {
			 if (line.equals("<!-- place : place id | STRING -->")){
				 insertIdPlace(buffWrite);
			 } else if (line.equals("<!-- posattr : place position x | STRING -->")) {
				 insertPlacePosx(buffWrite);
			 } else if (line.equals("<!-- posattr : place position y | STRING -->")) {
				 insertPlacePosy(buffWrite);
			 } else if (line.equals("<!-- text : name of the place | STRING -->")) {
				 insertPlaceName(buffWrite);
			 } else  if (line.equals("<!-- type : type place id | STRING -->")) {
				 insertTypeId(buffWrite);
			 } else  if (line.equals("<!-- posattr : type place position x | STRING -->")) {
				 insertTypePosx(buffWrite);
			 } else if (line.equals("<!-- posattr : type place position y | STRING -->")){
				 insertTypePosy(buffWrite);
			 } else if (line.equals("<!-- text : type place | STRING -->")) {
				 insertType(buffWrite);
			 } else if (line.equals("<!-- initmark : initmark id | STRING -->")) {
				 insertInitmarkId(buffWrite);
			 } else if (line.equals("<!-- posattr : color place position x | STRING -->")) {
				 insertInitmarkPosx(buffWrite);
			 } else  if (line.equals("<!-- posattr : color place position y | STRING -->")) {
				 insertInitmarkPosy(buffWrite);
			 } else  if (line.equals("<!-- text : color place | STRING -->")) {
				 insertColor(buffWrite);
			 } else if (line.equals("<!-- fusioninfo : is fusion? | STRING -->")){
				 insertFusion(buffWrite);
			 } else {
				 buffWrite.append(line+"\n");
			 }
		
			 line = buffPlace.readLine();		
		}				
		buffPlace.close();
	}

	

	private void insertFusion(BufferedWriter buffWrite) throws IOException {
		if (!this.fusionName.equals("")) {
			FusionInside f = new FusionInside(this.fusionName, this.x+20, this.y+20);
			URL resource = Arc.class.getResource("/templates/");
			String pathModels = resource.getFile();
			f.writerFusion(pathModels + "5-fusion.cpn", buffWrite);
		}
	}


	private void insertColor(BufferedWriter buffWrite) throws IOException {
		buffWrite.append(this.color+"\n");
	}


	private void insertInitmarkPosy(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\""+(this.y+55)+"\"\n");
	}


	private void insertInitmarkPosx(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\""+(this.x+40)+"\"\n");
	}


	private void insertInitmarkId(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+WriterPetri.increaseId()+"\"\n");
	}


	private void insertType(BufferedWriter buffWrite) throws IOException {
		buffWrite.append(this.type+"\n");
	}


	private void insertTypePosy(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\""+(this.y+40)+"\"\n");
	}


	private void insertTypePosx(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\""+(this.x+40)+"\"\n");
		
	}


	private void insertTypeId(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+WriterPetri.increaseId()+"\"\n");
	}


	private void insertPlaceName(BufferedWriter buffWrite) throws IOException {
		buffWrite.append(this.name+"\n");
	}


	private void insertPlacePosy(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\""+this.y+"\"\n");
	}


	private void insertPlacePosx(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\""+this.x+"\"\n");
	}


	private void insertIdPlace(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+this.id+"\"\n");
	}


	public int getId() {
		return id;
	}


	public float getPositionx() {
		return x;
	}


	public float getPostiony() {
		return y;
	}


	public String getName() {
		return name;
	}


	public String getType() {
		return type;
	}


	public String getColor() {
		return color;
	}


	public String getFusionName() {
		return fusionName;
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


	public void setType(String type) {
		this.type = type;
	}


	public void setColor(String color) {
		this.color = color;
	}


	public void setFusionName(String fusionName) {
		this.fusionName = fusionName;
	}
}
