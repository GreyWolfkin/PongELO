/*****
 * COPYRIGHT Joshua Supelana-Mix 2/25/2020
 * This product is for private use only
 * This product may not be modified, redistributed, sold, or used for any commercial purpose
 * except by the copyright holder
 *****/
 
/*****
 * file_utils v1.0
 * Supports "save" and "load" functionality
 * by reading and writing to a Base64
 * encrypted file "encScores.txt"
 * Also writes to a raw-text file
 * "rawScores.txt"
 *****/

import java.util.ArrayList;
import java.util.Base64;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class file_utils {

	// Returns an String Array for Player Names, Scores, and MVP status
	public String[] read() {
		try { // catch(IOException)
			FileReader fr = new FileReader("encScores.txt");
			String string = "";
			int i;
			while((i=fr.read()) != -1) { // Returns -1 when text file is read all the way through
				string += Character.toString((char)i); // Turns character at location i into a String
			}
			fr.close();
			byte[] actualByte = Base64.getDecoder().decode(string); // Turns encoded string into byte array
			String actualString = new String(actualByte); // Turns byte array into string
			String[] returnString = actualString.split("\\|"); // Splits string by | delimeter
			return returnString;
		} catch(IOException ex) { // Read errors
			System.out.println("Error Reading File");
			return new String[0];
		}
	}
	
	// Records scores to be read next time
	// the program is run
	public void write(ArrayList<Player> players) {
		try { // catch(IOException)
			FileWriter fwRaw = new FileWriter("rawScores.txt"); // For raw-text scores
			FileWriter fwEnc = new FileWriter("encScores.txt"); // For encoded scores
			ArrayList<String> stringArr = new ArrayList<String>(); // Will be printed to rawScores.txt
			String decString = ""; // Will be printed to encScores.txt
			for(Player player:players) {
				stringArr.add(player.getName());
				stringArr.add(Integer.toString(player.getScore()));
				stringArr.add(player.getMVP());
				decString += player.getName();
				decString += "|"; // Used as delimiter in read()
				decString += Integer.toString(player.getScore());
				decString += "|"; // Used as delimiter in read()
				decString += player.getMVP();
				decString += "|"; // Used as delimiter in read()
			}
			String name;
			String score;
			String mvp;
			fwRaw.write("NAME\t\tSCORE\t\tMVP\n");
			for(int i = 0; i < stringArr.size(); i+=3) {
				name = stringArr.get(i);
				score = stringArr.get(i+1);
				mvp = stringArr.get(i+2);
				fwRaw.write(name + "\t\t" + score + "\t\t" + mvp + "\n");
			}
			fwRaw.close();
			String BasicBase64format = Base64.getEncoder().encodeToString(decString.getBytes()); // Encodes decString to Base 64
			fwEnc.write(BasicBase64format);
			fwEnc.close();
		} catch(IOException ex) { // Write errors
			System.out.println("Error writing to file");
		}
	}
}