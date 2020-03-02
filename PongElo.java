/*****
 * COPYRIGHT Joshua Supelana-Mix 3/2/2020
 * This product is for private use only
 * This product may not be modified, redistributed, sold, or used for any commercial purpose
 * except by the copyright holder
 *****/
 
 /*****
  * PongElo v1.3
  * Calculates and records "ELO" or skill-level
  * for Ping Pong players.
  * Reads and writes to a Base64 encrypted
  * file for "save" and "load" purposes.
  * Ability to add, remove, and edit players, 
  * display current scores, declare an "MVP",
  * and award bonus points for Streak-wins.
  * Rules and scoring modeled after our
  * "house rules".
  * 
  * Points and Scoring
  * Each player starts with 1000 points. 
  * A win is worth a base ELO gain of 100 points.
  * Bonus points are awarded based on the point
  * spread.
  * Gains are modified in direct proportion to
  * the ELO difference between the players.
  * MVP is given once per week to the player with
  * the highest total ELO.
  * A win against the current MVP is worth a 150%
  * bonus.
  * ELO losses are exactly 50% of the opponent's
  * gain.
  * A bonus 25 points is given to the MVP per
  * game played, regardless of win or loss.
  * A bonus of 75 points can be manually added
  * for "Streak-wins" of 3 games in a row.
  *****/

import java.util.ArrayList;
import java.util.List;

public class PongElo {
	
	// Sends player list to file_utils to
	// write to encScores.txt
	static void saveAndQuit(file_utils file, ArrayList<Player> players) {
		file.write(players);
		System.exit(0);
	}
	
	public static void main(String[] args) {
		input_utils in = new input_utils(); // input_utils v1.5
		file_utils file = new file_utils(); // file_utils v1.1
		pong_utils pong = new pong_utils(in); // pong_utils v1.2
		calc_utils calc = new calc_utils(in, pong); // calc_utils v1.2
		
		// Generates Player list from encScores.txt, if it exists.
		//	Returns "Error Reading File" on first run.
		ArrayList<Player> players = new ArrayList<Player>();
		String[] stringArr = file.read();
		String playerName;
		int playerScore;
		String mvp;
		String archived;
		for(int i = 0; i < stringArr.length; i+=4) {
			playerName = stringArr[i];
			playerScore = Integer.parseInt(stringArr[i+1]);
			mvp = stringArr[i+2];
			archived = stringArr[i+3];
			Player player = new Player(playerName, playerScore, mvp, archived);
			players.add(player);
		}
		
		while(true) { // Primary loop
			calc.sort(players); // Sorts player list by total score
			String mainOptions = "";
			int numOpt = 0;
			if(players.size() == 0) { // First run of program, no players
				mainOptions += "1 - Add Player\n";
				mainOptions += "2 - Quit Without Saving\n";
				numOpt = 2;
			} else if(players.size() == 1) { // One player listed, cannot record game
				mainOptions += "1 - Add Player\n";
				mainOptions += "2 - Display Players\n";
				mainOptions += "3 - Edit Player\n";
				mainOptions += "4 - Archive Player\n";
				mainOptions += "5 - Remove Player\n";
				mainOptions += "6 - Save and Quit\n";
				mainOptions += "7 - Quit Without Saving\n";
				numOpt = 7;
			} else if(players.size() >= 2) { // More than one player, all options available
				mainOptions += "1 - Record Game\n";
				mainOptions += "2 - Crown MVP\n";
				mainOptions += "3 - Add Streak Bonus\n";
				mainOptions += "4 - Add Player\n";
				mainOptions += "5 - Display Players\n";
				mainOptions += "6 - Edit Player\n";
				mainOptions += "7 - Archive Player\n";
				mainOptions += "8 - Remove Player\n";
				mainOptions += "9 - Save and Quit\n";
				mainOptions += "10 - Quit Without Saving\n";
				numOpt = 10;
			}
			System.out.println(mainOptions);
			int userIn = in.getInt(1, numOpt);
			switch(userIn) {
				case 1:
					if(players.size() < 2) {
						pong.addPlayer(players);
					} else {
						calc.recordGame(players);
					}
					break;
				case 2:
					if(players.size() == 0) {
						System.exit(0);
					} else if(players.size() == 1) {
						pong.displayPlayers(players);
					} else if(players.size() >= 2) {
						calc.crownMVP(players);
					}
					break;
				case 3:
					if(players.size() == 1) {
						pong.editPlayer(players);
					} else if(players.size() >= 2) {
						pong.streakBonus(players);
					}
					break;
				case 4:
					if(players.size() == 1) {
						pong.archivePlayer(players);
					} else if(players.size() >= 2) {
						pong.addPlayer(players);
					}
					break;
				case 5:
					if(players.size() == 1) {
						pong.removePlayer(players);
					} else if(players.size() >= 2) {
						pong.displayPlayers(players);
					}
					break;
				case 6:
					if(players.size() == 1) {
						saveAndQuit(file, players);
					} else if(players.size() >= 2) {
						pong.editPlayer(players);
					}
					break;
				case 7:
					if(players.size() == 1) {
						System.exit(0);
					} else if(players.size() >= 2) {
						pong.archivePlayer(players);
					}
					break;
				case 8:
					pong.removePlayer(players);
					break;
				case 9:
					saveAndQuit(file, players);
					break;
				case 10:
					System.exit(0);
				default:
					System.out.println("Unrecognized Input");
					break;
			}
		}
	}
}