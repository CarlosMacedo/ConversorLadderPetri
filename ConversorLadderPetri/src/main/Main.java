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
package main;

import java.io.IOException;
import javax.swing.JFileChooser;

/**
 * Converts a Ladder file, generated in LDmicro,
 * in a .cpn file for the CPNTools 4.0.1.
 * 
 * @author Carlos Macedo
 */

public class Main {
	
	/**
	 * Select file, convert and write.
	 * 
	 * @param args 
	 * @throws IOException Error reading file.
	 */
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
	
	/**
	 * Finds from which directory the .ld file was selected.
	 * That is, it just removes the file name from the end of the string.
	 *  
	 * @param path Absolute path of the .ld file. Including the filename.
	 * @return Returns the absolute path, now without the name of the .ld file.
	 */
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
	
	/**
	 * Returns the absolute path, now with the name of the .cpn file.
	 * 
	 * @param path Path obtained by the getOut function.
	 * @param index Number of characters in the path.
	 * @return Returns the absolute path, now with the name of the .cpn file.
	 */
	private static String extractOut(String path, int index) {
		String result = "out.cpn";
		for (int i = index; i >= 0; i--) {
			result = path.charAt(i) + result;
		}	
		return result;
	}
}
