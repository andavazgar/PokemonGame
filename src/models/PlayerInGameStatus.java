/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.SerializedName;

import models.enums.CardStatus;
import rdg.DeckRDG;
import services.DatabaseManager;

public class PlayerInGameStatus {
	private String status;
	private transient List<Integer> hand;
	
	@SerializedName("handsize")
	private int handSize;
	
	@SerializedName("decksize")
	private int deckSize;
	
	@SerializedName("discardsize")
	private int discardSize;
	private List<Integer> bench;
	
	public PlayerInGameStatus(List<Integer> hand, int deckSize, int discardSize, List<Integer> bench) {
		this.hand = hand;
		this.handSize = hand.size();
		this.deckSize = deckSize;
		this.discardSize = discardSize;
		this.bench = bench;
	}
	
	public PlayerInGameStatus(String status, List<Integer> hand, int deckSize, int discardSize, List<Integer> bench) {
		this.status = status;
		this.hand = hand;
		this.handSize = hand.size();
		this.deckSize = deckSize;
		this.discardSize = discardSize;
		this.bench = bench;
	}
	
	public static PlayerInGameStatus find(int gameID, int playerID) {
		String query = "SELECT card_id, card_status FROM PlayingCards WHERE game_id = ? AND player_id = ?;";
		Connection conn = DatabaseManager.getConnection();
		PlayerInGameStatus output = null;
		List<Integer> hand = new ArrayList<>();
		int deckSize = DeckTDG.getNumberOfCardsNeeded();
		int discardSize = 0;
		List<Integer> bench = new ArrayList<>();
		
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, gameID);
			ps.setInt(2, playerID);
			
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				int cardID = rs.getInt("card_id");
				int cardStatus = rs.getInt("card_status");
				
				if(cardStatus == CardStatus.hand.ordinal()) {
					hand.add(cardID);
				}
				else if(cardStatus == CardStatus.bench.ordinal()) {
					bench.add(cardID);
				}
				else if(cardStatus == CardStatus.discarded.ordinal()) {
					discardSize++;
				}
            }
			
			deckSize = deckSize - hand.size() - discardSize - bench.size();
			output = new PlayerInGameStatus(hand, deckSize, discardSize, bench);
			
			ps.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return output;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public int getHandSize() {
		return handSize;
	}

	public void setHandSize(int handSize) {
		this.handSize = handSize;
	}

	public List<Integer> getHand() {
		return hand;
	}

	public void setHand(List<Integer> hand) {
		this.hand = hand;
	}

	public int getDeckSize() {
		return deckSize;
	}

	public void setDeckSize(int deckSize) {
		this.deckSize = deckSize;
	}

	public int getDiscardSize() {
		return discardSize;
	}

	public void setDiscardSize(int discardSize) {
		this.discardSize = discardSize;
	}

	public List<Integer> getBench() {
		return bench;
	}

	public void setBench(List<Integer> bench) {
		this.bench = bench;
	}
}
