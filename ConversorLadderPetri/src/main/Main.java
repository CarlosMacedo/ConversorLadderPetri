package main;

import java.io.IOException;
import javax.swing.JFileChooser;


public class Main {
	public static void main(String[] args) throws IOException {
		JFileChooser fc = new JFileChooser();
        fc.showOpenDialog(null);
        String path = fc.getSelectedFile().getAbsolutePath();
		System.out.println(path);
		
		String out = getOut(path);
		System.out.println(out);
		
		ManipulatorLadder fml = new ManipulatorLadder();
		fml.ladderToPetri(path, out);
	}
	
	private static String getOut(String path) {
		int index = 0;
		for (int i = path.length()-1; i >=0 ; i--) {
			char c = path.charAt(i);			
			if (c == '\\' || c == '/'){
				index = i;
				break;
			}
		}
		
		return extractOut(path, index);
	}
	private static String extractOut(String path, int index) {
		String result = "out.cpn";
		for (int i = index; i >= 0; i--) {
			result = path.charAt(i) + result;
		}	
		return result;
	}
}
