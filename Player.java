/*****
 * COPYRIGHT Joshua Supelana-Mix 3/2/2020
 * This product is for private use only
 * This product may not be modified, redistributed, sold, or used for any commercial purpose
 * except by the copyright holder
 *****/
 
/*****
 * Player v2.1
 *****/

public class Player {
	
	private String name;
	private int score;
	private String mvp;
	private String archived;
	
	// Constructor called when adding a new player
	// to list.
	public Player(String name) {
		this.name = name;
		this.score = 1000;
		this.mvp = "false";
		this.archived = "false";
	}
	
	// Constructor called when populating list of
	// players from encScores.txt
	public Player(String name, int score, String mvp, String archived) {
		this.name = name;
		this.score = score;
		this.mvp = mvp;
		this.archived = archived;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String set) {
		this.name = set;
	}
	
	public int getScore() {
		return this.score;
	}
	public void setScore(int set) {
		this.score = set;
		// Score cannot be less than 1
		if(this.score <= 0) {
			this.score = 1;
		}
	}
	public void adjScore(int adj) {
		this.score += adj;
		// Score cannot be less than 1
		if(this.score <= 0) {
			this.score = 1;
		}
	}
	
	public String getMVP() {
		return this.mvp;
	}
	public void setMVP(String set) {
		if(set.equals("true") || set.equals("false")) {
			this.mvp = set;
		} else {
			System.out.println("ERROR SETTING MVP");
		}
	}
	
	public String getArchived() {
		return this.archived;
	}
	public void setArchived(String set) {
		if(set.equals("true") || set.equals("false")) {
			this.archived = set;
		} else {
			System.out.println("ERROR SETTING ARCHIVED");
		}
	}
}