package main;

import java.io.BufferedWriter;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import models.Arc;
import models.Declaration;
import models.FusionOutside;
import models.Place;
import models.Transition;
 
public class WriterPetri {
	private ArrayList<String> inputs;
	private ArrayList<String> outputs;
	private ArrayList<String> functions;
	private ArrayList<Place> places;
	private ArrayList<Transition> transitions;
	private ArrayList<Arc> arcs;
	private static int qntID = 0;
	private static int fusionID = 0;
	private static final int x0 = 200;
	private static final int y0 = 140;
	private static final int space = 250;
	
	public WriterPetri(ArrayList<String> inputs, ArrayList<String> outputs, ArrayList<String> functions) {
		this.inputs = inputs;
		this.outputs = outputs;
		this.functions = functions;
		this.places = new ArrayList<Place>();
		this.transitions = new ArrayList<Transition>();
		this.arcs = new ArrayList<Arc>();		
	}
	
	/**
	 * Fun��o principal. Cria o xml.
	 * @param inputs Array com os nomes dos inputs do ladder.
	 * @param outputs Array com os nomes dos outputs do ladder.
	 * @throws IOException
	 */
	public void writePetri(String url) throws IOException {
		URL resource = Arc.class.getResource("/templates/");
		String pathModels = resource.getFile();		
		BufferedWriter buffWrite = new BufferedWriter(new FileWriter(url));

		
		this.copyAndPast(pathModels+"1-header.cpn", buffWrite);
		System.out.println("Cabe�alho pronto.");
		this.writerDeclarations(pathModels+"2-declarations.cpn", buffWrite);		
		System.out.println("Declara��es prontas.");
		this.copyAndPast(pathModels+"3-beginPag.cpn", buffWrite);
		this.createPlaces();
		this.writerPlaces(pathModels+"4-place.cpn", buffWrite);
		System.out.println("Lugares prontos.");
		this.createTrans();
		this.writerTrans(pathModels+"6-trans.cpn", buffWrite);
		System.out.println("Transa��es prontas.");
		this.createArcs();
		this.writerArcs(pathModels+"7-arc.cpn", buffWrite);
		System.out.println("Arcos prontos.");
		this.copyAndPast(pathModels+"8-endPag.cpn", buffWrite);
		System.out.println("Fim de p�gina.");
		this.createFusions(pathModels+"9-fusion.cpn", buffWrite);
		System.out.println("Lugares Fusions adicionados.");
		this.copyAndPast(pathModels+"10-footer.cpn", buffWrite);
		System.out.println("Rede Petri gerada com sucesso.");
		
		buffWrite.close();
	}


	
	
	private void writerArcs(String path, BufferedWriter buffWrite) throws IOException {
		for (int i = 0; i < arcs.size(); i++) {
			arcs.get(i).writerArc(path, buffWrite);
		}
	}
	
	private void createArcs() {			
		arcs.add(new Arc("PtoT", idTrans("ReadInputs", 0), idPlace("Start", 0), ""));
		arcs.add(new Arc("PtoT", idTrans("ReadInputs", 0), idPlace("In", 0), "tupleIn"));	
		arcs.add(new Arc("TtoP", idTrans("ReadInputs", 0), idPlace("In", 0), "newTupleIn"));
		arcs.add(new Arc("PtoT", idTrans("Release", 0), idPlace("Done", 0), ""));
		arcs.add(new Arc("TtoP", idTrans("Release", 0), idPlace("Start", 0), ""));
		arcs.add(new Arc("TtoP", idTrans("Release", 0), idPlace("Out", 0), "newTupleOut"));
		arcs.add(new Arc("PtoT", idTrans("Release", 0), idPlace("Out", 0), "tupleOut"));
		arcs.add(new Arc("BOTHDIR", idTrans("Release", 0), idPlace("Mem", outputs.size()-1), "newTupleOut"));
		arcs.add(new Arc("TtoP", idTrans("R"+outputs.size()+"Logic", 0), idPlace("Done", 0), ""));
		arcs.add(new Arc("TtoP", idTrans("ReadInputs", 0), idPlace("R1", 0), ""));
		
		for (int i = 1; i <= outputs.size(); i++) {
			arcs.add(new Arc("PtoT", idTrans("R"+i+"Logic", 0), idPlace("R"+i, 0), ""));
			arcs.add(new Arc("BOTHDIR", idTrans("R"+i+"Logic", 0), idPlace("In", i), "tupleIn"));		
			arcs.add(new Arc("PtoT", idTrans("R"+i+"Logic", 0), idPlace("Mem", i-1), colorOutVar()));
			arcs.add(new Arc("TtoP", idTrans("R"+i+"Logic", 0), idPlace("Mem", i-1), formula(i)));//formula			
			if (i <= outputs.size()-1) {
				arcs.add(new Arc("TtoP", idTrans("R"+i+"Logic", 0), idPlace("R"+(i+1), 0), ""));
			}			
		}
	}
	
	private String formula(int displacement) { ///
		if (outputs.size() == 1) return "formula"+displacement+"(tupleIn)";
		
		String result = "(";
		for (int i = 1; i <= outputs.size(); i++) {
			if (i == displacement) result += "formula"+displacement+"(tupleIn)";
			else result += "out"+i;
			if (i <= outputs.size()-1) result += "," ; 
		}
		result += ")";
		return result;
	}

