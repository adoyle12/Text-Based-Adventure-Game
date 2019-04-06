<!DOCTYPE html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
	<head>
		<title>Game Play</title>
		<meta name ="viewport" content = "width=device-width, initial-scale=1">
		<style type="text/css">
		.error {
			color: red;
		}
		
		body {
			background-color: black;
		}
		
		* {
			box-sizing: border-box;
		}
		
		.container {
			position: relative;
			margin: 0 auto;
		}
		
		.container img {vertical-align: center;}
		
		.container .content {
			position: absolute;
			bottom: 0;
			background: rgb(0, 0, 0);
			background: rgba(0, 0, 0, 0.5);
			color: #f1f1f1;
			width: 100%;
			padding: 60px;
		}	
		
		p {
			text-indent: 50px;
		}
		
		.container p.gamename {
			position: absolute;
			top: 0;
			background: rgb(0, 0, 0);
			background: rgba(0, 0, 0, 0.5);
			color: #f1f1f1;
			width: 100%;
			text-align: center;
			font-size: 80px;
		}
		
		td.label {
  			text-align: left;
		}
		
		</style>
	</head>

	<body>
		<%
			if(session.getAttribute("username") == null){
				response.sendRedirect("login.jsp");
			}
		%>
		<div class="container">
		<p class="gamename">The Escape of the Minotaur</p>
		<img src="labyrinth.jpg" alt="bg" style="width:100%; height: auto;">
			<div class="content">
				<c:if test="${! empty errorMessage}">
				<div class="error">${errorMessage}</div>
				</c:if>
		
		<p>A subtle shift in the current of air flowing through the room stirs your consciousness awake.
		Another day has passed, enclosed at the center of the labyrinth, which, for centuries, has been the place you call home.
		Slightly groggy, you stretch and yawn, causing a low rumbling growl to echo through the corridors that stretch beyond this main room.
		As you come to your senses, you glance around the familiar surroundings, assessing that everything is in its proper place.</p>
		
		<p>The wall behind you is painted with a mural of your father, King Minos, as he orders the craftsman,
		Daedalus, and the craftsman's son, Icarus, to build the labyrinth you currently reside in, an ever-present
		reminder of the one responsible for your enclosure. Home and a prison, merely because your father found you
		monstrous when it was your father who angered the Gods in the first place! It was because of him that the
		Gods made his wife, your mother, desire the majestic bull that sired you. You snort angrily, hot air bellowing
		out from your nostrils as you continue to scan the room. Along the walls there are several shelves of literature
		and ancient knowledge. Various treasures, miscellaneous garments, and other offerings lay strewn about, a
		rather huge collection built from thousands of years of Athenians trying to appease you. A jewel embellished sword
		catches your eye, it has the name "Asterion" intricately engraved into the blade. Memory stirs, "Ah, yes, that was
		my name, wasn't it?" you reminisce with a self-deprecating chuckle.</p>
		
		<p>Another glance around and you notice the amount of treasures that have been plundered from your hoard as
		would-be heroes have taken much over time with their attempts at glory and honor. Several items of power are
		missing, items you greatly desire returned. Suddenly, as thoughts of past heroes' trespassing and thievery
		fill your mind, the desire to leave the labyrinth, take back what is rightfully yours, and enact revenge on
		those that tormented you becomes too strong to ignore. A load roar escapes your throat, rattling the huge
		wooden door at the front of the room. How will you manage to escape the labyrinth? What do you do first?</p>
		
				<form action="${pageContext.servletContext.contextPath}/gameplay" method="post">
					<table>
					<tr>
						<td class="label"><c:out value="${gameplay.story }"/></td>
						<td class="label">Input:</td>
						<td><input type="text" name="input" size="12" value="${gameplay.input}" /></td>
					</tr>
					</table>
				</form>
			</div>
		</div>
	</body>
</html>