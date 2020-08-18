package org.ossg.store.model.serializer.impl;

import javax.enterprise.context.ApplicationScoped;

import org.bson.Document;
import org.ossg.store.model.DayOfEvent;
import org.ossg.store.model.Round;
import org.ossg.store.model.Score;
import org.ossg.store.model.serializer.RoundSerializer;

@ApplicationScoped
public class DocumentRoundSerializer implements RoundSerializer<Document>{

	@Override
	public Document serialize(Round round) {
		if (round == null
            || (round.getId() == null) 
            || (round.getPlayerId() == null) 
            || (round.getCourseId() == null) 
            || (round.getDayOfEvent() == null) 
            || (round.getPhcp() == 0) 
        ) {
            throw new IllegalArgumentException("Object contains invalid data");
        }

        Document document = new Document("id", round.getId())
                    .append("playerId", round.getPlayerId())
                    .append("playerName", round.getPlayerName())
                    .append("courseId", round.getCourseId())
                    .append("courseName", round.getCourseName())
                    .append("dayOfEvent", round.getDayOfEvent())
                    .append("phcp", round.getPhcp());

        if(round.getTournamentId() != null){
            document.append("tournamentId", round.getTournamentId())
                    .append("tournamentName", round.getTournamentName());
        }

        Document scores = new Document();
        for(int i = 1; i<= 18; i++){
            Score score = round.getScore(i);
            if(score != null){
                scores.append("" + i, score);
            }
        }
        document.append("scores", scores);
        return document;
	}

	@Override
	public Round deserialize(Document document) {
        String id = document.getString("id");
        String playerId = document.getString("playerId");
        String playerName = document.getString("playerName");
        String courseId = document.getString("courseId");
        String courseName = document.getString("courseName");
        int phcp = document.getInteger("phcp");
        DayOfEvent dayOfEvent = (DayOfEvent) document.get("dayOfEvent");

        if (document == null
				|| (id == null)
				|| (playerId == null)
				|| (courseId == null)
				|| (dayOfEvent == null)
			) {
			throw new IllegalArgumentException("Unable to deserialize the Round object. Document contains invalid data");
        }

        Round round = new Round()
                    .setId(id)
                    .setPlayerId(playerId)
                    .setPlayerName(playerName)
                    .setCourseId(courseId)
                    .setCourseName(courseName)
                    .setPhcp(phcp)
                    .setDayOfEvent(dayOfEvent);
        if(document.containsKey("tournamentId")){
            round.setTournamentId(document.getString("tournamentId")).setTournamentName("tournamentName");
        }
        if(document.containsKey("scores")){
            Document scores = (Document) document.get("scores");
            for(int i = 1; i<= 18; i++){
                Score score = (Score) scores.get("" + i);
                if(score != null){
                    round.setScore(i, score.getHcp(), score.getPar(), score.getStrokes());
                }
            }    
        }

        return round;
	}
    
}