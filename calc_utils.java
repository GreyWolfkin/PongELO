/*****
 * COPYRIGHT Joshua Supelana-Mix 3/2/2020
 * This product is for private use only
 * This product may not be modified, redistributed, sold, or used for any commercial purpose
 * except by the copyright holder
 *****/
 
/*****
 * calc_utils v1.2
 * Performs point calculations for
 * games recorded in main loop
 *****/

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.Math;

public class calc_utils {
	
	input_utils in;
	pong_utils pong;
	
	public calc_utils(input_utils in, pong_utils pong) {
		this.in = in;
		this.pong = pong;
	}
	
	// Sorts list of players by ELO score
	public void sort(ArrayList<Player> players) {
		ArrayList<Player> tempPlayerList = new ArrayList<Player>();
		// Constructs and iterates upon Iterator object
		for(Iterator<Player> iterator = players.iterator(); iterator.hasNext();) {
			Player player = iterator.next(); // Assigns object to iterator.next
			if(player.getMVP().equals("true")) { // Places MVP at top of list when being sorted
				tempPlayerList.add(player);
				iterator.remove();
			}
		}		
		boolean placed; // Used to determine if player was placed inside of list or at the end
		for(Iterator<Player> iterator = players.iterator(); iterator.hasNext();) {
			placed = false;
			Player player = iterator.next();
			for(int i = 0; i < tempPlayerList.size(); i++) {
				if(player.getScore() > tempPlayerList.get(i).getScore()) { // If player's score is greater than the score of the player in tempPlayerList being evaluated
					tempPlayerList.add(i, player); // Add player to tempPlayerList at that index, push all other elements to the right
					iterator.remove();
					placed = true; // Player has been placed
					break;
				}
			}
			if(!placed) { // If player has not been placed, then their score is lower than all other players in tempPlayerList
				tempPlayerList.add(player); // Place player at the end of the list.
				iterator.remove();
			}
		}
		// Assign all players back to ArrayList<Player> players.
		for(Player player:tempPlayerList) {
			players.add(player);
		}
	}
	
	// Takes players, winner, and point spread
	// as input, passes to calcPoints
	public void recordGame(ArrayList<Player> players) {
		System.out.println("Players:");
		for(Player player:players) {
			if(player.getArchived().equals("false")) {
				System.out.println(player.getName());
			} // else do not print
		}
		System.out.println();
		Player winner;
		Player loser;
		String winnerIn = in.getInput("Winner: ", 1);
		int winnerIndex = pong.getIndex(players, winnerIn);
		if(winnerIndex != -1) {
			winner = players.get(winnerIndex);
			if(winner.getArchived().equals("true")) {
				System.out.println("Player is archived");
				return;
			}
		} else {
			System.out.println("Unrecognized Input");
			return;
		}
		String loserIn = in.getInput("Loser: ", 1);
		if(loserIn.equals(winner.getName())) {
			System.out.println("Unrecognized Input");
			return;
		}
		int loserIndex = pong.getIndex(players, loserIn);
		if(loserIndex != -1) {
			loser = players.get(loserIndex);
			if(loser.getArchived().equals("true")) {
				System.out.println("Player is archived");
				return;
			}
		} else {
			System.out.println("Unrecognized Input");
			return;
		}
		int loserScore = in.getInt(loser.getName() + " score: ", 0, 10);
		int pointSpread = 10 - loserScore;
		calcPoints(winner, loser, pointSpread); // Calculates and awards points
		System.out.println();
	}
	
	// Calculates and awards points
	private void calcPoints(Player winner, Player loser, int pointSpread) {
		float modifier = (float) loser.getScore() / winner.getScore();
		// Maximum modifier 10x
		if(modifier > 10) {
			modifier = 10;
		}
		// Bonus of 0-100 points
		int bonus = 10 * pointSpread;
		double rawGain = (100 + bonus) * modifier;
		// Bonus 1.5x modifier for beating the MVP
		if(loser.getMVP().equals("true")) {
			rawGain = rawGain * 1.5;
		}
		// Loss is 50% opponent's gain
		double rawLoss = rawGain / 2;
		// Round gain and loss to next highest int
		int realGain = (int)Math.ceil(rawGain);
		int realLoss = (int)Math.ceil(rawLoss);
		winner.adjScore(realGain); // Adds realGain to winner's ELO
		loser.adjScore(-1 * realLoss); // Subtracts realLoss from loser's ELO
		System.out.println(winner.getName() + " earns " + realGain + " points!");
		System.out.println(loser.getName() + " loses " + realLoss + " points!");
		if(winner.getMVP().equals("true")) {
			winner.adjScore(25);
			System.out.println(winner.getName() + " earns 25 bonus points for being MVP!");
		} else if(loser.getMVP().equals("true")) {
			loser.adjScore(25);
			System.out.println(loser.getName() + " earns 25 bonus points for being MVP!");
		}
		System.out.println();
	}
	
	/*****
	 * Awards MVP status to whoever has the highest
	 * ELO at the time the function is run.
	 * Since player list is sorted by ELO score
	 * at the beginning of every main loop, when
	 * crownMVP is called, MVP can be awarded to
	 * whoever is at the top of the list.
	 * Uses String "true" and "false" instead of
	 * boolean for ease of reading and writing
	 * "save" files, as well as printing scores.
	 *****/
	public void crownMVP(ArrayList<Player> players) {
		// Sets MVP for all players to false
		for(Player player:players) {
			player.setMVP("false");
		}
		// Sets MVP for top player to true
		players.get(0).setMVP("true");
		System.out.println(players.get(0).getName() + " is MVP!");
		System.out.println();
	}
}