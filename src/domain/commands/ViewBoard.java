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
import models.JSONObject;

/**
 * Servlet implementation class ViewBoard
 */
@WebServlet("/ViewBoard")
public class ViewBoard extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public ViewBoard() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(true).getAttribute("userID") == null) {
			request.setAttribute("message", "You need to be logged in to view the games.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		int userID = (int) request.getSession(true).getAttribute("userID");
		String gameIDStr = request.getParameter("game");
		
		if(gameIDStr == null) {
			request.setAttribute("message", "Please provide a game ID.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		int gameID = Integer.parseInt(gameIDStr);
		Board board = Board.findBoard(gameID);
		
		int[] players = board.getPlayers();
		
		if(userID != players[0] && userID != players[1]) {
			request.setAttribute("message", "You can only view boards from games you are part of.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("board", board);
		
		request.setAttribute("jsonObj", jsonObj.getJSON());
		request.getRequestDispatcher("WEB-INF/jsp/outputJSON.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
