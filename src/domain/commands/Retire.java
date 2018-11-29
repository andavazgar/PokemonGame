/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package domain.commands;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Board;
import models.enums.GameStatus;
import models.enums.PlayerStatus;
import rdg.GameRDG;

/**
 * Servlet implementation class Retire
 */
@WebServlet("/Retire")
public class Retire extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
    public Retire() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(true).getAttribute("userID") == null) {
			request.setAttribute("message", "You need to be logged in to view the games.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		String gameIDStr = request.getParameter("game");
		
		if(gameIDStr == null) {
			request.setAttribute("message", "Please provide a game ID.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		int userID = (int) request.getSession(true).getAttribute("userID");
		int gameID = Integer.parseInt(gameIDStr);
		Board board = Board.findBoard(gameID);
		int[] players = board.getPlayers();
		
		if(userID != players[0] && userID != players[1]) {
			request.setAttribute("message", "You can only retire from a game you are part of.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		GameTDG game = GameTDG.find(gameID);
		
		if(board.getPlayers()[0] == userID) {
			// The challenger retired
			game.setChallengerStatus(PlayerStatus.retired.ordinal());
		}
		else {
			// The challengee retired
			game.setChallengeeStatus(PlayerStatus.retired.ordinal());
		}
		
		game.setGameStatus(GameStatus.finished.ordinal());
		game.update();
		
		request.setAttribute("message", "You retired from the game. This game is now finished.");
		request.getRequestDispatcher("WEB-INF/jsp/success.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
