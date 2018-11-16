/*
 * author: Andres Vazquez (#40007182)
 * SOEN 387
 */

package controllers;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.enums.ChallengeStatus;
import rdg.ChallengeRDG;

/**
 * Servlet implementation class RefuseChallenge
 */
@WebServlet("/RefuseChallenge")
public class RefuseChallenge extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	
    public RefuseChallenge() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getSession(true).getAttribute("userID") == null) {
			request.setAttribute("message", "You need to be logged in to refuse a challenge.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		int userID = (int) request.getSession(true).getAttribute("userID");
		int challengeID = Integer.parseInt(request.getParameter("challenge"));
		ChallengeRDG challenge = ChallengeRDG.find(challengeID);
		
		if(challenge == null) {
			request.setAttribute("message", "The challenge provided was not found.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		if(challenge.getChallenger() != userID && challenge.getChallengee() != userID) {
			request.setAttribute("message", "You are not part of the challenge. You cannot refuse it.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		if(challenge.getChallengeStatus() != ChallengeStatus.open) {
			request.setAttribute("message", "The challenge is not open. Therefore, it cannot be refused.");
			request.getRequestDispatcher("WEB-INF/jsp/fail.jsp").forward(request, response);
			return;
		}
		
		
		if(challenge.getChallenger() == userID) {
			// Challenger: Withdrawal
			challenge.setStatus(ChallengeStatus.withdrawn);
		}
		else {
			// Challengee: Refuse
			challenge.setStatus(ChallengeStatus.refused);
		}
		
		challenge.update();
		
		request.setAttribute("message", "Challenge was successfully refused / withdrawn.");
		request.getRequestDispatcher("WEB-INF/jsp/success.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
