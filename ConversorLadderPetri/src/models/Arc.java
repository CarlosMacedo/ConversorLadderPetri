package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;

import main.WriterPetri;

/**
 * Responsible for writing arch in CPNTools
 * 
 * @author Carlos Macedo
 */

public class Arc {
	private String orientation;
	private Transition idTrans;
	private Place idPlace;
	private String color;
	
	/**
	 * 
	 * @param orientation left or right
	 * @param idTrans  Trans id
	 * @param idPlace Place id
	 * @param color Arc color
	 */
	public Arc(String orientation, Transition idTrans, Place idPlace, String color) {
		this.orientation = orientation;
		this.idTrans = idTrans;
		this.idPlace = idPlace;
		this.color = color;
	}
	/**
	 * @param path Absolute path of the .cpn file.
	 * @param buffWrite It is used to write to the file.
	 * @throws IOException Error reading file.
	 */
	public void writerArc(String path, BufferedWriter buffWrite) throws IOException{
		BufferedReader buffArc = new BufferedReader(new FileReader(path));
		String line = buffArc.readLine();
		while (line != null) {
			 if (line.equals("<!-- arc : arc id | STRING -->")){
				 insertArcId(buffWrite);
			 } else if (line.equals("<!-- orientation : TtoP or PtoT or BOTHDIR | STRING -->")) {
				 insertOrientation(buffWrite);
			 } else if (line.equals("<!-- transend : id trans | STRING -->")) {
				 insertIdTrans(buffWrite);
			 } else if (line.equals("<!-- transend : id place | STRING -->")) {
				 insertIdPlace(buffWrite);
			 } else  if (line.equals("<!-- annot : annot id | STRING -->")) {
				 insertAnnotId(buffWrite);
			 } else if (line.equals("<!-- posattr : posx | ARC -->")) {
				 insertPosxArc(buffWrite);
			 } else if (line.equals("<!-- posattr : posy | ARC -->")) {
				 insertPosyArc(buffWrite);
		     } else  if (line.equals("<!-- text : color arc | STRING -->")) {
				 insertColor(buffWrite);
			 } else {
				 buffWrite.append(line+"\n");
			 }
		
			 line = buffArc.readLine();		
		}				
		buffArc.close();
	}

	
	private void insertPosyArc(BufferedWriter buffWrite) throws IOException {
		int desloc = 15;
		if (!this.orientation.equals("PtoT")) desloc *= -1;
		buffWrite.append("\""+ ((idPlace.getPostiony()+idTrans.getY())/2 + desloc) +"\"\n");
	}

	private void insertPosxArc(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\""+ (idPlace.getPositionx()+idTrans.getX())/2 +"\"\n");
	}

	private void insertColor(BufferedWriter buffWrite) throws IOException {
		buffWrite.append(this.color+"\n");
	}

	private void insertAnnotId(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+WriterPetri.increaseId()+"\"\n");
	}

	private void insertIdPlace(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+this.idPlace.getId()+"\"\n");
	}

	private void insertIdTrans(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+this.idTrans.getId()+"\"\n");
	}

	private void insertOrientation(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\""+this.orientation+"\"\n");
	}

	private void insertArcId(BufferedWriter buffWrite) throws IOException {
		buffWrite.append("\"IDLD"+WriterPetri.increaseId()+"\"\n");
	}

	public String getOrientation() {
		return orientation;
	} 

	public void setOrientation(String orientation) {
		this.orientation = orientation;
	}

	public Transition getIdTrans() {
		return idTrans;
	}

	public void setIdTrans(Transition idTrans) {
		this.idTrans = idTrans;
	}

	public Place getIdPlace() {
		return idPlace;
	}

	public void setIdPlace(Place idPlace) {
		this.idPlace = idPlace;
	}
	
	
}