	private Transition idTrans(String name, int displacement){
		for (int i = 0; i < transitions.size(); i++) {
			if (transitions.get(i).getName().equals(name)){
				if (displacement == 0)
					return transitions.get(i);
				else displacement--;
			}			
		}
		System.out.println("Erro: Transi��o n�o encontrada.");
		return null;
	} 
	
	private Place idPlace(String name, int displacement){
		for (int i = 0; i < places.size(); i++) {
			if (places.get(i).getName().equals(name)){
				if (displacement == 0)
					return places.get(i);
				else displacement--;
			}			
		}
		System.out.println("Erro: Lugar n�o encontrado.");
		return null;
	} 

	private void writerTrans(String path, BufferedWriter buffWrite) throws IOException {
		for (int i = 0; i < transitions.size(); i++) {
			transitions.get(i).writerTrans(path, buffWrite);
		}
	}
	
	private void createTrans() {
		transitions.add(new Transition(WriterPetri.increaseId(), x0+space, y0, "ReadInputs"));
		transitions.add(new Transition(WriterPetri.increaseId(), x0, -1*space*outputs.size(), "Release"));
		
		for (int i = 1; i <= outputs.size(); i++) {
			transitions.add(new Transition(WriterPetri.increaseId(), x0+space, y0-space*i, "R"+i+"Logic"));
		}
	}

	private void writerPlaces(String path, BufferedWriter buffWrite) throws IOException {
		for (int i = 0; i < places.size(); i++) {
			places.get(i).writerPlace(path, buffWrite);
		}
	}
	
	private void createPlaces() {
		places.add(new Place(WriterPetri.increaseId(), x0, y0, "Start", "UNIT", "1`()", ""));
		places.add(new Place(WriterPetri.increaseId(), x0+space, -1*space*outputs.size(), "Done", "UNIT", "", ""));
		places.add(new Place(WriterPetri.increaseId(), x0-space, -1*space*outputs.size(), "Out", "OUTPUTS", colorOut(), ""));
		places.add(new Place(WriterPetri.increaseId(), x0+space*2, y0, "In", "INPUTS", colorIn(), "Fusion 1"));
		
		for (int i = 1; i <= outputs.size(); i++) {
			places.add(new Place(WriterPetri.increaseId(), x0+space, y0*2-space*i, "R"+i, "UNIT", "", ""));
			places.add(new Place(WriterPetri.increaseId(), x0*1.25f, y0-space*i, "In", "INPUTS", colorIn(), "Fusion 1"));
			places.add(new Place(WriterPetri.increaseId(), x0+space*2, y0-space*i, "Mem", "OUTPUTS", colorOut(), "Fusion 2"));
		}
	}
	
	private void createFusions(String path, BufferedWriter buffWrite) throws IOException {
		ArrayList<Integer> idref = new ArrayList<Integer>();
		
		for (int i = 1; i <= 2; i++) {
			for (int j = 0; j < places.size(); j++) {
				if (places.get(j).getFusionName().equals("Fusion "+i)) {
					idref.add(places.get(j).getId());
				}
			}
			FusionOutside fo = new FusionOutside("Fusion "+i, idref);
			fo.writerArc(path, buffWrite);
			idref.clear();
		}
	}

	private void writerDeclarations(String path, BufferedWriter buffWrite) throws IOException {
		Declaration decl = new Declaration(this.inputs, this.outputs, this.functions);
		decl.writerDeclarations(path, buffWrite);
	}

	private String colorIn() {
		if (inputs.size() == 1) return "false";
		
		String result = "(";
		for (int i = 0; i < inputs.size(); i++) {
			result += "false";
			if (i < inputs.size()-1) result += "," ; 
		}
		result += ")";
		return result;
	}
	
	private String colorOut() {
		if (outputs.size() == 1) return "false";
		
		String result = "(";
		for (int i = 0; i < outputs.size(); i++) {
			result += "false";
			if (i < outputs.size()-1) result += "," ; 
		}
		result += ")";
		return result;
	}
	
	private String colorOutVar() {
		if (outputs.size() == 1) return "out1";
		
		String result = "(";
		for (int i = 1; i <= outputs.size(); i++) {
			result += "out"+i;
			if (i <= outputs.size()-1) result += "," ; 
		}
		result += ")";
		return result;
	}

	/**
	 * Copia e cola um arquivo para o outro.
	 * 
	 * @param path Caminho do arquivo a ser copiado
	 * @param buffWrite Buffer do arquivo para escrever
	 * @throws IOException 
	 */	
	private void copyAndPast(String path, BufferedWriter buffWrite) throws IOException{
		BufferedReader header = new BufferedReader(new FileReader(path));
		String line = header.readLine();
		while (line != null) {
			buffWrite.append(line+"\n");						
			line = header.readLine();
		}			
		header.close();
	}
	
	public static int increaseId() {
		return WriterPetri.qntID++;
	}
	
	public static int increaseFusion() {
		return WriterPetri.fusionID++;
	}

	public static int getFusionID() {
		return fusionID;
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

	public static int getQntID() {
		return qntID;
	}

	
	
	
}