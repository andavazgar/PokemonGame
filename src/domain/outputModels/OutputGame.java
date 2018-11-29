/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.outputModels;

public class OutputGame {
	private int id;
	private int[] players;
	public OutputGame(int id, int[] players) {
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
