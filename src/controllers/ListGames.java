/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Game;
import models.GameDetails;
import models.JSONObject;

/**
 * Servlet implementation class ListGames
 */
@WebServlet("/ListGames")
public class ListGames extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
    public ListGames() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(true).getAttribute("userID") == null) {
			request.setAttribute("message", "You need to be logged in to view the games.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		List<GameDetails> gamesDetails = GameDetails.findAll();
		List<Game> games = new ArrayList<>();
		
		for(GameDetails details: gamesDetails) {
			games.add(new Game(details.getId(), new int[] {details.getChallenger(), details.getChallengee()}));
		}
		
		JSONObject jsonObj = new JSONObject();
		
		jsonObj.put("games", games);
		
		request.setAttribute("jsonObj", jsonObj.getJSON());
		request.getRequestDispatcher("WEB-INF/jsp/outputJSON.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
