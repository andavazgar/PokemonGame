/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.outputModels;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import domain.enums.PlayerStatus;

public class Board {
	private static PlayerStatus[] playerStatuses = PlayerStatus.values();
	private int id;
	private int[] players = new int[2];
	private int[] decks = new int[2];
	private Map<String, PlayerInGameStatus> play = new Hashtable<>();
	
	
	public Board(int id, int challenger, int challengee, int challenger_deck, int challengee_deck) {
		this.id = id;
		this.players[0] = challenger;
		this.players[1] = challengee;
		this.decks[0] = challenger_deck;
		this.decks[1] = challengee_deck;
	}
	
	public Board(int id, int challenger, int challengee, int challengerDeck, int challengeeDeck, PlayerInGameStatus challengerStatus, PlayerInGameStatus challengeeStatus) {
		this.id = id;
		this.players[0] = challenger;
		this.players[1] = challengee;
		this.decks[0] = challengerDeck;
		this.decks[1] = challengeeDeck;
		this.play.put(String.valueOf(challenger), challengerStatus);
		this.play.put(String.valueOf(challengee), challengeeStatus);
	}
	
	public static Board findBoard(int gameID) {
		GameDetails gameDetails = GameDetails.find(gameID);
		Board output = new Board(gameDetails.getId(), gameDetails.getChallenger(), gameDetails.getChallengee(), gameDetails.getChallengerDeck(), gameDetails.getChallengeeDeck());
		
		PlayerInGameStatus challengerStatus = PlayerInGameStatus.find(gameDetails.getId(), gameDetails.getChallenger());
		PlayerInGameStatus challengeeStatus = PlayerInGameStatus.find(gameDetails.getId(), gameDetails.getChallengee());
		
		challengerStatus.setStatus(playerStatuses[gameDetails.getChallengerStatus()].toString());
		challengeeStatus.setStatus(playerStatuses[gameDetails.getChallengeeStatus()].toString());
		
		output.addInGameStatusForPlayer(gameDetails.getChallenger(), challengerStatus);
		output.addInGameStatusForPlayer(gameDetails.getChallengee(), challengeeStatus);
		
		return output;
	}
	
	public static List<Board> findBoards() {
		List<GameDetails> gamesDetails = GameDetails.findAll();
		List<Board> output = new ArrayList<>();
		
		for(GameDetails gameDetails: gamesDetails) {
			Board board = new Board(gameDetails.getId(), gameDetails.getChallenger(), gameDetails.getChallengee(), gameDetails.getChallengerDeck(), gameDetails.getChallengeeDeck());
			
			PlayerInGameStatus challengerStatus = PlayerInGameStatus.find(gameDetails.getId(), gameDetails.getChallenger());
			PlayerInGameStatus challengeeStatus = PlayerInGameStatus.find(gameDetails.getId(), gameDetails.getChallengee());
			
			challengerStatus.setStatus(playerStatuses[gameDetails.getChallengerStatus()].toString());
			challengeeStatus.setStatus(playerStatuses[gameDetails.getChallengeeStatus()].toString());
			
			board.addInGameStatusForPlayer(gameDetails.getChallenger(), challengerStatus);
			board.addInGameStatusForPlayer(gameDetails.getChallengee(), challengeeStatus);
			
			output.add(board);
		}
		
		return output;
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
	public int[] getDecks() {
		return decks;
	}
	public void setDecks(int[] decks) {
		this.decks = decks;
	}
	public Map<String, PlayerInGameStatus> getPlay() {
		return play;
	}
	public void setPlay(Map<String, PlayerInGameStatus> play) {
		this.play = play;
	}
	
	public PlayerInGameStatus getInGameStatusForPlayer(int player) {
		return play.get(String.valueOf(player));
	}
	
	public void addInGameStatusForPlayer(int player, PlayerInGameStatus status) {
		play.put(String.valueOf(player), status);
	}
	
	public PlayerInGameStatus getPlayStatusForPlayer(int playerID) {
			return play.get(String.valueOf(playerID));
	}
	
	public PlayerInGameStatus getPlayStatusForOtherPlayer(int playerID) {
		String opponentID;
		
		if(players[0] == playerID) {
			opponentID = String.valueOf(players[1]);
		}
		else {
			opponentID = String.valueOf(players[0]);
		}
		
		return play.get(opponentID);
	}
}
