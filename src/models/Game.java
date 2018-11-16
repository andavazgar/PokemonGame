/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package models;

public class Game {
	private int id;
	private int[] players;
	public Game(int id, int[] players) {
		super();
		this.id = id;
		this.players = players;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int[] getPlayers() {
		return players;
	}
	public void setPlayers(int[] players) {
		this.players = players;
	}
	
	
}
