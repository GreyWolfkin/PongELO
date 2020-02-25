/*****
 * COPYRIGHT Joshua Supelana-Mix 2/25/2020
 * This product is for private use only
 * This product may not be modified, redistributed, sold, or used for any commercial purpose
 * except by the copyright holder
 *****/
 
/*****
 * input_utils v1.5
 * Returns user input as String or
 * int, overloaded for optional 
 * arguments
 *****/

import java.util.Scanner;

public class input_utils {
	
	Scanner scan = new Scanner(System.in);
	
	// Gets input with no modifiers
	public String getInput() {
		String userIn = scan.nextLine();
		return userIn;
	}
	
	// Prints a header, then gets input
	public String getInput(String header) {
		System.out.print(header);
		String userIn = scan.nextLine();
		return userIn;
	}
	
	// Gets input, modifies based on passed int
	// Option: 1 - Capitalize First Letter; 2 - UPPER CASE; 3 - lower case
	public String getInput(int option) {
		String userIn = scan.nextLine();
		switch(option) {
			case 1:
				String first = userIn.substring(0,1);
				String afterFirst = userIn.substring(1);
				userIn = first.toUpperCase() + afterFirst.toLowerCase();
				break;
			case 2:
				userIn = userIn.toUpperCase();
				break;
			case 3:
				userIn = userIn.toLowerCase();
				break;
			default:
				break;
		}
		return userIn;
	}
	
	// Prints header, gets input, modifies based
	// on passed int
	// Option: 1 - Capitalize First Letter; 2 - UPPER CASE; 3 - lower case
	public String getInput(String header, int option) {
		System.out.print(header);
		String userIn = scan.nextLine();
		switch(option) {
			case 1:
				String first = userIn.substring(0,1);
				String afterFirst = userIn.substring(1);
				userIn = first.toUpperCase() + afterFirst.toLowerCase();
				break;
			case 2:
				userIn = userIn.toUpperCase();
				break;
			case 3:
				userIn = userIn.toLowerCase();
				break;
			default:
				break;
		}
		return userIn;
	}
	
	// Returns input as an integer
	public int getInt() {
		while(true) { // Loops until input is a valid integer
			String userIn = scan.nextLine();
			try { // catch(NumberFormatException)
				int inputInt = Integer.parseInt(userIn);
				return inputInt;
			} catch(NumberFormatException ex) { // Input is not an integer
				System.out.println("Unrecognized Input");
			}
		}
	}
	
	// Prints a header, then gets input, returns input
	// as an integer
	public int getInt(String header) {
		System.out.println(header);
		while(true) { // Loops until input is a valid integer
			String userIn = scan.nextLine();
			try { // catch(NumberFormatException)
				int inputInt = Integer.parseInt(userIn);
				return inputInt;
			} catch(NumberFormatException ex) { // Input is not an integer
				System.out.println("Unrecognized Input");
			}
		}
	}
	
	// Returns an integer between min and max
	public int getInt(int min, int max) {
		while(true) { // Loops until input is a valid integer between min and max
			String userIn = scan.nextLine();
			try { // catch(NumberFormatException)
				int inputInt = Integer.parseInt(userIn);
				if(min <= inputInt && inputInt <= max) {
					return inputInt;
				} else { // Invalid integer input
					System.out.println("Input must be between " + min + " and " + max);
				}
			} catch(NumberFormatException ex) { // Input is not an integer
				System.out.println("Unrecognized Input");
			}
		}
	}
	
	// Prints a header, then returns an integer
	// between min and max
	public int getInt(String header, int min, int max) {
		System.out.println(header);
		while(true) { // Loops until input is a valid integer between min and max
			String userIn = scan.nextLine();
			try { // catch(NumberFormatException)
				int inputInt = Integer.parseInt(userIn);
				if(min <= inputInt && inputInt <= max) {
					return inputInt;
				} else { // Invalid integer input
					System.out.println("Input must be between " + min + " and " + max);
				}
			} catch(NumberFormatException ex) { // Input is not an integer
				System.out.println("Unrecognized Input");
			}
		}
	}
}