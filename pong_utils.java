/*****
 * COPYRIGHT Joshua Supelana-Mix 2/25/2020
 * This product is for private use only
 * This product may not be modified, redistributed, sold, or used for any commercial purpose
 * except by the copyright holder
 *****/
 
 /*****
  * pong_utils v1.0
  * Does most of the "heavy lifting" for
  * PongElo primary loop.
  *****/

import java.util.ArrayList;
import java.util.List;

public class pong_utils {
	
	input_utils in; // input_utils v1.5
	
	public pong_utils(input_utils in) {
		this.in = in;
	}
	
	// Returns the index of a player within list
	// by player name.
	public int getIndex(ArrayList<Player> players, String name) {
		for(int i = 0; i < players.size(); i++) {
			if(name.equals(players.get(i).getName())) {
				return i; // Match found at index i
			}
		}
		return -1; // No match found
	}
	
	// Adds player to list, prevents duplicating a
	// player record.
	public void addPlayer(ArrayList<Player> players) {
		String userIn = in.getInput("Add Player?\nYES\nNO\n", 2);
		if(userIn.equals("YES")) {
			String playerName = in.getInput("Player Name: ", 1);
			for(Player player:players) {
				if(playerName.equals(player.getName())) { // Input matches name already in system
					System.out.println("A player named " + player.getName() + " already exists!");
					return;
				}
			}
			Player player = new Player(playerName);
			players.add(player);
		}
	}
	
	// Prints players and scores to console
	public void displayPlayers(ArrayList<Player> players) {
		System.out.println("NAME\t\tSCORE\t\tMVP");
		for(Player player:players) {
			System.out.println(player.getName() + "\t\t" + player.getScore() + "\t\t" + player.getMVP());
		}
		System.out.println();
	}
	
	// Edit player name or score
	public void editPlayer(ArrayList<Player> players) {
		System.out.println("Edit which player?");
		for(Player player:players) {
			System.out.println(player.getName() + "\t\t" + player.getScore());
		}
		String userIn = in.getInput(1);
		int index = getIndex(players, userIn);
		Player player;
		if(index != -1) { // Input matches a name
			player = players.get(index);
		} else { // Input does not match name
			System.out.println("Unrecognized Input");
			return;
		}
		System.out.println();
		userIn = in.getInput("Edit what?\nNAME\nSCORE\n", 2);
		switch(userIn) {
			case "NAME":
				userIn = in.getInput("New Name: ", 1);
				player.setName(userIn);
				break;
			case "SCORE":
				int inputInt = in.getInt("New Score: ");
				player.setScore(inputInt);
				break;
			default:
				System.out.println("Unrecognized Input");
				break;
		}
	}
	
	// Removes a player from list
	public void removePlayer(ArrayList<Player> players) {
		String userIn = in.getInput("Are you sure you wish to remove a player?\nThis will remove all record of the player, including their ELO and MVP status, from the database.\nYES\nNO\n", 2);
		if(userIn.equals("YES")) {
			System.out.println();
			System.out.println("Remove which player?");
			for(Player player:players) {
				System.out.println(player.getName());
			}
			System.out.println();
			userIn = in.getInput(1);
			int index = getIndex(players, userIn);
			Player player;
			if(index != -1) { // Input matches a name
				player = players.get(index);
			} else { //  Input does not match name
				System.out.println("Unrecognized Input");
				return;
			}
			userIn = in.getInput("Really remove player " + player.getName() + "?\nYES\nNO\n", 2);
			if(userIn.equals("YES")) {
				players.remove(index);
			} // else return
		}
	}
	
	// Function called to add 75 points for each streak
	public void streakBonus(ArrayList<Player> players) {
		System.out.println("Who gets the streak bonus?");
		for(Player player:players) {
			System.out.println(player.getName());
		}
		System.out.println("Return\n");
		String userIn = in.getInput(1);
		for(Player player:players) {
			if(userIn.equals(player.getName())) {
				player.adjScore(75);
				System.out.println(player.getName() + " gets a bonus 75 points!");
				return;
			}
		}
		return;
	}
}