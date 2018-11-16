<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Commands</title>
<style>
	.subheading {
		margin-left: 40px;
		margin-bottom: 40px;
	}
</style>
</head>
<body>
	<h1>List of Commands</h1>
		
		<div class="subheading">
			<h2>Users / Players</h2>
				<a href="ListPlayers">ListPlayers</a>
			
			<h2>Challenges</h2>
				<a href="ListChallenges">ListChallenges</a>
				
				<div class="subheading">
					<h3>ChallengePlayer</h3>
					<form action="ChallengePlayer" method="post">
						Challengee: <input type="text" name="challengee">
						<input type="submit">
					</form>
					
					<h3>AcceptChallenge</h3>
					<form action="AcceptChallenge" method="post">
						Challengee ID: <input type="text" name="challenge">
						<input type="submit">
					</form>
					
					<h3>RefuseChallenge</h3>
					<form action="RefuseChallenge" method="post">
						Challengee ID: <input type="text" name="challenge">
						<input type="submit">
					</form>
				</div>
			
			<h2>Decks</h2>
				<div class="subheading">
					<a href="ViewDeck">ViewDeck</a>
					
					<h3>UploadDeck</h3>
					<form action="UploadDeck" method="post">
						Deck:<br>
						<textarea name="deck" rows="40">
e "Fire"
e "Fire"
p "Charizard"
e "Fire"
e "Fire"
e "Fire"
p "Charizard"
p "Meowth"
e "Fire"
t "Misty"
t "Misty"
e "Fire"
e "Fire"
e "Fire"
p "Charizard"
e "Fire"
e "Fire"
e "Fire"
p "Charizard"
p "Meowth"
e "Fire"
t "Misty"
t "Misty"
e "Fire"
e "Fire"
e "Fire"
p "Charizard"
e "Fire"
e "Fire"
e "Fire"
p "Charizard"
p "Meowth"
e "Fire"
t "Misty"
t "Misty"
e "Fire"
e "Fire"
e "Fire"
p "Charizard"
e "Fire"
						</textarea>
						<input type="submit">
					</form>
				</div>
				
				<h2>Games</h2>
					<div class="subheading">
						<a href="ListGames">ListGames</a>
						
						<h3>ViewBoard</h3>
						<form action="ViewBoard" method="post">
							Game ID: <input type="text" name="game">
							<input type="submit">
						</form>
						
						<h3>ViewHand</h3>
						<form action="ViewHand" method="post">
							Game ID: <input type="text" name="game">
							<input type="submit">
						</form>
						
						<h3>DrawCard</h3>
						<form action="DrawCard" method="post">
							Game ID: <input type="text" name="game">
							<input type="submit">
						</form>
						
						<h3>PlayPokemonToBench</h3>
						<form action="PlayPokemonToBench" method="post">
							Game ID: <input type="text" name="game">
							Card ID: <input type="text" name="card">
							<input type="submit">
						</form>
					</div>
		</div>
		
		<h2>Logout</h2>
			<a href="Logout">Logout</a>	
</body>
</html>